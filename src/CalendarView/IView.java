package CalendarView;

import java.util.List;

/**
 * This represents an interface for the view.
 */
public interface IView {

  /**
   * Shows the status of the user on the given day/time.
   *
   * @param status
   */
  void showStatus(String status);

  /**
   * Prints all events in a given day/within an interval.
   *
   * @param events
   */
  void printEvents(List<String> events);

  /**
   * If the input is not a valid command.
   */
  void showOptionError();
}
