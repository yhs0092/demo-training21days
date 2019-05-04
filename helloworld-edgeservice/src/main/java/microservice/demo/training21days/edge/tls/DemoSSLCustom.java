package microservice.demo.training21days.edge.tls;

import java.net.URL;

import org.apache.servicecomb.foundation.ssl.SSLCustom;

public class DemoSSLCustom extends SSLCustom {
  @Override
  public char[] decode(char[] encrypted) {
    return encrypted;
  }

  @Override
  public String getFullPath(String filename) {
    URL url = Thread.currentThread().getContextClassLoader().getResource("certificates/" + filename);
    if (url == null) {
      return filename;
    }

    return url.getPath();
  }
}
