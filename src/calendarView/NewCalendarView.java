package calendarview;

import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

/**
 * Represents an implementation for the GUI view of the program.
 */
public class  NewCalendarView implements IView2 {
  private JFrame frame;
  private JPanel calendarPanel;
  private JLabel monthLabel;
  private JComboBox<String> calendarDropdown;
  private Map<String, Color> calendars;
  private Map<LocalDate, List<String>> events;
  private YearMonth currentMonth;
  private String selectedCalendar;
  private IView origView;

  /**
   *
   * @param origView the original IView interface
   */
  public NewCalendarView(IView origView) {
    this.origView = origView;
    frame = new JFrame("Calendar App");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 500);
    frame.setLayout(new BorderLayout());

    currentMonth = YearMonth.now();
    calendars = new HashMap<>();
    events = new HashMap<>();
    calendars.put("Default", Color.BLUE);
//    calendars.put("Personal", Color.GREEN);
//    calendars.put("Holidays", Color.RED);
    selectedCalendar = "Default";

    JPanel topPanel = new JPanel();
    JButton prevButton = new JButton("<");
    JButton nextButton = new JButton(">");
    JButton createCalendarButton = new JButton("Create Calendar");
    monthLabel = new JLabel();
    calendarDropdown = new JComboBox<>(calendars.keySet().toArray(new String[0]));
    topPanel.add(prevButton);
    topPanel.add(monthLabel);
    topPanel.add(nextButton);
    topPanel.add(calendarDropdown);
    topPanel.add(createCalendarButton);

    frame.add(topPanel, BorderLayout.NORTH);

    calendarPanel = new JPanel();
    frame.add(calendarPanel, BorderLayout.CENTER);

    prevButton.addActionListener(e -> changeMonth(-1));
    nextButton.addActionListener(e -> changeMonth(1));
    calendarDropdown.addActionListener(e -> changeCalendar());

    createCalendarButton.addActionListener(e -> createCalendar());

    updateCalendar();
    frame.setVisible(true);
  }

  private void createCalendar() {
    String name = JOptionPane
            .showInputDialog(frame, "Enter the name of the new calendar:");
    calendars.put(name, Color.CYAN);
    calendarDropdown.setSelectedIndex(0);
    selectedCalendar = name;
    updateCalendar();
  }

  private void updateCalendar() {
    calendarPanel.removeAll();
    calendarPanel.setLayout(new GridLayout(0, 7));
    monthLabel.setText(currentMonth.getMonth() + " " + currentMonth.getYear());
    calendarPanel.setBackground(calendars.get(selectedCalendar));

    for (int day = 1; day <= currentMonth.lengthOfMonth(); day++) {
      LocalDate date = currentMonth.atDay(day);
      JButton dayButton = new JButton(String.valueOf(day));
      dayButton.addActionListener(e -> showEvents(date));
      calendarPanel.add(dayButton);
    }

    frame.revalidate();
    frame.repaint();
  }

  private void changeMonth(int offset) {
    currentMonth = currentMonth.plusMonths(offset);
    updateCalendar();
  }

  private void changeCalendar() {
    selectedCalendar = (String) calendarDropdown.getSelectedItem();
    updateCalendar();
  }

  private void showEvents(LocalDate date) {
    List<String> dayEvents = events.getOrDefault(date, new ArrayList<>());

    String eventList = dayEvents.isEmpty() ? "No events" : String.join("\n", dayEvents);

    String newEvent = JOptionPane.showInputDialog(frame, "Events on " + date + ":\n"
            + eventList + "\n\nAdd new event:");
    if (newEvent != null && !newEvent.trim().isEmpty()) {
      dayEvents.add(newEvent);
      events.put(date, dayEvents);
    }
  }

  private void createEvent() {
  }

  @Override
  public void printOptions() {
    this.origView.printOptions();
  }

  @Override
  public void showStatus(String status) {
    this.origView.showStatus(status);

  }

  @Override
  public void printEvents(List<String> events) {
    this.origView.printEvents(events);

  }

  @Override
  public void showOptionError() {
    this.origView.showOptionError();

  }
}
