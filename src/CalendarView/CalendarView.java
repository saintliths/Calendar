package CalendarView;

import java.io.PrintStream;

/**
 * This class represents the implementation for the view of the calendar.
 */
public class CalendarView implements IView {
  private PrintStream out;

  public CalendarView(PrintStream out) {
    this.out = out;
  }


  @Override
  public void showString(String s) {

  }

  @Override
  public void showOptions() {

  }

  @Override
  public void showStringEntry() {

  }

  @Override
  public void showOptionError() {

  }
}
