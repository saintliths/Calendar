package CalendarModel;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarModel implements IModel {

  private final Map<LocalDateTime, List<Event>> eventsByDate;

  public CalendarModel() {
    eventsByDate = new HashMap<>();
  }

  // checks the availability to add an event
  public boolean verifyAddEvent(Event event) {
  }

  // adds the event to the calendar if verifyAddEvent is true
  public void addEvent(Event event) {
  }

  // removes the event from the calendar
  public void removeEvent(Event event) {
  }

  // gets the events of a certain DateTime
  public List<Event> getEventsOn(LocalDateTime dateTime) {
  }

  // gets the events of a certain DateTime
  public List<Event> getEventsBetween(LocalDateTime start, LocalDateTime end) {
  }

  // prints the event map
  public void printAllEvents() {
  }

}
