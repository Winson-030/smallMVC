package cn.adalab.exercise.autumn.app;

import cn.adalab.exercise.autumn.framework.GetMapping;
import cn.adalab.exercise.autumn.framework.ModelAndView;
import cn.adalab.exercise.autumn.framework.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author winson
 */
public class DispatcherServlet extends HttpServlet {

    //符合uri的类
    public Class<?> class1;
    //存储待controller注解的类集合
    public List<Class<?>> classList;

    public DispatcherServlet(Class<?> class1) {
        this.class1 = class1;
    }

    public DispatcherServlet(List<Class<?>> classList) {
        this.classList = classList;
    }


    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //根据uri获取class
        String uri = request.getRequestURI();
        for (Class<?> aClass : classList) {
            Method[] methods = aClass.getDeclaredMethods();
            for (Method method : methods) {
                //先判断注解是否存在，再判断注解是否符合要求
                if (method.isAnnotationPresent(GetMapping.class)) {
                    String value = method.getDeclaredAnnotation(GetMapping.class).value();
                    if (value.equals(uri)) {
                        class1 = aClass;
                    }
                }
            }
        }


        String method = request.getMethod();
        if (method.equals("GET")) {
            doGet(request, response);
        } else if (method.equals("POST")) {
            doPost(request, response);
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //获取请求的uri，并转换为字符串
        String uri = req.getRequestURI();
        Method[] methods = class1.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(GetMapping.class)) {
                //            获取函数的注解
                String value = method.getDeclaredAnnotation(GetMapping.class).value();
//            比较注解
                if (value.equals(uri)) {
                    try {
                        List paramList = new ArrayList();
                        Parameter[] parameters = method.getParameters();
                        for (Parameter parameter : parameters) {
                            paramList.add(req.getParameter(parameter.getAnnotation(RequestParam.class).value()));
                        }
                        //参数数组
                        Object[] paramsarray = paramList.toArray();
//                        创建实例
//                        Object invoke = method.invoke(class1.getDeclaredConstructor().newInstance(), paramsarray);
//                        Class<?> aClass = invoke.getClass();
                        Class<?> type = method.getReturnType();
                        String[] strings = type.getName().split("\\.");
                        String className = strings[strings.length - 1];
//              判断类名
                        if ("ModelAndView".equals(className)) {
                            ModelAndView modelAndView = (ModelAndView) method.invoke(class1.getDeclaredConstructor().newInstance(), paramsarray);
                            String filePath = modelAndView.fileName;
                            String name = (String) modelAndView.map.get("name");
                            String country = (String) modelAndView.map.get("country");
                            File file = new File("src/test/resources/templates/" + filePath + ".html");
                            BufferedReader reader = new BufferedReader(new FileReader(file));
                            resp.setCharacterEncoding("UTF-8");
                            var w = resp.getWriter();
//                            新建一个字符串装载字符流
                            String readLine;
                            StringBuilder str = new StringBuilder();
                            while ((readLine = reader.readLine()) != null) {
                                str.append(readLine);
                            }
//返回替换变量后的字符串流
                            String s = str.toString().replaceAll("\\$\\{name}", name);
                            String s1 = s.replaceAll("\\$\\{country}", country);

                            w.write(s1 + "\n");
                            w.flush();
                            w.close();
                        } else if (className.equals("Person")) {

                            Object invoke = method.invoke(class1.getDeclaredConstructor().newInstance(), paramsarray);
//                            jackson获取实例转json
                            ObjectMapper mapper = new ObjectMapper();
                            String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(invoke);
                            resp.setCharacterEncoding("UTF-8");
                            var w = resp.getWriter();
                            w.write(jsonString + "\n");
                            w.flush();
                            w.close();
                        } else {

                            String result = null;
                            result = (String) method.invoke(class1.getDeclaredConstructor().newInstance(), paramsarray);
                            //根据请求获取文件路径
                            String filePath = new String("src/test/resources/templates/" + result + ".html");
                            File file = new File(filePath);
                            BufferedReader reader = new BufferedReader(new FileReader(file));
                            resp.setCharacterEncoding("UTF-8");
                            var w = resp.getWriter();
                            String readLine = null;
                            while ((readLine = reader.readLine()) != null) {
                                w.write(readLine + "\n");
                            }
                            w.flush();
                            w.close();
                        }
                    } catch (IllegalAccessException | InvocationTargetException | InstantiationException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String result = null;
        Method[] methods = class1.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().equals("doPost")) {
                try {
                    result = (String) method.invoke(class1.getDeclaredConstructor().newInstance());
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException |
                         NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        resp.setCharacterEncoding("UTF-8");
        var w = resp.getWriter();
        w.write(result + "\n");
        w.flush();
        w.close();
    }
}
