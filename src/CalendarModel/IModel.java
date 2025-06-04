package CalendarModel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Represents the interface for the Model.
 */
public interface IModel {

  String getInput();

  void setString(String i);

  Event createEvent(String input);

  EventSeries createEventSeries(String input);

  Event editEvent(String input);

}
