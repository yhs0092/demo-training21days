package microservice.demo.training21days.provider.handler;

import org.apache.servicecomb.core.Invocation;
import org.apache.servicecomb.foundation.vertx.http.HttpServletRequestEx;
import org.apache.servicecomb.foundation.vertx.http.HttpServletResponseEx;
import org.apache.servicecomb.swagger.invocation.Response;

public interface HttpClientFilter {
  /**
   * 是否启用该Filter，默认启用
   */
  default boolean enabled() {
    return true;
  }

  /**
   * HttpClientFilter的优先级
   */
  int getOrder();

  /**
   * 微服务作为consumer发送请求时，会依次调用各HttpClientFilter的beforeSendRequest方法进行处理。
   * @param invocation 包含了本次请求的相关信息，包括服务契约相关的信息
   * @param requestEx  本次请求相关的参数信息，可以在这里自定义一些header信息，但建议不要修改body
   */
  void beforeSendRequest(Invocation invocation, HttpServletRequestEx requestEx);

  /**
   * 接收到服务端的应答后，会依次调用各HttpClientFilter的afterReceiveResponse方法进行处理。
   * @param invocation 与afterReceiveRequest方法接收到的invocation相同
   * @param responseEx 这里包含了一些应答的原始数据信息，如header等
   * @return 如果返回null则继续调用下一个HttpClientFilter处理，否则将该方法返回的Response作为应答返回给业务逻辑
   */
  Response afterReceiveResponse(Invocation invocation, HttpServletResponseEx responseEx);
}