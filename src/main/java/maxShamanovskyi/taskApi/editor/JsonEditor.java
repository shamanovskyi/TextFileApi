package maxShamanovskyi.taskApi.editor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonEditor {

    /**
     * Parse JSON string with help GSON library and return pretty looking string as a result.
     *
     * @see JsonEditor#parse(String)
     */
    public static String parse(String text)  {
        String result = "";
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(text);
            result = gson.toJson(obj);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
