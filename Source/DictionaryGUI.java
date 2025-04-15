import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;
import javax.swing.*;

public class DictionaryGUI extends JFrame {
    private Dictionary dictionary;
    private JComboBox<String> languageComboBox;
    private JTextField searchField;
    private JTextArea meaningArea;
    private JList<String> favoritesList;
    private DefaultListModel<String> favoritesModel;
    private JTable statisticsTable;
    private JTextField wordField;
    private JTextField meaningField;

    public DictionaryGUI() {
        dictionary = new Dictionary("bin/data/dictionary.xml");
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Dictionary Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel - Search
        JPanel topPanel = new JPanel(new FlowLayout());
        languageComboBox = new JComboBox<>(new String[]{"VI-EN", "EN-VI"});
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        topPanel.add(new JLabel("Language: "));
        topPanel.add(languageComboBox);
        topPanel.add(new JLabel("Search: "));
        topPanel.add(searchField);
        topPanel.add(searchButton);

        // Center Panel - Meaning Display
        meaningArea = new JTextArea();
        meaningArea.setEditable(false);
        JScrollPane meaningScroll = new JScrollPane(meaningArea);

        // Right Panel - Favorites
        JPanel rightPanel = new JPanel(new BorderLayout());
        favoritesModel = new DefaultListModel<>();
        favoritesList = new JList<>(favoritesModel);
        JScrollPane favoritesScroll = new JScrollPane(favoritesList);
        rightPanel.add(new JLabel("Favorites"), BorderLayout.NORTH);
        rightPanel.add(favoritesScroll, BorderLayout.CENTER);

        // Bottom Panel - Add/Remove Words
        JPanel bottomPanel = new JPanel(new GridLayout(2, 1));
        JPanel addPanel = new JPanel(new FlowLayout());
        wordField = new JTextField(15);
        meaningField = new JTextField(15);
        JButton addButton = new JButton("Add Word");
        JButton removeButton = new JButton("Remove Word");
        addPanel.add(new JLabel("Word: "));
        addPanel.add(wordField);
        addPanel.add(new JLabel("Meaning: "));
        addPanel.add(meaningField);
        addPanel.add(addButton);
        addPanel.add(removeButton);

        // Statistics Panel
        JPanel statsPanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Word", "Search Count"};
        statisticsTable = new JTable(new Object[][]{}, columnNames);
        JScrollPane statsScroll = new JScrollPane(statisticsTable);
        JButton statsButton = new JButton("Show Statistics");
        statsPanel.add(statsButton, BorderLayout.NORTH);
        statsPanel.add(statsScroll, BorderLayout.CENTER);

        bottomPanel.add(addPanel);
        bottomPanel.add(statsPanel);

        // Add components to frame
        add(topPanel, BorderLayout.NORTH);
        add(meaningScroll, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        // Add action listeners
        searchButton.addActionListener(e -> searchWord());
        addButton.addActionListener(e -> addWord());
        removeButton.addActionListener(e -> removeWord());
        statsButton.addActionListener(e -> showStatistics());
        favoritesList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    String selected = favoritesList.getSelectedValue();
                    if (selected != null) {
                        searchField.setText(selected);
                        searchWord();
                    }
                }
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    private void searchWord() {
        String word = searchField.getText().trim();
        String language = (String) languageComboBox.getSelectedItem();
        
        if (word.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a word to search");
            return;
        }

        String meaning = dictionary.lookup(word);
        if (meaning != null) {
            meaningArea.setText(meaning);
        } else {
            meaningArea.setText("Word not found");
        }
    }

    private void addWord() {
        String word = wordField.getText().trim();
        String meaning = meaningField.getText().trim();
        String language = (String) languageComboBox.getSelectedItem();

        if (word.isEmpty() || meaning.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both word and meaning");
            return;
        }

        if (dictionary.addWord(word, meaning)) {
            wordField.setText("");
            meaningField.setText("");
            JOptionPane.showMessageDialog(this, "Word added successfully");
        } else {
            JOptionPane.showMessageDialog(this, "Word already exists");
        }
    }

    private void removeWord() {
        String word = wordField.getText().trim();
        String language = (String) languageComboBox.getSelectedItem();

        if (word.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a word to remove");
            return;
        }

        if (dictionary.removeWord(word)) {
            wordField.setText("");
            JOptionPane.showMessageDialog(this, "Word removed successfully");
        } else {
            JOptionPane.showMessageDialog(this, "Word not found");
        }
    }

    private void updateFavoritesList() {
        favoritesModel.clear();
        for (DictionaryEntry entry : dictionary.getAllEntries()) {
            favoritesModel.addElement(entry.getWord());
        }
    }

    private void showStatistics() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusDays(7); // Last 7 days by default
        
        List<DictionaryEntry> stats = dictionary.getWordStats(startDate, now);
        TreeMap<String, Integer> sortedStats = new TreeMap<>();
        
        for (DictionaryEntry entry : stats) {
            sortedStats.put(entry.getWord(), entry.getAccessCount());
        }
        
        Object[][] data = sortedStats.entrySet().stream()
            .map(entry -> new Object[]{entry.getKey(), entry.getValue()})
            .toArray(Object[][]::new);
            
        statisticsTable.setModel(new javax.swing.table.DefaultTableModel(data, 
            new String[]{"Word", "Search Count"}));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DictionaryGUI().setVisible(true);
        });
    }
} 