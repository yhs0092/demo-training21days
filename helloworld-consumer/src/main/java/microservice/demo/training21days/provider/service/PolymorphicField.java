package microservice.demo.training21days.provider.service;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.CLASS, include = As.EXISTING_PROPERTY,
    property = "classType", defaultImpl = PolymorphicField.class)
public class PolymorphicField {
  private String name;

  private int i;

  @JsonTypeInfo(use = Id.CLASS, include = As.EXISTING_PROPERTY,
      property = "classType", defaultImpl = Object.class)
  private Object o;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getI() {
    return i;
  }

  public void setI(int i) {
    this.i = i;
  }

  public Object getO() {
    return o;
  }

  public void setO(Object o) {
    this.o = o;
  }

  public String getClassType() {
    return this.getClass().getName();
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("PolymorphicField{");
    sb.append("name='").append(name).append('\'');
    sb.append(", i=").append(i);
    sb.append(", o=").append(o);
    sb.append('}');
    return sb.toString();
  }
}
