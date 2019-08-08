package microservice.demo.training21days.perfclient;

import org.apache.servicecomb.core.BootListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import microservice.demo.training21days.perfclient.service.PressureSource;

@Component
public class EventListener implements BootListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(EventListener.class);

  @Autowired
  private PressureSource pressureSource;

  @Override
  public void onBootEvent(BootEvent event) {
    if (EventType.AFTER_REGISTRY.equals(event.getEventType())) {
      LOGGER.info("start test!");
      pressureSource.startPerfTest();
    } else if (EventType.BEFORE_CLOSE.equals(event.getEventType())) {
      try {
        pressureSource.stopPerfTest();
      } catch (InterruptedException e) {
        LOGGER.error("failed to stop test executor", e);
      }
    }
  }
}
