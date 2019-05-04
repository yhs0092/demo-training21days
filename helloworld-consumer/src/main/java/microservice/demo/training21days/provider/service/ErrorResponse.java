package microservice.demo.training21days.provider.service;

public class ErrorResponse {
  private String errMsg;

  private int code;

  public String getErrMsg() {
    return errMsg;
  }

  public void setErrMsg(String errMsg) {
    this.errMsg = errMsg;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("ErrorResponse{");
    sb.append("errMsg='").append(errMsg).append('\'');
    sb.append(", code=").append(code);
    sb.append('}');
    return sb.toString();
  }
}
