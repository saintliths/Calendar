package CalendarModel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a series of events.
 */
public class EventSeries {

  private UUID seriesId;
  private String subject;
  private LocalTime startTime;
  private LocalTime endTime;
  private String description;
  private String location;
  private boolean isPublic;

  private LocalDate startDate;
  private Set<DayOfWeek> recurrenceDays;  // e.g., [MONDAY, WEDNESDAY]
  private int occurrenceCount;            // Optional (N times)
  private LocalDate endDate;


}
