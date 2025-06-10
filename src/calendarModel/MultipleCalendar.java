package calendarmodel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
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
    // return a new multipleCalender (name, timezone)
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
        break;
      default:
        throw new IllegalArgumentException("Invalid property");
    }
    return cal;
    // return a new multipleCalender (name, timezone)

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
  public IModel2 copySpecificEvent(String input) {
    // input "copy event Hehe on 2025-03-23T12:00 --target Lola to 2025-04-23T12:00"
    String[] arg = input.split(" ");
    String targetName = arg[6];
    String eventName = arg[2];
    LocalDateTime dateOld = LocalDateTime.parse(arg[4]);
    LocalDateTime dateNew = LocalDateTime.parse(arg[8]);


    // find event Hehe on 2025-03-23T12:00 from the events hashmap of the current calendar
    // check if that already exists in Lola
    // if not then find key date in Lola calender hashmap of events and add it to the corresponding value which is
    // the list of events on that date

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
      throw new IllegalArgumentException("Cannot find event.");
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

    // put the matching events into the target calendar
    // hashmap of the target
    Map<LocalDateTime, List<Event>> targetHash = target.getModel().getHashMap();

    targetHash.get(dateOld).add(matchingEvents);


    return null;

  }

  @Override
  public IModel2 copyMultipleEvents(String input) {
    String[] arg = input.split(" ");
    String date = arg[3];
    String targetName = arg[5];
    List<Event> eventsToCopy = new ArrayList<>();

    IModel2 target;

    if (calendars.containsKey(targetName)) {
      target = calendars.get(targetName);
    } else {
      throw new IllegalArgumentException("Calendar does not exist");
    }

    Map<LocalDateTime, List<Event>> thisHash = this.getModel().getHashMap();

    List<Event> events = thisHash.get(date);

    Map<LocalDateTime, List<Event>> targetHash = target.getModel().getHashMap();

    return null;
  }

  @Override
  public IModel2 copyEventsBetween(String input) {
    return null;
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
