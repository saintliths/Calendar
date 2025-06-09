package calendarmodel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Represents the interface for the Model.
 */
public interface IModel {

  /**
   * Creates a singular event.
   *
   * @param input the user input
   * @return an Event
   */
  Event createEvent(String input);

  /**
   * Creates a series of events.
   *
   * @param input the user input
   * @return an EventSeries
   */
  EventSeries createEventSeries(String input);

  /**
   * Edit a singular event.
   *
   * @param input the user input
   * @return an Event after it has been edited
   */
  Event editEvent(String input);

  /**
   * Edit a series of events.
   *
   * @param input the user input
   * @return an EventSeries after it has been edited
   */
  EventSeries editEventSeries(String input);

  /**
   * Print out all the events on a given date or within a given interval.
   *
   * @param input the user input
   * @return a List of String containing all the events to be printed
   */

  EventSeries editSeries(String input);

  /**
   * Print all the events on a given day/interval.
   *
   * @param input the user input
   * @return a List of String that contains all the events as strings
   */
  List<String> printEvents(String input);

  /**
   * Shows the status on a given day.
   *
   * @param input the user input
   * @return either a "Busy" String or "Not busy" String
   */
  String showStatus(String input);

  /**
   * Getter that gets the hash map of this model.
   *
   * @return a Map of LocalDateTime and List of Event
   */
  Map<LocalDateTime, List<Event>> getHashMap();
}
