package czihao.rpc.transport;

/**
 * 服务提供者接口
 */
public interface RpcServer {

    void start();

    <T> void publishService(T service, String serviceName);

}
