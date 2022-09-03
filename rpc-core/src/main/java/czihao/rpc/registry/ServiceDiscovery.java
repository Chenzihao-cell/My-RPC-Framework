package czihao.rpc.registry;

import java.net.InetSocketAddress;

/**
 * 服务发现接口
 * 供服务使用侧（客户端）使用
 */
public interface ServiceDiscovery {

    /**
     * 根据服务名称查找该服务所在的服务端（即服务提供侧）地址（即IP地址+端口号）
     *
     * @param serviceName 服务名称
     * @return 服务实体所在的地址
     */
    InetSocketAddress lookupService(String serviceName);

}
