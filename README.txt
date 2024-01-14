GSE Guess Who
======================
To run this code in Eclipse, create a new Java project, then copy the contents
of the `src` folder (NOT the `src` folder itself!) into the `src` folder in
Eclipse. Open `GuessWho.java` to run the program.

PACKAGE LISTING
================
ca.gse.guesswho - root package
- .assets - folder for assets
  - .characters - image assets for all 24 characters
  - .music - MIDI files for music
  - .sfx - Sound effects in .wav format
- .components - custom components used in the view.
- .events - event objects. Originally we had an event system for switching panels, 
  so it's a bit of "legacy code" left behind from then.
- .models - view-independent models of Guess Who. "Business logic".
  - .history - classes for recording game history.
  - .players - subclasses of `ca.gse.guesswho.models.Player` for both human and
    computer players.
  - .questions - subclasses of `ca.gse.guesswho.models.Question`.
- .sound - classes for managing sound effects and music.
- .views - classes representing discrete units of the GUI.


LEGAL NOTICE
================
jeopardy.mid is a transcription of the main theme from "Jeopardy!"(tm) by Alex Trebek.
The transcription was made by ear; transcribed into MuseScore and exported as MIDI.

COMMENT EXPLANATIONS
************************
Below are explanations of various non-trivial lines of code.

NOTE 1
========
Let X represent whether P1 is answering (equivalently, P2 is asking) (1 if P1 is answering, 0 if not)
Let Y represent the last answer (1 for yes, 0 for no).
Let Q represent whether the winner is P1 (1 for P1 win, 0 for P2 win).

We draw a truth table:
X | Y | Q
--+---+--
0 | 0 | 0
0 | 1 | 1
1 | 0 | 1
1 | 1 | 0

The truth table shows that Q = X XOR Y. This is why we use ^ (the XOR operator).

NOTE 2
========
This is a regular expression that parses a timestamp. It's rather terse, but I'll do my 
best to explain it here.
^(?:(\d|[1-9]\d+)d\+)?(\d{2}):(\d{2}):(\d{2})(?:.(\d{3}))?$

PRIMER
-------
` ^ ` matches the beginning of the string.
` $ ` matches the end of the string.
The above two are used to ensure the whole string is matched.

` (stuff) ` encloses a group and makes its contents available for later use.
` (?:stuff) ` encloses a group and does not make its contents available for later use.

Most characters match themselves (letters, numbers, etc.) Backslash can be used to escape special 
characters where needed. As they are in Java strings, the backslashes themselves must also be 
escaped.

` \d ` matches a single digit character (0-9).
` . ` matches any character except for newline.

Quantifiers modify the letter or group they're written after.
` ? ` matches its segment either no times or once. Think of it as "optional".
` + ` matches its segment at least once. Think of it as "repeated".
` {n} ` matches its segment exactly n times.

The parts
----------
Days are matched by this: (?:(\d|[1-9]\d+)d\+)?
This matches optionally:
  - some valid integer without leading zeros (captured), then the string "d+"
Note that + must be escaped because it has another meaning that we don't want.

The hours, minutes and seconds are matched by this: (\d{2}):(\d{2}):(\d{2})
This matches a pair of digits (captured), then a colon, then another pair of digits (captured),
then another colon, then finally one last pair of digits (captured).

The milliseconds are matched by this: (?:\\.(\d{3}))?
This optionally matches:
  - a period (escaped), 
  - followed by 3 digits (captured).
