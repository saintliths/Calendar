package calendarview;

import java.io.PrintStream;
import java.util.List;

/**
 * This class represents the implementation for the view of the calendar. It prints out
 * things to the console.
 */
public class CalendarView implements IView {
  private final PrintStream out;

  /**
   * Constructs a CalendarView object.
   *
   * @param out the output stream
   */
  public CalendarView(PrintStream out) {
    this.out = out;
  }

  @Override
  public void printOptions() {
    out.println("Hi! Welcome to our Calendar. The supported commands are: ");
    out.println("create event <eventSubject> from <dateStringTtimeString> to "
            + "<dateStringTtimeString>");
    out.println("create event <eventSubject> from <dateStringTtimeString> to "
            + "<dateStringTtimeString> repeats <weekdays> for <N> times");
    out.println("create event <eventSubject> from <dateStringTtimeString> to "
            + "<dateStringTtimeString> repeats <weekdays> until <dateString>");
    out.println("create event <eventSubject> on <dateString>");
    out.println("create event <eventSubject> on <dateString> repeats <weekdays> for <N> times");
    out.println("create event <eventSubject> on <dateString> repeats <weekdays> until "
            + "<dateString>");
    out.println("edit event <property> <eventSubject> from <dateStringTtimeString> to "
            + "<dateStringTtimeString> with <NewPropertyValue>");
    out.println("edit events <property> <eventSubject> from <dateStringTtimeString> with "
            + "<NewPropertyValue>");
    out.println("edit series <property> <eventSubject> from <dateStringTtimeString> with "
            + "<NewPropertyValue>");
    out.println("print events on <dateString>");
    out.println("print events from <dateStringTtimeString> to <dateStringTtimeString>");
    out.println("show status on <dateStringTtimeString>");
    out.println("create calendar --name <calName> --timezone area/location");
    out.println("edit calendar --name <name-of-calendar> --property <property-name> "
            + "<new-property-value>");
    out.println("use calendar --name <name-of-calendar>");
    out.println("copy event <eventName> on <dateStringTtimeString> --target <calendarName> "
            + "to <dateStringTtimeString>");
    out.println("copy events on <dateString> --target <calendarName> to <dateString>");
    out.println("copy events between <dateString> and <dateString> --target <calendarName> "
            + "to <dateString>");
    out.println("exit (quit the program) ");
  }

  @Override
  public void printEvents(List<String> events) {
    for (String event : events) {
      out.println(event);
    }
  }

  @Override
  public void showStatus(String status) {

    out.println(status + System.lineSeparator()) ;
  }

  @Override
  public void showOptionError() {
    out.print("\nInvalid command. Please try again.");

  }
}
