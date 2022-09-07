package cn.adalab.exercise.autumn.P5;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

import static cn.adalab.exercise.autumn.TestConstants.TOMCAT_STARTUP_DELAY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class P5Test {

  @Test
  void test() throws Exception {
    new Thread(() -> HelloApplication.main(null)).start();
    TimeUnit.SECONDS.sleep(TOMCAT_STARTUP_DELAY);

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://127.0.0.1:8082"))
        .build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals("hello get", response.body().strip());

    request = HttpRequest.newBuilder()
        .uri(URI.create("http://127.0.0.1:8082"))
        .POST(HttpRequest.BodyPublishers.noBody())
        .build();
    response = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals("hello post", response.body().strip());
  }

}