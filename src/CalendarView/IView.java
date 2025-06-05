package CalendarView;

import java.util.List;

/**
 * This represents an interface for the view.
 */
public interface IView {

  void showStatus(String status);

  void printEvents(List<String> events);

  /**
   * If the input is not a valid command.
   */
  void showOptionError();
}
