package it.unisa.guessthecover;

import org.json.JSONException;
import org.json.JSONObject;
import database.DatabaseHandler;
import database.UserFunctions;
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
 
public class RegisterActivity extends Activity {
	
	class doRegister extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String name = inputName.getText().toString();
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            int point=0;
            Log.d("REGISTRA","PRELEVA LE INFO");
            userFunction = new UserFunctions();
            Log.d("REGISTRA","PASSO LE INFO USANDO USERFUNCTION.REGISTERUSER");
            json = userFunction.registerUser(name, email, password, point);
            
            
        
            return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			try {
            	Log.d("REGISTRA","sono entrato nel try"); 
                if (!(json.getString(KEY_SUCCESS).equals(null))) {
                	Log.d("REGISTRA","IF VERO"); 
                	registerErrorMsg.setText("");
             
                	Log.d("REGISTRA","registerErrorMsg.setText()"); 
                    String res = json.getString(KEY_SUCCESS); 
                    Log.d("REGISTRA","String res = json.getString(KEY_SUCCESS); ");
                    Log.d("REGISTRA","CONTENUTO DI RES:= "+ res);
                    if(Integer.parseInt(res) == 1){
                    	Log.d("REGISTRA","SECONDO IF VERO");
                        
                        // user successfully registred
                        // Store user details in SQLite Database
                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        JSONObject json_user = json.getJSONObject("user");
                         
                        // Clear all previous data in database
                        //userFunction.logoutUser(getApplicationContext());
                        Log.d("REGISTRA","sto per salvare le info sul db locale"); 
                        //db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getInt(KEY_POINT), json_user.getString(KEY_PASSWORD));                        
                        db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getInt(KEY_POINT));                        
                        
                        Log.d("REGISTRA","HO SALVATO LE INFO SUL BD LOCALE"); 
                        // Launch Dashboard Screen
                        Intent dashboard = new Intent(getApplicationContext(), Home.class);
                        // Close all views before launching Dashboard
                        dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(dashboard);
                        // Close Registration Screen
                        finish();
                    }else{
                        // Error in registration
                        registerErrorMsg.setText("Error occured in registration");
                    }
                }
            } catch (JSONException e) {e.printStackTrace();}
			//super.onPostExecute(result);
		}
		
	}
	
	Button btnRegister;
	Button btnLinkToLogin;
	EditText inputName;
	EditText inputEmail;
	EditText inputPassword;
	EditText confPassword;
	TextView registerErrorMsg;
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
        setContentView(R.layout.activity_registra);
 
        Log.d("REGISTRA","SONO ENTRATO IN REGISTRA");
        
        // Importing all assets like buttons, text fields
        inputName = (EditText) findViewById(R.id.nickname_form);
        inputEmail = (EditText) findViewById(R.id.email_form);
        inputPassword = (EditText) findViewById(R.id.password_form);
        confPassword = (EditText) findViewById(R.id.confpass_form);
        btnRegister = (Button) findViewById(R.id.registra_button);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);
        
        Log.d("REGISTRA","CREO I COLLEGAMENTI CON GLI ELEMENTI A SCHERMO");
        
        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {         
            public void onClick(View view) {
            	Log.d("REGISTRA","HAI PREMUTO SUL PULSANTE REGISTRA");
            	
            	String password = inputPassword.getText().toString();
            	String conf_password = confPassword.getText().toString();
            	String name = inputName.getText().toString();
            	String email = inputEmail.getText().toString();
            	
            	if (password.equals(conf_password)) {
            	if((!name.equals(null)) && (!email.equals(null)) && (password.equals(null)) ) {
            		///
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
            	    	registerErrorMsg.setText("connesione non disponibile");
            	    }
            	}
            	else{
            		registerErrorMsg.setText("informazioni mancanti");
            		}
            	}
            	else{
            		registerErrorMsg.setText("password non corrispondenti");
            	}
            	//new doRegister().execute();
            	
            	Log.d("REGISTRA","LE INFO SONO ANDATE"); 
            }
        });
 
        // Link to Login Screen
        /*
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                // Close Registration View
                finish();
            }
        });*/
    }
    @Override
	public void onBackPressed() {
		Intent intent;
		intent = new Intent(getApplicationContext(), LoginActivity.class);
		startActivity(intent);
		finish();
	}
    
}