package cn.adalab.exercise.autumn.P6.english;

import cn.adalab.exercise.autumn.framework.Controller;
import cn.adalab.exercise.autumn.framework.GetMapping;

@Controller
public class EnglishHelloController {
  @GetMapping("/hello")
  public String hello() {
    return "hello";
  }

  @GetMapping("/world")
  public String world() {
    return "world";
  }
}
