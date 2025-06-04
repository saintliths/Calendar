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

  }

  @Test
  public void testCreateEventSeries() {
    EventSeries e = model.createEventSeries("create event Yabba from " +
            "2025-09-23T04:56 to 2025-09-30T09:33 " +
            "repeats MWF for 5 times");
    assertEquals("Yabba", e.getSubject());
    assertEquals("2025-09-23", e.getStartDate().toString());
    assertEquals("04:56", e.getStartTime().toString());
    assertEquals("2025-09-30", e.getEndDate().toString());
    assertEquals("09:33", e.getEndTime().toString());
    assertEquals("MWF", e.getRecurrenceDays());
    assertEquals(5, e.getOccurrenceCount());
    assertEquals("2025-09-30", e.getUntilDate().toString());

  }

  @Test
  public void testEditEvent_ChangeSubject() {
    Event oldEvent =
            model.createEvent("create event Hehe from 2025-03-23T12:00 to 2025-04-04T03:00");

    Event newEvent = model.editEvent("edit event subject Hehe from 2025-03-23T12:00" +
            " to 2025-04-04T03:00 with 2025-04-23T12:00");

    assertEquals("Hehe", newEvent.getSubject());
    assertEquals("2025-04-23", newEvent.getStartDate().toString());
    assertEquals("12:00", newEvent.getStartTime().toString());
    assertEquals("2025-04-04", newEvent.getEndDate().toString());
    assertEquals("03:00", newEvent.getEndTime().toString());
    assertEquals("", newEvent.getDescription());

  }

}