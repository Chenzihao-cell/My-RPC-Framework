package czihao.rpc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 消费者向提供者发送的请求对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RpcRequest implements Serializable {

    private String requestId;//请求号

    private String interfaceName;//待调用接口名称

    private String methodName;//待调用方法名称

    private Object[] parameters;//调用方法的参数

    private Class<?>[] paramTypes;//调用方法的参数类型

    private Boolean heartBeat;//是否是心跳包

}
