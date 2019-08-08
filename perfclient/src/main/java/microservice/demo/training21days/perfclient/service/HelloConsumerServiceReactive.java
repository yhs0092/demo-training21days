package microservice.demo.training21days.perfclient.service;

import java.util.concurrent.CompletableFuture;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

@Path("/consumer/v0/reactive")
public interface HelloConsumerServiceReactive {
  @Path("/hello")
  @GET
  CompletableFuture<String> sayHello(@QueryParam("name") String name);

  @Path("/greeting")
  @POST
  CompletableFuture<Person> greeting(Person person);
}
