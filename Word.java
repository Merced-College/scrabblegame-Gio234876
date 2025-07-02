import java.util.Objects;


// Represents a word in the Scrabble game, implementing Comparable for sorting
public class Word implements Comparable<Word> {
    private final String value;

    //assigning the value of the word
    public Word(String value) {
        this.value = value;
    }

    // Getter for the word value
    public String getValue() {
        return value;
    }

    // Method to get the length of the word
    @Override
    public int compareTo(Word other) {
        return this.value.compareToIgnoreCase(other.value);
    }

    // Method to compare two words for equality, ignoring case
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word word = (Word) o;
        return value.equalsIgnoreCase(word.value);
    }

    //hascode for the word
    @Override
    public int hashCode() {
        return Objects.hash(value.toLowerCase());
    }

    //representing the word as a string
    @Override
    public String toString() {
        return value;
    }
}