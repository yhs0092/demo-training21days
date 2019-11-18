# CSEJavaSDK 多sc集群使用方法

## 使用步骤

1. 创建一个`io.vertx.core.http.HttpClientOptions`对象作为服务中心客户端的配置，这是Vert.x的原生配置类型，SSL证书相关的配置也在这里设置，可以直接参考Vert.x官方说明文档。
2. 创建一个`ArrayList<IpPort>`对象，用于设置服务中心的地址列表。
3. 创建一个`org.apache.servicecomb.serviceregistry.config.ServiceRegistryConfig`对象，设置其名称、地址列表、客户端配置。将这个`ServiceRegistryConfig`加入到`RegistryUtils`中，服务启动时会根据配置自动连接sc。

> **注意**:
> - `RegistryName`必须是唯一的，这个名字仅仅是该微服务实例在本地给它所对接的不同的sc集群取的名字，并不会影响到它注册到这些sc的微服务名称等信息。
> - 由于`RegistryUtils`初始化`ServiceRegistry`实例的时间点非常早（实际逻辑在`org.apache.servicecomb.serviceregistry.RegistryUtils#init`中），推荐上述设置在main类中执行以避免拖过初始化时机。

## 代码示例

```java
import java.util.ArrayList;

import org.apache.servicecomb.foundation.common.net.IpPort;
import org.apache.servicecomb.foundation.common.utils.BeanUtils;
import org.apache.servicecomb.foundation.common.utils.Log4jUtils;
import org.apache.servicecomb.foundation.ssl.SSLCustom;
import org.apache.servicecomb.foundation.ssl.SSLOption;
import org.apache.servicecomb.foundation.vertx.VertxTLSBuilder;
import org.apache.servicecomb.serviceregistry.RegistryUtils;
import org.apache.servicecomb.serviceregistry.config.ServiceRegistryConfig;

import io.vertx.core.http.HttpClientOptions;

public class AppMain {
  public static void main(String[] args) throws Exception {
    preset();
    Log4jUtils.init(); // 初始化默认的日志组件
    BeanUtils.init();  // 加载Spring bean定义文件，正式开始启动流程
  }

  /**
   * 在服务启动最开始完成{@link ServiceRegistryConfig}的配置。
   * 这里借用了Java-Chassis的工具类如{@link SSLOption}/{@link VertxTLSBuilder}等设置{@link HttpClientOptions}，
   * 这样在设置逻辑上跟microservice.yaml配置文件里的配置格式比较匹配，方便使用，但这不是必须的。
   * 用户完全可以自行手动设置{@link HttpClientOptions}。
   */
  private static void preset() {
    final ArrayList<IpPort> ipPorts = new ArrayList<>();
    final HttpClientOptions httpClientOptions = buildHttpClientOptions();

    final SSLOption sslOption = buildSslOption();
    final SSLCustom sslCustom = SSLCustom.createSSLCustom("microservice.demo.training21days.tls.DemoSSLCustomN1");
    VertxTLSBuilder.buildHttpClientOptions(sslOption, sslCustom, httpClientOptions);
    httpClientOptions.setSsl(true);

    ipPorts.add(new IpPort("127.0.0.1", 30200));
    RegistryUtils.addExtraServiceRegistryConfig(new ServiceRegistryConfig()
        .setRegistryName("local-sc-n1")
        .setIpPorts(ipPorts)
        .setRegistryClientOptions(httpClientOptions)
    );
  }

  private static SSLOption buildSslOption() {
    final SSLOption sslOption = SSLOption.buildFromYaml("sc.consumer");
    sslOption.setKeyStore("server.p12");
    sslOption.setKeyStoreType("PKCS12");
    sslOption.setKeyStoreValue("aaaaaa");
    sslOption.setTrustStore("trust.jks");
    sslOption.setTrustStoreType("JKS");
    sslOption.setTrustStoreValue("aaaaaa");
    sslOption.setSslCustomClass("microservice.demo.training21days.tls.DemoSSLCustomN1");
    return sslOption;
  }

  private static HttpClientOptions buildHttpClientOptions() {
    return new HttpClientOptions();
  }
}
```