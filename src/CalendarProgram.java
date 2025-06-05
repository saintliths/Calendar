import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;

import CalendarController.CalendarController;
import CalendarController.IController;
import CalendarModel.CalendarModel;
import CalendarModel.IModel;
import CalendarView.CalendarView;
import CalendarView.IView;

/**
 * This class represents our Calendar Program with main.
 */
public class CalendarProgram {
  public static void main(String[] args) {
    IModel model = new CalendarModel(new HashMap<>(), new HashMap<>());
    IView view = new CalendarView(System.out);
    System.out.println(args);

    if (args.length > 0 && args[1].equals("headless")) {
      System.setProperty("java.awt.headless", "true");
      String filePath = args[2];

    } else if  (args.length > 0 && args[1].equals("interactive")) {
      IController controller = new CalendarController(model, System.in,
              view);
      controller.go();

    }

  }
}
