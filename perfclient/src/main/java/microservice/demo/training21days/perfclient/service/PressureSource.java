package microservice.demo.training21days.perfclient.service;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.serviceregistry.RegistryUtils;
import org.springframework.stereotype.Component;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;

import io.vertx.core.json.Json;

@Component
public class PressureSource {
  private DynamicStringProperty perfTargetMethod = DynamicPropertyFactory
      .getInstance().getStringProperty("perf.targetMethod", "");

  @RpcReference(microserviceName = "targetSvc", schemaId = "targetSvc")
  private EdgeHelloConsumerService edgeHelloConsumerService;

  @RpcReference(microserviceName = "targetSvc", schemaId = "targetSvc")
  private EdgeHelloConsumerServiceReactive edgeHelloConsumerServiceReactive;

  @RpcReference(microserviceName = "targetSvc", schemaId = "targetSvc")
  private HelloConsumerService helloConsumerService;

  @RpcReference(microserviceName = "targetSvc", schemaId = "targetSvc")
  private HelloConsumerServiceReactive helloConsumerServiceReactive;

  private String sayHelloParam;

  private Person greetingParam = new Person();

  private String targetAddress = "";

  private int concurrency;

  private ExecutorService executorService;

  private boolean continueFlag = true;

  public void startPerfTest() {
    registerTargetService();
    initParam();
    concurrency = DynamicPropertyFactory
        .getInstance().getIntProperty("perf.concurrency", 1).get();
    executorService = Executors.newFixedThreadPool(concurrency);
    startTest();
  }

  public void stopPerfTest() throws InterruptedException {
    continueFlag = false;
    executorService.shutdown();
    executorService.awaitTermination(60_000, TimeUnit.MILLISECONDS);
  }

  private void initParam() {
    sayHelloParam = DynamicPropertyFactory
        .getInstance().getStringProperty("perf.sayHelloParam", "abcd").getValue();
    String rawGreetingParam = DynamicPropertyFactory
        .getInstance().getStringProperty("perf.greetingParam", "{\"name\":\"abcd\",\"index\":1}").getValue();
    greetingParam = Json.decodeValue(rawGreetingParam, Person.class);
  }

  private void registerTargetService() {
    targetAddress = DynamicPropertyFactory
        .getInstance().getStringProperty("perf.targetAddress", "abcd").getValue();
    String targetMethodValue = perfTargetMethod.getValue();
    if (targetMethodValue.startsWith("edge")) {
      if (targetMethodValue.endsWith("reactive")) {
        registerTargetServiceAddress(EdgeHelloConsumerServiceReactive.class);
      } else {
        registerTargetServiceAddress(EdgeHelloConsumerService.class);
      }
    } else {
      if (targetMethodValue.endsWith("reactive")) {
        registerTargetServiceAddress(HelloConsumerServiceReactive.class);
      } else {
        registerTargetServiceAddress(HelloConsumerService.class);
      }
    }
  }

  private void registerTargetServiceAddress(Class<?> targetServiceClass) {
    RegistryUtils.getServiceRegistry().registerMicroserviceMappingByEndpoints("targetSvc", "0.0.1",
        Arrays.asList(targetAddress.split(",")), targetServiceClass);
  }

  private void startTest() {
    switch (perfTargetMethod.getValue()) {
      case "edge.hello":
        for (int i = 0; i < concurrency; ++i) {
          executorService.execute(() -> loopHello(() -> edgeHelloConsumerService.sayHello(sayHelloParam)));
        }
        break;
      case "edge.hello.reactive":
        for (int i = 0; i < concurrency; ++i) {
          executorService.execute(() -> loopHello(() -> edgeHelloConsumerServiceReactive.sayHello(sayHelloParam)));
        }
        break;
      case "consumer.hello":
        for (int i = 0; i < concurrency; ++i) {
          executorService.execute(() -> loopHello(() -> helloConsumerService.sayHello(sayHelloParam)));
        }
        break;
      case "consumer.hello.reactive":
        for (int i = 0; i < concurrency; ++i) {
          executorService.execute(() -> loopHello(() -> helloConsumerServiceReactive.sayHello(sayHelloParam)));
        }
        break;
      case "edge.greeting":
        for (int i = 0; i < concurrency; ++i) {
          executorService.execute(() -> loopGreeting(() -> edgeHelloConsumerService.greeting(greetingParam)));
        }
        break;
      case "edge.greeting.reactive":
        for (int i = 0; i < concurrency; ++i) {
          executorService.execute(() -> loopGreeting(() -> edgeHelloConsumerServiceReactive.greeting(greetingParam)));
        }
        break;
      case "consumer.greeting":
        for (int i = 0; i < concurrency; ++i) {
          executorService.execute(() -> loopGreeting(() -> helloConsumerService.greeting(greetingParam)));
        }
        break;
      case "consumer.greeting.reactive":
        for (int i = 0; i < concurrency; ++i) {
          executorService.execute(() -> loopGreeting(() -> helloConsumerServiceReactive.greeting(greetingParam)));
        }
        break;
      default:
        throw new IllegalArgumentException();
    }
  }

  private void loopHello(Supplier<CompletableFuture<String>> targetMethod) {
    CompletableFuture<String> stringCompletableFuture = targetMethod.get();
    if (continueFlag) {
      stringCompletableFuture.whenComplete((s, throwable) -> loopHello(targetMethod));
    }
  }

  private void loopGreeting(Supplier<CompletableFuture<Person>> targetMethod) {
    CompletableFuture<Person> future = targetMethod.get();
    if (continueFlag) {
      future.whenComplete((person, throwable) -> loopGreeting(targetMethod));
    }
  }
}
