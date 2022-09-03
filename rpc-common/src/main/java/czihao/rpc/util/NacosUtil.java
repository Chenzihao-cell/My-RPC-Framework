package czihao.rpc.util;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import czihao.rpc.enumeration.RpcError;
import czihao.rpc.exception.RpcException;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 管理Nacos连接的工具类
 */

/*
 * Nacos-分布式注册中心原理介绍
 *
 * 参考文章：
 * https://blog.csdn.net/m0_59513162/article/details/126324137#:~:text=Nacos%20%E6%98%AF%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4%E7%9A%84%E6%96%B0%E5%BC%80%E6%BA%90%E9%A1%B9%E7%9B%AE%EF%BC%8C%E5%85%B6%E6%A0%B8%E5%BF%83%E5%AE%9A%E4%BD%8D%E6%98%AF%20%E2%80%9C%E4%B8%80%E4%B8%AA%E6%9B%B4%E6%98%93%E4%BA%8E%E5%B8%AE%E5%8A%A9%E6%9E%84%E5%BB%BA%E4%BA%91%E5%8E%9F%E7%94%9F%E5%BA%94%E7%94%A8%E7%9A%84%E9%9B%86%E6%B3%A8%E5%86%8C%E4%B8%AD%E5%BF%83%E4%B8%8E%E9%85%8D%E7%BD%AE%E4%B8%AD%E5%BF%83%E4%BA%8E%E4%B8%80%E4%BD%93%E7%9A%84%E7%AE%A1%E7%90%86%E5%B9%B3%E5%8F%B0%E2%80%9D%E3%80%82%20%E5%89%8D%E9%9D%A2%E5%9B%9B%E4%B8%AA%E5%AD%97%E6%AF%8D%E5%88%86%E5%88%AB%E8%A1%A8%E7%A4%BA%20Naming,%E5%92%8C%20Configuration%20%E7%9A%84%E5%89%8D%E4%B8%A4%E4%B8%AA%E5%AD%97%E6%AF%8D%EF%BC%8C%20%E6%9C%80%E5%90%8E%E4%B8%80%E4%B8%AAs%20%E4%B8%BA%20Service%E3%80%82
 * https://blog.csdn.net/a745233700/article/details/122915663
 * https://zhuanlan.zhihu.com/p/248141877
 * */
public class NacosUtil {

    private static final Logger logger = LoggerFactory.getLogger(NacosUtil.class);

    private static final NamingService namingService;
    private static final Set<String> serviceNames = new HashSet<>();
    private static InetSocketAddress address;

    private static final String SERVER_ADDR = "192.168.117.1:8848";

    static {
        namingService = getNacosNamingService();
    }

    public static NamingService getNacosNamingService() {
        try {
            return NamingFactory.createNamingService(SERVER_ADDR);
        } catch (NacosException e) {
            logger.error("连接到Nacos时有错误发生: ", e);
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }

    /*
     * 仅在NacosServiceRegistry.register(String serviceName, InetSocketAddress inetSocketAddress)
     * 这一处被调用过.
     * */
    public static void registerService(String serviceName, InetSocketAddress address) throws NacosException {
        namingService.registerInstance(serviceName, address.getHostName(), address.getPort());
        NacosUtil.address = address;
        serviceNames.add(serviceName);
    }

    /*
     * 仅在NacosServiceDiscovery.lookupService(String serviceName)这一处被调用过
     * */
    public static List<Instance> getAllInstance(String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }

    /*
     * 仅在ShutdownHook.addClearAllHook()这一处被调用过
     * */
    public static void clearRegistry() {
        if (!serviceNames.isEmpty() && address != null) {
            String host = address.getHostName();
            int port = address.getPort();
            Iterator<String> iterator = serviceNames.iterator();
            while (iterator.hasNext()) {
                String serviceName = iterator.next();
                try {
                    namingService.deregisterInstance(serviceName, host, port);
                } catch (NacosException e) {
                    logger.error("注销服务 {} 失败", serviceName, e);
                }
            }
        }
    }
}
