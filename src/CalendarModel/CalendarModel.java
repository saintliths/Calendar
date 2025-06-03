package CalendarModel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import CalendarModel.Event.EventBuilder;

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
  public Event createEvent(String input) {
    String[] arg = input.split(" ");
    String[] start = arg[4].split("T");
    LocalDate startDate = LocalDate.parse(start[0]);
    LocalTime startTime = LocalTime.parse(start[1]);

    EventBuilder e = new EventBuilder(arg[2], startDate, startTime);

    if (arg.length == 7) {
      String[] end = arg[6].split("T");
      LocalDate endDate = LocalDate.parse(end[0]);
      LocalTime endTime = LocalTime.parse(end[1]);
      e.endDate(endDate).endTime(endTime);
    }


    if (eventsByDate.containsKey(startDate)) {
      eventsByDate.get(startDate).add(e.build());
    } else {
      List<Event> newStartDate = new ArrayList<>();
      newStartDate.add(e.build());
      eventsByDate.put(startDate, newStartDate);
    }
    return e.build();
  }

  @Override
  public EventSeries createEventSeries(String input) {
    
  }

}
