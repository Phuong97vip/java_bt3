import java.time.LocalDate;

public class DictionaryEntry {
    private String word;
    private String meaning;
    private LocalDate lastAccessed;
    private int accessCount;
    
    public DictionaryEntry(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
        this.accessCount = 0;
    }
    
    public String getWord() {
        return word;
    }
    
    public void setWord(String word) {
        this.word = word;
    }
    
    public String getMeaning() {
        return meaning;
    }
    
    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
    
    public LocalDate getLastAccessed() {
        return lastAccessed;
    }
    
    public void setLastAccessed(LocalDate lastAccessed) {
        this.lastAccessed = lastAccessed;
    }
    
    public int getAccessCount() {
        return accessCount;
    }
    
    public void setAccessCount(int accessCount) {
        this.accessCount = accessCount;
    }
    
    @Override
    public String toString() {
        return word + ": " + meaning + " (Tra cứu " + accessCount + " lần)";
    }
}