package calendarmodel;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a series of events.
 */
public class EventSeries {
  private final String subject;
  private final LocalDate startDate;
  private final LocalTime startTime;
  private final LocalDate endDate;
  private final LocalTime endTime;
  private final String description;
  private final String location;
  private final boolean isPrivate;
  private final String recurrenceDays;  // e.g., [MONDAY, WEDNESDAY]
  private final int occurrenceCount;
  private final LocalDate untilDate;

  /**
   * Public constructor for an EventSeries.
   *
   * @param subject         the subject of the event
   * @param startDate       the start date of the event
   * @param startTime       the time the event starts
   * @param endDate         the end date of the event
   * @param endTime         the time the event ends
   * @param description     description of the event
   * @param location        location of the event
   * @param isPrivate       whether is it private or public
   * @param recurrenceDays  what days the event repeats on
   * @param occurrenceCount how many times the event repeat
   * @param untilDate       when does the event repeat until
   */
  public EventSeries(String subject, LocalDate startDate, LocalTime startTime, LocalDate endDate,
                     LocalTime endTime, String description, String location, boolean isPrivate,
                     String recurrenceDays, int occurrenceCount, LocalDate untilDate) {
    this.subject = subject;
    this.startDate = startDate;
    this.startTime = startTime;
    this.endDate = endDate;
    this.endTime = endTime;
    this.description = description;
    this.location = location;
    this.isPrivate = isPrivate;
    this.recurrenceDays = recurrenceDays;
    this.occurrenceCount = occurrenceCount;
    this.untilDate = untilDate;
  }

  /**
   * Inner class representing a builder for the EventSeries class.
   */
  static class EventSeriesBuilder {
    private String subject;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private String description;
    private String location;
    private boolean isPrivate;
    private String recurrenceDays;  // e.g., [MONDAY, WEDNESDAY]
    private int occurrenceCount;
    private LocalDate untilDate;

    /**
     * Constructs an EventSeriesBuilder object.
     *
     * @param subject   the subject of the event series
     * @param startDate the start date of the event series
     * @param startTime the start time of the event series
     */
    public EventSeriesBuilder(String subject, LocalDate startDate, LocalTime startTime) {
      this.subject = subject;
      this.startDate = startDate;
      this.startTime = startTime;
      this.endDate = startDate;
      this.endTime = LocalTime.of(17, 0);
      this.recurrenceDays = "";
      this.occurrenceCount = 0;
      this.untilDate = endDate;
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
    public EventSeriesBuilder startDate(LocalDate s) {
      this.startDate = s;
      return this;
    }

    /**
     * Changes the startTime to the given start time.
     *
     * @param s the given start time
     * @return this builder
     */
    public EventSeriesBuilder startTime(LocalTime s) {
      this.startTime = s;
      return this;
    }

    /**
     * Changes the endDate to the given end date.
     *
     * @param e the given end date
     * @return this builder
     */
    public EventSeriesBuilder endDate(LocalDate e) {
      this.endDate = e;
      return this;
    }

    /**
     * Changes the endTime to the given end time.
     *
     * @param e the given end time
     * @return this builder
     */
    public EventSeriesBuilder endTime(LocalTime e) {
      this.endTime = e;
      return this;
    }

    /**
     * Changes the subject to the given subject.
     *
     * @param s the given subject
     * @return this builder
     */
    public EventSeriesBuilder subject(String s) {
      this.subject = s;
      return this;
    }

    /**
     * Changes the description to the given description.
     *
     * @param d the given description
     * @return this builder
     */
    public EventSeriesBuilder description(String d) {
      this.description = d;
      return this;
    }

    /**
     * Changes the location to the given location.
     *
     * @param l the given location
     * @return this builder
     */
    public EventSeriesBuilder location(String l) {
      this.location = l;
      return this;
    }

    /**
     * Changes the isPrivate field to the given boolean.
     *
     * @param p the given boolean
     * @return this builder
     */
    public EventSeriesBuilder isPrivate(boolean p) {
      this.isPrivate = p;
      return this;
    }

    /**
     * Changes the recurrenceDays to the given recurrence days.
     *
     * @param r the given recurrence days
     * @return this builder
     */
    public EventSeriesBuilder recurrenceDays(String r) {
      this.recurrenceDays = r;
      return this;
    }

    /**
     * Changes the occurrenceCount to the given occurrence count.
     *
     * @param c the given occurrence count
     * @return this builder
     */
    public EventSeriesBuilder occurrenceCount(int c) {
      this.occurrenceCount = c;
      return this;
    }

    /**
     * Changes the untilDate to the given until date.
     *
     * @param u the given until date
     * @return this builder
     */
    public EventSeriesBuilder untilDate(LocalDate u) {
      this.untilDate = u;
      return this;
    }

    /**
     * Builds an EventSeries from these fields.
     *
     * @return an EventSeries object.
     */
    public EventSeries build() {
      return new EventSeries(subject, startDate, startTime, endDate, endTime,
              description, location, isPrivate, recurrenceDays, occurrenceCount, untilDate);
    }

  }

  /**
   * Get the subject.
   *
   * @return a String representing this subject
   */
  public String getSubject() {
    return this.subject;
  }

  /**
   * Get the start date.
   *
   * @return a LocalDate representing this start date
   */
  public LocalDate getStartDate() {
    return this.startDate;
  }

  /**
   * Get the start time.
   *
   * @return a LocalTime representing this start time.
   */
  public LocalTime getStartTime() {
    return this.startTime;
  }

  /**
   * Get the end date.
   *
   * @return a LocalDate representing this end date.
   */
  public LocalDate getEndDate() {
    return this.endDate;
  }

  /**
   * Get the end time.
   *
   * @return a LocalTime representing this end time.
   */
  public LocalTime getEndTime() {
    return this.endTime;
  }

  /**
   * Get the recurrence days.
   *
   * @return a String representing this recurrence days.
   */
  public String getRecurrenceDays() {
    return this.recurrenceDays;
  }

  /**
   * Gets the corresponding number of occurrences of an event series.
   *
   * @return Number of occurrences.
   */
  public int getOccurrenceCount() {
    return this.occurrenceCount;
  }

  /**
   * Gets the corresponding endDate of event series.
   *
   * @return LocalDate of the end.
   */
  public LocalDate getUntilDate() {
    return this.untilDate;
  }

  /**
   * Gets the corresponding description of event series.
   *
   * @return description of the event
   */
  public String getDescription() {
    return description;
  }


  /**
   * Gets the corresponding location of event series.
   *
   * @return String of the location
   */
  public String getLocation() {
    return location;
  }


  /**
   * Gets the corresponding status of event series.
   *
   * @return true for private status
   */
  public boolean isPrivate() {
    return isPrivate;
  }


}
