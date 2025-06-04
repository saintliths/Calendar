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


  // create event <eventSubject> from <dateStringTtimeString> to <dateStringTtimeString>
  // 2025-07-06T03:22
  // create event <eventSubject> on <dateString>
  @Override
  public Event createEvent(String input) {
    String[] arg = input.split(" ");
    LocalDate startDate = null;
    LocalTime startTime = null;

    if (input.contains("on")) {
      startDate = LocalDate.parse(arg[4]);
      startTime = LocalTime.of(8, 0);
    }

    if (input.contains("from")) {
      String[] start = arg[4].split("T");
       startDate = LocalDate.parse(start[0]);
       startTime = LocalTime.parse(start[1]);
    }


    EventBuilder e = new EventBuilder(arg[2], startDate, startTime);


    if (input.contains("to")) {
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


    Event newEvent = e.build();
    eventsByDate.get(startDate).add(newEvent);


    return newEvent;
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
    String[] start = arg[5].split("T");
    LocalDate startDate = LocalDate.parse(start[0]);
    LocalTime startTime = LocalTime.parse(start[1]);

    LocalDate endDate = null;
    LocalTime endTime = null;

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

    // handles the one with all the fields
    switch (property) {
      case "subject":
        e.subject(newValue);
        break;
      case "start":
        e.startDate(LocalDate.parse(newValue));
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



    // find the event

    // use the eventBuilder
//    EventBuilder builder = new EventBuilder(
//            e.getSubject(),
//            e.getStartDate(),
//            e.getStartTime()
//    );
  }
  // do String input so u get the entire input string
  // and similar to what i did above split the input string into diff parts
  // sorry but why do we have to split the string
  // cuz the user inputs an entire string, ex. "create event Hehe from " +
  //            "2025-03-23T12:00 to 2025-04-04T03:00" do then u gotta get parts of the string

}
