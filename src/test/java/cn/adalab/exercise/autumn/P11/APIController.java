package cn.adalab.exercise.autumn.P11;

import cn.adalab.exercise.autumn.framework.*;

@RestController
public class APIController {
  @GetMapping("/api/echo")
  public Person hello(@RequestParam("name") String name, @RequestParam("country") String country) {
    var p = new Person();
    p.setName(name);
    p.setCountry(country);
    return p;
  }
}
