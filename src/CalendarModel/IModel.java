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
   * Edit an event and if it's part of a series, edit this event and the ones after it.
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


  /**
   * Print out all the events on a given date or within a given interval.
   *
   * @param input the user input
   * @return a List of strings containing all the events to be printed
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
   * Getter that gets the hash map of this model
   * @return a Map of LocalDateTime and List of events
   */
  Map<LocalDateTime, List<Event>> getHashMap();
}
