package cn.adalab.exercise.autumn.P7;

import cn.adalab.exercise.autumn.framework.Controller;
import cn.adalab.exercise.autumn.framework.GetMapping;

@Controller
public class GoodbyeController {
  @GetMapping("/goodbye")
  public String hello() {
    return "goodbye";
  }

  public String doGet() {
    return "good morning";
  }
}
