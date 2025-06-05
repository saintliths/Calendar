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
   *
   * @param subject     the subject of the event
   * @param startDate   the start date of the event
   * @param startTime   the time the event starts
   * @param endDate     the end date of the event
   * @param endTime     the time the event ends
   * @param description description of the event
   * @param location    location of the event
   * @param isPrivate   whether is it private or public
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
     * @param subject   the subject of the event
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
     * Changes the startDate to the given start date.
     *
     * @param s the given startDate
     * @return this builder
     */
    public EventBuilder startDate(LocalDate s) {
      this.startDate = s;
      return this;
    }

    /**
     * Changes the startTime to the given start time.
     *
     * @param s the given start time
     * @return this builder
     */
    public EventBuilder startTime(LocalTime s) {
      this.startTime = s;
      return this;
    }

    /**
     * Changes the endDate to the given end date.
     *
     * @param e the given end date
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
     * Changes the end time to the given time.
     *
     * @param e the given time
     * @return this builder
     */
    public EventBuilder endTime(LocalTime e) {
      this.endTime = e;
      return this;
    }

    /**
     * Changes the description to the given string.
     *
     * @param d the given string/new description
     * @return this builder
     */
    public EventBuilder description(String d) {
      this.description = d;
      return this;
    }

    /**
     * Changes the location to the given string.
     *
     * @param l
     * @return
     */
    public EventBuilder location(String l) {
      this.location = l;
      return this;
    }

    /**
     * Changes the isPrivate to the given boolean.
     *
     * @param p
     * @return
     */
    public EventBuilder isPrivate(boolean p) {
      this.isPrivate = p;
      return this;
    }

    /**
     *
     * Constructs a new event instance.
     * @return
     */
    public Event build() {
      return new Event(subject, startDate, startTime, endDate, endTime,
              description, location, isPrivate);
    }

  }

  /**
   * Gets the corresponding subject of an event.
   * @return String
   */
  public String getSubject() {
    return this.subject;
  }

  /**
   * Gets the corresponding startDate of an event.
   * @return LocalDate
   */
  public LocalDate getStartDate() {
    return this.startDate;
  }

  /**
   * Gets the corresponding startTime of an event.
   * @return LocalTime
   */
  public LocalTime getStartTime() {
    return this.startTime;
  }

  /**
   * Gets the corresponding endDate of an event.
   * @return LocalDate
   */
  public LocalDate getEndDate() {
    return this.endDate;
  }

  /**
   * Gets the corresponding endTime of an event.
   * @return LocalTime
   */
  public LocalTime getEndTime() {
    return this.endTime;
  }

  /**
   * Gets the corresponding description of an event.
   * @return String
   */
  public String getDescription() {
    return description;
  }

  /**
   * Gets the corresponding location of an event.
   * @return String
   */

  public String getLocation() {
    return location;
  }


  /**
   * Gets the corresponding status of an event.
   * @return boolean
   */
  public boolean isPrivate() {
    return isPrivate;
  }

  /**
   * Overrides Java's toString method.
   * @return this event as a String with subject, startTime, endTime, and location
   */
  @Override
  public String toString() {
    return subject + " " + startTime + " " + endTime + " " + location;
  }

}
