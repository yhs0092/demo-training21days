package microservice.demo.training21days.consumer.boot;

import static org.apache.servicecomb.serviceregistry.api.Const.REGISTER_URL_PREFIX;
import static org.apache.servicecomb.serviceregistry.api.Const.URL_PREFIX;

import org.apache.servicecomb.core.BootListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.netflix.config.DynamicPropertyFactory;

import io.swagger.models.Swagger;

public class BasePathPrefixListener implements BootListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(BasePathPrefixListener.class);

  @Override
  public void onBootEvent(BootEvent bootEvent) {
    if (EventType.AFTER_PRODUCER_PROVIDER.equals(bootEvent.getEventType())
        && DynamicPropertyFactory.getInstance().getBooleanProperty(REGISTER_URL_PREFIX, false).get()) {
      modifyProducerSchemas(bootEvent);
    }
  }

  private void modifyProducerSchemas(BootEvent bootEvent) {
    bootEvent.getScbEngine().getProducerMicroserviceMeta().getSchemaMetas().values().forEach(
        schemaMeta -> basePathAddUrlPrefix(schemaMeta.getSwagger())
    );
  }

  private void basePathAddUrlPrefix(Swagger swagger) {
    String urlPrefix = System.getProperty(URL_PREFIX);
    if (!StringUtils.isEmpty(urlPrefix) && !swagger.getBasePath().startsWith(urlPrefix)) {
      LOGGER.info("Add swagger base path prefix for {} with {}", swagger.getBasePath(), urlPrefix);
      swagger.setBasePath(urlPrefix + swagger.getBasePath());
    }
  }
}
