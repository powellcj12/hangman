hangman
=======

When it plays Hangman, the computer first selects a secret word at random from a list built into the program. The program then prints out a row of dashes, one for each letter in the secret word, and asks the user to guess a letter. If the user guesses a letter that is in the word, the word is redisplayed with all instances of that letter shown in the correct positions, along with any letters correctly guessed on previous turns. If the letter does not appear in the word, the user is charged with an incorrect guess. The user keeps guessing letters until either (1) the user has correctly guessed all the letters in the word or (2) the user has made eight incorrect guesses.

When it is played by children, the real fascination (a somewhat morbid fascination, I suppose) from Hangman comes from the fact that incorrect guesses are recorded by drawing an evolving picture of the user being hanged at a scaffold. For each incorrect guess, a new part of a stick-figure body—first the head, then the body, then each arm, each leg, and finally each foot—is added to the scaffold until the hanging is complete.

Getting Started
---------------
First, fork this repository so you have your own repo to make changes to. Clone it locally to your computer, and start working in a separate branch for each bug fix or feature. Remember to make small, frequent commits. When you feel you've completed all the work, submit a pull request to have the changes reviewed.

Bugs
----

There are a number of bugs in the project that you need to fix:

done     1. If the user inputs a lower-case letter, the program counts it as an incorrect guess even if the word contains the letter.
done     2. Occasionally, the test for the failure condition (```testFailure```) fails when run. It doesn't always fail.
done     3. When the user makes an incorrect guess, the entire hangman diagram is drawn on screen. The game continues to run as expected, but the graphical display is inconsistent with the game state.
done     4. Two of the tests in ```TestHangmanCanvas``` are failing, specifically ```testNoteIncorrectGuess``` and ```testDisplayWord```.
5. There's an inconsistent, rare crash when starting the game. The stack trace is included in bug #5 on GitHub.

New Features
------------

There are a number of new features to be implemented:

1. Ensure that if a user enters a letter that was already guessed, they are notified of this and it doesn't count as another inncorrect guess.
2. Expand the program to allow for multi-word phrases (like "Bishop Blanchet Braves") instead of single words to be guessed
3. Instead of having the body parts and letters merely appear on the screen, you could have them move in from offscreen for a type of animation effect.

Testing
-------

For any bugs fixed or features added, ensure that automated tests are added/modified/removed as appropriate. Before submitting changes, all tests should pass.