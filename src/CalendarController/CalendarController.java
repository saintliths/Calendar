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
  private Scanner in;
  private IView view;
  private IModel model;

  public CalendarController(IModel model, InputStream in, IView view) {
    this.model = model;
    this.view = view;
    this.in = new Scanner(in);

  }

  @Override
  public void go() {
    boolean quit = false;

    while (!quit) {
      //tell view to show the string so far.
      view.showString(this.model.getInput());
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
      view.showOptionError();
      view.enterSubject();
    }

    view.enterStartDate();
    String startd = in.nextLine();
    if (startd.isEmpty()) {
      view.showOptionError();
    }
    LocalDate startDate = LocalDate.parse(in.nextLine());

    view.enterStartTime();
    String startt = in.nextLine();
    while (startt.isEmpty()) {
      view.showOptionError();
    }
    LocalTime startTime = LocalTime.parse(in.nextLine());

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
