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
import CalendarModel.EventSeries.EventSeriesBuilder;

public class CalendarModel implements IModel {

  private Map<LocalDateTime, List<Event>> eventsByDate;
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


  // create event <eventSubject> from <dateStringTtimeString> to <dateStringTtimeString>
  // 2025-07-06T03:22
  // create event <eventSubject> on <dateString>
  @Override
  public Event createEvent(String input) {
    String[] arg = input.split(" ");
    LocalDate startDate = null;
    LocalTime startTime = null;
    LocalDate endDate = null;
    LocalTime endTime = null;

    if (input.contains("on")) {
      startDate = LocalDate.parse(arg[4]);
      startTime = LocalTime.of(8, 0);
      endDate = LocalDate.parse(arg[4]);
      endTime = LocalTime.of(17, 0);
    }

    if (input.contains("from")) {
      String[] start = arg[4].split("T");
      startDate = LocalDate.parse(start[0]);
      startTime = LocalTime.parse(start[1]);
    }

    if (input.contains("to")) {
      String[] end = arg[6].split("T");
      endDate = LocalDate.parse(end[0]);
      endTime = LocalTime.parse(end[1]);
    }


    EventBuilder e = new EventBuilder(arg[2], startDate, startTime);
    e.endDate(endDate).endTime(endTime);


    if (eventsByDate.containsKey(LocalDateTime.of(startDate, startTime))) {
      eventsByDate.get(LocalDateTime.of(startDate, startTime)).add(e.build());
    } else {
      List<Event> newStartDate = new ArrayList<>();
      newStartDate.add(e.build());
      eventsByDate.put(LocalDateTime.of(startDate, startTime),
              new ArrayList<>());
    }

    return e.build();
  }


  @Override
  public EventSeries createEventSeries(String input) {
    String[] arg = input.split(" ");
    String[] start = arg[4].split("T");
    LocalDate startDate = LocalDate.parse(start[0]);
    LocalTime startTime = LocalTime.parse(start[1]);
    String recurringDays = arg[6];
    int occurrenceCount;

    EventSeriesBuilder e = new EventSeriesBuilder(arg[2], startDate, startTime)
            .recurrenceDays(recurringDays);

    if (input.contains("to")) {
      String[] end = arg[6].split("T");
      LocalDate endDate = LocalDate.parse(end[0]);
      LocalTime endTime = LocalTime.parse(end[1]);
      recurringDays = arg[8];
      e.endDate(endDate).endTime(endTime).recurrenceDays(recurringDays);
    }

    if (input.contains("for")) {
      occurrenceCount = Integer.parseInt(arg[10]);
      e.occurrenceCount(occurrenceCount);
    }


    return e.build();
  }

  public Event editEvent(String input) {

    // edit event <property> <eventSubject> from
    // <dateStringTtimeString> to <dateStringTtimeString> with <NewPropertyValue>

    // parse the event input
    String[] arg = input.split(" ");
    String property = arg[2];
    String eventSubject = arg[3];
    LocalDate startDate = null;
    LocalTime startTime = null;
    LocalDate endDate = null;
    LocalTime endTime = null;

    if (input.contains("from")) {
      String[] start = arg[5].split("T");
      startDate = LocalDate.parse(start[0]);
      startTime = LocalTime.parse(start[1]);
    }

    if (input.contains("to")) {
      String[] end = arg[7].split("T");
      endDate = LocalDate.parse(end[0]);
      endTime = LocalTime.parse(end[1]);
    }

    EventBuilder e = new EventBuilder(eventSubject, startDate, startTime);

    // check if it has a definitive end date bc it affects the parsing
    if (endDate != null && endTime != null) {
      e.endDate(endDate).endTime(endTime);
    }

    String newValue = arg[arg.length - 1];

    String newDate = null;
    String newTime = null;

    if (newValue.contains("T")) {
      String[] newProperty = newValue.split("T");
      newDate = newProperty[0];
      newTime = newProperty[1];
    }


    // handles the one with all the fields
    switch (property) {
      case "subject":
        e.subject(newValue);
        break;
      case "start":
        e.startDate(LocalDate.parse(newDate));
        e.startTime(LocalTime.parse(newTime));
        break;
      case "end":
        e.endDate(LocalDate.parse(newValue));
        break;
      case "description":
        e.description(newValue);
        break;
      case "location":
        e.location(newValue);
        break;
      case "status":
        e.isPrivate(Boolean.parseBoolean(newValue));  // change the true to "false"
        break;
    }

    // handle when its default


    return e.build();
  }

  public List<String> printEvents(String input) {
    List<String> listOfEvents = new ArrayList<>();
    String[] arg = input.split(" ");

    if (input.contains("on")) {
      String date = arg[3];
      List<Event> events = eventsByDate.get(LocalDateTime.of(LocalDate.parse(date),
              LocalTime.parse("08:00")));
      for (Event event : events) {
        listOfEvents.add(event.toString());
      }
    }

    if (input.contains("from") && input.contains("to")) {
      String[] from = arg[3].split("T");
      String fromDate = from[0];
      String fromTime = from[1];

      String[] to = arg[5].split("T");
      String toDate = to[0];
      String toTime = to[1];

      LocalDateTime start = LocalDateTime.of(LocalDate.parse(fromDate),
              LocalTime.parse(fromTime));
      LocalDateTime end = LocalDateTime.of(LocalDate.parse(toDate),
              LocalTime.parse(toTime));

      for (LocalDateTime dateTime : eventsByDate.keySet()) {
        if (!dateTime.isBefore(start) && !dateTime.isAfter(end)) {
          for (Event event : eventsByDate.get(dateTime)) {
            listOfEvents.add(event.toString());
          }
        }
      }
    }
    return listOfEvents;
  }


  public String showStatus(String input) {
    String[] arg = input.split(" ");
    String[] date = arg[3].split("T");
    LocalDate eventDate = LocalDate.parse(date[0]);
    LocalTime eventTime = LocalTime.parse(date[1]);

    if (eventsByDate.containsKey(LocalDateTime.of(eventDate, eventTime))) {
      List<Event> events = eventsByDate.get(LocalDateTime.of(eventDate, eventTime));
      if (!events.isEmpty()) {
        return "Busy";
      }
    }
    return "Not busy";
  }
}
