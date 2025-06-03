package CalendarView;

import java.io.PrintStream;

/**
 * This class represents the implementation for the view of the calendar.
 */
public class CalendarView implements IView {
  private final PrintStream out;

  public CalendarView(PrintStream out) {

    this.out = out;
  }


  @Override
  public void showOptions() {
    //print the UI
    out.println("Commands: ");
    out.println("E: to create a singular event");
    out.println("ES: to create a series of events");
    out.println("EDIT: to edit a singular event");
    out.println("EES: to edit a series of events");
    out.println("PRINT: to print events");
    out.println("STATUS: to show your status on a given day/time");
    out.println("Q: Quit the program");
    out.print("Enter your choice: ");

  }

  @Override
  public void enterSubject() {
    out.print("\n(REQUIRED) Enter your subject: ");
  }

  @Override
  public void enterStartDate() {
    out.print("\n(REQUIRED) Enter your start date (YYYY-MM-DD): ");

  }

  @Override
  public void enterStartTime() {
    out.print("\n(REQUIRED) Enter your start time (HH:MM): ");

  }

  @Override
  public void enterEndDate() {
    out.print("\n(OPTIONAL) Enter your end date (YYYY-MM-DD): ");

  }

  @Override
  public void enterEndTime() {
    out.print("\n(OPTIONAL) Enter your end time (HH:MM): ");

  }

  @Override
  public void enterDescription() {
    out.print("\n(OPTIONAL) Enter your description: ");

  }

  @Override
  public void enterLocation() {
    out.print("\n(OPTIONAL) Enter your location: ");

  }

  @Override
  public void enterEventStatus() {
    out.print("\n(OPTIONAL) Enter your event status (public or private): ");

  }

  @Override
  public void showOptionError() {
    out.print("\nInvalid command. Please try again.");

  }
}
