package microservice.demo.training21days.provider.service;

public class SubTypeField1 extends PolymorphicField {
  private long l;
  private String ss1;

  public long getL() {
    return l;
  }

  public void setL(long l) {
    this.l = l;
  }

  public String getSs1() {
    return ss1;
  }

  public void setSs1(String ss1) {
    this.ss1 = ss1;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("SubTypeField1{");
    sb.append("l=").append(l);
    sb.append(", ss1='").append(ss1).append('\'');
    sb.append(", name='").append(getName()).append('\'');
    sb.append(", i=").append(getI());
    sb.append(", o=").append(getO());
    sb.append('}');
    return sb.toString();
  }
}
