package microservice.demo.training21days.provider.bootevent;

import org.apache.servicecomb.core.BootListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomBootEventListener implements BootListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(CustomBootEventListener.class);

  public void onBootEvent(BootEvent bootEvent) {
    if (!EventType.AFTER_REGISTRY.equals(bootEvent.getEventType())) {
      return;
    }

    LOGGER.info("=============================");
    LOGGER.info("Service startup completed!");
    LOGGER.info("=============================");
  }
}
