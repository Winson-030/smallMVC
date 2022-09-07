package cn.adalab.exercise.autumn.framework;

import cn.adalab.exercise.autumn.app.App;
import cn.adalab.exercise.autumn.app.DispatcherServlet;
import cn.adalab.exercise.autumn.framework.ioc.DIContainer;
import cn.adalab.exercise.autumn.framework.ioc.ElementScanner;
import com.google.common.reflect.TypeToken;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.http.HttpServlet;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class AutumnApplication {

    /**
     * 启动一个Autumn程序，类似{@code SpringApplication#run}，但没有其他参数
     *
     * @param clazz 主程序的类对象。Autumn框架会扫描这个类所在的包，然后开始运行。
     * @see <a href="https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/SpringApplication.html#run-java.lang.Class-java.lang.String...-">SpringApplication#run</a>
     */
    public static void run(Class<?> clazz) {

        //根据传入的类获取所在包的所有类
        List<Class<?>> allClasses = ElementScanner.findAllClassesInSamePackage(clazz);
//    找到符合条件的类
        DIContainer container = new DIContainer();

        List<Class<?>> classesisControllerClasses = new ArrayList<>();
        boolean ifControllerList = false;
        boolean ifRestControllerList = false;

        for (Class<?> class1 : allClasses) {

            //      获取类名
            String[] strings = class1.getName().split("\\.");
            String className = strings[strings.length - 1];
            if ("DispatcherServlet".equals(className)) {
                try {
//          通过构造函数创建实例并加入容器
                    container.registerSupplierClass(class1, class1);
                    HttpServlet bean = (HttpServlet) container.lookupBean(class1);
//运行程序
                    App.start(bean);
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }

            if (class1.getAnnotation(Controller.class) != null) {
                classesisControllerClasses.add(class1);
                ifControllerList = true;
            }

//需要先在对应接口添加Retention注解才能识别
            if (class1.getAnnotation(RestController.class) != null){
                classesisControllerClasses.add(class1);
                ifRestControllerList = true;
            }



        }
//符合条件再执行这个函数
        if (ifControllerList == true|| ifRestControllerList ==true) {
            System.out.println(classesisControllerClasses);
            DispatcherServlet dispatcherServlet = new DispatcherServlet(classesisControllerClasses);
//运行程序
            App.start(dispatcherServlet);
        }

    }
}
