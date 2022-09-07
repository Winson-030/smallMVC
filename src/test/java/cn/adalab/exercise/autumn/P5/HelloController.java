package cn.adalab.exercise.autumn.P5;

import cn.adalab.exercise.autumn.framework.Controller;

@Controller
public class HelloController {
  public String doGet() {
    return "hello get";
  }

  public String doPost() {
    return "hello post";
  }
}
