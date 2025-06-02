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

  Event createEvent(String subject, LocalDate startDate, LocalTime startTime, LocalDate endDate,
                    LocalTime endTime, String description, String location, boolean isPrivate);

  EventSeries createEventSeries(String subject, LocalDate startDate, LocalTime startTime, LocalDate endDate,
                                LocalTime endTime, String description, String location, boolean isPrivate,
                                ArrayList<DayOfWeek> repeatDays, int times);



}
