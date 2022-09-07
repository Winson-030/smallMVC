package cn.adalab.exercise.autumn.P2;

import cn.adalab.exercise.autumn.app.App;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

import static cn.adalab.exercise.autumn.TestConstants.TOMCAT_STARTUP_DELAY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class P2Test {

  @Test
  void test() throws Exception {
    var thread = new Thread(() -> App.main(null));
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
}