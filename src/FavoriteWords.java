import java.io.*;
import java.util.*;

public class FavoriteWords {
    private Set<String> favorites;
    private String filePath;
    
    public FavoriteWords(Set<String> favorites, String filePath) {
        this.favorites = favorites;
        this.filePath = filePath;
        loadFavorites();
    }
    
    public void loadFavorites() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                return;
            }
            
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        favorites.add(line.trim().toLowerCase());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void saveFavorites() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String word : favorites) {
                writer.write(word);
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean addFavorite(String word) {
        if (word == null || word.trim().isEmpty()) {
            return false;
        }
        
        String normalizedWord = word.trim().toLowerCase();
        if (favorites.add(normalizedWord)) {
            saveFavorites();
            return true;
        }
        return false;
    }
    
    public boolean removeFavorite(String word) {
        if (word == null || word.trim().isEmpty()) {
            return false;
        }
        
        String normalizedWord = word.trim().toLowerCase();
        if (favorites.remove(normalizedWord)) {
            saveFavorites();
            return true;
        }
        return false;
    }
    
    public List<String> getFavorites(boolean asc) {
        List<String> result = new ArrayList<>(favorites);
        if (!asc) {
            Collections.reverse(result);
        }
        return result;
    }
    
    public void toggleFavorite(String word) {
        if (word == null || word.trim().isEmpty()) {
            return;
        }
        
        String normalizedWord = word.trim().toLowerCase();
        if (favorites.contains(normalizedWord)) {
            favorites.remove(normalizedWord);
        } else {
            favorites.add(normalizedWord);
        }
        saveFavorites();
    }
}