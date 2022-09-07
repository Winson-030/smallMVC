package cn.adalab.exercise.autumn.framework.ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

public final class DIContainer {
    //注册bean本质上是把bean的类型和名称按照键-值的形式存入map中
    Map<Class, Object> map;
    //存储supplier
    Map<Class, Supplier> map2;

    public DIContainer() {
//        创建实例的时候创建map容器
        map = new HashMap<>();
        map2 = new HashMap<>();

    }

    /**
     * 实现注册bean，实现单例
     *
     * @param type
     * @param object
     */
    public <T> void registerBean(Class<T> type, Object object) {
        map.put(type, object);
    }

    /**
     * 查询已经注册的bean，返回实例类型
     *
     * @param type
     * @return
     */
    public Object lookupBean(Class type) {
        Object obj;
        //判断type属于哪个map
        if (map.containsKey(type)) {
            obj = map.get(type);
        } else if (map2.containsKey(type)) {
            obj = map2.get(type).get();
        } else {
            return null;
        }
        return obj;
    }


    /**
     * 实现注册supplier，实现多例
     *
     * @param type
     * @param supplier
     */
    public <T> void registerSupplier(Class<?> type, Supplier<?> supplier) {
        map2.put(type, supplier);
    }


    public void registerSupplierClass(Class<?> type, Class<?> supplierClass) throws NoSuchMethodException {

        if (isSingleAnnotation(type)) {
            Supplier<?> supplier = () -> {
                //调用supplier 创建实例
                return chooseConstructor(supplierClass);
            };
            System.out.println(supplier);
            registerSupplier(type, supplier);
        } else {
            throw new RuntimeException("出现判断异常");
        }


    }

    /**
     * 判断构造函数是否只有一个注解
     *
     * @param type
     * @return
     */
    public boolean isSingleAnnotation(Class type) {
        int count = 0;
        var constructors = type.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            if (constructor.getAnnotation(Inject.class) != null) {
                count++;
            }
        }
        return count == 1 || count == 0;
    }

    /**
     * 返回符合要求的构造函数，优先返回有注解的构造函数
     *
     * @param type
     * @return
     */
    public Object chooseConstructor(Class type) {
        //查找所有构造函数
        Constructor[] constructors = type.getDeclaredConstructors();
        Object obj = null;
//        boolean nothava = false;
        for (Constructor constructor : constructors) {
            Class[] parameterTypes = constructor.getParameterTypes();
            //参数数组
            Object[] objects = new Object[parameterTypes.length];
            if (parameterTypes.length == 0) {
                //无参构造器
                obj = Helpers.newInstance(constructor);
            } else if (constructor.getAnnotation(Inject.class) != null) {
//                nothava = true;

//根据参数数组元素的类型到map中寻找对应的参数，并保存到参数数组中
                for (int i = 0; i < parameterTypes.length; i++) {
                    objects[i] = lookupBean(parameterTypes[i]);
                    //判断参数找不到的情况
                    if (objects[i] == null) {
                        throw new RuntimeException("找不到bean");
                    }
                }

                obj = Helpers.newInstance(constructor, objects);
                break;
            }

        }
        ;


        return obj;
    }


}
