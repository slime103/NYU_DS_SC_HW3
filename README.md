# NYU_DS_SC_HW3
A service counter simulator created for Homework Three in Professor Bari's Data Structures class at NYU, designed to accept text file arguments and output query results. 

This Program is runnable via the ServiceCounter.jar file through the Java Runtime Environment. It can be launched via the command line by navigating to the program folder location and typing "java -jar ServiceCounter.jar customersfile.txt queriesfile.txt". 

The customer and query files may be altered to your liking although its formatting must remain the same. The customerfile.txt follow the format of the service time in seconds in the first line. A space seperates each paragraph after. The paragraphs are two lines and consist of the following information - ID-NUMBER: (integer value)
ARRIVAL-TIME: (hh:mm:ss).

The queries file may contain the following commands - 
NUMBER-OF-CUSTOMERS-SERVED
LONGEST-BREAK-LENGTH
TOTAL-IDLE-TIME
MAXIMUM-NUMBER-OF-PEOPLE-IN-QUEUE-AT-ANY-TIME
WAITING-TIME-OF (customer id number)

The program could probably be more efficient however this was my first time dealing with time and it can be quite a headache.

Shout out to my TA Alan for being smart.
