import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Scanner;

import calendarcontroller.CalendarController;
import calendarcontroller.IController;
import calendarmodel.CalendarModel;
import calendarmodel.IModel;
import calendarmodel.IModel2;
import calendarmodel.MultipleCalendar;
import calendarview.CalendarView;
import calendarview.IView;

/**
 * This class represents our Calendar Program with main.
 */
public class CalendarProgram {

  /**
   * Main method that runs the program.
   * @param args command line arguments
   */
  public static void main(String[] args) throws FileNotFoundException {
    IModel model = new CalendarModel(new HashMap<>(), new HashMap<>());
    ZoneId est = ZoneId.of("EST");
    IModel2 model2 = new MultipleCalendar("Basic", est, model);
    IView view = new CalendarView(System.out);

    if (args.length > 0 && args[1].equals("headless")) {
      FileReader file = new FileReader(args[2]);
      IController controller = new CalendarController(model2, file, view);
      controller.control();

    } else if  (args.length > 0 && args[1].equals("interactive")) {
      Readable in = new InputStreamReader(System.in);
      IController controller = new CalendarController(model2, in, view);
      controller.control();

    }

  }
}
