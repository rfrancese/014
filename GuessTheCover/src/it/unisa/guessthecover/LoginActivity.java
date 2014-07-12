package it.unisa.guessthecover;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import database.DatabaseHandler;
import database.UserFunctions;
 
public class LoginActivity extends Activity {
    
	class doRegister extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            UserFunctions userFunction = new UserFunctions();
            json = userFunction.loginUser(email, password);
            return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			//super.onPostExecute(result);
			try {
                if (!(json.getString(KEY_SUCCESS).equals(null))) {
                    loginErrorMsg.setText("");
                    String res = json.getString(KEY_SUCCESS); 
                    if(Integer.parseInt(res) == 1){
                        // user successfully logged in
                        // Store user details in SQLite Database
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        JSONObject json_user = json.getJSONObject("user");
                         
                        // Clear all previous data in database
                        //userFunction.logoutUser(getApplicationContext());
                        //db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getInt(KEY_POINT), json_user.getString(KEY_PASSWORD));                        
                        db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getInt(KEY_POINT)); 
                        // Launch Dashboard Screen
                        Intent dashboard = new Intent(getApplicationContext(), Home.class);
                         
                        // Close all views before launching Dashboard
                        dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(dashboard);
                         
                        // Close Login Screen
                        finish();
                    }else{
                        // Error in login
                        loginErrorMsg.setText("Incorrect username/password");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
		}
		
	}
	
	
	Button btnLogin;
    Button btnLinkToRegister;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;
    JSONObject json;
	UserFunctions userFunction;
	
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_POINT = "point";
    private static String KEY_PASSWORD = "password";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        Log.d("login","sono entrato nel login");
        
        // Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.email_form);
        inputPassword = (EditText) findViewById(R.id.password_form);
        btnLogin = (Button) findViewById(R.id.login_button);
        btnLinkToRegister = (Button) findViewById(R.id.registra_button);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
 
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
            	/*
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.loginUser(email, password);
 				*/
                // check for login response
            	
            	
        		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        		boolean connessioneDisponibile=false;
        		if(cm!=null && cm.getActiveNetworkInfo()!=null){
        			//controllo disponibilità di rete
        			connessioneDisponibile= cm.getActiveNetworkInfo().isConnectedOrConnecting();
        	    	}    		 
        	    if(connessioneDisponibile){
        	    	//carichiamo la pagina web
        	    	new doRegister().execute();
        	    	
        	    }
        	    else{
        	    	loginErrorMsg.setText("connesione non disponibile");
        	    }

            	
            	//new doRegister().execute();
            	/*
            	try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        loginErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS); 
                        if(Integer.parseInt(res) == 1){
                            // user successfully logged in
                            // Store user details in SQLite Database
                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            JSONObject json_user = json.getJSONObject("user");
                             
                            // Clear all previous data in database
                            userFunction.logoutUser(getApplicationContext());
                            //db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getInt(KEY_POINT), json_user.getString(KEY_PASSWORD));                        
                            db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getInt(KEY_POINT)); 
                            // Launch Dashboard Screen
                            Intent dashboard = new Intent(getApplicationContext(), Home.class);
                             
                            // Close all views before launching Dashboard
                            dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(dashboard);
                             
                            // Close Login Screen
                            finish();
                        }else{
                            // Error in login
                            loginErrorMsg.setText("Incorrect username/password");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        });
 
        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
    @Override
	public void onBackPressed() {
		finish();
	}
    
}