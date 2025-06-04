package CalendarView;

import java.io.PrintStream;
import java.util.List;

/**
 * This class represents the implementation for the view of the calendar.
 */
public class CalendarView implements IView {
  private final PrintStream out;

  public CalendarView(PrintStream out) {

    this.out = out;
  }

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
