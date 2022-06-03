package czihao.rpc.transport;

import czihao.rpc.serializer.CommonSerializer;

/**
 * 服务提供者接口
 *
 * @author czihao
 */
public interface RpcServer {

    int DEFAULT_SERIALIZER = CommonSerializer.PROTOBUF_SERIALIZER;

    void start();

    <T> void publishService(T service, String serviceName);

}
