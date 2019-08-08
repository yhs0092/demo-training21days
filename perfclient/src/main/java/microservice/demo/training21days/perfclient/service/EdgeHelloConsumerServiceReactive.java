package microservice.demo.training21days.perfclient.service;

import java.util.concurrent.CompletableFuture;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/rest/consumer/v0/reactive")
public interface EdgeHelloConsumerServiceReactive {
  @Path("/hello")
  @GET
  CompletableFuture<String> sayHello(@QueryParam("name") String name);

  @Path("/greeting")
  @POST
  CompletableFuture<Person> greeting(Person person);
}
