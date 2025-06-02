package CalendarModel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * Represents a series of events.
 */
public class EventSeries {
  private String subject;
  private LocalDate startDate;
  private LocalTime startTime;
  private LocalDate endDate;
  private LocalTime endTime;
  private String description;
  private String location;
  private boolean isPrivate;
  private ArrayList<DayOfWeek> recurrenceDays;  // e.g., [MONDAY, WEDNESDAY]
  private int occurrenceCount;

  /**
   * Private constructor for an EventSeries.
   * @param subject the subject of the event
   * @param startDate the start date of the event
   * @param startTime the time the event starts
   * @param endDate the end date of the event
   * @param endTime the time the event ends
   * @param description description of the event
   * @param location location of the event
   * @param isPrivate whether is it private or public
   * @param
   */
  public EventSeries(String subject, LocalDate startDate, LocalTime startTime, LocalDate endDate,
               LocalTime endTime, String description, String location, boolean isPrivate,
                     ArrayList<DayOfWeek> recurrenceDays, int occurenceCount) {
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
  }

  public static EventSeriesBuilder getBuilder() {
    return new EventSeriesBuilder();
  }

  static class EventSeriesBuilder {
    private LocalTime endDate;
    private String description;
    private String location;
    private boolean isPrivate;
    private int occurrenceCount;

    public EventSeriesBuilder() {
      this.endDate = LocalTime.MIDNIGHT;
      this.description = "";
      this.location = "";
      this.isPrivate = false;
      this.occurrenceCount = 0;
    }

    public EventSeriesBuilder endDate(LocalTime e) {
      this.endDate = e;
      return this;
    }

    public EventSeriesBuilder description(String d) {
      this.description = d;
      return this;
    }

    public EventSeriesBuilder location(String l) {
      this.location = l;
      return this;
    }

    public EventSeriesBuilder isPrivate(boolean p) {
      this.isPrivate = p;
      return this;
    }

    public EventSeriesBuilder occurenceCount(int n) {
      this.occurrenceCount = n;
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


}
