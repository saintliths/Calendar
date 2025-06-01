package CalendarController;

import java.io.InputStream;
import java.util.Scanner;

import CalendarModel.IModel;
import CalendarView.IView;

/**
 * This class represents the controller implementation for the controller.
 */
public class CalendarController implements IController {
  private Scanner in;
  private IView view;
  private IModel model;

  public CalendarController(IModel model, InputStream in, IView view) {
    this.model = model;
    this.view = view;
    this.in = new Scanner(in);

  }

  @Override
  public void go() {

  }
}
