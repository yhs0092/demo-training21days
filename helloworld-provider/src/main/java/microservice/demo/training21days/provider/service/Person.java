package microservice.demo.training21days.provider.service;

public class Person {
  private String name;

  private long index;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getIndex() {
    return index;
  }

  public void setIndex(long index) {
    this.index = index;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Person{");
    sb.append("name='").append(name).append('\'');
    sb.append(", index=").append(index);
    sb.append('}');
    return sb.toString();
  }
}
