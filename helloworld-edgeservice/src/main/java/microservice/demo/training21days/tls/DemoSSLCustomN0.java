package microservice.demo.training21days.tls;

import java.net.URL;

import org.apache.servicecomb.foundation.ssl.SSLCustom;

public class DemoSSLCustomN0 extends SSLCustom {
  @Override
  public char[] decode(char[] encrypted) {
    return encrypted;
  }

  @Override
  public String getFullPath(String filename) {
    URL url = Thread.currentThread().getContextClassLoader().getResource("certificates-n0/" + filename);
    if (url == null) {
      return filename;
    }

    return url.getPath();
  }
}
