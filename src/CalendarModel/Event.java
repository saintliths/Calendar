package CalendarModel;

import java.time.LocalDate;
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
   * A Getter for the inner builder class.
   *
   * @return a builder for this event
   */
  public EventBuilder getBuilder() {
    return new EventBuilder(this.subject, this.startDate, this.startTime);
  }

  /**
   * This class represents an inner builder class that builds some of the fields
   * of this event.
   */
   static class EventBuilder {
    private String subject;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private String description;
    private String location;
    private boolean isPrivate;

     /**
      * Constructs an EventBuilder object.
      *
      * @param subject the subject of the event
      * @param startDate the start date of the event
      * @param startTime the start time of the event
      */
    public EventBuilder(String subject, LocalDate startDate, LocalTime startTime) {
      this.subject = subject;
      this.startDate = startDate;
      this.startTime = startTime;
      this.endDate = LocalDate.now();
      this.endTime = LocalTime.of(17, 0);
      this.description = "";
      this.location = "";
      this.isPrivate = false;
    }

     /**
      * Changes the end date to the given date
      * @param e the given date
      *
      * @return this builder
      */
     public EventBuilder endDate(LocalDate e) {
       this.endDate = e;
       return this;
     }

    public EventBuilder subject(String s) {
      this.subject = s;
      return this;
    }

     /**
      * Changes the end time to the given time
      * @param e the given time
      * @return this builder
      */
    public EventBuilder endTime(LocalTime e) {
      this.endTime = e;
      return this;
    }

     /**
      * Changes the description to the given string
      * @param d the given string/new description
      * @return this builder
      */
    public EventBuilder description(String d) {
      this.description = d;
      return this;
    }

     /**
      * Changes the location to the given string
      * @param l
      * @return
      */
    public EventBuilder location(String l) {
      this.location = l;
      return this;
    }

     /**
      * Changes the isPrivate to the given boolean
      * @param p
      * @return
      */
    public EventBuilder isPrivate(boolean p) {
      this.isPrivate = p;
      return this;
    }

     /**
      *
      * @return
      */
    public Event build() {
      return new Event(subject, startDate, startTime, endDate, endTime,
              description, location, isPrivate);
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
