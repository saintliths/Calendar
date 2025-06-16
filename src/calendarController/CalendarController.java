package calendarcontroller;

import java.util.Scanner;

import calendarmodel.IModel2;
import calendarview.IView;
import calendarview.IView2;

/**
 * This class represents the controller implementation for a calendar.
 */
public class CalendarController implements IController {
  private final Readable in;
  private final IView2 view;
  private final IModel2 model;

  /**
   * Constructs a CalendarController object.
   *
   * @param model the model that is passed into this controller
   * @param in    the user input
   * @param view  the view that is passed into this controller
   */
  public CalendarController(IModel2 model, Readable in, IView2 view) {
    this.model = model;
    this.view = view;
    this.in = in;

  }

  @Override
  public void control() {
    boolean quit = false;
    view.printOptions();

    Scanner input = new Scanner(in);

    if (in.equals("exit")) {
      quit = true;
    }

    while (!quit && input.hasNextLine()) {
      String in = input.nextLine();
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

      } else if (in.startsWith("create calendar")) {
        model.createCalendar(in);

      } else if (in.startsWith("edit calendar")) {
        model.editCalendar(in);

      } else if (in.startsWith("use calendar")) {
        model.useCalendar(in);

      } else if (in.startsWith("copy event")) {
        model.copySpecificEvent(in);

      } else if (in.startsWith("copy events on")) {
        model.copyMultipleEvents(in);

      } else if (in.startsWith("copy events between")) {
        model.copyEventsBetween(in);

      } else {
        view.showOptionError();
        break;
      }

    }
  }
}
