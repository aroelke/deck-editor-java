# deck-editor-java
This is a Magic: the Gathering deck editor that supports custom categories for cards in decks.

Magic: the Gathering is owned by Wizards of the Coast, to which I have no affiliation.

This program depends on the following:
 - Java 14 JDK, which can be installed from https://www.oracle.com/java/technologies/javase-downloads.html
 - Google JSON library, GSON, which can be found at https://code.google.com/p/google-gson/
 - Natty, which can be found at http://natty.joestelmach.com/

This depends on MTGJSON (http://www.mtgjson.com/) for its card list.

To build on the command line, run `mvn compile`.  Then run it by running `mvn exec:java -pl application`.