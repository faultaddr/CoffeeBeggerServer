package com.faultaddr.coffeebeggerserver.utils;

import com.faultaddr.coffeebeggerserver.form.JoinGameForm;
import com.faultaddr.coffeebeggerserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class Utils {
  @Autowired static UserRepository userRepository;

  public static void PrintMessage(
      HttpServletResponse response, String successStr, String failedStr) {
    PrintWriter printWriter = null;
    try {
      printWriter = response.getWriter();
      printWriter.write(successStr);
    } catch (Exception e) {
      e.printStackTrace();
      printWriter.write(failedStr);
    } finally {
      printWriter.flush();
      printWriter.close();
    }
  }

  public static boolean checkForm(JoinGameForm form) {
    if (form == null) {
      return false;
    }
    if (form.getId() == null || form.getId().isEmpty()) {
      return false;
    } else {
      return form.getAvatar() != null;
    }
  }
}
