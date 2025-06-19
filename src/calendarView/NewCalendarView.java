package calendarview;

import java.awt.*;
import java.io.PrintStream;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import javax.swing.*;

import calendarcontroller.CalendarController;
import calendarcontroller.IController;

/**
 * Represents an implementation for the GUI view of the program.
 */
public class NewCalendarView extends CalendarView implements IView2 {
  private final JFrame frame;
  private final JPanel calendarPanel;
  private final JLabel monthLabel;
  private final JComboBox<String> calendarDropdown;
  private final Map<String, Color> calendars;
  private final Map<LocalDate, List<String>> events;
  private YearMonth currentMonth;
  private String selectedCalendar;
  private PrintStream out;

  /**
   * Constructs a NewCalendarView object and builds the starter calendar view.
   */
  public NewCalendarView(PrintStream out) {
    super(out);
    frame = new JFrame("Calendar App");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600, 600);
    frame.setLayout(new BorderLayout());

    currentMonth = YearMonth.now();
    calendars = new HashMap<>();
    events = new HashMap<>();
    calendars.put("Default", Color.BLUE);
    selectedCalendar = "Default";

    JPanel topPanel = new JPanel();
    JButton prevButton = new JButton("<");
    JButton nextButton = new JButton(">");
    JButton createCalendarButton = new JButton("Create Calendar");
    JButton scheduleView = new JButton("Schedule View");
//    JButton editEvent = new JButton("Edit Events");
//    topPanel.add(editEvent);
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
//    editEvent.addActionListener(e -> editEvents());
    createCalendarButton.addActionListener(e -> createCalendar());
    prevButton.addActionListener(e -> changeMonth(-1));
    nextButton.addActionListener(e -> changeMonth(1));
    calendarDropdown.addActionListener(e -> changeCalendar());

    updateCalendar();
    frame.setVisible(true);
  }

  /**
   * Shows the schedule view of a calendar with a specified start date.
   */
  private void scheduleView() {
    int count = 0;
    String start = JOptionPane
            .showInputDialog(frame, "Enter start date:" + "\n"
                    + "(Ex. 2025-06-01)");

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
          List<String> dayEvents = entry.getValue();

          // header of the event date
          boolean isHeader = false;

          if (!dayEvents.isEmpty()) {

            eventList.append(entry.getKey()).append(":<br>");
            for (String event : dayEvents) {
              if (count == 10) break;

              if (!isHeader) {
                isHeader = true;
              }

              eventList.append(entry.getKey()).append(" ").append(event).append("<br>");
              count += 1;
            }

            if (count == 10) {
              break;
            }
          }
        }
      }

      eventList.append("<html>");

      JLabel eventLabel = new JLabel(eventList.toString());
      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      panel.add(eventLabel);

      JOptionPane.showMessageDialog(frame, panel, "Events" + ":\n",
              JOptionPane.INFORMATION_MESSAGE);
    } else {
      JOptionPane.showMessageDialog(frame,
              "Please enter date in YYYY-MM-DD format",
              "Invalid Date Format",
              JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Allows the user to create a new Calendar.
   */
  private void createCalendar() {
    String name = JOptionPane
            .showInputDialog(frame, "Enter the name of the new calendar:");

    if (name == null) {
      return;
    } else if (name.isEmpty()) {
      JOptionPane.showMessageDialog(frame, "Please enter a valid calendar name",
              "Invalid Name",
              JOptionPane.ERROR_MESSAGE);
      return;
    }

    if (calendars.containsKey(name)) {
      JOptionPane.showMessageDialog(frame,
              "Calendar with the same name already exists", "Duplicate Name",
              JOptionPane.ERROR_MESSAGE);
      return;
    }

    Random random = new Random();
    Color randomColor = new Color(random.nextInt(256),
            random.nextInt(256), random.nextInt(256));

    calendars.put(name, randomColor);
    calendarDropdown.removeAllItems();

    for (String cal : calendars.keySet()) {
      calendarDropdown.addItem(cal);
    }

    int switchCalendar = JOptionPane.showConfirmDialog(frame,
            "Do you want to switch to this calendar?",
            "Switch Calendar?",
            JOptionPane.YES_NO_OPTION);

    if (switchCalendar == JOptionPane.YES_OPTION) {
      calendarDropdown.setSelectedItem(name);
      selectedCalendar = name;
      events.clear();
      updateCalendar();
    }
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



  /**
   * Creates an event upon a button press, with optional fields a user can fill out also.
   *
   * @param date      the given date to create the event on
   * @param dayEvents the list of events already on that day
   */
  private void createEvent(LocalDate date, List<String> dayEvents) {


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

    StringBuilder required = new StringBuilder();

    required.append("<html>");

    required.append(nameEvent).append(" ")
            .append(startTime).append(" to ")
            .append(endTime).append("<br>");
    // "Smiski 12:00-14:00"

    required.append("<html>");

    StringBuilder optionals = new StringBuilder();

    optionals.append("<html>");

    optionals.append("Description: ").append(description.getText()).append("<br>");
    optionals.append("Location: ").append(location.getText()).append("<br>");
    optionals.append("Privacy: ")
            .append(Objects.requireNonNull(privacy.getSelectedItem())).append("<br>");

    optionals.append("<html>");

    if (!nameEvent.isEmpty() && validFormat(startTime) && validFormat(endTime)) {
      dayEvents.add(required + " " + optionals);
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

  /**
   * Determines whether a date is in correct format.
   *
   * @param date the given date in String format
   * @return true if the time is in valid format, false otherwise
   */
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
}
