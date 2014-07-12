package database;

import java.util.ArrayList;
import java.util.List;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
 
import android.content.Context;
import android.util.Log;
 
public class UserFunctions {
     
    private JSONParser jsonParser;
     
    // Testing in localhost using wamp or xampp 
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    private static String loginURL = "http://guessthecover.altervista.org/db/";
    private static String registerURL = "http://guessthecover.altervista.org/db/";
    private static String updateURL = "http://guessthecover.altervista.org/db/";
    
    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String update_tag = "update";
    private static String top_tag = "top";
     
    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }
     
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject loginUser(String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        //JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        JSONObject json = jsonParser.makeHttpRequest(loginURL, "POST", params);
        // return json
        // Log.e("JSON", json.toString());
        return json;
    }
     
    /**
     * function make Login Request
     * @param name
     * @param email
     * @param password
     * @param point 
     * */
    public JSONObject registerUser(String name, String email, String password, int point){//int point
        // Building Parameters
    	Log.d("USER FUNCTION","LE INFO SONO ARRIVATE");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Log.d("USER FUNCTION","CREO LA LISTA PARAMS");
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("point", String.valueOf(point)));
        Log.d("USER FUNCTION","HO AGGIUNTO LE INFO A PARAMS");
        // getting JSON Object
        Log.d("USER FUNCTION","CREO UN OGGETTO JSONOBJECT A CUI ASSEGNO GETJSONFROMURL CON PARAMETRI REGISTER URL E PARAMS ");
        //JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        JSONObject json = jsonParser.makeHttpRequest(registerURL, "POST", params);
        // return json
        Log.d("USER FUNCTION","RITORNO L'OGGETTO JSON");
        return json;
    }
     
    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }
     
    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }

	public JSONObject updateUser(String email, int point) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag",update_tag ));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("point", String.valueOf(point)));
		//Log.d("updateuser","email: "+email+" point: "+point);   
		JSONObject json = jsonParser.makeHttpRequest(updateURL, "POST", params);
		Log.d("classifica","json " +json);   
        return json; 
	}
	public JSONArray top100(){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag",top_tag ));
		JSONArray json = jsonParser.makeHttpRequestArray(updateURL, "POST", params);
		return json;
	}
}