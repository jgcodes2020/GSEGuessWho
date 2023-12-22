package ca.gse.guesswho.models.players;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import ca.gse.guesswho.models.DataCaches;
import ca.gse.guesswho.models.GameState;
import ca.gse.guesswho.models.GuessWhoCharacter;
import ca.gse.guesswho.models.Player;
import ca.gse.guesswho.models.Question;
import ca.gse.guesswho.models.questions.AttributeQuestion;
import ca.gse.guesswho.models.questions.CharacterQuestion;

public class DumbAIPlayer extends Player {
	private Random rng;
	private HashSet<Question> previousQuestions;
  private Scanner scanner = new Scanner(System.in);

	/**
	 * Creates a new dumb AI player.
	 */
	public DumbAIPlayer() {
		rng = new Random();
		previousQuestions = new HashSet<>();
	}

  public static void main(String[] args) {
    DumbAIPlayer player = new DumbAIPlayer();
    Scanner scanner = new Scanner(System.in);

    while (true) {
        System.out.println("It's your turn, " + player.getName());
        Question question = player.takeTurn(scanner);
        System.out.println("Your question: " + question);
        // ... handle the answer and game logic ...
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
    List<GuessWhoCharacter> characters = DataCaches.getCharacterList();

    // if we're down to one option, guess that one
    if (remainingIndexes.cardinality() == 1) {
        int finalIndex = remainingIndexes.nextSetBit(0);
        return new CharacterQuestion(characters.get(finalIndex));
    }
    
    Question nextQuestion;
    do {
        System.out.println("Enter attribute number:");
        int randomAttribute = scanner.nextInt();
        System.out.println("Enter attribute value:");
        byte randomValue = scanner.nextByte();
        nextQuestion = new AttributeQuestion(randomAttribute, randomValue);
        // if it has been asked before, ask a different one (hopefully this doesn't hang or something)
    } while (previousQuestions.contains(nextQuestion));
    // add this question to the list of previously-asked questions
    previousQuestions.add(nextQuestion);
    return nextQuestion;
	}

	/**
	 * Always returns false, since this is an AI playe
	 * @return false
	 */
	@Override
	public boolean isHuman() {
		return false;
	}

	@Override
  public String getName() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter your name:");
    String name = scanner.nextLine();
    scanner.close();
    return name;
  }

}
