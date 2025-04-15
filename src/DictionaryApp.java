import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.swing.*;

public class DictionaryApp extends JFrame {
    private Dictionary vieDict;
    private Dictionary enDict;
    private FavoriteWords favoriteWords;
    private boolean isViEn = true;
    
    private JTextField searchField;
    private JTextArea resultArea;
    private JComboBox<String> sortComboBox;
    private JButton searchButton, addButton, removeButton, favoriteButton, statsButton, showAllButton, showFavoritesButton;
    private JLabel titleLabel, languageLabel;
    private JPanel mainPanel, buttonPanel, statsPanel;
    private JTextField fromDateField, toDateField;
    private JList<String> wordList;
    private DefaultListModel<String> wordListModel;
    
    public DictionaryApp() {
        // Khởi tạo các đối tượng từ điển
        vieDict = new Dictionary("release/viet_anh.xml");
        enDict = new Dictionary("release/anh_viet.xml");
        favoriteWords = new FavoriteWords(new java.util.TreeSet<>(), "release/favorites.txt");
        
        // Thiết lập giao diện
        setTitle("Dictionary Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        titleLabel = new JLabel("TỪ ĐIỂN VIỆT-ANH / ANH-VIỆT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        mainPanel.add(titleLabel, gbc);
        
        // Language switch
        languageLabel = new JLabel("Đang tra: Việt-Anh");
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(languageLabel, gbc);
        
        JButton switchButton = new JButton("Chuyển đổi");
        switchButton.addActionListener(e -> switchLanguage());
        gbc.gridx = 1;
        mainPanel.add(switchButton, gbc);
        
        // Search area
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        searchField = new JTextField(20);
        mainPanel.add(searchField, gbc);
        
        searchButton = new JButton("Tra từ");
        searchButton.addActionListener(e -> searchWord());
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        mainPanel.add(searchButton, gbc);
        
        // Word list
        wordListModel = new DefaultListModel<>();
        wordList = new JList<>(wordListModel);
        wordList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        wordList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedWord = wordList.getSelectedValue();
                if (selectedWord != null) {
                    searchField.setText(selectedWord);
                    searchWord();
                }
            }
        });
        JScrollPane wordListScrollPane = new JScrollPane(wordList);
        wordListScrollPane.setPreferredSize(new Dimension(200, 300));
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        mainPanel.add(wordListScrollPane, gbc);
        
        // Result area
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        mainPanel.add(scrollPane, gbc);
        
        // Button panel
        buttonPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        addButton = new JButton("Thêm từ");
        addButton.addActionListener(e -> addWord());
        
        removeButton = new JButton("Xóa từ");
        removeButton.addActionListener(e -> removeWord());
        
        favoriteButton = new JButton("Yêu thích");
        favoriteButton.addActionListener(e -> toggleFavorite());
        
        statsButton = new JButton("Thống kê");
        statsButton.addActionListener(e -> showStatsPanel());
        
        showAllButton = new JButton("Hiển thị tất cả");
        showAllButton.addActionListener(e -> showAllWords());
        buttonPanel.add(showAllButton); // Thêm trước statsButton
        
        showFavoritesButton = new JButton("Hiển thị từ yêu thích");
        showFavoritesButton.addActionListener(e -> showFavorites());
        buttonPanel.add(showFavoritesButton);
        
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(favoriteButton);
        buttonPanel.add(statsButton);
        
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0.0;
        mainPanel.add(buttonPanel, gbc);
        
        // Stats panel (hidden by default)
        statsPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        statsPanel.add(new JLabel("Từ ngày:"));
        fromDateField = new JTextField(LocalDate.now().minusDays(7).toString());
        statsPanel.add(fromDateField);
        
        statsPanel.add(new JLabel("Đến ngày:"));
        toDateField = new JTextField(LocalDate.now().toString());
        statsPanel.add(toDateField);
        
        JButton showStatsButton = new JButton("Hiển thị");
        showStatsButton.addActionListener(e -> showStatistics());
        statsPanel.add(showStatsButton);
        
        gbc.gridy = 5;
        statsPanel.setVisible(false);
        mainPanel.add(statsPanel, gbc);
        
        // Sort combo box
        gbc.gridy = 6;
        sortComboBox = new JComboBox<>(new String[]{"Sắp xếp A-Z", "Sắp xếp Z-A"});
        sortComboBox.addActionListener(e -> sortFavorites());
        sortComboBox.setVisible(false);
        mainPanel.add(sortComboBox, gbc);
        
        add(mainPanel);
        
        // Load initial word list
        updateWordList();
    }
    
    private void showAllWords() {
        Dictionary dict = isViEn ? vieDict : enDict;
        Map<String, DictionaryEntry> entries = dict.getEntries();
        
        if (entries.isEmpty()) {
            resultArea.setText("Từ điển hiện đang trống");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Danh sách tất cả các từ (").append(entries.size()).append(" từ):\n\n");
        
        // Sắp xếp các từ theo thứ tự alphabet
        List<String> sortedWords = new ArrayList<>(entries.keySet());
        Collections.sort(sortedWords, String.CASE_INSENSITIVE_ORDER);
        
        for (String word : sortedWords) {
            DictionaryEntry entry = entries.get(word);
            sb.append("- ").append(entry.getWord()).append(": ").append(entry.getMeaning()).append("\n");
        }
        
        resultArea.setText(sb.toString());
    }
    
    private void switchLanguage() {
        isViEn = !isViEn;
        if (isViEn) {
            languageLabel.setText("Đang tra: Việt-Anh");
        } else {
            languageLabel.setText("Đang tra: Anh-Việt");
        }
        searchField.setText("");
        resultArea.setText("");
        updateWordList();
    }
    
    private void searchWord() {
        String word = searchField.getText().trim();
        if (word.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ cần tra", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String meaning = isViEn ? vieDict.lookup(word) : enDict.lookup(word);
        if (meaning != null) {
            resultArea.setText(meaning);
        } else {
            resultArea.setText("Không tìm thấy từ \"" + word + "\" trong từ điển");
        }
    }
    
    private void addWord() {
        String word = JOptionPane.showInputDialog(this, "Nhập từ mới:");
        if (word == null || word.trim().isEmpty()) return;
        
        String meaning = JOptionPane.showInputDialog(this, "Nhập nghĩa của từ:");
        if (meaning == null || meaning.trim().isEmpty()) return;
        
        boolean success = isViEn ? vieDict.addWord(word, meaning) : enDict.addWord(word, meaning);
        if (success) {
            JOptionPane.showMessageDialog(this, "Đã thêm từ mới thành công");
            updateWordList();
        } else {
            JOptionPane.showMessageDialog(this, "Từ này đã tồn tại trong từ điển", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void removeWord() {
        String word = searchField.getText().trim();
        if (word.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ cần xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        boolean success = isViEn ? vieDict.removeWord(word) : enDict.removeWord(word);
        if (success) {
            // Xóa từ khỏi danh sách yêu thích nếu có
            favoriteWords.removeFavorite(word);
            
            JOptionPane.showMessageDialog(this, "Đã xóa từ thành công");
            searchField.setText("");
            resultArea.setText("");
            updateWordList();
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy từ \"" + word + "\" trong từ điển", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void toggleFavorite() {
        String word = searchField.getText().trim();
        if (word.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ cần thêm vào yêu thích", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        favoriteWords.toggleFavorite(word);
        updateWordList();
    }
    
    private void sortFavorites() {
        showFavorites();
    }
    
    private void showStatsPanel() {
        statsPanel.setVisible(true);
        sortComboBox.setVisible(false);
    }
    
    private void showStatistics() {
        try {
            LocalDate fromDate = LocalDate.parse(fromDateField.getText());
            LocalDate toDate = LocalDate.parse(toDateField.getText());
            
            if (fromDate.isAfter(toDate)) {
                JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải trước ngày kết thúc", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            List<DictionaryEntry> stats = isViEn ? vieDict.getWordStats(fromDate, toDate) : enDict.getWordStats(fromDate, toDate);
            
            StringBuilder sb = new StringBuilder();
            sb.append("Thống kê tra cứu từ ").append(fromDate).append(" đến ").append(toDate).append(":\n\n");
            
            if (stats.isEmpty()) {
                sb.append("Không có dữ liệu thống kê trong khoảng thời gian này");
            } else {
                for (DictionaryEntry entry : stats) {
                    sb.append("- ").append(entry.getWord()).append(": ").append(entry.getAccessCount()).append(" lần\n");
                }
            }
            
            resultArea.setText(sb.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ (yyyy-mm-dd)", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showFavorites() {
        Dictionary dict = isViEn ? vieDict : enDict;
        Map<String, DictionaryEntry> entries = dict.getEntries();
        
        // Hiển thị ComboBox sắp xếp
        sortComboBox.setVisible(true);
        statsPanel.setVisible(false);
        
        // Lấy thứ tự sắp xếp từ ComboBox
        boolean asc = sortComboBox.getSelectedIndex() == 0;
        
        List<String> favorites = favoriteWords.getFavorites(asc);
        if (favorites.isEmpty()) {
            resultArea.setText("Không có từ yêu thích nào");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("Danh sách từ yêu thích (Sắp xếp ").append(asc ? "A-Z" : "Z-A").append("):\n\n");
        
        int count = 0;
        for (String word : favorites) {
            DictionaryEntry entry = entries.get(word.toLowerCase());
            if (entry != null) {
                sb.append("- ").append(entry.getWord()).append(": ").append(entry.getMeaning()).append("\n");
                count++;
            }
        }
        
        if (count == 0) {
            sb.append("Không có từ yêu thích nào trong từ điển hiện tại");
        }
        
        resultArea.setText(sb.toString());
    }
    
    private void updateWordList() {
        Dictionary dict = isViEn ? vieDict : enDict;
        Map<String, DictionaryEntry> entries = dict.getEntries();
        
        wordListModel.clear();
        for (String word : entries.keySet()) {
            wordListModel.addElement(word);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DictionaryApp app = new DictionaryApp();
            app.setVisible(true);
        });
    }
}