import java.io.*;
import java.util.TreeSet;

public class FavoriteWords {
    private TreeSet<String> favorites;
    private String filePath;
    
    public FavoriteWords(TreeSet<String> favorites, String filePath) {
        this.favorites = favorites;
        this.filePath = filePath;
        loadFavorites();
    }
    
    private void loadFavorites() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                favorites.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void saveFavorites() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (String word : favorites) {
                writer.println(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addFavorite(String word) {
        favorites.add(word);
        saveFavorites();
    }
    
    public void removeFavorite(String word) {
        favorites.remove(word);
        saveFavorites();
    }
    
    public TreeSet<String> getFavorites() {
        return favorites;
    }
}