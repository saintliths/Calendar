package calendarview;

import java.io.PrintStream;
import java.util.List;

/**
 * This class represents the implementation for the view of the calendar.
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
    out.println("Hi! Welcome to our Calendar. The supported commands are: "
            + System.lineSeparator());
    out.println("create event <eventSubject> from <dateStringTtimeString> to " +
            "<dateStringTtimeString>"
            + System.lineSeparator());
    out.println("create event <eventSubject> from <dateStringTtimeString> to " +
            "<dateStringTtimeString> repeats <weekdays> for <N> times"
            + System.lineSeparator());
    out.println("create event <eventSubject> from <dateStringTtimeString> to " +
            "<dateStringTtimeString> repeats <weekdays> until <dateString>"
            + System.lineSeparator());
    out.println("create event <eventSubject> on <dateString>"
            + System.lineSeparator());
    out.println("create event <eventSubject> on <dateString> repeats <weekdays> for <N> times"
            + System.lineSeparator());
    out.println("create event <eventSubject> on <dateString> repeats <weekdays> until <dateString>"
            + System.lineSeparator());
    out.println("edit event <property> <eventSubject> from <dateStringTtimeString> to " +
            "<dateStringTtimeString> with <NewPropertyValue>"
            + System.lineSeparator());
    out.println("edit events <property> <eventSubject> from <dateStringTtimeString> with " +
            "<NewPropertyValue>" + System.lineSeparator());
    out.println("edit series <property> <eventSubject> from <dateStringTtimeString> with " +
            "<NewPropertyValue>" + System.lineSeparator());
    out.println("print events on <dateString>" + System.lineSeparator());
    out.println("print events from <dateStringTtimeString> to <dateStringTtimeString>"
            + System.lineSeparator());
    out.println("show status on <dateStringTtimeString>" + System.lineSeparator());
    out.println("exit (quit the program) " + System.lineSeparator());

  }

  @Override
  public void printEvents(List<String> events) {
    for (String event : events) {
      out.println(event);
    }
  }

  @Override
  public void showStatus(String status) {
    out.println(status);
  }

  @Override
  public void showOptionError() {
    out.print("\nInvalid command. Please try again.");

  }
}
