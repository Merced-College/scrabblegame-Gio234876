import java.io.*;
import java.util.*;

public class ScrabbleGame {
    private final List<Word> words = new ArrayList<>();
    private final Random random = new Random();

    public void loadWords(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    words.add(new Word(line.trim()));
                }
            }
        }
        Collections.sort(words);
    }

    public List<Word> getWords() {
        return Collections.unmodifiableList(words);
    }

    public char[] getRandomLetters(int count) {
        char[] letters = new char[count];
        for (int i = 0; i < count; i++) {
            letters[i] = (char) ('A' + random.nextInt(26));
        }
        return letters;
    }

    public boolean isValidWord(String input, char[] allowedLetters) {
        input = input.toUpperCase();
        char[] allowed = Arrays.copyOf(allowedLetters, allowedLetters.length);
        for (char c : input.toCharArray()) {
            boolean found = false;
            for (int i = 0; i < allowed.length; i++) {
                if (allowed[i] == c) {
                    allowed[i] = 0; // Mark as used
                    found = true;
                    break;
                }
            }
            if (!found) return false;
        }
        return true;
    }

    public boolean isWordInList(String input) {
        return words.contains(new Word(input));
    }

    public boolean isWordInListBinarySearch(String input) {
        return Collections.binarySearch(words, new Word(input)) >= 0;
    }

    public static int calculateScore(String word) {
        return word.length(); // 1 point per letter, can be adjusted
    }

    public static char[] exchangeLetter(char[] letters, Scanner scanner) {
        System.out.println("Your letters: " + Arrays.toString(letters));
        System.out.print("Enter the letter you want to exchange: ");
        String input = scanner.nextLine().trim().toUpperCase();
        if (input.length() != 1) {
            System.out.println("Invalid input. No letter exchanged.");
            return letters;
        }
        char toExchange = input.charAt(0);
        for (int i = 0; i < letters.length; i++) {
            if (letters[i] == toExchange) {
                char newLetter;
                Random rand = new Random();
                do {
                    newLetter = (char) ('A' + rand.nextInt(26));
                } while (newLetter == toExchange);
                letters[i] = newLetter;
                System.out.println("Exchanged " + toExchange + " for " + newLetter);
                return letters;
            }
        }
        System.out.println("Letter not found. No letter exchanged.");
        return letters;
    }

    public static void main(String[] args) {
        ScrabbleGame game = new ScrabbleGame();
        try {
            game.loadWords("CollinsScarbbleWords_2019.txt");
            Scanner scanner = new Scanner(System.in);

            char[] letters = game.getRandomLetters(4);
            System.out.println("Starting letters: " + Arrays.toString(letters));

            String[] playerNames = {"Player 1", "Player 2"};
            String[] playerWords = new String[2];
            int[] playerScores = new int[2];

            for (int p = 0; p < 2; p++) {
                System.out.println("\n" + playerNames[p] + "'s turn.");
                System.out.print("Would you like to exchange a letter? (y/n): ");
                String exchange = scanner.nextLine().trim().toLowerCase();
                char[] playerLetters = Arrays.copyOf(letters, letters.length);
                if (exchange.equals("y")) {
                    playerLetters = exchangeLetter(playerLetters, scanner);
                }
                while (true) {
                    System.out.println("Your letters: " + Arrays.toString(playerLetters));
                    System.out.print("Enter your word using these letters: ");
                    String userWord = scanner.nextLine().trim().toUpperCase();

                    if (!game.isValidWord(userWord, playerLetters)) {
                        System.out.println("Invalid: Your word must use only the given letters.");
                    } else if (!game.isWordInListBinarySearch(userWord)) {
                        System.out.println("Invalid: That word is not in the Scrabble dictionary.");
                    } else {
                        playerWords[p] = userWord;
                        playerScores[p] = calculateScore(userWord);
                        System.out.println("Great! '" + userWord + "' is valid and scores " + playerScores[p] + " points.");
                        break;
                    }
                }
            }

            System.out.println("\nResults:");
            for (int p = 0; p < 2; p++) {
                System.out.println(playerNames[p] + ": " + playerWords[p] + " (" + playerScores[p] + " points)");
            }
            if (playerScores[0] > playerScores[1]) {
                System.out.println(playerNames[0] + " wins!");
            } else if (playerScores[1] > playerScores[0]) {
                System.out.println(playerNames[1] + " wins!");
            } else {
                System.out.println("It's a tie!");
            }

        } catch (IOException e) {
            System.err.println("Error loading words: " + e.getMessage());
        }
    }
}