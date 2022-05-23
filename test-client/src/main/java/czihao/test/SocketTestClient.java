package czihao.test;

import czihao.rpc.api.ByeService;
import czihao.rpc.api.HelloObject;
import czihao.rpc.api.HelloService;
import czihao.rpc.serializer.CommonSerializer;
import czihao.rpc.transport.RpcClientProxy;
import czihao.rpc.transport.socket.client.SocketClient;

/**
 * 测试用消费者（客户端）
 *
 * @author czihao
 */
public class SocketTestClient {

    public static void main(String[] args) {
        SocketClient client = new SocketClient(CommonSerializer.KRYO_SERIALIZER);
        RpcClientProxy proxy = new RpcClientProxy(client);
        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);
        ByeService byeService = proxy.getProxy(ByeService.class);
        System.out.println(byeService.bye("Netty"));
    }

}
