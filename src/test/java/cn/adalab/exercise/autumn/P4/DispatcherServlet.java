package cn.adalab.exercise.autumn.P4;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class DispatcherServlet extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try (Writer w = resp.getWriter()) {
      w.write("Get adalab\n");
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    try (Writer w = resp.getWriter()) {
      w.write("Post adalab\n");
    }
  }
}
