package CalendarModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import CalendarModel.Event.EventBuilder;
import CalendarModel.EventSeries.EventSeriesBuilder;

/**
 *
 */
public class CalendarModel implements IModel {

  private final Map<LocalDateTime, List<Event>> eventsByDate;
  private final Map<LocalDateTime, List<EventSeries>> eventSeriesByDate;
  // a way to represent months and days LocalDate
  private String input;

  public CalendarModel(Map<LocalDateTime, List<Event>> eventsByDate,
                       Map<LocalDateTime, List<EventSeries>> eventSeriesByDate) {
    this.eventsByDate = eventsByDate;
    this.eventSeriesByDate = eventSeriesByDate;
  }

  @Override
  public Map<LocalDateTime, List<Event>> getHashMap() {
    return this.eventsByDate;
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

    // check if two events are the same

    if (eventsByDate.containsKey(LocalDateTime.of(startDate, startTime))) {
      eventsByDate.get(LocalDateTime.of(startDate, startTime)).add(e.build());
    } else {
      eventsByDate.put(LocalDateTime.of(startDate, startTime),
              new ArrayList<>());
      eventsByDate.get(LocalDateTime.of(startDate, startTime)).add(e.build());
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


    if (eventSeriesByDate.containsKey(LocalDateTime.of(startDate, startTime))) {
      eventSeriesByDate.get(LocalDateTime.of(startDate, startTime)).add(e.build());
    } else {
      eventSeriesByDate.put(LocalDateTime.of(startDate, startTime),
              new ArrayList<>());
      eventSeriesByDate.get(LocalDateTime.of(startDate, startTime)).add(e.build());
    }

    return e.build();
  }

  @Override
  public EventSeries editEventSeries(String input) {

    //    EventSeries e = model.createEventSeries("create event Yabba from " +
    //            "2025-09-23T04:56 to 2025-09-23T09:33 " +
    //            "repeats MWF for 5 times");
    //
    // edit events <property> <eventSubject> from <dateStringTtimeString> with <NewPropertyValue>

    // parse the event input
    String[] arg = input.split(" ");
    String property = arg[2];
    String[] start = arg[5].split("T");
    LocalDate startDate = LocalDate.parse(start[0]);
    LocalTime startTime = LocalTime.parse(start[1]);
    String eventSubject = arg[3];

    EventSeries es = eventSeriesByDate.get(LocalDateTime.of(startDate, startTime)).get(0);
    String oldSubject = es.getSubject();
    LocalDate oldStartDate = es.getStartDate();
    LocalTime oldStartTime = es.getStartTime();
    LocalDate oldEndDate = es.getEndDate();
    LocalTime oldEndTime = es.getEndTime();
    String oldDescription = es.getDescription();
    String oldLocation = es.getLocation();
    String oldRecurrence = es.getRecurrenceDays();
    Boolean oldStatus = es.isPrivate();
    int oldOccurence = es.getOccurrenceCount();

    String newValue = arg[arg.length - 1];

    EventSeriesBuilder e = new EventSeriesBuilder(eventSubject, startDate, startTime);

      switch (property) {
        case "subject":
          e.subject(newValue);
          e.startDate(oldStartDate);
          e.startTime(oldStartTime);
          e.endDate(oldEndDate);
          e.endTime(oldEndTime);
          e.description(oldDescription);
          e.location(oldLocation);
          e.isPrivate(oldStatus);
          e.recurrenceDays(oldRecurrence);
          e.occurrenceCount(oldOccurence);
          break;
        case "start":
          String[] newStartProperty = newValue.split("T");
          String newStartDate = newStartProperty[0];
          String newStartTime = newStartProperty[1];
          e.subject(oldSubject);
          e.startDate(LocalDate.parse(newStartDate));
          e.startTime(LocalTime.parse(newStartTime));
          e.endDate(oldEndDate);
          e.endTime(oldEndTime);
          e.description(oldDescription);
          e.location(oldLocation);
          e.isPrivate(oldStatus);
          e.recurrenceDays(oldRecurrence);
          e.occurrenceCount(oldOccurence);
          break;
        case "end":
          String[] newEndProperty = newValue.split("T");
          String newEndDate = newEndProperty[0];
          String newEndTime = newEndProperty[1];
          e.subject(oldSubject);
          e.startDate(oldStartDate);
          e.startTime(oldStartTime);
          e.endDate(LocalDate.parse(newEndDate));
          e.endTime(LocalTime.parse(newEndTime));
          e.description(oldDescription);
          e.location(oldLocation);
          e.isPrivate(oldStatus);
          e.recurrenceDays(oldRecurrence);
          e.occurrenceCount(oldOccurence);
          break;
        case "description":
          e.subject(oldSubject);
          e.startDate(oldStartDate);
          e.startTime(oldStartTime);
          e.endDate(oldEndDate);
          e.endTime(oldEndTime);
          e.description(newValue);
          e.location(oldLocation);
          e.isPrivate(oldStatus);
          e.recurrenceDays(oldRecurrence);
          e.occurrenceCount(oldOccurence);
          break;
        case "location":
          e.location(newValue);
          e.subject(oldSubject);
          e.startDate(oldStartDate);
          e.startTime(oldStartTime);
          e.endDate(oldEndDate);
          e.endTime(oldEndTime);
          e.description(oldDescription);
          e.isPrivate(oldStatus);
          e.recurrenceDays(oldRecurrence);
          e.occurrenceCount(oldOccurence);
          break;
        case "status":
          e.subject(oldSubject);
          e.startDate(oldStartDate);
          e.startTime(oldStartTime);
          e.endDate(oldEndDate);
          e.endTime(oldEndTime);
          e.location(oldLocation);
          e.description(oldDescription);
          e.recurrenceDays(oldRecurrence);
          e.isPrivate(Boolean.parseBoolean(newValue));
          break;
      }

    return e.build();

  }

  @Override
  public EventSeries editSeries(String input) {

    // parse the event input
    String[] arg = input.split(" ");
    String property = arg[2];
    String eventSeriesSubject = arg[3];
    String[] start = arg[5].split("T");
    LocalDate startDate = LocalDate.parse(start[0]);
    LocalTime startTime = LocalTime.parse(start[1]);
    String newValue = arg[arg.length - 1];
    LocalDateTime key = LocalDateTime.of(startDate, startTime);

    if (newValue.contains("T")) {
      String[] newProperty = newValue.split("T");
      String newDate = newProperty[0];
      String newTime = newProperty[1];
    }

    // holds the series that the input is referrring to
    EventSeries currentSeries = null;

    // look for series
    if (eventSeriesByDate.containsKey(key)) {
      // for events with this startDate
      for (EventSeries series : eventSeriesByDate.get(key)) {
        // checks if the series has the matching subject
        if (series.getSubject().equals(eventSeriesSubject)) {
          currentSeries = series;
          break;
        }
      } // what if it is an event just not an event series
    } else {
      throw new IllegalArgumentException("No such event series.");
    }


    EventSeriesBuilder e = new EventSeriesBuilder(eventSeriesSubject, startDate, startTime);

    // handles the one with all the fields
    switch (property) {
      case "subject":
        e.subject(newValue);
        break;
      case "start":
        // if they wanna just change the start date or just the start time
        if (newValue.contains("T")) {
          // separate the date and time
          String[] dateAndTime = newValue.split("T");
          e.startDate(LocalDate.parse(dateAndTime[0]));
          e.startTime(LocalTime.parse(dateAndTime[1]));
        } else if (newValue.contains(":")){
          // if it was only the time that is being changed
          e.startTime(LocalTime.parse(newValue));
        } else {
          // if only the date is being changed
          e.startDate(LocalDate.parse(newValue));
      }
        break;
      case "end":
        // if they wanna just change the start date or just the start time
        if (newValue.contains("T")) {
          // separate the date and time
          String[] dateAndTime = newValue.split("T");
          e.endDate(LocalDate.parse(dateAndTime[0]));
          e.endTime(LocalTime.parse(dateAndTime[1]));
        } else if (newValue.contains(":")){
          // if it was only the time that is being changed
          e.endTime(LocalTime.parse(newValue));
        } else {
          // if only the date is being changed
          e.endDate(LocalDate.parse(newValue));
        }
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

    return e.build();
  }

  @Override
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
    String newValue = arg[arg.length - 1];
    LocalDateTime key = LocalDateTime.of(startDate, startTime);

    Event currentEvent = null;
    if (eventsByDate.containsKey(key)) {
      for (Event event : eventsByDate.get(key)) {
        if (event.getSubject().equals(eventSubject)) {
          currentEvent = event;
          break;
        }
      }
    }

    // get and set the current properties
    String oldSubject = currentEvent.getSubject();
    LocalDate oldStartDate = currentEvent.getStartDate();
    LocalTime oldStartTime = currentEvent.getStartTime();
    LocalDate oldEndDate = currentEvent.getEndDate();
    LocalTime oldEndTime = currentEvent.getEndTime();
    String oldDescription = currentEvent.getDescription();
    String oldLocation = currentEvent.getLocation();
    Boolean oldStatus = currentEvent.isPrivate();
    LocalDate endDate = oldEndDate;
    LocalTime endTime = oldEndTime;

    if (input.contains("to")) {
      String[] end = arg[7].split("T");
      endDate = LocalDate.parse(end[0]);
      endTime = LocalTime.parse(end[1]);
    }

    // keep the old version
    EventBuilder e = new EventBuilder(oldSubject, oldStartDate, oldStartTime)
            .endDate(endDate)
            .endTime(endTime)
            .description(oldDescription)
            .location(oldLocation)
            .isPrivate(oldStatus);

    switch (property) {
      case "subject":
        e = e.subject(newValue);
        break;
      case "start":
        // if they wanna just change the start date or just the start time
        if (newValue.contains("T")) {
          // separate the date and time
          String[] dateAndTime = newValue.split("T");
          e = e.startDate(LocalDate.parse(dateAndTime[0])).startTime(LocalTime.parse(dateAndTime[1]));
        } else if (newValue.contains(":")){
          // if it was only the time that is being changed
          e.startTime(LocalTime.parse(newValue));
        } else {
          // if only the date is being changed
          e.startDate(LocalDate.parse(newValue));
        }
        break;
      case "end":
        if (newValue.contains("T")) {
          // separate the date and time
          String[] dateAndTime = newValue.split("T");
          e= e.endDate(LocalDate.parse(dateAndTime[0])).endTime(LocalTime.parse(dateAndTime[1]));
        } else if (newValue.contains(":")){
          // if it was only the time that is being changed
          e= e.endTime(LocalTime.parse(newValue));
        } else {
          // if only the date is being changed
          e= e.endDate(LocalDate.parse(newValue));
        }
        break;
      case "description":
        e= e.description(newValue);
        break;
      case "location":
        e= e.location(newValue);
        break;
      case "status":
        e= e.isPrivate(Boolean.parseBoolean(newValue));  // change the true to "false"
        break;
        // throw exception if property is not a case
    }

    Event newEvent = e.build();

    // put the new event back into the hashmap
    LocalDateTime newKey = LocalDateTime.of(newEvent.getStartDate(), newEvent.getStartTime());
    if (eventsByDate.containsKey(newKey)) {
      eventsByDate.get(newKey).add(newEvent);
    } else {
      eventsByDate.put(newKey, new ArrayList<>());
      eventsByDate.get(newKey).add(newEvent);
    }

    return newEvent;

  }

  @Override
  public List<String> printEvents(String input) {
    List<String> listOfEvents = new ArrayList<>();
    String[] arg = input.split(" ");

    if (input.contains("on")) {
      String date = arg[3];
      LocalTime fromTime = LocalTime.of(0, 0);
      LocalTime toTime = LocalTime.of(23, 59);

      if (eventsByDate.containsKey(date)) {
        List<Event> events = eventsByDate.get(date);
        for (Event event : events) {
          listOfEvents.add(event.toString());
        }
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

  @Override
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
