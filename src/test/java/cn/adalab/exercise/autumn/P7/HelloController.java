package cn.adalab.exercise.autumn.P7;

import cn.adalab.exercise.autumn.framework.Controller;
import cn.adalab.exercise.autumn.framework.GetMapping;

@Controller
public class HelloController {
  @GetMapping("/hello")
  public String hello() {
    return "hello";
  }
}
