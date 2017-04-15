package maxShamanovskyi.taskApi.servlet;

import maxShamanovskyi.taskApi.editor.JsonEditor;
import maxShamanovskyi.taskApi.parser.TextParser;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * MainServlet is general servlet which get parameters from user,
 * send them to TextParser and then render the results in the response.
 *
 * @author Maxym Shamanovskyi <mshamanovskyi@gmail.com>
 * @version 1.0
 * @see MainServlet#doGet(HttpServletRequest, HttpServletResponse)
 * @see JsonEditor
 * @see TextParser
 */
@WebServlet(name = "main", urlPatterns = {"/*"})
public class MainServlet extends HttpServlet {

    private Integer limit;
    private String q;
    private Integer length;
    private boolean isQ;
    private TextParser tp;

    @Override
    public void init() throws ServletException {
        tp = new TextParser();
        super.init();
    }


    /**
     * Parse parameters from url, check them and send to the TextParser's methods.
     * <p>If parameters are empty, set default values.
     * <p>Result put in JSONObject, send them to JsonEditor for pretty view and render the response page.
     *
     * @param req contains parameters from URL
     * @param resp render page with JSON content type
     * @see TextParser#search(boolean, String, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject json = new JSONObject();

        String limitS = req.getParameter("limit");
        if(limitS == null){
            limitS = "10000";
        }

        String legthS = req.getParameter("length");
        if(legthS == null){
            legthS = limitS;
        }

        String text = req.getParameter("q");
        if(text != null){
            if (!text.isEmpty() && text.trim().length() > 0) {
                q = text;
                isQ = true;
            }
        }
        if (!limitS.isEmpty()) {
            int textLimit = Integer.parseInt(limitS);
            if (textLimit >= 0) {
                limit = textLimit;
            }
        }
        if (!legthS.isEmpty()) {
            int textLength = Integer.parseInt(legthS);
            if (textLength >= 0) {
                length = textLength;
            }
        }

        json.put("text", tp.search(isQ, q, limit, length));

        resp.setContentType("application/json");
        resp.getWriter().write(JsonEditor.parse(json.toJSONString()));
    }

}
