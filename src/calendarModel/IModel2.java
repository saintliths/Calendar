package calendarmodel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

import calendarmodel.MultipleCalendar.CalendarBuilder;

/**
 * This interface extends the old IModel interface with a couple of new functions, including
 * creating new calendars, editing calendars, and copying events.
 */
public interface IModel2 extends IModel {

  /**
   * Creates a new calendar.
   *
   * @param input the user input
   * @return an IModel2
   */
  IModel2 createCalendar(String input);

  /**
   * Get the builder of this class.
   *
   * @return a CalendarBuilder
   */
  CalendarBuilder getBuilder();

  /**
   * Edits a calendar.
   *
   * @param input the user input
   * @return an IModel2
   */
  IModel2 editCalendar(String input);


  /**
   * Use a calendar.
   *
   * @param input the user input
   * @return an IModel2
   */
  IModel2 useCalendar(String input);

  /**
   * Used to copy a singular specific event from one calendar to another.
   *
   * @param input the user input
   */
  void copySpecificEvent(String input);

  /**
   * @param input the user input
   */
  void copyMultipleEvents(String input);

  /**
   * @param input the user input
   */
  void copyEventsBetween(String input);

  /**
   * Get the name of the calendar.
   *
   * @return this name
   */
  String getName();

  /**
   * Get the time zone of the calendar.
   *
   * @return this time zone
   */
  ZoneId getTimeZone();

  /**
   * Get the model of the calendar.
   *
   * @return this model
   */
  IModel getModel();


  Map<String, IModel2> getCalendars();

}
