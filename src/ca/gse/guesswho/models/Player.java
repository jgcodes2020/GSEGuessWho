/*
Player.java
Authors: Jacky Guo, Chapman Yu
Date: Jan. 11, 2024
Java version: 8
*/
package ca.gse.guesswho.models;

import java.util.BitSet;
import java.util.List;

/**
 * Base class for all players (actors within the game).
 * Players are capable of asking and answering questions.
 */
public abstract class Player {
    protected BitSet remainingIndexes;

    /**
     * Gets the player's next question.
     * 
     * @return the next question
     */
    public abstract Question askQuestion();

    /**
     * Answers the last question asked; the last question is
     * typically made available to the game state
     * 
     * @param question the last question asked.
     * @return the answer to the question.
     */
    public abstract boolean answerQuestion(Question question);

    /**
     * Checks if this player is human. Human players generally
     * ask and/or answer through user input.
     * 
     * @return true if the player is human.
     */
    public abstract boolean isHuman();

    /**
     * Gets this player's name. This is not related to the character
     * they choose; and merely acts as something they know.
     * 
     * @return the player's name.
     */
    public abstract String getName();

    protected Player() {
        List<GuessWhoCharacter> characters = DataCaches.getCharacterList();

        if (characters == null)
            throw new IllegalArgumentException("Player creation requires a loaded character list!");

        // create and fill the "remaining indexes" array with true
        this.remainingIndexes = new BitSet(characters.size());
        this.remainingIndexes.set(0, characters.size());
    }

    /**
     * Returns a bitset containing the remaining indexes for this player
     * 
     * @return the remaining indexes for this player
     * @see DataCaches#getCharacterList()
     */
    public BitSet getRemainingIndexes() {
        return this.remainingIndexes;
    }



}
