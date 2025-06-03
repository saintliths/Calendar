package CalendarModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This class represents a singular event that the user can schedule with a start and end time.
 */
public class Event {

  private final String subject;
  private final LocalDate startDate;
  private final LocalTime startTime;
  private final LocalDate endDate;
  private final LocalTime endTime;
  private final String description;
  private final String location;
  private final boolean isPrivate;

  /**
   * Public constructor for an Event.
   * @param subject the subject of the event
   * @param startDate the start date of the event
   * @param startTime the time the event starts
   * @param endDate the end date of the event
   * @param endTime the time the event ends
   * @param description description of the event
   * @param location location of the event
   * @param isPrivate whether is it private or public
   */
  public Event(String subject, LocalDate startDate, LocalTime startTime, LocalDate endDate,
               LocalTime endTime, String description, String location, boolean isPrivate) {
    this.subject = subject;
    this.startDate = startDate;
    this.startTime = startTime;
    this.endDate = endDate;
    this.endTime = endTime;
    this.description = description;
    this.location = location;
    this.isPrivate = isPrivate;
  }

  /**
   *
   * @return
   */
  public static EventBuilder getBuilder() {
    return new EventBuilder();
  }

  static class EventBuilder {
    private LocalTime endDate;
    private String description;
    private String location;
    private boolean isPrivate;

    public EventBuilder() {
      this.endDate = LocalTime.MIDNIGHT;
      this.description = "";
      this.location = "";
      this.isPrivate = false;
    }

    public EventBuilder endDate(LocalTime e) {
      this.endDate = e;
      return this;
    }

    public EventBuilder description(String d) {
      this.description = d;
      return this;
    }

    public EventBuilder location(String l) {
      this.location = l;
      return this;
    }

    public EventBuilder isPrivate(boolean p) {
      this.isPrivate = p;
      return this;
    }

  }

  public String getSubject() {
    return this.subject;
  }

  public LocalDate getStartDate() {
    return this.startDate;
  }

  public LocalTime getStartTime() {
    return this.startTime;
  }

  public LocalDate getEndDate() {
    return this.endDate;
  }

  public LocalTime getEndTime() {
    return this.endTime;
  }

  public String getDescription() {
    return description;
  }


  public String getLocation() {
    return location;
  }


  public boolean isPrivate() {
    return isPrivate;
  }

  public boolean checkEventOverlap(Object o) {
    return false;
  }

}
