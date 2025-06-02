package CalendarView;

/**
 * This represents an interface for the view.
 */
public interface IView {
  void showString(String s);
  void showOptions();
  void enterSubject();
  void enterStartDate();
  void enterStartTime();
  void enterEndDate();
  void enterEndTime();
  void enterDescription();
  void enterLocation();
  void enterEventStatus();
  void showOptionError();
}
