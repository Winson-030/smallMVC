package cn.adalab.exercise.autumn.P10;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

import static cn.adalab.exercise.autumn.TestConstants.TOMCAT_STARTUP_DELAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class P10Test {

  @Test
  void test() throws Exception {
    new Thread(() -> HelloApplication.main(null)).start();
    TimeUnit.SECONDS.sleep(TOMCAT_STARTUP_DELAY);
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://127.0.0.1:8082/hello?name=Chris"))
        .build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    var body = response.body().strip();
    assertTrue(body.contains("\uD83D\uDC4B Hello Chris!"));
    assertTrue(body.contains("background: #f06d06;"));

    request = HttpRequest.newBuilder()
        .uri(URI.create("http://127.0.0.1:8082/goodbye"))
        .build();
    response = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals("<html><head></head><body>goodbye</body></html>", response.body().strip());
  }
}