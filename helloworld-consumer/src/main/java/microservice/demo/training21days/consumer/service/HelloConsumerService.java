package microservice.demo.training21days.consumer.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.provider.rest.common.RestSchema;

import microservice.demo.training21days.provider.service.HelloService;
import microservice.demo.training21days.provider.service.Person;

@RestSchema(schemaId = "helloConsumer")
@Path("/consumer/v0")
public class HelloConsumerService {
  @RpcReference(microserviceName = "provider", schemaId = "hello")
  private HelloService helloService;

  @Path("/hello")
  @GET
  public String sayHello(@QueryParam("name") String name) {
    return helloService.sayHello(name);
  }

  @Path("/greeting")
  @POST
  public Person greeting(Person person) {
    return helloService.greeting(person);
  }
}
