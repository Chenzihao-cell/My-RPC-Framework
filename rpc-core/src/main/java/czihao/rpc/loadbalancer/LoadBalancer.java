package czihao.rpc.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * 负载均衡接口
 *
 * @author czihao
 */
public interface LoadBalancer {

    Instance select(List<Instance> instances);

}
