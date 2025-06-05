import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.FileInputStream;
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


  /**
   * Runs the program with the command line arguments and passes it to the controller.
   * @param args command line arguments
   * @throws FileNotFoundException
   */
  public static void main(String[] args) throws FileNotFoundException {
    IModel model = new CalendarModel(new HashMap<>(), new HashMap<>());
    IView view = new CalendarView(System.out);
    System.out.println(args);

    if (args.length > 0 && args[1].equals("headless")) {
      InputStream in = new FileInputStream(args[2]);
      IController controller = new CalendarController(model, in, view);
      controller.go();

    } else if  (args.length > 0 && args[1].equals("interactive")) {
      IController controller = new CalendarController(model, System.in,
              view);
      controller.go();
    }

  }
}
