package CalendarModel;

import java.time.LocalDateTime;

public class Event {

  private final String subject;
  private final LocalDateTime startDateTime;
  private final LocalDateTime endDateTime;
  private final String description;
  private final String location;
  private final boolean isPrivate;
  private final boolean partOfSeries;

  private Event(String subject, LocalDateTime start, LocalDateTime end,
               String description, String location, boolean isPrivate, boolean partOfSeries) {
    this.subject = subject;
    this.startDateTime = start;
    this.endDateTime = end;
    this.description = description;
    this.location = location;
    this.isPrivate = isPrivate;
    this.partOfSeries = partOfSeries;
  }

  // default constructor
  public Event(String subject, LocalDateTime date, String description,
               String location, boolean isPrivate, boolean partOfSeries) {
    this.subject = subject;
    this.startDateTime = date.withHour(8).withMinute(0);
    this.endDateTime = date.withHour(17).withMinute(0);
    this.description = description;
    this.location = location;
    this.isPrivate = isPrivate;
    this.partOfSeries = partOfSeries;
  }

}
