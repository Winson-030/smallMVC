package cn.adalab.exercise.autumn.P8;

import cn.adalab.exercise.autumn.framework.Controller;
import cn.adalab.exercise.autumn.framework.GetMapping;
import cn.adalab.exercise.autumn.framework.RequestParam;

@Controller
public class HelloController {
  @GetMapping("/hello")
  public String hello(@RequestParam("name") String name, @RequestParam("from") String country) {
    return "hello " + name + " from " + country;
  }
}
