package microservice.demo.training21days.consumer.service;

import java.util.HashMap;

import org.apache.servicecomb.common.rest.codec.AbstractRestObjectMapper;
import org.apache.servicecomb.common.rest.codec.RestObjectMapperFactory;
import org.apache.servicecomb.provider.pojo.RpcReference;
import org.apache.servicecomb.provider.pojo.RpcSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import microservice.demo.training21days.consumer.swagger.ext.ModelResolveObjectMapperProvider;
import microservice.demo.training21days.provider.service.Gender;
import microservice.demo.training21days.provider.service.GreetingResponse;
import microservice.demo.training21days.provider.service.Person;
import microservice.demo.training21days.provider.service.PolymorphicField;
import microservice.demo.training21days.provider.service.SubTypeField0;
import microservice.demo.training21days.provider.service.SubTypeField1;

@RpcSchema(schemaId = "POJOService")
public class POJOService {

  private static final Logger LOGGER = LoggerFactory.getLogger(POJOService.class);

  @RpcReference(microserviceName = "provider", schemaId = "POJOService")
  private microservice.demo.training21days.provider.service.POJOService pojoServiceClient;

  public Person test(Person person, String c, boolean b, int a) {
    LOGGER.info("test: person:[{}], c:[{}], b:[{}], a:[{}]", person, c, b, a);
    final Person response = pojoServiceClient.test(person, b, a, c);
    return response;
  }

  public static void main(String[] args) throws JsonProcessingException {
    final ModelResolveObjectMapperProvider modelResolveObjectMapperProvider = new ModelResolveObjectMapperProvider();
    final ObjectMapper mapper = modelResolveObjectMapperProvider.getMapper();
    final Person person = new Person();
    person.setName("Bob");
    person.setGender(Gender.MALE);
    person.setA("aaaa");
    person.setB(123);
    person.setC("cccc");
    person.setD("abcddd");
    person.setAa(1234);
    person.setCc(23);
    person.setPf(new SubTypeField1());
    final HashMap<String, PolymorphicField> oMap = new HashMap<>();
    oMap.put("k1", new SubTypeField0());
    oMap.put("k2", new SubTypeField1());
    person.getPf().setO(oMap);
    String jsonString = mapper.writeValueAsString(person);
    System.out.println(jsonString);
    final AbstractRestObjectMapper restObjectMapper = RestObjectMapperFactory.getRestObjectMapper();
    jsonString= restObjectMapper.writeValueAsString(person);
    System.out.println(jsonString);
  }
}
