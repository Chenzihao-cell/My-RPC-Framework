package czihao.rpc.provider;

/**
 * 服务注册表，保存和查找服务端提供的本地服务实例对象
 *
 * @author czihao
 */
public interface ServiceProvider {


    <T> void addServiceProvider(T service, String serviceName);

    Object getServiceProvider(String serviceName);

}
