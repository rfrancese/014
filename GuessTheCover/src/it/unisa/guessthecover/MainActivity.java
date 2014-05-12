package it.unisa.guessthecover;

import database.DbAdapter;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
	private DbAdapter dbHelper; 
	private Cursor cursor;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);  
	    Log.d("prova","sto per fare la roba sul bd");
	    dbHelper = new DbAdapter(getApplicationContext());
	    Log.d("prova","apro il bd");
	    dbHelper.open();
	    Log.d("prova","riempio il cursore");
        cursor = dbHelper.fetchAllContacts();
        
        /*
        Log.d("prova","provo a ftampare la roba nel cursore");
        
        while ( cursor.moveToNext() ) {
            String contactID = cursor.getString( cursor.getColumnIndex(DbAdapter.KEY_CONTACTID) );
            Log.d("TAG", "contact id = " + contactID);
            String email = cursor.getString( cursor.getColumnIndex(DbAdapter.KEY_EMAIL) );
            Log.d("TAG", "email = " + email);
            String nick = cursor.getString( cursor.getColumnIndex(DbAdapter.KEY_NICKNAME) );
            Log.d("TAG", "nickname = " + nick);
        } */
        Log.d("if", "sto per entrare nell if");
        if(cursor.moveToNext()==false){
        	Log.d("if", "condizione vera");
        	cursor.close();
            dbHelper.close();
        	
        	//super.onCreate(savedInstanceState);
    		setContentView(R.layout.activity_login);
    		
    		if (savedInstanceState == null) {
				getSupportFragmentManager().beginTransaction()
				.add(R.id.container, new PlaceholderFragment()).commit();
			}
			
			Button login=(Button)findViewById(R.id.login_button);
			login.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					//leggo dal form
					final EditText edit_email = (EditText)findViewById(R.id.email_form);
		            final EditText edit_password = (EditText)findViewById(R.id.password_form);
					//leggo dal bd
		            
		            //Log.d("prova if","sto per fare la roba sul bd");
		    	    dbHelper = new DbAdapter(getApplicationContext());
		    	    //Log.d("prova","apro il bd");
		    	    dbHelper.open();
		    	    //Log.d("prova","riempio il cursore");
		            cursor = dbHelper.fetchAllContacts();
		            
		            cursor.moveToFirst();
			        //confronto i dati del form con quelli del bd locale... dovremmo confrontarli con quelli del bd online 
		            if(edit_email.getText().equals(cursor.getString( cursor.getColumnIndex(DbAdapter.KEY_EMAIL))) && edit_password.getText().equals(cursor.getColumnIndex(DbAdapter.KEY_PASSWORD))){		            				
		            	cursor.close();
		            	Intent intent;
		            	intent = new Intent(MainActivity.this, Home.class);
		            	startActivity(intent);
		            }
		            else{
		            	//stampare messaggio email o password errate
		            	Log.d("login","email o password non corrispondono");
		            }
				}
			});
			
			Button registra=(Button)findViewById(R.id.registra_button);
			registra.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent;
					intent = new Intent(MainActivity.this, Registra.class);
					startActivity(intent);
				}
			});
        }
        
        else{
        	Log.d("if", "condizione falsa");
        	cursor.close();
            Log.d("prova","chiudo il db");
            dbHelper.close();
        	Intent intent;
			intent = new Intent(MainActivity.this, Home.class);
			startActivity(intent);
        }
        
        //il bd va chiuso dopo il cursore
        /*
        cursor.close();
        Log.d("prova","chiudo il db");
        dbHelper.close();
        */
        Log.d("prova","andata");
        
        //startManagingCursor(cursor);
	    //controllo se dati utente per login sono già presenti nel database 
		
			
			
	} 
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
			
		}
	}

}
