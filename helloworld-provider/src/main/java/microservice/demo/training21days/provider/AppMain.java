package microservice.demo.training21days.provider;

import org.apache.servicecomb.foundation.common.utils.BeanUtils;

public class AppMain {
  public static void main(String[] args) {
    BeanUtils.init();  // 加载Spring bean定义文件，正式开始启动流程
  }
}
