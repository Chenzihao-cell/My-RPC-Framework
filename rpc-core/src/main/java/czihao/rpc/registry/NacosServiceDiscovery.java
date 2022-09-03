package czihao.rpc.registry;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import czihao.rpc.enumeration.RpcError;
import czihao.rpc.exception.RpcException;
import czihao.rpc.loadbalancer.LoadBalancer;
import czihao.rpc.loadbalancer.RandomLoadBalancer;
import czihao.rpc.util.NacosUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 服务发现
 * 供服务使用侧（客户端）使用
 */
public class NacosServiceDiscovery implements ServiceDiscovery {

    private static final Logger logger = LoggerFactory.getLogger(NacosServiceDiscovery.class);

    private final LoadBalancer loadBalancer;

    public NacosServiceDiscovery(LoadBalancer loadBalancer) {
        if (loadBalancer == null) this.loadBalancer = new RandomLoadBalancer();
        else this.loadBalancer = loadBalancer;
    }

    /**
     * 根据服务名称查找该服务所在的服务端（即服务提供侧）地址（即IP地址+端口号）
     *
     * @param serviceName 服务名称
     * @return 服务实体所在的地址
     */
    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<Instance> instances = NacosUtil.getAllInstance(serviceName);
            if (instances.size() == 0) {
                logger.error("找不到对应的服务: " + serviceName);
                throw new RpcException(RpcError.SERVICE_NOT_FOUND);
            }
            Instance instance = loadBalancer.select(instances);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            logger.error("获取服务时有错误发生:", e);
        }
        return null;
    }

}
