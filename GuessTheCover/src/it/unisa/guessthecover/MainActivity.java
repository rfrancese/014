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
		/*
		Log.d("main","sto per chiamare il login");
		Intent intent;
		intent = new Intent(MainActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
		*/
		
		
		Log.d("prova","sto per fare la roba sul bd");
	    dbHelper = new DbAdapter(getApplicationContext());
	    Log.d("prova","apro il bd");
	    dbHelper.open();
	    Log.d("prova","riempio il cursore");
        cursor = dbHelper.fetchAllContacts();
        
        
        Log.d("prova","provo a stampare la roba nel cursore");
        
        while ( cursor.moveToNext() ) {
            String contactID = cursor.getString( cursor.getColumnIndex(DbAdapter.KEY_ID) );
            Log.d("TAG", "KEY_ID = " + contactID);
            String email = cursor.getString( cursor.getColumnIndex(DbAdapter.KEY_EMAIL) );
            Log.d("TAG", "email = " + email);
            String nick = cursor.getString( cursor.getColumnIndex(DbAdapter.KEY_NAME) );
            Log.d("TAG", "nickname = " + nick);
        } 
		
        Log.d("if", "sto per entrare nell if");
        
        if(cursor.moveToFirst()==false){
        	Log.d("if", "condizione vera");
        	cursor.close();
            dbHelper.close();
        	
            Log.d("main","sto per chiamare il login");
    		Intent intent;
    		intent = new Intent(MainActivity.this, LoginActivity.class);
    		startActivity(intent);
    		finish();
        }
        
        else{
        	Log.d("if", "condizione falsa");
        	cursor.close();
            Log.d("prova","chiudo il db");
            dbHelper.close();
        	Intent intent;
			intent = new Intent(MainActivity.this, Home.class);
			startActivity(intent);
			finish();
        }
        
        //il bd va chiuso dopo il cursore
        /*
        cursor.close();
        Log.d("prova","chiudo il db");
        dbHelper.close();
        */
		/*
        Log.d("prova","andata");
        
        //startManagingCursor(cursor);
	    //controllo se dati utente per login sono già presenti nel database 
		*/
			
			
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
