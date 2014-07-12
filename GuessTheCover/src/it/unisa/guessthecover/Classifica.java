package it.unisa.guessthecover;

import it.unisa.guessthecover.LoginActivity.doRegister;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import database.DatabaseHandler;
import database.DbAdapter;
import database.UserFunctions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Classifica extends Activity {
	private int multiplier=1;
	//private ArrayList<User> UserList=new ArrayList<User>();
	private DbAdapter dbHelper; 
	private Cursor cursor;
	private JSONObject json;
	private JSONArray jsonTop;
	private UserFunctions userFunction;
	private String email;
	private int point;
	private static String KEY_SUCCESS = "success";
	private static String KEY_NAME = "name";
	private static String KEY_POINT = "point";
    
	protected void onCreate(Bundle savedInstanceState) {
		class doRegister extends AsyncTask<String, String, String>{
			
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
			}
			
			@Override
			protected String doInBackground(String... params) {
				Log.d("classifica", "doInBackground");
				dbHelper = new DbAdapter(getApplicationContext());
				dbHelper.open();
				cursor = dbHelper.fetchAllContacts();
				cursor.moveToFirst();
				email = cursor.getString( cursor.getColumnIndex(DbAdapter.KEY_EMAIL) );
				point = cursor.getInt( cursor.getColumnIndex(DbAdapter.KEY_POINT) );
				//Log.d("classifica","email: "+email+" point: "+point);   
		        userFunction = new UserFunctions();
		        json = userFunction.updateUser(email, point);
		        //Log.d("classifica",""+json);
		        //new top100().execute();
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				Log.d("classifica", "onPostExecute");
				try { 
	                if (!(json.getString(KEY_SUCCESS).equals(null))) {
	                	
	                	//registerErrorMsg.setText("");
	             
	                	String res = json.getString(KEY_SUCCESS); 
	                    if(Integer.parseInt(res) == 1){
	                    	//WebView myWebView = (WebView) findViewById(R.id.webview);
	            	    	//myWebView.loadUrl("http://guessthecover.altervista.org/db/classifica.php");
	                        
	                    }else{
	                        // Error in update
	                        //registerErrorMsg.setText("Error occured in registration");
	                    }
	                }
	            } catch (JSONException e) {e.printStackTrace();}
				//super.onPostExecute(result);
			}
		}
		
		Log.d("classifica", "sono appena entrato nella classifica");  
			 
		super.onCreate(savedInstanceState);
	    //setContentView(R.layout.activity_category);
	    //////
	    	    
	    Intent intent=getIntent(); // l'intent di questa activity
	    String pkg=getPackageName();
	    int mult=intent.getIntExtra(pkg+".myNewMultiplier",1); //prendi moltiplicatore
	    multiplier=mult;
	       
	    
       // new doRegister().execute();  
        
        /////
        setContentView(R.layout.activity_web);
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean connessioneDisponibile=false;
		if(cm!=null && cm.getActiveNetworkInfo()!=null){
			//controllo disponibilità di rete
			connessioneDisponibile= cm.getActiveNetworkInfo().isConnectedOrConnecting();
	    	}    		 
	    if(connessioneDisponibile){
	    	//carichiamo la pagina web
	    	new doRegister().execute();
	    	WebView myWebView = (WebView) findViewById(R.id.webview);
	    	myWebView.loadUrl("http://guessthecover.altervista.org/db/classifica.php");
	    }
	    else{
	    	Toast t=new Toast(Classifica.this);
	    	t.makeText(this, "Connessione non disponibile", Toast.LENGTH_LONG).show();
	    }
	 }
	@Override
	public void onBackPressed() {
		Intent intent;
		intent = new Intent(getApplicationContext(), Home.class);
		String pkg=getPackageName();
		intent.putExtra(pkg+".myNewMultiplier", multiplier);
		startActivity(intent);
		finish();
	}
	@Override 
	public boolean onCreateOptionsMenu(Menu menu) {
	        menu.add("Logout");
	        return true; 
	}
	static final int Logout= 0;
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 0:
				UserFunctions f=new UserFunctions();
				f.logoutUser(getBaseContext());
				Intent intent;
				intent = new Intent(getApplicationContext(), LoginActivity.class);
				String pkg=getPackageName();
				intent.putExtra(pkg+".myMultiplier", multiplier); //invio moltiplicatore
				startActivity(intent);
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
    }
}
