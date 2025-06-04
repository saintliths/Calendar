import org.junit.Before;
import org.junit.Test;

import CalendarModel.CalendarModel;
import CalendarModel.Event;
import CalendarModel.EventSeries;
import CalendarModel.IModel;

import static org.junit.Assert.*;

public class CalendarModelTest {
  IModel model;

  @Before
  public void setUp() {
    model = new CalendarModel();
  }

  @Test
  public void testCreateEvent() {
    Event e =
            model.createEvent("create event Hehe from 2025-03-23T12:00 to 2025-04-04T03:00");

    assertEquals("Hehe", e.getSubject());
    assertEquals("2025-03-23", e.getStartDate().toString());
    assertEquals("12:00", e.getStartTime().toString());
    assertEquals("2025-04-04", e.getEndDate().toString());
    assertEquals("03:00", e.getEndTime().toString());
    assertEquals("", e.getDescription());


    Event d = model.createEvent("create event OOD on 2025-06-03");
    assertEquals("OOD", d.getSubject());
    assertEquals("2025-06-03", d.getStartDate().toString());
    assertEquals("08:00", d.getStartTime().toString());
    assertEquals("2025-06-03", d.getEndDate().toString());
    assertEquals("17:00", d.getEndTime().toString());
    assertEquals("", d.getDescription());

    String output = model.showStatus("show status on 2025-06-03T08:00");
    assertEquals("Busy", output);

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

    Event newEvent2 = model.editEvent("edit event start Hehe from 2025-03-23T12:00" +
            " to 2025-04-04T03:00 with 2025-02-02T02:00");

    assertEquals("Hehe", newEvent2.getSubject());
    assertEquals("2025-02-02", newEvent2.getStartDate().toString());
    assertEquals("02:00", newEvent2.getStartTime().toString());
    assertEquals("2025-04-04", newEvent2.getEndDate().toString());
    assertEquals("03:00", newEvent2.getEndTime().toString());
    assertEquals("", newEvent2.getDescription());

    Event newEvent3 = model.editEvent("edit event start Hehe on 2025-03-23T12:00" +
            " with 2025-02-02T02:00");

    assertEquals("Hehe", newEvent2.getSubject());
    assertEquals("2025-02-02", newEvent2.getStartDate().toString());
    assertEquals("02:00", newEvent2.getStartTime().toString());
    assertEquals("2025-04-04", newEvent2.getEndDate().toString());
    assertEquals("03:00", newEvent2.getEndTime().toString());
    assertEquals("", newEvent2.getDescription());

  }

  @Test
  public void testPrintEvents() {

  }

}