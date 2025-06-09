package calendarmodel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class MultipleCalendar extends CalendarModel {

  /**
   * Constructs a CalendarModel object.
   *
   * @param eventsByDate      the hashmap that maps each event to a specific date
   * @param eventSeriesByDate the hashmap that maps each event series to a specific date
   */
  public MultipleCalendar(Map<LocalDateTime, List<Event>> eventsByDate,
                          Map<LocalDateTime, List<EventSeries>> eventSeriesByDate) {
    super(eventsByDate, eventSeriesByDate);
  }



}
