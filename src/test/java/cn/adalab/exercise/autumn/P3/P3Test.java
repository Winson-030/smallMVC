package cn.adalab.exercise.autumn.P3;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.Test;

import javax.servlet.Servlet;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

import static cn.adalab.exercise.autumn.TestConstants.TOMCAT_STARTUP_DELAY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class P3Test {

  @Test
  void test() throws Exception {
    var thread = new Thread(this::runTomcat);
    thread.start();
    TimeUnit.SECONDS.sleep(TOMCAT_STARTUP_DELAY);

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://127.0.0.1:8082/"))
        .build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    var body = response.body().strip();
    assertEquals("hello get", body);

    request = HttpRequest.newBuilder()
        .uri(URI.create("http://127.0.0.1:8082/"))
        .POST(HttpRequest.BodyPublishers.noBody())
        .build();
    response = client.send(request, HttpResponse.BodyHandlers.ofString());
    body = response.body().strip();
    assertEquals("hello post", body);
  }

  private void runTomcat() {
    Servlet servlet = getServlet();
    Tomcat tomcat = new Tomcat();
    tomcat.setPort(8082);
    tomcat.getConnector(); // setPort只有在调用了getConnector时才会生效
    Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());
    //noinspection AccessStaticViaInstance
    tomcat.addServlet(ctx, "autumn", servlet);
    ctx.addServletMappingDecoded("/*", "autumn");
    try {
      tomcat.start();
    } catch (LifecycleException e) {
      e.printStackTrace();
    }
    tomcat.getServer().await();
  }

  private Servlet getServlet() {
    try {
      Class<?> clazz = Class.forName("cn.adalab.exercise.autumn.app.DispatcherServlet");
      var ctor = clazz.getDeclaredConstructor();
      return (Servlet) ctor.newInstance();
    } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }
}
