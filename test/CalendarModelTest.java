import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import CalendarModel.CalendarModel;
import CalendarModel.Event;
import CalendarModel.EventSeries;
import CalendarModel.IModel;

import static org.junit.Assert.*;

public class CalendarModelTest {
  IModel model;

  @Before
  public void setUp() {
    model = new CalendarModel(new HashMap<>(), new HashMap<>());
  }

  @Test
  public void testCreateEvent_UntilSpecificDate() {
    Event e =
            model.createEvent("create event Hehe from 2025-03-23T12:00 to 2025-04-04T03:00");

    assertEquals("Hehe", e.getSubject());
    assertEquals("2025-03-23", e.getStartDate().toString());
    assertEquals("12:00", e.getStartTime().toString());
    assertEquals("2025-04-04", e.getEndDate().toString());
    assertEquals("03:00", e.getEndTime().toString());
    assertEquals("", e.getDescription());
  }

  @Test
  public void testCreateEvents_AllDayEvent() {
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

  // throw exception if a property has more than its expected
  @Test
  public void testCreateEvents_MultipleSubjectWords() {
    Event e =
            model.createEvent("create event " + "Hehe Haha" + "from 2025-03-23T12:00 to 2025-04-04T03:00");

    assertEquals("Hehe Haha", e.getSubject());
    assertEquals("2025-03-23", e.getStartDate().toString());
    assertEquals("12:00", e.getStartTime().toString());
    assertEquals("2025-04-04", e.getEndDate().toString());
    assertEquals("03:00", e.getEndTime().toString());
    assertEquals("", e.getDescription());
  }


  @Test
  public void testCreateEventSeries() {
    EventSeries e = model.createEventSeries("create event Yabba from " +
            "2025-09-23T04:56 to 2025-09-23T09:33 " +
            "repeats MWF for 5 times");
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
    Event oldEvent =
            model.createEvent("create event Hehe from 2025-03-23T12:00 to 2025-04-04T03:00");

    Event anotherEvent =
            model.createEvent("create event OOD on 2025-06-03");

    Event newEvent = model.editEvent("edit event subject Hehe from 2025-03-23T12:00" +
            " to 2025-04-04T03:00 with Haha");

    assertEquals("Haha", newEvent.getSubject());
    assertEquals("2025-03-23", newEvent.getStartDate().toString());
    assertEquals("12:00", newEvent.getStartTime().toString());
    assertEquals("2025-04-04", newEvent.getEndDate().toString());
    assertEquals("03:00", newEvent.getEndTime().toString());
    assertEquals("", newEvent.getDescription());
  }

  @Test
  public void testEditEvent_ChangeStart() {

    Event oldEvent =
            model.createEvent("create event Hehe from 2025-03-23T12:00 to 2025-04-04T03:00");

    Event newEvent2 = model.editEvent("edit event start Hehe from 2025-03-23T12:00" +
            " to 2025-04-04T03:00 with 2025-02-02T02:00");

    assertEquals("Hehe", newEvent2.getSubject());
    assertEquals("2025-02-02", newEvent2.getStartDate().toString());
    assertEquals("02:00", newEvent2.getStartTime().toString());
    assertEquals("2025-04-04", newEvent2.getEndDate().toString());
    assertEquals("03:00", newEvent2.getEndTime().toString());
    assertEquals("", newEvent2.getDescription());
  }


  @Test
  public void testEditEvent_changeLocation() {
    model.createEvent("create event class from 2025-07-06T12:00 to 2025-07-06T13:00");
    Event updated = model.editEvent("edit event location class from 2025-07-06T12:00 to 2025-07-06T13:00 with home");
    assertEquals("home", updated.getLocation());
  }

  @Test
  public void testEditEvent_changeSubject() {
    model.createEvent("create event class from 2025-07-06T08:00 to 2025-07-06T09:00");
    Event updated = model.editEvent("edit event subject class from 2025-07-06T08:00 to 2025-07-06T09:00 with study");
    assertEquals("study", updated.getSubject());
  }

  @Test
  public void testEditEvents_ChangeSubject() {
    this.setUp();
    // event is part of a series

    EventSeries e = model.createEventSeries("create event Yabba from " +
            "2025-09-23T04:56 to 2025-09-23T09:33 " +
            "repeats MWF for 5 times");
    System.out.print(e.getEndTime());

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

    EventSeries e = model.createEventSeries("create event Yabba from " +
            "2025-09-23T04:56 to 2025-09-23T09:33 " +
            "repeats MWF for 5 times");
    System.out.print(e.getEndTime());

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
  public void testPrintEvents() {
    Event event1 = model.createEvent("create event Hi " +
            "from 2025-04-03T09:00 to 2025-04-03T10:00");
    Event event2 = model.createEvent("create event Hai " +
            "from 2025-04-03T11:00 to 2025-04-03T12:00");
    Event event3 = model.createEvent("create event Bye " +
            "from 2025-04-03T14:00 to 2025-04-03T17:00");

    List<String> expected = new ArrayList<>(Arrays.asList(event3.toString(),
            event1.toString(), event2.toString()));

    List<String> actual = model.printEvents("print events from 2025-04-03T09:00 " +
            "to 2025-04-03T17:00");

    assertEquals(expected, actual);
  }

  @Test
  public void testStatus_isBusy() {
    Event event1 = model.createEvent("create event on 2025-03-23");
    System.out.println(model.getHashMap().get(LocalDateTime.of(event1.getStartDate(),
            event1.getStartTime())));
    String actual = model.showStatus("show status on 2025-03-23T03:33");
    assertEquals("Busy", actual);
  }

  @Test
  public void testStatus_isNotBusy() {
    Event event1 = model.createEvent("create event on 2025-03-23");
    System.out.println(model.getHashMap().get(LocalDateTime.of(event1.getStartDate(),
            event1.getStartTime())));
    String actual = model.showStatus("show status on 2025-03-23T07:00");
    assertEquals("Not Busy", actual);
  }


  // test event toString
  @Test
  public void testToString() {
    Event e =
            model.createEvent("create event Hehe from 2025-03-23T12:00 to 2025-04-04T03:00");
    Event d =
            model.createEvent("create event Hehe from 2025-03-23T12:00 to 2025-04-04T03:00");
  }

  // test checkEventOverlap



  // test inclusivity for creating event series until a specific date

  // test checkEventOverlap

  // test isPrivate

  // test getLocation

  // test getDescription

  // test getEndTime

  // test getEndDate

  // test getStartTim

  // test getStartDate

  // test getSubject

  // test Event Build

  // test EventBuilder isPrivate(boolean p)

  // test EventBuilder location(String l)

  // test EventBuilder description(String d)

  // test EventBuilder endTime(LocalTime e)

  // test EventBuilder subject(String s)

  // test EventBuilder endDate(LocalDate e)

  // test EventBuilder startTime(LocalTime s)

  // test EventBuilder startDate(LocalDate s)





}