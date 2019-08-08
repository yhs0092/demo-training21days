package microservice.demo.training21days.provider.service;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestSchema(schemaId = "hello")
@RequestMapping(path = "/provider/v0")
public class HelloService {
  @RequestMapping(path = "/hello/{name}", method = RequestMethod.GET)
  public String sayHello(@PathVariable(value = "name") String name) {
    return name;
  }

  @PostMapping(path = "/greeting")
  public Person greeting(@RequestBody Person person) {
    return person;
  }
}
