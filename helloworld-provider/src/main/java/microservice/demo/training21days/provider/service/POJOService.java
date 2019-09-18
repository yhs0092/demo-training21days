package microservice.demo.training21days.provider.service;

import org.apache.servicecomb.provider.pojo.RpcSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RpcSchema(schemaId = "POJOService")
public class POJOService {

  private static final Logger LOGGER = LoggerFactory.getLogger(POJOService.class);

  public Person test(Person person, boolean b, int a, String c) {
    LOGGER.info("test: person:[{}], b:[{}], a:[{}], c:[{}]", person, b, a, c);
    return person;
  }
}
