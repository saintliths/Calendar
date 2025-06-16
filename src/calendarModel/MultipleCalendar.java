package calendarmodel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represents a support for multiple calendars where the user can create, edit, use,
 * and copy events over to a new calendar.
 */
public class MultipleCalendar implements IModel2 {
  private final String name;
  private final ZoneId timeZone;
  private final IModel model;
  private final Map<String, IModel2> calendars;


  /**
   * Constructs a MultipleCalendar object.
   *
   * @param name     name of the calendar
   * @param timeZone timezone of the calendar
   * @param model    the model of the calendar that allows it to create, edit, and query events
   */
  public MultipleCalendar(String name, ZoneId timeZone, IModel model) {
    this.name = name;
    this.timeZone = timeZone;
    this.model = model;
    this.calendars = new HashMap<>();
    this.calendars.put(name, this);
  }

  @Override
  public IModel2 createCalendar(String input) {
    String[] arg = input.split(" ");
    String name = arg[3];
    ZoneId timeZone = ZoneId.of(arg[5]);
    IModel model = new CalendarModel(new HashMap<>(), new HashMap<>());
    IModel2 calendar = new MultipleCalendar(name, timeZone, model);

    if (!calendars.containsKey(name)) {
      calendars.put(name, calendar);
    } else {
      throw new IllegalArgumentException("Calendar already exists");
    }
    return calendar;
  }

  @Override
  public IModel2 editCalendar(String input) {
    String[] arg = input.split(" ");
    String name = arg[3];
    String property = arg[5];
    String newValue = arg[arg.length - 1];

    IModel2 calendar;

    if (calendars.containsKey(name)) {
      calendar = calendars.get(name);
    } else {
      throw new IllegalArgumentException("Calendar does not exist");
    }

    String oldName = calendar.getName();
    ZoneId oldZone = calendar.getTimeZone();
    CalendarBuilder cb = calendar.getBuilder();
    MultipleCalendar cal;

    switch (property) {
      case "name":
        cb.name(newValue);
        cb.timeZone(oldZone);
        cal = cb.build();
        break;
      case "timezone":
        cb.timeZone(ZoneId.of(newValue));
        cb.name(oldName);
        cal = cb.build();

        Map<LocalDateTime, List<Event>> e =
                cal.getCalendars().get(oldName).getModel().getHashMap();

        // convert the time of the events after changing the time zone
        for (Map.Entry<LocalDateTime, List<Event>> entry : e.entrySet()) {
          convertTimeZone(entry.getKey(), cal);
        }
        break;
      default:
        throw new IllegalArgumentException("Invalid property");
    }
    return cal;
  }

  @Override
  public IModel2 useCalendar(String input) {
    String[] arg = input.split(" ");
    String name = arg[3];

    IModel2 calendar;
    if (calendars.containsKey(name)) {
      calendar = calendars.get(name);
    } else {
      throw new IllegalArgumentException("Calendar does not exist");
    }

    String newName = calendar.getName();
    ZoneId timeZone = calendar.getTimeZone();
    IModel model = calendar.getModel();

    return new MultipleCalendar(newName, timeZone, model);
  }

  @Override
  public void copySpecificEvent(String input) {
    String[] arg = input.split(" ");
    String targetName = arg[6];
    String eventName = arg[2];
    String[] orig = arg[4].split("T");
    String[] newDate = arg[8].split("T");
    LocalDateTime dateOld = LocalDateTime.of(LocalDate.parse(orig[0]),
            LocalTime.parse(orig[1]));
    LocalDateTime dateNew = LocalDateTime.of(LocalDate.parse(newDate[0]),
            LocalTime.parse(newDate[1]));

    IModel2 target;

    // find the target calendar first
    if (calendars.containsKey(targetName)) {
      target = calendars.get(targetName);
    } else {
      throw new IllegalArgumentException("Calendar does not exist");
    }

    // find the event from the current calendar with that date
    List<Event> findEvents = this.model.getHashMap().get(dateOld);
    Event matchingEvents;
    if (findEvents == null) {
      throw new IllegalArgumentException("Cannot find event");
    } else {
      // find the events from that date with the same name
      matchingEvents = null;
      for (Event event : findEvents) {
        if (event.getSubject().equals(eventName)) {
          matchingEvents = event;
          break;
        }
      }
    }

    // hashmap of the target
    Map<LocalDateTime, List<Event>> targetHash = target.getModel().getHashMap();

    targetHash.put(dateNew, new ArrayList<>());
    // put the matching events into the target calendar
    targetHash.get(dateNew).add(matchingEvents);
  }

  @Override
  public void copyMultipleEvents(String input) {
    String[] arg = input.split(" ");
    LocalDate date = LocalDate.parse(arg[3]);
    String targetName = arg[5];
    LocalDate targetDate = LocalDate.parse(arg[7]);
    List<Event> eventsToCopy = new ArrayList<>();

    IModel2 target;

    if (calendars.containsKey(targetName)) {
      target = calendars.get(targetName);
    } else {
      throw new IllegalArgumentException("Calendar does not exist");
    }

    Map<LocalDateTime, List<Event>> thisHash = this.getModel().getHashMap();

    for (Map.Entry<LocalDateTime, List<Event>> entry : thisHash.entrySet()) {
      if (entry.getKey().toLocalDate().equals(date)) {
        eventsToCopy.addAll(entry.getValue());
      }
    }

    // no events between the start and end date
    if (eventsToCopy.isEmpty()) {
      throw new IllegalArgumentException("No events found on this date");
    }

    Map<LocalDateTime, List<Event>> targetHash = target.getModel().getHashMap();

    for (Event event : eventsToCopy) {
      LocalDateTime dateTime =
              LocalDateTime.of(targetDate, event.getStartTime());

      LocalDateTime converted = convertTimeZone(dateTime, target);

      if (targetHash.containsKey(converted)) {
        targetHash.get(converted).add(event);
      } else {
        targetHash.put(converted, new ArrayList<>());
        targetHash.get(converted).add(event);
      }
    }
  }


  @Override
  public void copyEventsBetween(String input) {
    // copy events between <dateString> and <dateString> --target <calendarName> to <dateString>
    String[] arg = input.split(" ");
    LocalDate dateStart = LocalDate.parse(arg[3]); // <dateString>
    LocalDate dateEnd = LocalDate.parse(arg[5]); // <dateString>
    String targetName = arg[7]; // <calendarName>
    LocalDate targetDate = LocalDate.parse(arg[9]); // <dateString>

    IModel2 target;

    // check if target calendar even exists
    if (calendars.containsKey(targetName)) {
      target = calendars.get(targetName);
    } else {
      throw new IllegalArgumentException("Calendar does not exist");
    }

    // get the current calendars hash map
    Map<LocalDateTime, List<Event>> thisHash = this.getModel().getHashMap();
    List<Event> eventsBetween = new ArrayList<>();

    // find all events between the dateStart and dateEnd
    for (Map.Entry<LocalDateTime, List<Event>> entry : thisHash.entrySet()) {
      // check for in between
      if ((entry.getKey().toLocalDate().isAfter(dateStart)
              || entry.getKey().toLocalDate().isEqual(dateStart))
              && (entry.getKey().toLocalDate().isBefore(dateEnd)
              || entry.getKey().toLocalDate().isEqual(dateEnd))) {
        // now we have a list of all the events to be added
        eventsBetween.addAll(thisHash.get(entry.getKey()));
      }
    }

    // no events between the start and end date
    if (eventsBetween.isEmpty()) {
      throw new IllegalArgumentException("No events found between these dates");
    }

    // the calendar of the target calendar
    Map<LocalDateTime, List<Event>> targetHash = target.getModel().getHashMap();
    // put the eventsBetween list into the targetDate
    for (Event event : eventsBetween) {
      LocalDateTime dateTime =
              LocalDateTime.of(targetDate, event.getStartTime());

      LocalDateTime converted = convertTimeZone(dateTime, target);

      if (targetHash.containsKey(converted)) {
        targetHash.get(converted).add(event);
      } else {
        targetHash.put(converted, new ArrayList<>());
        targetHash.get(converted).add(event);
      }
    }
  }


  // convert date time helper
  //  localDateTime is the copied events localDateTime
  @Override
  public LocalDateTime convertTimeZone(LocalDateTime localDateTime, IModel2 t) {
    // get the timeZone of target calendar
    ZoneId tZone = t.getTimeZone();

    // get the zone of the current events date time
    ZonedDateTime currentZonedDateTime = localDateTime.atZone(this.timeZone);
    // convert from the current date time to the target calendars date time
    ZonedDateTime tZonedDateTime = currentZonedDateTime.withZoneSameInstant(tZone);

    // toLocalDateTime extracts date and time from zone
    return tZonedDateTime.toLocalDateTime();

    // check if that exists in the target calendar and if it does throw exception
  }

  @Override
  public Event createEvent(String input) {
    return this.model.createEvent(input);
  }

  @Override
  public EventSeries createEventSeries(String input) {
    return this.model.createEventSeries(input);
  }

  @Override
  public Event editEvent(String input) {
    return this.model.editEvent(input);
  }

  @Override
  public EventSeries editEventSeries(String input) {
    return this.model.editEventSeries(input);
  }

  @Override
  public EventSeries editSeries(String input) {
    return this.model.editSeries(input);
  }

  @Override
  public List<String> printEvents(String input) {
    return this.model.printEvents(input);
  }

  @Override
  public String showStatus(String input) {
    return this.model.showStatus(input);
  }

  @Override
  public Map<LocalDateTime, List<Event>> getHashMap() {
    return this.model.getHashMap();
  }

  @Override
  public CalendarBuilder getBuilder() {
    return new CalendarBuilder();
  }

  /**
   * Represents a builder class for this calendar.
   */
  static class CalendarBuilder {
    private String name;
    private ZoneId timeZone;
    private final IModel model;

    /**
     * Constructs a CalendarBuilder object.
     */
    CalendarBuilder() {
      this.model = new CalendarModel(new HashMap<>(), new HashMap<>());
    }

    /**
     * Changes this name to the given name.
     *
     * @param name the given name
     * @return this builder
     */
    public CalendarBuilder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Changes this time zone to the given time zone.
     *
     * @param timeZone the given time zone
     * @return this builder
     */
    public CalendarBuilder timeZone(ZoneId timeZone) {
      this.timeZone = timeZone;
      return this;
    }

    /**
     * Builds a MultipleCalendar object from these fields.
     *
     * @return a MultipleCalendar object
     */
    public MultipleCalendar build() {
      return new MultipleCalendar(name, timeZone, model);
    }
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public ZoneId getTimeZone() {
    return this.timeZone;
  }

  @Override
  public IModel getModel() {
    return this.model;
  }

  @Override
  public Map<String, IModel2> getCalendars() {
    return this.calendars;
  }

}
