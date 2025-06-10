package calendarmodel;

import java.time.ZoneId;
import java.util.Map;

import calendarmodel.MultipleCalendar.CalendarBuilder;

/**
 * This interface extends the old IModel interface with a couple of new functions.
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
   * Used to copy a singular specific event from one calender to another.
   *
   * @param input the user input
   * @return an IModel2
   */
  IModel2 copySpecificEvent(String input);

  /**
   * @param input the user input
   * @return an IModel2
   */
  IModel2 copyMultipleEvents(String input);

  /**
   * @param input the user input
   * @return an IModel2
   */
  IModel2 copyEventsBetween(String input);

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
