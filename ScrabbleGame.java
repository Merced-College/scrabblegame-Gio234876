import java.io.*;
import java.util.*;

public class ScrabbleGame {
    //the arraylist data structure to hold the words
    //and the random number generator to generate random letters
    //the words are sorted in alphabetical order for efficient searching
    private final List<Word> words = new ArrayList<>();
    private final Random random = new Random();

    //A way to load the accepted words from the 2019.txt file 
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

    //get the words from within the file
    public List<Word> getWords() {
        return Collections.unmodifiableList(words);
    }

    //randomly generate a specified number of letters from A-Z and then provide it to the user
    public char[] getRandomLetters(int count) {
        char[] letters = new char[count];
        for (int i = 0; i < count; i++) {
            letters[i] = (char) ('A' + random.nextInt(26));
        }
        return letters;
    }

    //a way to check if the input is within the 2019.txt file to see if it's a valid word from the user
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

    //checking to see if the input is within the 2019.txt file
    public boolean isWordInList(String input) {
        return words.contains(new Word(input));
    }   

    //using binary search to check if the input is within the 2019.txt file
    public boolean isWordInListBinarySearch(String input) {
        return Collections.binarySearch(words, new Word(input)) >= 0;
    }   

    //scoreboard to see if the input is a valid word and decide how much points should be award to the player 3(1point)
    // or 4(2points) letter words and no points if there is no valid word//
    public static int calculateScore(String word) { 
        if (word.length() == 3) {
            return 1;
        } else if (word.length() == 4) {              
            return 2;
        } else {
            return 0; // No points for other lengths, adjust as needed
        }
    } 

    //a way to exchange a letter from the user if the can't spell a word with their initial letters that were randomly generated
    public static char[] exchangeLetter(char[] letters, Scanner scanner) {
        System.out.println("Your letters: " + Arrays.toString(letters));
        System.out.print("Enter the letter you want to exchange: ");
        String input = scanner.nextLine().trim().toUpperCase();
        if (input.length() != 1) {
            System.out.println("Invalid input. No letter exchanged.");
            return letters;
        }
        // Check if the input is a single character and making sure the letter is within A-Z and they don't try to put 
        //something that might not work or could be the game//
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
    
    //the main method which will allow the program to execute and let the game run if it's working properly
    public static void main(String[] args) {
        ScrabbleGame game = new ScrabbleGame();
        try {
            game.loadWords("CollinsScarbbleWords_2019.txt");
            Scanner scanner = new Scanner(System.in);

            // Display the number of words loaded
            char[] letters = game.getRandomLetters(4);
            System.out.println("Starting letters: " + Arrays.toString(letters));


            // Allow two players to take turns
            String[] playerNames = {"Player 1", "Player 2"};
            String[] playerWords = new String[2];
            int[] playerScores = new int[2];

            //prompting the plater when it's their turn and when they could enter a word to the game & also added a skip 
            //options to the game as sometimes a word might not be possible with the four choices provided.//
            for (int p = 0; p < 2; p++) {
                System.out.println("\n" + playerNames[p] + "'s turn.");
                System.out.print("Would you like to exchange a letter? (y/n): ");
                String exchange = scanner.nextLine().trim().toLowerCase();
                char[] playerLetters = Arrays.copyOf(letters, letters.length);
                if (exchange.equals("y")) {
                    playerLetters = exchangeLetter(playerLetters, scanner);
                }
                // Prompt the player to enter a word using the letters
                while (true) {
                    System.out.println("Your letters: " + Arrays.toString(playerLetters));
                    System.out.print("Enter your word using these letters (or '-' to skip): ");
                    String userWord = scanner.nextLine().trim().toUpperCase();

                    // Allow the player to skip their turn
                    if (userWord.equals("-") || userWord.isEmpty()) {
                        System.out.println("Turn skipped.");
                        playerWords[p] = "(skipped)";
                        playerScores[p] = 0;
                        break;
                    }

                    // Check if the word is valid and in the dictionary
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


            //also added a point system to see which player within by the given points for the letter length of the word
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