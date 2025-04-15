import java.util.Date;

public class DictionaryEntry {
    private String word;
    private String meaning;
    private Date lastAccessed;
    private int accessCount;
    
    public DictionaryEntry(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
        this.lastAccessed = null;
        this.accessCount = 0;
    }
    
    public String getWord() {
        return word;
    }
    
    public String getMeaning() {
        return meaning;
    }
    
    public Date getLastAccessed() {
        return lastAccessed;
    }
    
    public void setLastAccessed(Date date) {
        this.lastAccessed = date;
    }
    
    public int getAccessCount() {
        return accessCount;
    }
    
    public void incrementAccessCount() {
        this.accessCount++;
    }
    
    @Override
    public String toString() {
        return word + ": " + meaning + " (Tra cứu " + accessCount + " lần)";
    }
}