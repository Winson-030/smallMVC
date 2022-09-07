package cn.adalab.exercise.autumn.P11;

import cn.adalab.exercise.autumn.framework.Controller;
import cn.adalab.exercise.autumn.framework.GetMapping;
import cn.adalab.exercise.autumn.framework.ModelAndView;
import cn.adalab.exercise.autumn.framework.RequestParam;

@Controller
public class MVCController {
  @GetMapping("/echo")
  public ModelAndView hello(@RequestParam("name") String name, @RequestParam("country") String country) {
    var mv = new ModelAndView("echo");
    mv.addAttribute("name", name);
    mv.addAttribute("country", country);
    return mv;
  }
}
