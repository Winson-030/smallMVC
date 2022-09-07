package cn.adalab.exercise.autumn.P6.chinese;

import cn.adalab.exercise.autumn.framework.Controller;
import cn.adalab.exercise.autumn.framework.GetMapping;

@Controller
public class ChineseHelloController {
  @GetMapping("/hello")
  public String hello() {
    return "你好";
  }

  @GetMapping("/world")
  public String world() {
    return "世界";
  }
}
