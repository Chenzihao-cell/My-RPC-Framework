package czihao.rpc.transport;

import czihao.rpc.entity.RpcRequest;
import czihao.rpc.serializer.CommonSerializer;

/**
 * 服务消费侧（客户端）网络传输接口
 * RpcClient的作用就是将一个rpcRequest发过去，并且接受返回的RpcResponse
 *
 * @author czihao
 */
public interface RpcClient {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    Object sendRequest(RpcRequest rpcRequest);

}
