package cn.adalab.exercise.autumn.framework.ioc;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ElementScanner {
    /**
     * 找到某个类（class）所在的包（package）中所有的类。注意：
     * 1. 只包括直接属于这个包的类，不包括这个包下面子包里的类
     * 2. 接口也应算作类，包含在返回的结果当中
     *
     * @param clazz 一个类
     * @return 这个类所在的包中（不包括子包）所有的类构成的列表
     */
    public static List<Class<?>> findAllClassesInSamePackage(Class<?> clazz) {
        //需要返回的类集合
        List<Class<?>> classList = new ArrayList<>();
//   根据类获取的包路径集合，同一个类名可能存在于多个包里，所以返回集合
        List<Path> packagePaths = Helpers.getPackagePaths(clazz);
//    获取包名
        String packageName = clazz.getPackageName();
//判断每一个包路径
        for (Path path : packagePaths) {
            //获取该包路径下的所有文件和文件夹
            File file = new File(String.valueOf(path));
            File[] files = file.listFiles();
            //判断文件是否属于class文件
            for (File file1 : files) {
                if (!file1.isDirectory()) {
                    String[] split = file1.getName().split("\\.");
                    //根据包名和class文件名获取类，并存入需要返回的类集合里
                    Class<?> aClass = Helpers.getClass(packageName + "." + split[0]);
                    classList.add(aClass);
                }
            }
        }

        return classList;
    }

    /**
     * 对某个类所在的包进行元素扫描，所有被{@code @Element}标记的类都将被DI容器管理
     *
     * @param container 依赖注入容器
     * @param clazz     一个类，将会对该类所在的包进行扫描
     */
    public static void scanForElements(DIContainer container, Class<?> clazz) {

        List<Class<?>> allClassesInSamePackage = findAllClassesInSamePackage(clazz);
        for (Class<?> aClass : allClassesInSamePackage) {

            if (aClass.getAnnotation(Element.class) !=null) {
                try {
                    container.registerSupplierClass(aClass, aClass);
                } catch (RuntimeException | NoSuchMethodException e) {
                    System.out.println("????");
                }

            }

        }
    }
}
