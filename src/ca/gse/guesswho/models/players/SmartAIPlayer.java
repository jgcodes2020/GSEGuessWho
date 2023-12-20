package ca.gse.guesswho.models.players;

import java.util.BitSet;
import java.util.List;
import java.util.Random;

import ca.gse.guesswho.models.*;
import ca.gse.guesswho.models.questions.*;

public class SmartAIPlayer extends Player {
    private int turnCount;
    private int[][] frequencyList;

    /**
     * Creates a new smart AI player.
     */
    public SmartAIPlayer() {
        turnCount = 0;

        List<GuessWhoCharacter> characterList = GameState.getCharacterList();

        frequencyList = new int[GuessWhoCharacter.ATTRIBUTE_NUM_VALS][];
        for (int i = 0; i < frequencyList.length; i++) {
            frequencyList[i] = new int[GuessWhoCharacter.attributeMaxValue(i)];
        }
    }

    /**
     * {@inheritDoc}
     * Dumb AI players are incredibly basic.
     * <ul>
     * <li>if there is only one option for them to pick, they make their guess.</li>
     * <li>otherwise, ask a random question that may or may not narrow down the set
     * of possible characters.</li>
     * </ul>
     */
    @Override
    public Question takeTurn() {
        List<GuessWhoCharacter> characters = GameState.getCharacterList();

        // if we're down to one option, use that one
        if (remainingIndexes.cardinality() == 1) {
            int finalIndex = remainingIndexes.nextSetBit(0);
            return new CharacterQuestion(characters.get(finalIndex));
        }

        // reset the frequency list values to 0
        for (int i = 0; i < frequencyList.length; i++) {
            for (int j = 0; j < frequencyList.length; j++) {
                frequencyList[i][j] = 0;
            }
        }

        // iterate over set bits (i.e. remaining characters)
        for (int c = remainingIndexes.nextSetBit(0); c != Integer.MAX_VALUE; c = remainingIndexes.nextSetBit(c + 1)) {
            GuessWhoCharacter character = characters.get(c);
            for (int a = 0; a < frequencyList.length; a++) {
                // increase frequency of (attribute, value) pair
                frequencyList[a][character.getAttribute(a)]++;
            }
        }
        
        // best question will split the set of remaining elements in two
        final int EVEN_SPLIT = remainingIndexes.cardinality() / 2;
        // track the best attribute and value
        int bestAttribute = 0;
        byte bestValue = 0;
        // Loop over the frequency list, search for the best attribute and value to play
        outerLoop:
        for (int a = 0; a < frequencyList.length; a++) {
            // this is a jagged 2D array, so we can't simply assume that
            // each row is the same length
            for (byte v = 0; v < frequencyList[a].length; v++) {
                // this value will be better if it's closer 
                if (Math.abs(frequencyList[a][v] - EVEN_SPLIT) < 
                    Math.abs(frequencyList[bestAttribute][bestValue] - EVEN_SPLIT)) {
                    bestAttribute = a;
                    bestValue = v;
                    
                    // shortcut: if we have exactly the even split, we can't go lower
                    if (frequencyList[a][v] == EVEN_SPLIT)
                        break outerLoop;
                }
            }
        }
        
        return new AttributeQuestion(bestAttribute, bestValue);
    }

    /**
     * Always returns false, since this is an AI playe
     * 
     * @return false
     */
    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public String getName() {
        // TODO: should we add actual names or leave this one?
        return "NoobGamer69420";
    }

}
