package microservice.demo.training21days.provider.service;

import java.util.concurrent.CompletableFuture;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestSchema(schemaId = "helloReactive")
@RequestMapping(path = "/provider/v0/reactive")
public class HelloServiceReactive {

  @RequestMapping(path = "/hello/{name}", method = RequestMethod.GET)
  public CompletableFuture<String> sayHello(@PathVariable(value = "name") String name) {
    CompletableFuture<String> future = new CompletableFuture<>();
    future.complete(name);
    return future;
  }

  @PostMapping(path = "/greeting")
  public CompletableFuture<Person> greeting(@RequestBody Person person) {
    CompletableFuture<Person> future = new CompletableFuture<>();
    future.complete(person);
    return future;
  }
}
