package calendarview;

import java.awt.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import calendarmodel.Event;

/**
 * Represents an implementation for the GUI view of the program.
 */
public class NewCalendarView implements IView2 {
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
   * Constructs a NewCalendarView object and builds the starter calendar view.
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
    JButton scheduleView = new  JButton("Schedule View");
    monthLabel = new JLabel();
    calendarDropdown = new JComboBox<>(calendars.keySet().toArray(new String[0]));
    topPanel.add(scheduleView);
    topPanel.add(prevButton);
    topPanel.add(monthLabel);
    topPanel.add(nextButton);
    topPanel.add(calendarDropdown);
    topPanel.add(createCalendarButton);

    frame.add(topPanel, BorderLayout.NORTH);

    calendarPanel = new JPanel();
    frame.add(calendarPanel, BorderLayout.CENTER);

    scheduleView.addActionListener(e -> scheduleView());
    prevButton.addActionListener(e -> changeMonth(-1));
    nextButton.addActionListener(e -> changeMonth(1));
    calendarDropdown.addActionListener(e -> changeCalendar());
    
    createCalendarButton.addActionListener(e -> createCalendar());

    updateCalendar();
    frame.setVisible(true);
  }

  private void scheduleView() {
    String start = JOptionPane
            .showInputDialog(frame, "Enter start date:");

    if (start == null) {
      return;
    }

    if (validDay(start)) {
      LocalDate startDate = LocalDate.parse(start);

      StringBuilder eventList = new StringBuilder();
      eventList.append("<html>");

      for (Map.Entry<LocalDate, List<String>> entry : events.entrySet()) {
        if (entry.getKey().isAfter(startDate)
                || entry.getKey().isEqual(startDate)) {
          eventList.append(events.get(entry.getKey())).append("<br>");
        }
      }

      eventList.append("<html>");

      if (eventList.length() > 10) {
        eventList.delete(eventList.length() - 10, eventList.length());
      }

      JLabel eventLabel = new JLabel(eventList.toString());
      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      panel.add(eventLabel);

      JOptionPane.showMessageDialog(frame, panel, "Events" + ":\n",
              JOptionPane.INFORMATION_MESSAGE);
    } else {
      JOptionPane.showMessageDialog(frame,
              "Please enter time in HH:MM format (Military time)",
              "Invalid Time Format",
              JOptionPane.ERROR_MESSAGE);
    }



  }

  /**
   * Allows the user to create a new Calendar.
   */
  private void createCalendar() {
    String name = JOptionPane
            .showInputDialog(frame, "Enter the name of the new calendar:");
    calendars.put(name, Color.CYAN);
    calendarDropdown.setSelectedIndex(0);
    selectedCalendar = name;
    updateCalendar();
  }

  /**
   * Updates the calendar after a key press.
   */
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

  /**
   * Changes the month by the given offset.
   *
   * @param offset the given int to change the months by
   */
  private void changeMonth(int offset) {
    currentMonth = currentMonth.plusMonths(offset);
    updateCalendar();
  }

  /**
   * Change the calendar the user wants to use.
   */
  private void changeCalendar() {
    selectedCalendar = (String) calendarDropdown.getSelectedItem();
    updateCalendar();
  }

  /**
   * Show the events on a given day and lets the user create new events.
   *
   * @param date the given day
   */
  private void showEvents(LocalDate date) {
    List<String> dayEvents = events.getOrDefault(date, new ArrayList<>());

    StringBuilder eventText = new StringBuilder();
    eventText.append("<html>");

    if (dayEvents.size() <= 10) {
      if (dayEvents.isEmpty()) {
        eventText.append("No events scheduled for ").append(date);
      } else {
        eventText.append("Events on ").append(date).append(":<br>");
        for (String event : dayEvents) {
          eventText.append(event).append("<br>");
        }
      }
    } else {
      // move the view down to view the most recent events,

    }
    eventText.append("<html>");

    JLabel eventLabel = new JLabel(eventText.toString());

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(eventLabel);
    JButton createEvent = new JButton("Create Event");
    createEvent.addActionListener(e -> createEvent(date, dayEvents));
    panel.add(createEvent);

    JOptionPane.showMessageDialog(frame, panel, "Events on " + date + ":\n",
            JOptionPane.INFORMATION_MESSAGE);
  }

//  private void showDetails() {}
  /**
   * Creates the event upon a button press.
   *
   * @param date the given date to create the event on
   * @param dayEvents the list of events already on that day
   */
  private void createEvent(LocalDate date, List<String> dayEvents) {

    // 2. then the user inputs name, start time, end time, and optional fields such as
    // description, location, isPrivate, etc.

    JTextField nameField = new JTextField();
    JTextField startField = new JTextField();
    JTextField endField = new JTextField();
    JTextField location = new JTextField();
    JTextField description = new JTextField();
    JComboBox<String> privacy = new JComboBox<>(new String[]{"No", "Yes"});

    JPanel panel = new JPanel(new GridLayout(0, 1));
    panel.add(new JLabel("Event Name:"));
    panel.add(nameField);
    panel.add(new JLabel("Start Time:"));
    panel.add(startField);
    panel.add(new JLabel("End Time:"));
    panel.add(endField);
    panel.add(new JLabel("Location:"));
    panel.add(location);
    panel.add(new JLabel("Description:"));
    panel.add(description);
    panel.add(new JLabel("Private?"));
    panel.add(privacy);

    int result = JOptionPane.showConfirmDialog(frame, panel, "Create New Event",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    String nameEvent = "";
    String startTime = "";
    String endTime = "";

    if (result == JOptionPane.OK_OPTION) {
      nameEvent = nameField.getText();
      startTime = startField.getText();
      endTime = endField.getText();

      if (nameEvent.isEmpty()) {
        JOptionPane.showMessageDialog(frame,
                "Please enter a name for the event",
                "Invalid Name",
                JOptionPane.ERROR_MESSAGE);
      }

      if (!validFormat(startTime) || !validFormat(endTime)) {
        JOptionPane.showMessageDialog(frame,
                "Please enter time in HH:MM format (Military time)",
                "Invalid Time Format",
                JOptionPane.ERROR_MESSAGE);
      }
    }

    String value = nameEvent + " " + startTime + "-" + endTime; // "Smiski 12:00-14:00


    if (!value.trim().isEmpty() && !nameEvent.isEmpty()
            && validFormat(startTime) && validFormat(endTime)) {
      dayEvents.add(value.trim());
      events.put(date, dayEvents);
    }

    showEvents(date);

  }

  /**
   * Determines whether the time is in valid format.
   *
   * @param time the given time in String format
   * @return true if the time is in valid format, false otherwise
   */
  private boolean validFormat(String time) {
    if (time.length() != 5 || time.charAt(2) != ':') {
      return false;
    }

    try {
      int hour = Integer.parseInt(time.substring(0, 2));
      int minute = Integer.parseInt(time.substring(3, 5));
      return (hour >= 0 && hour <= 23) && (minute >= 0 && minute <= 59);

    } catch (NumberFormatException e) {
      return false;
    }

  }

  private boolean validDay(String date) {
    if (date.length() != 10 || (date.charAt(4) != '-' || date.charAt(7) != '-')) {
      return false;
    }

    try {
      LocalDate localDate = LocalDate.parse(date);
    } catch (DateTimeException e) {
      return false;
    }

    return true;
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
