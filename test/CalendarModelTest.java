import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import calendarmodel.CalendarModel;
import calendarmodel.Event;
import calendarmodel.EventSeries;
import calendarmodel.IModel;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the methods within the calendar model.
 */
public class CalendarModelTest {
  IModel model;

  @Before
  public void setUp() {
    model = new CalendarModel(new HashMap<>(), new HashMap<>());
  }

  @Test
  public void testCreateEvent_UntilSpecificDate() {
    this.setUp();
    Event e =
            model.createEvent("create event Hehe from 2025-03-23T12:00 to 2025-04-04T03:00");

    assertEquals("Hehe", e.getSubject());
    assertEquals("2025-03-23", e.getStartDate().toString());
    assertEquals("12:00", e.getStartTime().toString());
    assertEquals("2025-04-04", e.getEndDate().toString());
    assertEquals("03:00", e.getEndTime().toString());
    assertEquals("", e.getDescription());

    try {
      model.createEvent("create event Hehe from 2025-03-23T12:00 to 2025-04-04T03:00");
    } catch (IllegalArgumentException f) {
      // should pass since the event overlaps
    }
  }

  @Test
  public void testCreateEvents_AllDayEvent() {
    this.setUp();
    Event d = model.createEvent("create event OOD on 2025-06-03");
    assertEquals("OOD", d.getSubject());
    assertEquals("2025-06-03", d.getStartDate().toString());
    assertEquals("08:00", d.getStartTime().toString());
    assertEquals("2025-06-03", d.getEndDate().toString());
    assertEquals("17:00", d.getEndTime().toString());
    assertEquals("", d.getDescription());
  }

  // you cannot create an all day event with day and time so it will be default which is correct
  // you can create a default event over an existing event day because it is not the same
  @Test
  public void testCreateEvents_SameStartDate_Default() {
    this.setUp();
    Event e =
            model.createEvent("create event Hehe from 2025-03-23T12:00 to 2025-04-04T03:00");
    Event d = model.createEvent("create event Hehe on 2025-03-23");
    assertEquals("Hehe", d.getSubject());
    assertEquals("2025-03-23", d.getStartDate().toString());
    assertEquals("08:00", d.getStartTime().toString());
    assertEquals("2025-03-23", d.getEndDate().toString());
    assertEquals("17:00", d.getEndTime().toString());
    assertEquals("", d.getDescription());
  }

  @Test
  public void testCreateEventSeries() {
    this.setUp();
    EventSeries e = model.createEventSeries("create event Yabba from "
            + "2025-09-23T04:56 to 2025-09-23T09:33 "
            + "repeats MWF for 5 times");
    assertEquals("Yabba", e.getSubject());
    assertEquals("2025-09-23", e.getStartDate().toString());
    assertEquals("04:56", e.getStartTime().toString());
    assertEquals("2025-09-23", e.getEndDate().toString());
    assertEquals("09:33", e.getEndTime().toString());
    assertEquals("MWF", e.getRecurrenceDays());
    assertEquals(5, e.getOccurrenceCount());
    assertEquals("2025-09-23", e.getUntilDate().toString());
  }

  @Test
  public void testEditEvent_ChangeSubject() {
    this.setUp();
    Event oldEvent =
            model.createEvent("create event Hehe from 2025-03-23T12:00 to 2025-04-04T03:00");

    Event anotherEvent =
            model.createEvent("create event OOD on 2025-06-03");

    Event newEvent = model.editEvent("edit event subject Hehe from 2025-03-23T12:00"
            + " to 2025-04-04T03:00 with Haha");

    assertEquals("Haha", newEvent.getSubject());
    assertEquals("2025-03-23", newEvent.getStartDate().toString());
    assertEquals("12:00", newEvent.getStartTime().toString());
    assertEquals("2025-04-04", newEvent.getEndDate().toString());
    assertEquals("03:00", newEvent.getEndTime().toString());
    assertEquals("", newEvent.getDescription());
  }

  @Test
  public void testEditEvent_ChangeStart() {
    this.setUp();

    Event oldEvent =
            model.createEvent("create event Hehe from 2025-03-23T12:00 to 2025-04-04T03:00");

    Event newEvent2 = model.editEvent("edit event start Hehe from 2025-03-23T12:00"
            + " to 2025-04-04T03:00 with 2025-02-02T02:00");

    assertEquals("Hehe", newEvent2.getSubject());
    assertEquals("2025-02-02", newEvent2.getStartDate().toString());
    assertEquals("02:00", newEvent2.getStartTime().toString());
    assertEquals("2025-04-04", newEvent2.getEndDate().toString());
    assertEquals("03:00", newEvent2.getEndTime().toString());
    assertEquals("", newEvent2.getDescription());
  }


  @Test
  public void testEditEvent_ChangeEnd() {
    this.setUp();

    Event oldEvent =
            model.createEvent("create event Hehe from 2025-03-23T12:00 to 2025-03-23T13:00");

    Event newEvent2 = model.editEvent("edit event end Hehe from 2025-03-23T12:00"
            + " to 2025-03-23T13:00 with 2025-03-23T14:00");

    assertEquals("Hehe", newEvent2.getSubject());
    assertEquals("2025-03-23", newEvent2.getStartDate().toString());
    assertEquals("12:00", newEvent2.getStartTime().toString());
    assertEquals("2025-03-23", newEvent2.getEndDate().toString());
    assertEquals("14:00", newEvent2.getEndTime().toString());
    assertEquals("", newEvent2.getDescription());
  }


  @Test
  public void testEditEvent_changeLocation() {
    this.setUp();
    model.createEvent("create event class from 2025-07-06T12:00 to 2025-07-06T13:00");
    Event updated = model.editEvent("edit event location class from 2025-07-06T12:00 to " +
            "2025-07-06T13:00 with home");
    assertEquals("home", updated.getLocation());
  }

  @Test
  public void testEditEvent_changeSubject() {
    this.setUp();
    model.createEvent("create event class from 2025-07-06T08:00 to 2025-07-06T09:00");
    Event updated = model.editEvent("edit event subject class from 2025-07-06T08:00 to " +
            "2025-07-06T09:00 with study");
    assertEquals("study", updated.getSubject());
  }

  @Test
  public void testEditEvents_ChangeSubject() {
    this.setUp();
    // event is part of a series

    EventSeries e = model.createEventSeries("create event Yabba from " +
            "2025-09-23T04:56 to 2025-09-23T09:33 " +
            "repeats MWF for 5 times");

    // change all subjects of all the events part of the series on or after the start date
    EventSeries newSeries = model.editEventSeries("edit events subject Yabba from " +
            "2025-09-23T04:56 with Fundies");

    assertEquals("Fundies", newSeries.getSubject());
    assertEquals("2025-09-23", newSeries.getStartDate().toString());
    assertEquals("04:56", newSeries.getStartTime().toString());
    assertEquals("2025-09-23", newSeries.getEndDate().toString());
    assertEquals("09:33", newSeries.getEndTime().toString());
    assertEquals("MWF", newSeries.getRecurrenceDays());
    assertEquals(5, newSeries.getOccurrenceCount());
    assertEquals("2025-09-23", newSeries.getUntilDate().toString());
    // else if the event is not part of the series
    // change only that singular event
  }

  @Test
  public void testEditSeries_ChangeSubject() {
    this.setUp();
    // event is part of a series

    EventSeries e = model.createEventSeries("create event Yabba from "
            + "2025-09-23T04:56 to 2025-09-23T09:33 "
            + "repeats MWF for 5 times");

    // change all subjects of all the events part of the series on or after the start date
    EventSeries newSeries = model.editEventSeries("edit events subject Yabba from "
            + "2025-09-23T04:56 with Fundies");

    assertEquals("Fundies", newSeries.getSubject());
    assertEquals("2025-09-23", newSeries.getStartDate().toString());
    assertEquals("04:56", newSeries.getStartTime().toString());
    assertEquals("2025-09-23", newSeries.getEndDate().toString());
    assertEquals("09:33", newSeries.getEndTime().toString());
    assertEquals("MWF", newSeries.getRecurrenceDays());
    assertEquals(5, newSeries.getOccurrenceCount());
    assertEquals("2025-09-23", newSeries.getUntilDate().toString());
    // else if the event is not part of the series
    // change only that singular event
  }

  @Test
  public void testEditEvents_ChangeStartDate() {
    this.setUp();
    EventSeries e = model.createEventSeries("create event Yabba from " +
            "2025-09-23T04:56 to 2025-09-23T09:23 " +
            "repeats MWF for 5 times");

    EventSeries newSeries = model.editEventSeries("edit events start Yabba from " +
            "2025-09-23T04:56 with 2025-10-23T09:23");

    assertEquals("Yabba", newSeries.getSubject());
    assertEquals("2025-10-23", newSeries.getStartDate().toString());
    assertEquals("09:23", newSeries.getStartTime().toString());
    assertEquals("2025-09-23", newSeries.getEndDate().toString());
    assertEquals("09:23", newSeries.getEndTime().toString());
    assertEquals("MWF", newSeries.getRecurrenceDays());
    assertEquals(5, newSeries.getOccurrenceCount());
    assertEquals("2025-09-23", newSeries.getUntilDate().toString());
  }

  @Test
  public void testEditEvents_ChangeStartTime() {
    this.setUp();
    EventSeries e = model.createEventSeries("create event Yabba from " +
            "2025-09-23T04:56 to 2025-09-23T09:23 " +
            "repeats MWF for 5 times");

    EventSeries newSeries = model.editEventSeries("edit events start Yabba from " +
            "2025-09-23T04:56 with 2025-09-23T10:23");

    assertEquals("Yabba", newSeries.getSubject());
    assertEquals("2025-09-23", newSeries.getStartDate().toString());
    assertEquals("10:23", newSeries.getStartTime().toString());
    assertEquals("2025-09-23", newSeries.getEndDate().toString());
    assertEquals("09:23", newSeries.getEndTime().toString());
    assertEquals("MWF", newSeries.getRecurrenceDays());
    assertEquals(5, newSeries.getOccurrenceCount());
    assertEquals("2025-09-23", newSeries.getUntilDate().toString());
  }

  @Test
  public void testEditEvents_ChangeEndDate() {
    this.setUp();
    EventSeries e = model.createEventSeries("create event Yabba from " +
            "2025-09-23T04:56 to 2025-09-23T09:23 " +
            "repeats MWF for 5 times");

    EventSeries newSeries = model.editEventSeries("edit events end Yabba from " +
            "2025-09-23T04:56 with 2000-09-23T09:23");

    assertEquals("Yabba", newSeries.getSubject());
    assertEquals("2025-09-23", newSeries.getStartDate().toString());
    assertEquals("04:56", newSeries.getStartTime().toString());
    assertEquals("2000-09-23", newSeries.getEndDate().toString());
    assertEquals("09:23", newSeries.getEndTime().toString());
    assertEquals("MWF", newSeries.getRecurrenceDays());
    assertEquals(5, newSeries.getOccurrenceCount());
    assertEquals("2025-09-23", newSeries.getUntilDate().toString());
  }

  @Test
  public void testEditEvents_ChangeEndTime() {
    this.setUp();
    EventSeries e = model.createEventSeries("create event Yabba from " +
            "2025-09-23T04:56 to 2025-09-23T09:23 " +
            "repeats MWF for 5 times");

    EventSeries newSeries = model.editEventSeries("edit events end Yabba from " +
            "2025-09-23T04:56 with 2000-09-23T09:22");

    assertEquals("Yabba", newSeries.getSubject());
    assertEquals("2025-09-23", newSeries.getStartDate().toString());
    assertEquals("04:56", newSeries.getStartTime().toString());
    assertEquals("2000-09-23", newSeries.getEndDate().toString());
    assertEquals("09:22", newSeries.getEndTime().toString());
    assertEquals("MWF", newSeries.getRecurrenceDays());
    assertEquals(5, newSeries.getOccurrenceCount());
    assertEquals("2025-09-23", newSeries.getUntilDate().toString());
  }


  @Test
  public void testPrintEvents() {
    this.setUp();
    Event event1 = model.createEvent("create event Hi "
            + "from 2025-04-03T09:00 to 2025-04-03T10:00");
    Event event2 = model.createEvent("create event Hai "
            + "from 2025-04-03T11:00 to 2025-04-03T12:00");
    Event event3 = model.createEvent("create event Bye "
            + "from 2025-04-03T14:00 to 2025-04-03T17:00");

    List<String> expected = new ArrayList<>(Arrays.asList(event3.toString(),
            event1.toString(), event2.toString()));

    List<String> actual = model.printEvents("print events from 2025-04-03T09:00 " +
            "to 2025-04-03T17:00");

    assertEquals(expected, actual);
  }

  @Test
  public void testStatus_isBusy() {
    this.setUp();
    model.createEvent("create event Happy on 2026-02-03");
    String actual = model.showStatus("show status on 2026-02-03T15:33");
    assertEquals("Busy", actual);
  }

  @Test
  public void testStatus_isNotBusy() {
    model.createEvent("create event Lala on 2025-03-23");
    String actual = model.showStatus("show status on 2025-03-23T18:00");
    assertEquals("Not Busy", actual);
  }


  // test checkEventOverlap
  @Test
  public void testEventOverlap() {
    this.setUp();

    model.createEvent("create event Hehe from 2025-03-23T12:00 to 2025-04-04T03:00");
    assertEquals("Hehe", model.getHashMap()
            .get(LocalDateTime.of(LocalDate.of(2025, 3, 23),
                    LocalTime.of(12, 0))).get(0).getSubject());

    try {
      model.createEvent("create event Hehe from 2025-03-23T12:00 to 2025-04-04T03:00");
    } catch (IllegalArgumentException e) {
      // passes
    }
  }

  @Test
  public void testExceptions() {
    this.setUp();

    // wrong input format
    try {
      model.createEvent("create event from 2025-03-23T12:00 to 2025-04-04T03:00");
    } catch (DateTimeParseException e) {
      // passes
    }

    model.createEvent("create event Breakfast "
            + "from 2025-04-03T09:00 to 2025-04-03T10:00");
    assertEquals("Breakfast", model.getHashMap()
            .get(LocalDateTime.of(LocalDate.of(2025, 4, 3),
                    LocalTime.of(9, 0))).get(0).getSubject());

    // invalid property to edit
    try {
      model.editEvent("edit event time Breakfast from " +
              "2025-03-23T12:00 to 2025-04-04T03:00 with study");
    } catch (IllegalArgumentException e) {
      // passes
    }
  }


}