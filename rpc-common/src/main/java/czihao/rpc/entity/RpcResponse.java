package czihao.rpc.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import czihao.rpc.enumeration.ResponseCode;

import java.io.Serializable;

/**
 * 提供者执行完成或出错后向消费者返回的结果响应对象
 */
@Data
@NoArgsConstructor
public class RpcResponse<T> implements Serializable {

    private String requestId;//响应对应的请求号

    private Integer statusCode;//响应状态码

    private String message;//响应状态补充信息

    private T data;//响应数据

    public static <T> RpcResponse<T> success(T data, String requestId) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setRequestId(requestId);
        response.setStatusCode(ResponseCode.SUCCESS.getCode());
        response.setData(data);
        return response;
    }

    public static <T> RpcResponse<T> fail(ResponseCode code, String requestId) {
        RpcResponse<T> response = new RpcResponse<>();
        response.setRequestId(requestId);
        response.setStatusCode(code.getCode());
        response.setMessage(code.getMessage());
        return response;
    }

}
