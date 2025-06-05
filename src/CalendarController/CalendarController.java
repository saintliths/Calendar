package CalendarController;

import java.io.InputStream;
import java.util.Scanner;

import CalendarModel.IModel;
import CalendarView.IView;

/**
 * This class represents the controller implementation for a calendar.
 */
public class CalendarController implements IController {
  private final InputStream in;
  private final IView view;
  private final IModel model;

  /**
   * Constructs a CalendarController object.
   *
   * @param model the model that is passed into this controller
   * @param in    the user input
   * @param view  the view that is passed into this controller
   */
  public CalendarController(IModel model, InputStream in, IView view) {
    this.model = model;
    this.view = view;
    this.in = in;

  }

  @Override
  public void go() {
    boolean quit = false;
    Scanner input = new Scanner(in);
    String in = input.nextLine();
    System.out.println(in);

    // create event <eventSubject> from <dateStringTtimeString> to <dateStringTtimeString>

    if (in.equals("exit")) {
      quit = true;
    }

    while (!quit) {
      if (in.startsWith("create event")) {
        if (in.contains("repeats")) {
          model.createEventSeries(in);
        } else {
          model.createEvent(in);
        }
      } else if (in.startsWith("edit event")) {
        model.editEvent(in);

      } else if (in.startsWith("edit events")) {
        model.editEventSeries(in);

      } else if (in.startsWith("edit series")) {
        model.editSeries(in);

      } else if (in.startsWith("print events")) {
        view.printEvents(model.printEvents(in));

      } else if (in.startsWith("show status")) {
        view.showStatus(model.showStatus(in));

      } else {
        view.showOptionError();
      }

    }
  }
}
