package microservice.demo.training21days.consumer.service;

import java.util.concurrent.CompletableFuture;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import microservice.demo.training21days.provider.service.GreetingResponse;
import microservice.demo.training21days.provider.service.HelloServiceReactive;
import microservice.demo.training21days.provider.service.Person;

@RestSchema(schemaId = "helloConsumerReactive")
@Path("/consumer/v0/reactive")  // 这里使用JAX-RS风格开发的consumer服务
public class HelloConsumerServiceReactive {
  // RPC调用方式需要声明一个provider服务的REST接口代理
  @RpcReference(microserviceName = "provider", schemaId = "helloReactive")
  private HelloServiceReactive helloService;

  // RestTemplate调用方式需要创建一个 ServiceComb 的 RestTemplate
  private RestTemplate restTemplate = RestTemplateBuilder.create();

  @Path("/hello")
  @GET
  public CompletableFuture<String> sayHello(@QueryParam("name") String name) {
    // RPC 调用方式体验与本地调用相同
    return helloService.sayHello(name);
  }

  @Path("/greeting")
  @POST
  public CompletableFuture<GreetingResponse> greeting(Person person) {
    return helloService.greeting(person);
  }
}
