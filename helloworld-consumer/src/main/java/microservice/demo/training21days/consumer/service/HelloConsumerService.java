package microservice.demo.training21days.consumer.service;

import java.util.concurrent.CountDownLatch;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.apache.servicecomb.provider.springmvc.reference.async.CseAsyncRestTemplate;
import org.apache.servicecomb.swagger.invocation.exception.InvocationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import com.netflix.config.DynamicLongProperty;
import com.netflix.config.DynamicPropertyFactory;

import microservice.demo.training21days.provider.service.GreetingResponse;
import microservice.demo.training21days.provider.service.Hello;
import microservice.demo.training21days.provider.service.HelloService;
import microservice.demo.training21days.provider.service.NormalResponse;
import microservice.demo.training21days.provider.service.Person;

@RestSchema(schemaId = "helloConsumer")
@Path("/consumer/v0")  // 这里使用JAX-RS风格开发的consumer服务
public class HelloConsumerService {
  private static final Logger LOGGER = LoggerFactory.getLogger(HelloConsumerService.class);

  // RPC调用方式需要声明一个provider服务的REST接口代理
  @RpcReference(microserviceName = "provider", schemaId = "hello")
  private HelloService helloService;

  // RestTemplate调用方式需要创建一个 ServiceComb 的 RestTemplate
  private RestTemplate restTemplate = RestTemplateBuilder.create();

  private DynamicLongProperty helloDelay = DynamicPropertyFactory.getInstance().getLongProperty("delay.sayHello", 0);

  @Path("/hello")
  @GET
  public String sayHello(@QueryParam("name") String name) {
    // RPC 调用方式体验与本地调用相同
    if (helloDelay.get() > 0) {
      try {
        Thread.sleep(helloDelay.get());
      } catch (InterruptedException e) {
        LOGGER.error("sayHello sleeping is interrupted!", e);
      }
    }
    return helloService.sayHello(name);
  }

  @Path("/helloRT")
  @GET
  public String sayHelloRestTemplate(@QueryParam("name") String name) {
    // RestTemplate 使用方式与原生的Spring RestTemplate相同，可以直接参考原生Spring的资料
    // 注意URL不是 http://{IP}:{port} ， 而是 cse://{provider端服务名} ， 其他部分如path/query等与原生调用方式一致
    ResponseEntity<String> responseEntity =
        restTemplate.getForEntity("cse://provider/provider/v0/hello/" + name, String.class);
    return responseEntity.getBody();
  }

  @Path("/greeting")
  @POST
  public GreetingResponse greeting(Person person) {
    return helloService.greeting(person);
  }

  @RpcReference(microserviceName = "provider", schemaId = "hello2")
  private Hello hello2;

  private CseAsyncRestTemplate cseAsyncRestTemplate = new CseAsyncRestTemplate();

  @Path("/testtest")
  @GET
  public String testtest() {
    CountDownLatch countDownLatch = new CountDownLatch(1);
    StringBuilder responseBuilder = new StringBuilder();

//    hello2.deletex6().whenComplete((s, e) -> {
//      responseBuilder.append(s);
//      countDownLatch.countDown();
//    });
    ListenableFuture<ResponseEntity<String>> asyncResponseEntity = cseAsyncRestTemplate
        .getForEntity("cse://provider/provider/v0/hello2/future/body", String.class);
    asyncResponseEntity.addCallback(entity -> {
      responseBuilder
          .append(entity.getBody())
          .append("-")
          .append(entity.getStatusCode())
          .append("-")
          .append(entity.getHeaders().toString());
      countDownLatch.countDown();
    }, ex -> {
      LOGGER.error("get failure!!!!", ex);
      countDownLatch.countDown();
    });
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    responseBuilder.append("-");
    ResponseEntity<String> responseEntity = restTemplate
        .getForEntity("cse://provider/provider/v0/hello2/future/body", String.class);
    responseBuilder
        .append(responseEntity.getBody())
        .append("-")
        .append(responseEntity.getStatusCode())
        .append("-")
        .append(responseEntity.getHeaders().toString());
    return responseBuilder.toString();
  }

  @Path("testResponse")
  @GET
  public String testResponse(@QueryParam("code") int code) {
    NormalResponse normalResponse = null;
    try {
      normalResponse = helloService.testResponse(code);
    } catch (InvocationException e) {
      System.out.println(e.toString());
      return e.getErrorData().toString();
    }
    return normalResponse.toString();
  }
}
