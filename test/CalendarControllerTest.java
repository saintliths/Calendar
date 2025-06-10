import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Scanner;

import calendarcontroller.CalendarController;
import calendarcontroller.IController;
import calendarmodel.CalendarModel;
import calendarmodel.IModel;
import calendarmodel.IModel2;
import calendarmodel.MultipleCalendar;
import calendarview.CalendarView;
import calendarview.IView;

import static org.junit.Assert.assertEquals;

/**
 * This class represents a test for a CalendarController.
 */
public class CalendarControllerTest {


  @Test
  public void controllerTest() {

    String input = "show status on 2025-03-23T03:23";
    Readable in = new InputStreamReader(new ByteArrayInputStream(input.getBytes()));


    OutputStream outStream = new ByteArrayOutputStream();
    PrintStream pStream = new PrintStream(outStream);

    IView view = new CalendarView(pStream);
    IModel model = new CalendarModel(new HashMap<>(),
            new HashMap<>());
    ZoneId est = ZoneId.of("EST");
    IModel2 model2 = new MultipleCalendar("Basic", est, model);

    model.createEvent("create event Birthday on 2025-03-23");

    IController controller = new CalendarController(model2, in, view);
    controller.control();

    String output = outStream.toString();

    assertEquals("Busy", output);
  }
}