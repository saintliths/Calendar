import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.time.ZoneId;
import java.util.HashMap;

import calendarcontroller.CalendarController;
import calendarcontroller.IController;
import calendarmodel.CalendarModel;
import calendarmodel.IModel;
import calendarmodel.IModel2;
import calendarmodel.MultipleCalendar;
import calendarview.CalendarView;
import calendarview.IView;
import calendarview.IView2;
import calendarview.NewCalendarView;

/**
 * This class represents our Calendar Program with main.
 */
public class CalendarProgram {

  /**
   * Main method that runs the program.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) throws FileNotFoundException {
    IModel model = new CalendarModel(new HashMap<>(), new HashMap<>());
    ZoneId est = ZoneId.of("America/New_York");
    IModel2 model2 = new MultipleCalendar("Basic", est, model);
    IView2 view = new NewCalendarView(new CalendarView(System.out));

    if (args.length > 0 && args[1].equals("headless")) {
      FileReader file = new FileReader(args[2]);
      IController controller = new CalendarController(model2, file, view);
      controller.control();

    } else if (args.length > 0 && args[1].equals("interactive")) {
      Readable in = new InputStreamReader(System.in);
      IController controller = new CalendarController(model2, in, view);
      controller.control();
    }

  }
}
