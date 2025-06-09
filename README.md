a. Instructions on how to run your program.

To run our program, input a mode either --headless, followed by a file name, or --interactive.
If interactive mode is selected, please enter one of the CalenderValidCommands.

b. Which features work and which do not.

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

c. A rough distribution of which team member contributed to which parts of the assignment.

We worked mostly together but we each focused on different parts of the assignment while helping
each other along the way.
Jennifer - focused on the builder, controller, view, and worked on print and status, 
wrote the calendar program 
Vanessa - focused on edit event/events/series, added refinements to the create methods, and did
a lot of the tests

d. Anything else you need us to know when we grade.

We tried really hard on this assignment. :(