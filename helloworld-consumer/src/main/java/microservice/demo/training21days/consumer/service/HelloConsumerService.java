package microservice.demo.training21days.consumer.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.provider.rest.common.RestSchema;

import microservice.demo.training21days.provider.service.HelloService;

@RestSchema(schemaId = "helloConsumer")
@Path("/consumer/v0")  // 这里使用JAX-RS风格开发的consumer服务
public class HelloConsumerService {
  // RPC调用方式需要声明一个provider服务的REST接口代理
  @RpcReference(microserviceName = "provider", schemaId = "hello")
  private HelloService helloService;

  @Path("/hello")
  @GET
  public String sayHello(@QueryParam("name") String name) {
    // RPC 调用方式体验与本地调用相同
    return helloService.sayHello(name);
  }
}
