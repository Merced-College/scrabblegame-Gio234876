import java.util.Objects;

public class Word implements Comparable<Word> {
    private final String value;

    public Word(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int compareTo(Word other) {
        return this.value.compareToIgnoreCase(other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;
        Word word = (Word) o;
        return value.equalsIgnoreCase(word.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value.toLowerCase());
    }

    @Override
    public String toString() {
        return value;
    }
}