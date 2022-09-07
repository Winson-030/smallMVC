package cn.adalab.exercise.autumn.P8;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

import static cn.adalab.exercise.autumn.TestConstants.TOMCAT_STARTUP_DELAY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class P8Test {

  @Test
  void test() throws Exception {
    new Thread(() -> HelloApplication.main(null)).start();
    TimeUnit.SECONDS.sleep(TOMCAT_STARTUP_DELAY);
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://127.0.0.1:8082/hello?name=alice&from=china"))
        .build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals("hello alice from china", response.body().strip());

    request = HttpRequest.newBuilder()
        .uri(URI.create("http://127.0.0.1:8082/hello?from=japan&age=12&name=bob"))
        .build();
    response = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals("hello bob from japan", response.body().strip());
  }
}