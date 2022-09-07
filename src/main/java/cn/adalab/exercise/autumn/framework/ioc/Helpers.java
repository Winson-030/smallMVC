package cn.adalab.exercise.autumn.framework.ioc;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

final class Helpers {
  private Helpers() {
    throw new AssertionError();
  }

  // 获取某个类的无参构造函数（必须是public的）
  public static <T> Constructor<T> noArgConstructor(Class<T> clazz) {
    try {
      return clazz.getConstructor();
    } catch (NoSuchMethodException e) {
      throw new RuntimeException("找不到类" + clazz.getName() + "的公开无参构造函数");
    }
  }

  // 调用类的构造函数，返回该类的一个实例
  public static <T> T newInstance(Constructor<T> constructor, Object... args) {
    try {
      return constructor.newInstance(args);
    } catch (IllegalArgumentException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e.getCause());
    }
  }

  // 根据全限定类名获取对应的Class对象
  public static Class<?> getClass(String className) {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e.getCause());
    }
  }

  // 获取某个类所在的包的（所有）路径
  // 注意：由于classpath中可以有多个文件夹和JAR，即便是完全相同的包，也可能对应多个不同路径，因此返回的是List
  public static List<Path> getPackagePaths(Class<?> clazz) {
    var packageName = clazz.getPackageName();
    var folderName = packageName.replace('.', File.separatorChar);
    var classLoader = clazz.getClassLoader();
    try {
      return Collections.list(classLoader.getResources(folderName)).stream().map(url -> {
        try {
          return Path.of(url.toURI());
        } catch (URISyntaxException e) {
          throw new RuntimeException("无法查找" + packageName + "中的类");
        }
      }).collect(Collectors.toList());
    } catch (IOException e) {
      throw new RuntimeException("无法查找" + packageName + "中的类");
    }
  }
}
