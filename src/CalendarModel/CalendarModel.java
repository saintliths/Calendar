package CalendarModel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarModel implements IModel {

  private Map<LocalDate, List<Event>> eventsByDate;
  // a way to represent months and days LocalDate
  private String input;

  public CalendarModel() {
    this.eventsByDate = new HashMap<>();
  }

  @Override
  public String getInput() {
    return input;
  }

  @Override
  public void setString(String i) {
    input = i;
  }


  @Override
  public Event createEvent(String subject, LocalDate startDate, LocalTime startTime, LocalDate endDate,
                           LocalTime endTime, String description, String location, boolean isPrivate) {

    Event e = new Event(subject, startDate, startTime, endDate, endTime,
            description, location, isPrivate);

    if (eventsByDate.containsKey(startDate)) {
      eventsByDate.get(startDate).add(e);
    } else {
      List<Event> newStartDate = new ArrayList<>();
      newStartDate.add(e);
      eventsByDate.put(startDate, newStartDate);
    }
    return e;
  }


  @Override
  public EventSeries createEventSeries(String subject, LocalDate startDate, LocalTime startTime, LocalDate endDate,
                                       LocalTime endTime, String description, String location, boolean isPrivate,
                                       ArrayList<DayOfWeek> repeatDays, int times) {
    return new EventSeries(subject, startDate, startTime, endDate, endTime, description, location,
            isPrivate, repeatDays, times);
  }
}
