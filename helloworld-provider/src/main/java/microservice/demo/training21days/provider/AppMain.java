package microservice.demo.training21days.provider;

import java.util.ArrayList;

import org.apache.servicecomb.foundation.common.net.IpPort;
import org.apache.servicecomb.foundation.common.utils.BeanUtils;
import org.apache.servicecomb.foundation.common.utils.Log4jUtils;
import org.apache.servicecomb.serviceregistry.RegistryUtils;
import org.apache.servicecomb.serviceregistry.client.http.HttpClientPool;
import org.apache.servicecomb.serviceregistry.config.ServiceRegistryConfig;

import io.vertx.core.http.HttpClientOptions;

public class AppMain {
  public static void main(String[] args) throws Exception {
    final ArrayList<IpPort> ipPorts = new ArrayList<>();
    final HttpClientOptions httpClientOptions = new HttpClientPool(null, null).createHttpClientOptions();
    httpClientOptions.setSsl(false);
    ipPorts.add(new IpPort("127.0.0.1", 30100));
    RegistryUtils.addExtraServiceRegistryConfig(new ServiceRegistryConfig()
        .setRegistryName("local-sc")
        .setIpPorts(ipPorts)
        .setRegistryClientOptions(httpClientOptions)
    );
    Log4jUtils.init(); // 初始化默认的日志组件
    BeanUtils.init();  // 加载Spring bean定义文件，正式开始启动流程
  }
}
