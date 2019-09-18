package microservice.demo.training21days.provider.service;

public class SubTypeField0 extends PolymorphicField {
  private String ss0;

  public String getSs0() {
    return ss0;
  }

  public void setSs0(String ss0) {
    this.ss0 = ss0;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("SubTypeField0{");
    sb.append("ss0='").append(ss0).append('\'');
    sb.append(", name='").append(getName()).append('\'');
    sb.append(", i=").append(getI());
    sb.append(", o=").append(getO());
    sb.append('}');
    return sb.toString();
  }
}
