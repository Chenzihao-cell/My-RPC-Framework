package czihao.test;

import czihao.rpc.annotation.ServiceScan;
import czihao.rpc.serializer.CommonSerializer;
import czihao.rpc.transport.RpcServer;
import czihao.rpc.transport.netty.server.NettyServer;

/**
 * 测试用基于Netty网络传输的服务提供者（服务端）
 */
@ServiceScan
public class NettyTestServer {

    public static void main(String[] args) {
        RpcServer server = new NettyServer("127.0.0.1", 9999, CommonSerializer.PROTOBUF_SERIALIZER);

        /*
         * 如果服务端启动失败，无法提供服务，这时应该怎么处理？
         * */
        server.start();
    }

}
