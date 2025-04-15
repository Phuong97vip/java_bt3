import java.io.*;
import java.time.LocalDate;
import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

public class Dictionary {
    private Map<String, DictionaryEntry> entries;
    private String xmlFilePath;
    
    public Dictionary(String xmlFilePath) {
        this.entries = new HashMap<>();
        this.xmlFilePath = xmlFilePath;
        loadFromXML();
    }
    
    
    
    private void loadFromXML() {
        try {
            File file = new File(xmlFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                saveToXML(); // Create empty file if not exists
                return;
            }
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            
            NodeList entryList = doc.getElementsByTagName("entry");
            for (int i = 0; i < entryList.getLength(); i++) {
                Node node = entryList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String word = element.getElementsByTagName("word").item(0).getTextContent();
                    String meaning = element.getElementsByTagName("meaning").item(0).getTextContent();
                    
                    DictionaryEntry entry = new DictionaryEntry(word, meaning);
                    
                    // Load access count if available
                    Node accessCountNode = element.getElementsByTagName("accessCount").item(0);
                    if (accessCountNode != null) {
                        entry.setAccessCount(Integer.parseInt(accessCountNode.getTextContent()));
                    }
                    
                    // Load last accessed date if available
                    Node lastAccessedNode = element.getElementsByTagName("lastAccessed").item(0);
                    if (lastAccessedNode != null) {
                        entry.setLastAccessed(LocalDate.parse(lastAccessedNode.getTextContent()));
                    }
                    
                    entries.put(word.toLowerCase(), entry);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void saveToXML() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();
            
            Element root = doc.createElement("dictionary");
            doc.appendChild(root);
            
            for (DictionaryEntry entry : entries.values()) {
                Element entryElement = doc.createElement("entry");
                
                Element wordElement = doc.createElement("word");
                wordElement.appendChild(doc.createTextNode(entry.getWord()));
                entryElement.appendChild(wordElement);
                
                Element meaningElement = doc.createElement("meaning");
                meaningElement.appendChild(doc.createTextNode(entry.getMeaning()));
                entryElement.appendChild(meaningElement);
                
                Element accessCountElement = doc.createElement("accessCount");
                accessCountElement.appendChild(doc.createTextNode(String.valueOf(entry.getAccessCount())));
                entryElement.appendChild(accessCountElement);
                
                if (entry.getLastAccessed() != null) {
                    Element lastAccessedElement = doc.createElement("lastAccessed");
                    lastAccessedElement.appendChild(doc.createTextNode(entry.getLastAccessed().toString()));
                    entryElement.appendChild(lastAccessedElement);
                }
                
                root.appendChild(entryElement);
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String lookup(String word) {
        DictionaryEntry entry = entries.get(word.toLowerCase());
        if (entry != null) {
            entry.setAccessCount(entry.getAccessCount() + 1);
            entry.setLastAccessed(LocalDate.now());
            saveToXML();
            return entry.getMeaning();
        }
        return null;
    }
    
    public boolean addWord(String word, String meaning) {
        if (entries.containsKey(word.toLowerCase())) {
            return false;
        }
        entries.put(word.toLowerCase(), new DictionaryEntry(word, meaning));
        saveToXML();
        return true;
    }
    
    public boolean removeWord(String word) {
        if (!entries.containsKey(word.toLowerCase())) {
            return false;
        }
        entries.remove(word.toLowerCase());
        saveToXML();
        return true;
    }
    
    public List<DictionaryEntry> getWordStats(LocalDate fromDate, LocalDate toDate) {
        List<DictionaryEntry> result = new ArrayList<>();
        for (DictionaryEntry entry : entries.values()) {
            if (entry.getLastAccessed() != null && 
                !entry.getLastAccessed().isBefore(fromDate) && 
                !entry.getLastAccessed().isAfter(toDate)) {
                result.add(entry);
            }
        }
        return result;
    }
    
    public List<String> getAllWords() {
        List<String> words = new ArrayList<>();
        for (DictionaryEntry entry : entries.values()) {
            words.add(entry.getWord());
        }
        Collections.sort(words);
        return words;
    }
    
    public List<DictionaryEntry> getAllEntries() {
        List<DictionaryEntry> sortedEntries = new ArrayList<>(entries.values());
        Collections.sort(sortedEntries, (a, b) -> a.getWord().compareToIgnoreCase(b.getWord()));
        return sortedEntries;
    }
    
    public Map<String, DictionaryEntry> getEntries() {
        return entries;
    }
}