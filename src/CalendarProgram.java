import CalendarController.CalendarController;
import CalendarController.IController;
import CalendarModel.CalendarModel;
import CalendarModel.IModel;
import CalendarView.CalendarView;
import CalendarView.IView;

public class CalendarProgram {
  public static void main(String []args) {
    IModel model = new CalendarModel();
    IView view = new CalendarView(System.out);
    IController controller = new CalendarController(model, System.in,view);
    controller.go();
  }
}
