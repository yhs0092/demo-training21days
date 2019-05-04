package microservice.demo.training21days.provider.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestSchema(schemaId = "hello2")
@RequestMapping(path = "/provider/v0/hello2", produces = MediaType.APPLICATION_JSON)
public class Hello {

  @RequestMapping(value = "/future/body", method = RequestMethod.GET, produces = {"application/json"})
  public CompletableFuture<ResponseEntity<String>> deletex6(HttpServletRequest request) throws Exception {

    CompletableFuture<ResponseEntity<String>> future = new CompletableFuture<ResponseEntity<String>>();

    ExecutorService ex = Executors.newFixedThreadPool(1);
    ex.execute(() -> {
      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.add("test1", "234");
      ResponseEntity<String> res =  ResponseEntity.status(203).headers(responseHeaders).body("{\"name\":\"1234\"}");
      future.complete(res);
    });

    return future;
  }
}
