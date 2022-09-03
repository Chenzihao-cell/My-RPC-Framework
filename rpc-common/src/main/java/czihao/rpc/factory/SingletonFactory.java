package czihao.rpc.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * 单例工厂
 */
public class SingletonFactory {

    private static Map<Class, Object> objectMap = new HashMap<>();

    /**
     * 双重检测实现单例创建
     */
    public static <T> T getInstance(Class<T> clazz) {
        Object instance = objectMap.get(clazz);
        if (instance == null) {
            synchronized (clazz) {
                if (instance == null) {
                    try {
                        instance = clazz.newInstance();
                        objectMap.put(clazz, instance);
                    } catch (IllegalAccessException | InstantiationException e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
            }
        }
        return clazz.cast(instance);
    }

}
