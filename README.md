Equipment-System
================

##### Equipment checkout tracking, simplified.

Equipment-System is a simple interface that allows users to check equipment in and out for various uses,
and gives Executives an at-a-glance view of the status of the team's equipment. Gone are the days of
having no idea who checked out the cameras - just look at the logs.

## Usage Documentation

### Equipment Management

##### Check-Out
To check out an item, simply select checkout, scan or enter in the barcode reference of the item followed by the ID number of
the member checking the item out. Both these values are simple integers.

Some items require an Executive to authorize checkout.

##### Check-In
Checkin is easy. Just scan the barcode reference of the item they are checking in.
As soon as it is scanned, the item is made available for use by other members.

##### Search
The search function allows for the table to show only what is in the search query. It searches in (mostly) real time. Hitting enter isn't required, but recommended.

### System Management

#### Item Manager
The Item Manager is the way all items that are added in, or removed from the system are managed. It gives a full table view just as the main window does, and allows for items to be added in realtime.

##### Adding an Item
To add an item, first Enter the Reference Number (1001-9999) then followed by the Name of the item.
Then thereafter the selectionbox reads "false". This is in reference to the need for executive permission. If permission is to be required, simply change that to read true.

##### Removing an Item
To remove an item, simply select it within the table and then press the "delete" button in the bottom right hand corner.
This task is immediate and there is no undo, so make sure you mean to press it. (Although as you see, re-adding an item is a very simple task).

#### Log Viewer
The log allows for the executive staff to see a log of all the items checked out, in, when and by who.
Essentually it allows for a searchable, efficient, realtime log of all equipment.
Gone are the days of "who has this?"
