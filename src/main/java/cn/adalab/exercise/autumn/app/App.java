package cn.adalab.exercise.autumn.app;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) {

    }
    public static void start(HttpServlet servlet) {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8082);
        tomcat.getConnector(); // setPort只有在调用了getConnector时才会生效

        Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());


//    noinspection AccessStaticViaInstance
        tomcat.addServlet(ctx, "autumn", servlet);

        ctx.addServletMappingDecoded("/*", "autumn");

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
        tomcat.getServer().await();

    }
}
