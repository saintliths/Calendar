import java.io.InputStreamReader;

import CalendarController.CalendarController;
import CalendarController.IController;
import CalendarModel.CalendarModel;
import CalendarModel.IModel;
import CalendarView.CalendarView;
import CalendarView.IView;

public class CalendarProgram {
  public static void main(String[] args) {
    IModel model = new CalendarModel();
    IView view = new CalendarView(System.out);
    System.out.println(args);

    if (args.length > 0 && args[1].equals("headless")) {
     //

    } else if  (args.length > 0 && args[1].equals("interactive")) {
      IController controller = new CalendarController(model, System.in,
              view);
      controller.go();

    }



  }
}
