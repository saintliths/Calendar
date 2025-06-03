package CalendarController;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import CalendarModel.Event;
import CalendarModel.IModel;
import CalendarView.IView;

/**
 * This class represents the controller implementation for a calendar.
 */
public class CalendarController implements IController {
  private final InputStream in;
  private final IView view;
  private final IModel model;

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
      }



    }
  }
}
