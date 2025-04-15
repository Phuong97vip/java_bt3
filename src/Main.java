import java.awt.*;
import java.io.File;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Use SwingUtilities.invokeLater to ensure thread safety
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel for better appearance
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                
                // Set the data directory path
                String dataPath = "bin/data/";
                File dataDir = new File(dataPath);
                if (!dataDir.exists()) {
                    dataDir.mkdirs();
                }
                
                // Create and show the dictionary application
                DictionaryApp dictionaryApp = new DictionaryApp();
                dictionaryApp.setVisible(true);
                
                // Center the window on screen
                centerWindow(dictionaryApp);
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Failed to start application: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    private static void centerWindow(Window window) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - window.getWidth()) / 2;
        int y = (screenSize.height - window.getHeight()) / 2;
        window.setLocation(x, y);
    }
} 