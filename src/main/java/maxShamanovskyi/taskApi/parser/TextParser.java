package maxShamanovskyi.taskApi.parser;

import maxShamanovskyi.taskApi.servlet.MainServlet;
import org.json.simple.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TextParser {
    private JSONArray result;
    private int limit;
    private int count;

    public TextParser() {
        result = new JSONArray();
    }

    private static File getFile() {
        return new File(TextParser.class.getResource("/text.txt").getFile());
    }

    /**
     * Basic method which get parameters from MainServlet.
     * <p>Returns a JSONArray list in which were added strings after search.
     * <p>Search comes with four parameters.
     *
     * @param isQ    boolean which represents availability text to search in file.
     * @param q      string which represents text to search in file. If parameter is blank or missing - method return
     *               all text from file.
     * @param limit  integer which represents max number of chars in text that method return.
     * @param length integer which represents max string length. Method return string which.
     *               doesn’t exceed that number.
     *
     * @see JSONArray
     * @see MainServlet
     */
    public JSONArray search(boolean isQ, String q, int limit, int length) {
        init();
        this.limit = limit;
        try (BufferedReader reader = new BufferedReader(new FileReader(getFile()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            if (isQ) {
                while ((line = reader.readLine()) != null) {
                    if (line.trim().equals("") || !reader.ready()) {
                        String text = sb.toString();
                        if (text.contains(q)) {
                            if (parseText(text, length)) {
                                break;
                            }
                        }
                        sb.setLength(0);
                    }
                    sb.append(line);
                }
            } else {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                addResult(sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void init() {
        result.clear();
        count = 0;
    }

    private boolean parseText(String text, int length) {
        if (text.length() >= length) {
            return addResult(text.substring(0, length));
        } else {
            return addResult(text);
        }
    }

    @SuppressWarnings("unchecked")
    private boolean addResult(String text) {
        count += text.length();

        if(count <= limit){
            result.add(text);
        }else{
            result.add(text.substring(0, limit - (count - text.length())));
            return true;
        }
        return false;
    }
}
