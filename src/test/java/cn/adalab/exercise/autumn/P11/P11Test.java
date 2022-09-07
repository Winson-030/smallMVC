package cn.adalab.exercise.autumn.P11;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.TimeUnit;

import static cn.adalab.exercise.autumn.TestConstants.TOMCAT_STARTUP_DELAY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class P11Test {

  @Test
  void test() throws Exception {
    new Thread(() -> EchoApplication.main(null)).start();
    TimeUnit.SECONDS.sleep(TOMCAT_STARTUP_DELAY);
    HttpClient client = HttpClient.newHttpClient();
    ObjectMapper mapper = new ObjectMapper();

    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("http://127.0.0.1:8082/echo?name=Chris&country=France"))
        .build();
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
    var body = response.body().strip();
    assertEquals("Chris from France", body);

    request = HttpRequest.newBuilder()
        .uri(URI.create("http://127.0.0.1:8082/api/echo?name=Chris&country=France"))
        .build();
    response = client.send(request, HttpResponse.BodyHandlers.ofString());
    Person p = new Person();
    p.setName("Chris");
    p.setCountry("France");
    assertEquals(p, mapper.readValue(response.body().strip(), Person.class));

    request = HttpRequest.newBuilder()
        .uri(URI.create("http://127.0.0.1:8082/echo?name=david&country=usa"))
        .build();
    response = client.send(request, HttpResponse.BodyHandlers.ofString());
    assertEquals("david from usa", response.body().strip());

    request = HttpRequest.newBuilder()
        .uri(URI.create("http://127.0.0.1:8082/api/echo?name=david&country=usa"))
        .build();
    response = client.send(request, HttpResponse.BodyHandlers.ofString());
    p.setName("david");
    p.setCountry("usa");
    assertEquals(p, mapper.readValue(response.body().strip(), Person.class));
  }
}