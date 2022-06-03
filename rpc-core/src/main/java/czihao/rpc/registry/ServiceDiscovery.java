package czihao.rpc.registry;

import java.net.InetSocketAddress;

/**
 * 服务发现接口
 * 供服务使用侧（客户端）使用
 */
public interface ServiceDiscovery {

    /**
     * 根据服务名称查找服务实体所在的位置（即IP地址+端口号）
     *
     * @param serviceName 服务名称
     * @return 服务实体所在的位置
     */
    InetSocketAddress lookupService(String serviceName);

}
