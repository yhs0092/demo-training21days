package microservice.demo.training21days.provider.handler;

import org.apache.servicecomb.core.Invocation;
import org.apache.servicecomb.foundation.vertx.http.HttpServletRequestEx;
import org.apache.servicecomb.foundation.vertx.http.HttpServletResponseEx;
import org.apache.servicecomb.swagger.invocation.Response;

public interface HttpServerFilter {
  /**
   * HttpServerFilter的优先级
   */
  int getOrder();

  /**
   * 是否启用该Filter，默认启用
   */
  default boolean enabled() {
    return true;
  }

  /**
   * 微服务作为provider接到请求后，会依次调用各HttpServerFilter的afterReceiveRequest方法进行处理。
   * 注意：如果您不了解框架的底层逻辑，建议对于invocation和requestEx两个参数只读不写，否则很容易导致意料之外的问题。
   * @param invocation 包含了本次请求的相关信息
   * @param requestEx  包含了一些请求的原始信息。例如，不在服务契约中声明的header是不会存放在Invocation中传递给下游的provider端
   * Handler链的，但是在requestEx里我们可以拿到这些header信息
   * @return 如果返回null，则该请求还会继续向下执行；否则会将该方法返回的Response作为应答发给调用方，不再执行接下来的请求处理逻辑
   */
  Response afterReceiveRequest(Invocation invocation, HttpServletRequestEx requestEx);

  /**
   * 在应答发送给调用方之前，依次调用各HttpServerFilter的beforeSendResponse方法进行处理。默认不处理。
   * 注意：如果您不了解框架的底层逻辑，建议对于invocation和requestEx两个参数只读不写。
   * @param invocation 与afterReceiveRequest方法接收到的invocation相同
   * @param responseEx 即将发送给调用方的应答。responseEx内可以自定义一些返回的header信息，但建议不要修改body。
   */
  default void beforeSendResponse(Invocation invocation, HttpServletResponseEx responseEx) {
  }
}