package microservice.demo.training21days.provider.service;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Person {
  private String name;

  private Gender gender;

  private long aa;

  private PolymorphicField pf;

  @JsonIgnore
  private String bb;

  private int cc;

  private String d;

  private String c;

  private int b;

  private String a;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public String getD() {
    return d;
  }

  public void setD(String d) {
    this.d = d;
  }

  public String getC() {
    return c;
  }

  public void setC(String c) {
    this.c = c;
  }

  public void setA(String a) {
    this.a = a;
  }

  public long getAa() {
    return aa;
  }

  public int getB() {
    return b;
  }

  public void setB(int b) {
    this.b = b;
  }

  public String getA() {
    return a;
  }

  public void setAa(long aa) {
    this.aa = aa;
  }

  public String getBb() {
    return bb;
  }

  public void setBb(String bb) {
    this.bb = bb;
  }

  public int getCc() {
    return cc;
  }

  public void setCc(int cc) {
    this.cc = cc;
  }

  public String getDd() {
    return null;
  }

  public void setDd(String dd) {
//    this.dd = dd;
  }

  public PolymorphicField getPf() {
    return pf;
  }

  public void setPf(PolymorphicField pf) {
    this.pf = pf;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Person{");
    sb.append("name='").append(name).append('\'');
    sb.append(", gender=").append(gender);
    sb.append(", aa=").append(aa);
    sb.append(", pf=").append(pf);
    sb.append(", bb='").append(bb).append('\'');
    sb.append(", cc=").append(cc);
    sb.append(", d='").append(d).append('\'');
    sb.append(", c='").append(c).append('\'');
    sb.append(", b=").append(b);
    sb.append(", a='").append(a).append('\'');
    sb.append(", dd='").append(getDd()).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
