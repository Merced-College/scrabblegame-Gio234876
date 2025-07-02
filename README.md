[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=19881124)
# scrabbleGame

Scrabble Game 

For this assignment use your OOD and ArrayList skills and binary search algorithm to make a simple Scrabble-like game.  This game will give the user 4 random letters.  

* Create a Word object class and a ScrabbleGame class to read in the Word objects into a sorted ArrayList of Words. Read in the words and make the Word objects using the text file called "CollinsScarbbleWords_2019.txt".
* Have your game then choose 4 random characters, and output these to the user.
* Ask the user for a word made form those 4 letters.
* Use binary search to search for that word in the Words ArrayList to see if the word is valid. Output it if it is a valid word.
* Then make at least one improvement to the game.  Write your improvement as comments at the top of your game, and also comment the code that you inserted into the game to make this improvement.  It should be at least 20 lines of quality code. Some examples of an improvement would be:

* Give the user different amounts of points for different size words.
* Allow the user to exchange one of their letters.
* Have 2 players competing for the biggest word.
* Any other improvement.
* Use the given list of words to make your Word objects for the Arraylist and verify that the user typed in a real word.

** Add comments throughout your code, especially where you made changes or additions. If you're working in a group, ensure that your comments clearly indicate the sections you personally contributed to.

Turn in a copy of your code and the reflection response.

TODO Turn in: Turn in your 2 files (The Word and ScrabbleGame java files) to the github classroom assignment.

TODO: Each student must respond to the reflection question individually, providing a unique answer. This part of the assignment should not be done collaboratively. Submit your reflection as a document either on Canvas or in the GitHub repository.

Reflect on your personal problem-solving process. How did your understanding of object-oriented programming (e.g., classes, constructors, and data structures like ArrayLists) evolve as you worked on this task? What challenges did you encounter and how did you go about fixing them? Explain which LLM, your prompts, or internet help and how debugged your code?


Reflection
- During this assignment, i did struggle a lot and sometimes had to rely on GitHub AI to explain or code certain sections for me, as I felt like I understood the concepts but needed some more time and extra help to execute them within programming. Classes I felt comfortable with, and had some difficulty with constructors and what I should put inside them throughout the scrabble game. For using data structure as arraylist and binary search, I'm getting more familiar with he concept, but need some help when working with creating an arraylist that can be helpful and get somewhat better at implementing a binary search as I was doing the zybooks assignment earlier throughout the day, and have seen the purpose of a binary search. I think the challenges I encountered has to be getting started as I felt like I was bulding the bridge to complete the sections before i had to get some extra help from the Github AI but also exchanging letter was a pretty big issue i faced and had some explaining do for me throughout the time of working on the scrabble project. Some prompts I ended up using "included break down how I could do an exchangeLetter()" which helped me with using some keywords as trim, convert to string, and other features included. As well, I noticed the 2019.txt file wasn't included so I thought of a few words and had some generated for me to help with being able to execute this Scrabble game as I came to many stops by not having a specific word registered.




//point system 
    public static int calculateScore(String word) { 
            if (word.length() == 3) {
                return 1;
            } else if (word.length() == 4) {            
                return 2;
            } else {
                return 0; // No points for other lengths, adjust as needed
            }
        } 

            //wins or loses system 

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

//////Skip turn feature 

      System.out.print("Enter your word using these letters (or '-' to skip): ");
                    String userWord = scanner.nextLine().trim().toUpperCase();

                    if (userWord.equals("-") || userWord.isEmpty()) {
                        System.out.println("Turn skipped.");
                        playerWords[p] = "(skipped)";
                        playerScores[p] = 0;
                        break;
                    }