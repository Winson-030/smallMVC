package cn.adalab.exercise.autumn.framework;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

  public String fileName;

  public Map<String, Object> map = new HashMap<>();
  /**
   * @param view view的名字，此名字将用于查找html模版文件
   */
  public ModelAndView(String view) {
    fileName = view;
  }

  /**
   * 向Model中添加一个属性
   *
   * @param name  属性名
   * @param value 属性值
   */
  public void addAttribute(String name, Object value) {
    map.put(name, value);
  }
}
