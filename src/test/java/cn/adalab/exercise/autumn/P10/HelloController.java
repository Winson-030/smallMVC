package cn.adalab.exercise.autumn.P10;

import cn.adalab.exercise.autumn.framework.Controller;
import cn.adalab.exercise.autumn.framework.GetMapping;
import cn.adalab.exercise.autumn.framework.ModelAndView;
import cn.adalab.exercise.autumn.framework.RequestParam;

@Controller
public class HelloController {
  @GetMapping("/hello")
  public ModelAndView hello(@RequestParam("name") String name) {
    var mv = new ModelAndView("hello");
    mv.addAttribute("name", name);
    return mv;
  }

  @GetMapping("/goodbye")
  public String goodbye() {
    return "bye";
  }
}
