package microservice.demo.training21days.provider.service;

public class NormalResponse {
  String msg;

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("NormalResponse{");
    sb.append("msg='").append(msg).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
