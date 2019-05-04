package microservice.demo.training21days.edge.dispatcher;

import java.util.Map.Entry;

import org.apache.servicecomb.edge.core.AbstractEdgeDispatcher;
import org.apache.servicecomb.foundation.vertx.VertxUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.config.DynamicPropertyFactory;

import io.vertx.core.Context;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class DemoDispatcher extends AbstractEdgeDispatcher {

//  private static final Logger LOGGER = LoggerFactory.getLogger(DemoDispatcher.class);

  private static Vertx vertx = VertxUtils.getOrCreateVertxByName("transport",
      null);

  @Override
  public int getOrder() {
    return 0;
  }

  public DemoDispatcher() {
  }

  @Override
  public void init(Router router) {
    router.routeWithRegex("/demo/.*").failureHandler(routingContext -> {
      if (!routingContext.failed()) {
//        LOGGER.warn("should be aware, seems like a fake failure");
        return;
      }
      if (routingContext.response().ended() || routingContext.response().closed()) {
//        LOGGER.error("get an exception but cannot response", routingContext.failure());
        return;
      }
      routingContext.response().setStatusCode(599)
          .end("unknown error" + (null == routingContext.failure() ? "" : routingContext.failure().getMessage()));
    }).handler(this::onRequest);
  }

  private void onRequest(RoutingContext routingContext) {
    String requestUri = routingContext.request().uri().substring("/demo".length());
    HttpClientRequest httpClientRequest = getHttpClient()
        .request(routingContext.request().method(), 9090, "127.0.0.1", requestUri, httpClientResponse -> {

          httpClientResponse.bodyHandler(body -> {
//            LOGGER.info("response body to server response");
            copyHeader(routingContext, httpClientResponse);
            routingContext.response().end(body);
          });
          httpClientResponse.exceptionHandler(t -> {
//            LOGGER.error("clientResponse catch error", t);
            if (!(routingContext.response().closed() || routingContext.response().ended())) {
              routingContext.response().end();
            }
          });
          httpClientResponse.endHandler(v->{
            throw new IllegalStateException("test!!!!");
          });
        });
    httpClientRequest.headers().setAll(routingContext.request().headers());
    httpClientRequest.exceptionHandler(t -> {
//      LOGGER.error("catch a throwable", t);
      if (!(routingContext.response().closed() || routingContext.response().ended())) {
//        LOGGER.warn("HttpServerResponse is not closed, try to end it");
        routingContext.response().end();
      }
    });
    httpClientRequest.endHandler(v -> {
    });
    httpClientRequest.connectionHandler(clientConnection -> {
//      LOGGER.info("get connection: {}", clientConnection);
      clientConnection.closeHandler(v -> {
//        LOGGER.info("connection is closed, {}", clientConnection.toString());
      });
    });

    routingContext.request().exceptionHandler(t -> {
//      LOGGER.error("catch an exception in server request exception handler: {}", t.getMessage());
    });
    routingContext.request().handler(requestBodyBufferPart -> {
//      LOGGER.info("write buffer part to httpClientRequest: {}", requestBodyBufferPart);
      httpClientRequest.write(requestBodyBufferPart);
//      throw new NullPointerException("test!!");
    });
    routingContext.request().endHandler(v -> {
//      LOGGER.info("end writing buffer to httpClientRequest");
      httpClientRequest.end();
    });
  }

  private void copyHeader(RoutingContext routingContext, HttpClientResponse response) {
    for (Entry<String, String> headers : response.headers().entries()) {
      routingContext.response().putHeader(headers.getKey(), headers.getValue());
    }
  }

  private HttpClient getHttpClient() {
    Context context = Vertx.currentContext();
    HttpClient httpClient = context.get("cbc-http-client");
    if (httpClient == null) {
      HttpClientOptions httpOptions = new HttpClientOptions();

      httpOptions.setIdleTimeout(DynamicPropertyFactory.getInstance()
          .getIntProperty(
              "servicecomb.rest.client.connection.idleTimeoutInSeconds",
              30)
          .get());
      httpOptions.setMaxPoolSize(DynamicPropertyFactory.getInstance()
          .getIntProperty(
              "servicecomb.rest.client.connection.maxPoolSize",
              5)
          .get());

      httpOptions.setMaxWaitQueueSize(DynamicPropertyFactory.getInstance()
          .getIntProperty(
              "servicecomb.rest.server.edge-vertical-pool-size",
              -1)
          .get());

      httpOptions.setMaxHeaderSize(DynamicPropertyFactory.getInstance()
          .getIntProperty("servicecomb.rest.client.maxHeaderSize",
              8 * 1024)
          .get());

      httpOptions.setMaxInitialLineLength(DynamicPropertyFactory
          .getInstance()
          .getIntProperty(
              "servicecomb.rest.server.maxInitialLineLength",
              8 * 1024)
          .get());

      httpOptions.setKeepAlive(DynamicPropertyFactory.getInstance()
          .getBooleanProperty(
              "servicecomb.rest.client.connection.keepAlive",
              true)
          .get());

      httpClient = vertx.createHttpClient(httpOptions);

      context.put("cbc-http-client", httpClient);
    }
    return httpClient;
  }
}
