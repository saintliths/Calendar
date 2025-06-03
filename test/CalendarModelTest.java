import org.junit.Before;
import org.junit.Test;

import CalendarModel.CalendarModel;
import CalendarModel.Event;
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
    Event e = model.createEvent("create event Hehe from " +
            "2025-03-23T12:00 to 2025-04-04T03:00");

    assertEquals("Hehe", e.getSubject());
    assertEquals("2025-03-23", e.getStartDate().toString());
    assertEquals("12:00", e.getStartTime().toString());
    assertEquals("2025-04-04", e.getEndDate().toString());
    assertEquals("03:00", e.getEndTime().toString());
    assertEquals("", e.getDescription());

  }

}