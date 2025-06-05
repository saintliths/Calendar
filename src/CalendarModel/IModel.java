package CalendarModel;

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
   * Checks if the event already exists within the hashmap.
   * @param ne is the event being compared
   * @return true/false
   */
  Boolean checkEventOverlap(Event ne);

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
   * Edit an entire series of events.
   *
   * @param input the user input
   * @return an EventSeries after it has been edited
   */
  EventSeries editSeries(String input);

  List<String> printEvents(String input);

  /**
   * Shows the status on a given day.
   *
   * @param input the user input
   * @return either a "Busy" String or "Not busy" String
   */
  String showStatus(String input);

  /**
   * Getter that gets the hash map of this model
   * @return a Map of LocalDateTime and List of events
   */
  Map<LocalDateTime, List<Event>> getHashMap();
}
