package CalendarController;

import java.io.InputStream;
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
  private final Scanner in;
  private final IView view;
  private final IModel model;

  public CalendarController(IModel model, InputStream in, IView view) {
    this.model = model;
    this.view = view;
    this.in = new Scanner(in);

  }

  @Override
  public void go() {
    boolean quit = false;

    while (!quit) {
      //tell view to show options
      view.showOptions();
      //accept user input
      String option = in.next();
      in.nextLine();

      switch (option) {
        case "E":
          createEvent();
          break;
        case "Q":
          quit = true;
          break;
        default:
          view.showOptionError();
      }

    }

  }

  private void createEvent() {
    view.enterSubject();
    String subject =  in.nextLine();
    if (subject.isEmpty()) {
      throw new IllegalArgumentException("Subject is required");
    }

    view.enterStartDate();
    String startDateString = in.nextLine();
    if (startDateString.isEmpty()) {
      throw new IllegalArgumentException("Start date is required");
    }
    LocalDate startDate = LocalDate.parse(startDateString);

    view.enterStartTime();
    String startTimeString = in.nextLine();
    if (startTimeString.isEmpty()) {
      throw new IllegalArgumentException("Start time is required");
    }
    LocalTime startTime = LocalTime.parse(startTimeString);

    view.enterEndDate();
      String endd = in.nextLine();
      LocalDate endDate = startDate;
      if (!endd.isEmpty()) {
        endDate = LocalDate.parse(endd);
      }


    view.enterEndTime();
      String endt = in.nextLine();
      LocalTime endTime = LocalTime.MIDNIGHT;;
      if (!endt.isEmpty()) {
        endTime = LocalTime.parse(in.nextLine());
      }

    view.enterDescription();
    String description = in.nextLine();

    view.enterLocation();
    String location = in.nextLine();

    view.enterEventStatus();
    String bool = in.nextLine();
    boolean status = false;
    if (bool.isEmpty()) {
      status = Boolean.parseBoolean(in.nextLine());
    }

    model.createEvent(subject, startDate, startTime, endDate, endTime,
            description, location, status);
  }
}
