package CalendarModel;

import java.time.LocalDateTime;

/**
 * This class represents a singular event that the user can schedule with a start and end time.
 */
public class Event {

  private final String subject;
  private final LocalDateTime startDateTime;
  private final LocalDateTime endDateTime;
  private final String description;
  private final String location;
  private final boolean isPrivate;
  private final boolean partOfSeries;

  /**
   * Private constructor for an Event.
   * @param subject the subject of the event
   * @param start the time the event starts
   * @param end the time the event ends
   * @param description description of the event
   * @param location location of the event
   * @param isPrivate whether is it private or public
   * @param partOfSeries whether it is part of a series
   */
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

  // I think the default public constructor should have no arguments
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
