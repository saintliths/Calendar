package CalendarModel;

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
   * Private constructor for an EventSeries.
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
     * @param subject the subject of the event series
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
     * Changes the startDate to the given start date
     * @param s the given startDate
     * @return this builder
     */
    public EventSeriesBuilder startDate(LocalDate s) {
      this.startDate = s;
      return this;
    }

    /**
     * Changes the startTime to the given start time
     * @param s the given start time
     * @return this builder
     */
    public EventSeriesBuilder startTime(LocalTime s) {
      this.startTime = s;
      return this;
    }

    /**
     * Changes the endDate to the given end date
     * @param e the given end date
     * @return this builder
     */
    public EventSeriesBuilder endDate(LocalDate e) {
      this.endDate = e;
      return this;
    }

    /**
     * @param e
     * @return
     */
    public EventSeriesBuilder endTime(LocalTime e) {
      this.endTime = e;
      return this;
    }

    public EventSeriesBuilder subject(String s) {
      this.subject = s;
      return this;
    }

    /**
     * @param d
     * @return
     */
    public EventSeriesBuilder description(String d) {
      this.description = d;
      return this;
    }

    /**
     * @param l
     * @return
     */
    public EventSeriesBuilder location(String l) {
      this.location = l;
      return this;
    }

    /**
     * @param p
     * @return
     */
    public EventSeriesBuilder isPrivate(boolean p) {
      this.isPrivate = p;
      return this;
    }

    /**
     * @param r
     * @return
     */
    public EventSeriesBuilder recurrenceDays(String r) {
      this.recurrenceDays = r;
      return this;
    }

    /**
     * @param c
     * @return
     */
    public EventSeriesBuilder occurrenceCount(int c) {
      this.occurrenceCount = c;
      return this;
    }

    /**
     * @param u
     * @return
     */
    public EventSeriesBuilder untilDate(LocalDate u) {
      this.untilDate = u;
      return this;
    }

    /**
     * @return
     */
    public EventSeries build() {
      return new EventSeries(subject, startDate, startTime, endDate, endTime,
              description, location, isPrivate, recurrenceDays, occurrenceCount, untilDate);
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

  public String getRecurrenceDays() {
    return this.recurrenceDays;
  }

  public int getOccurrenceCount() {
    return this.occurrenceCount;
  }

  public LocalDate getUntilDate() {
    return this.untilDate;
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


}
