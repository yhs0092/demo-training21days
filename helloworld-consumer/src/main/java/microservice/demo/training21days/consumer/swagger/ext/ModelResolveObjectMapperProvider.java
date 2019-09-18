package microservice.demo.training21days.consumer.swagger.ext;

import org.apache.servicecomb.swagger.extend.DefaultModelResolveObjectMapperProvider;
import org.apache.servicecomb.swagger.extend.module.EnumModuleExt;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.util.Json;

public class ModelResolveObjectMapperProvider extends DefaultModelResolveObjectMapperProvider {
  @Override
  public ObjectMapper getMapper() {
    ObjectMapper mapper = Json.mapper();
    mapper.registerModule(new EnumModuleExt());
    mapper.setVisibility(PropertyAccessor.SETTER, Visibility.NONE);
    mapper.setVisibility(PropertyAccessor.GETTER, Visibility.NONE);
    mapper.setVisibility(PropertyAccessor.FIELD , Visibility.ANY );

    return  mapper;
  }
}
