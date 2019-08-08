package microservice.demo.training21days.consumer.service;

import java.util.concurrent.CompletableFuture;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.provider.rest.common.RestSchema;

import microservice.demo.training21days.provider.service.HelloServiceReactive;
import microservice.demo.training21days.provider.service.Person;

@RestSchema(schemaId = "helloConsumerReactive")
@Path("/consumer/v0/reactive")
public class HelloConsumerServiceReactive {
  @RpcReference(microserviceName = "provider", schemaId = "helloReactive")
  private HelloServiceReactive helloService;

  @Path("/hello")
  @GET
  public CompletableFuture<String> sayHello(@QueryParam("name") String name) {
    return helloService.sayHello(name);
  }

  @Path("/greeting")
  @POST
  public CompletableFuture<Person> greeting(Person person) {
    return helloService.greeting(person);
  }
}
