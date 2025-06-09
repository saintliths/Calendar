package calendarview;

import java.util.List;

/**
 * This represents an interface for the view.
 */
public interface IView {

  /**
   * Prints a list of valid commands.
   */
  void printOptions();

  /**
   * Shows the status of the user on the given day/time.
   *
   * @param status the status to be shown
   */
  void showStatus(String status);

  /**
   * Prints all events in a given day/within an interval.
   *
   * @param events the list of events to print
   */
  void printEvents(List<String> events);

  /**
   * If the input is not a valid command.
   */
  void showOptionError();
}
