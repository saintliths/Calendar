a.  A list of changes to the design of your program, along with a brief justification of each. 
Describing changes only in paragraph form will result in a point deduction

1. Created a MultipleCalendar class that implements the IModel2 interface to reduce coupling. 
2. Used the composition pattern to add the MultipleCalendar class without making large 
modifications to the existing code or changing any subclasses already being used.
3. In the CalendarModel we were instructed to break the editEvent, editEventSeries, and editSeries 
into helpers for easier testing and debugging without changing the logic of old code, so we did. 
4. We did not put Event or EventSeries into an interface or abstract class because we felt like
since they represented separate things, they should stay separate classes. By putting them in an
interface, we would have to change all of our old examples, therefore, touching old code.
5. Changed the input source in the controller to a Readable to make things easier.

b.  Instructions on how to run your program (using the jar file from the terminal).

To run our program, right-click the jar file and input "java -jar Calendar.jar". Then, do the same
thing and input your mode, either "--mode interactive" to run the program interactively based
on user input, or "--mode headless FILE-NAME" for the program to read a file and run it. 
If interactive mode is selected, please enter one of the valid commands. It will show the list of
valid commands in the console.

c. Which features work and which do not.

Calendar Features: 
Based on the input, a calendar can be created using a name and timeZone. The unique
calendars are stored in hash map of calendars, the key is the name with the corresponding 
events inside the calendar as the value. Before creating calendar, we also check if the calendar 
already exists. The name and time zone can be edited by calling the editCalendar method. Events 
can also be selected through the useCalendar feature. Event(s) can be copied from one calendar to 
another. Using the convertTimeZone's method ensures that the correct time is converted when events 
are copied. A singular event can be copied from one calendar to another using copySpecificEvent, 
copyMultipleEvents allows the person to copy multiple events from one day to another calendar. 
Finally, the copyEventsBetween allows the user to select a time frame within one calendar and 
copy it to another. 


Events Features: 
Based on the input, events and event series can be created, edited, and queried.
Depending on the command that is input, the createEvent or createSeries will create a event or
event series, when the command contains key words. Similarly, depending on the key words in the
command that is input the model will call editEvent, editEvents, or editSeries.
EditEvent supports changes to a singular property of a singular event such as the subject,
startDate, startTime, endDate, endTime, description, location, and status. The editEvents supports
changes to a singular property of multiple events after but including the start date if the
selected event is part of a series. The editSeries supports changes to a singular property for an
entire series and all events in that series with the same start date and subject. If it does not
contain a property or a new value no event will be changed. If no event or event series is found 
then it will throw an exception.

d. A rough distribution of which team member contributed to which parts of the assignment.

We worked mostly together but we each focused on different parts of the assignment while helping
each other along the way.

Jennifer - focused on the builder, controller, view, and worked on print and status, 
wrote the calendar program. Worked on the composition pattern, and copy event(s), tested
the create/use/edit calendar. Created the helpers for edit events/series for calendar model.

Vanessa - focused on edit event/events/series, added refinements to the create methods, and did
a lot of the tests. Worked on copy event/events, wrote more tests, wrote the convertedTime helper.
Also put a lot of work into the README.

We met up in person a lot and discussed how to extend/design our code. Most of the methods were
done by two people, with one person doing half and the other person finishing.

e. Anything else you need us to know when we grade.

We tried hard on this assignment too. :(