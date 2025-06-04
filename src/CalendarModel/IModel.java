package CalendarModel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the interface for the Model.
 */
public interface IModel {

  String getInput();

  void setString(String i);

  Event createEvent(String input);

  EventSeries createEventSeries(String input);

  Event editEvent(String input);

  List<String> printEvents(String input);

  String showStatus(String input);

}
