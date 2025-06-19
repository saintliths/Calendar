import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.ZoneId;
import java.util.HashMap;

import calendarcontroller.CalendarController;
import calendarcontroller.IController;
import calendarmodel.CalendarModel;
import calendarmodel.IModel;
import calendarmodel.IModel2;
import calendarmodel.MultipleCalendar;
import calendarview.CalendarView;
import calendarview.IView;
import calendarview.IView2;
import calendarview.NewCalendarView;

import static org.junit.Assert.assertEquals;

/**
 * This class represents a test for a CalendarController.
 */
public class CalendarControllerTest {


  @Test
  public void controllerTest() {

    IModel model = new CalendarModel(new HashMap<>(),
            new HashMap<>());
    model.createEvent("create event Birthday on 2025-03-23");

    String input = "show status on 2025-03-23T13:23";
    Readable in = new InputStreamReader(new ByteArrayInputStream(input.getBytes()));


    OutputStream outStream = new ByteArrayOutputStream();
    PrintStream pStream = new PrintStream(outStream);

    IView view = new CalendarView(pStream);
    ZoneId newYork = ZoneId.of("America/New_York");
    IModel2 model2 = new MultipleCalendar("Basic", newYork, model);

    IController controller = new CalendarController(model2, in, view);
    controller.control();

    String output = outStream.toString();

    assertEquals("Hi! Welcome to our Calendar. The supported commands are: "
            + System.lineSeparator()
            + "create event <eventSubject> from <dateStringTtimeString> to " +
            "<dateStringTtimeString>"
            + System.lineSeparator()
            + "create event <eventSubject> from <dateStringTtimeString> to " +
            "<dateStringTtimeString> repeats <weekdays> for <N> times"
            + System.lineSeparator()
            + "create event <eventSubject> from <dateStringTtimeString> to " +
            "<dateStringTtimeString> repeats <weekdays> until <dateString>"
            + System.lineSeparator()
            + "create event <eventSubject> on <dateString>"
            + System.lineSeparator()
            + "create event <eventSubject> on <dateString> repeats <weekdays> for <N> times"
            + System.lineSeparator()
            + "create event <eventSubject> on <dateString> repeats <weekdays> until <dateString>"
            + System.lineSeparator()
            + "edit event <property> <eventSubject> from <dateStringTtimeString> to " +
            "<dateStringTtimeString> with <NewPropertyValue>"
            + System.lineSeparator()
            + "edit events <property> <eventSubject> from <dateStringTtimeString> with " +
            "<NewPropertyValue>" + System.lineSeparator()
            + "edit series <property> <eventSubject> from <dateStringTtimeString> with " +
            "<NewPropertyValue>" + System.lineSeparator()
            + "print events on <dateString>" + System.lineSeparator()
            + "print events from <dateStringTtimeString> to <dateStringTtimeString>"
            + System.lineSeparator()
            + "show status on <dateStringTtimeString>" + System.lineSeparator()
            + "create calendar --name <calName> --timezone area/location"
            + System.lineSeparator()
            + "edit calendar --name <name-of-calendar> --property <property-name> " +
            "<new-property-value>" + System.lineSeparator()
            + "use calendar --name <name-of-calendar>" + System.lineSeparator()
            + "copy event <eventName> on <dateStringTtimeString> --target <calendarName> " +
            "to <dateStringTtimeString>" + System.lineSeparator()
            + "copy events on <dateString> --target <calendarName> to <dateString>"
            + System.lineSeparator()
            + "copy events between <dateString> and <dateString> --target <calendarName> " +
            "to <dateString>" + System.lineSeparator()
            + "exit (quit the program) " + System.lineSeparator()
            + "Busy" + System.lineSeparator() + System.lineSeparator(), output);
  }
}