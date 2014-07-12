package it.unisa.guessthecover;

import database.DbAdapter;
import database.UserFunctions;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends Activity {
	private DbAdapter dbHelper; 
	private Cursor cursor;
	private int multiplier=1;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		Log.d("home", "sono appena entrato nella home");  
		 
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        Intent intent=getIntent(); // l'intent di questa activity
        String pkg=getPackageName();
        int mult=intent.getIntExtra(pkg+".myNewMultiplier",1); //prendi moltiplicatore
        multiplier=mult;
        
        Log.d("Home","sto per fare la roba sul bd");
	    dbHelper = new DbAdapter(getApplicationContext());
	    Log.d("Home","apro il bd");
	    dbHelper.open();
	    Log.d("Home","riempio il cursore");
        cursor = dbHelper.fetchAllContacts();
        
        Log.d("Home","vado sul primo");
        cursor.moveToFirst();
        Log.d("Home","prendo il nick");
        String nick = cursor.getString( cursor.getColumnIndex(DbAdapter.KEY_NAME) );
        Log.d("Home","prendo il punteggio");
        int point = cursor.getInt(cursor.getColumnIndex(DbAdapter.KEY_POINT));
        
        cursor.close();
        Log.d("Home","chiudo il db");
        dbHelper.close();
        
        Log.d("Home","cerco l'id del nick");
        TextView txt = (TextView)findViewById(R.id.user);
        Log.d("Home","scrivo il nick");
        txt.setText(nick);  
        
        txt = (TextView)findViewById(R.id.punteggio);
        txt.setText(point + " punti");
        
        Button button1=(Button)findViewById(R.id.gioca);
        button1.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		Intent intent;
       			intent = new Intent(Home.this, Gioco.class);
       			
       			String pkg=getPackageName();
       			intent.putExtra(pkg+".myMultiplier", multiplier); //invio moltiplicatore
       			startActivity(intent);
       			finish();
               }
        });
        ///
        Button button2=(Button)findViewById(R.id.classifica);
        button2.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		setContentView(R.layout.activity_web);
        		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        		boolean connessioneDisponibile=false;
        		if(cm!=null && cm.getActiveNetworkInfo()!=null){
        			//controllo disponibilità di rete
        			connessioneDisponibile= cm.getActiveNetworkInfo().isConnectedOrConnecting();
        	    	}    		 
        	    if(connessioneDisponibile){
        	    	//
        	    	Intent intent;
            		
           			intent = new Intent(Home.this, Classifica.class);
           			
           			String pkg=getPackageName();
           			intent.putExtra(pkg+".myMultiplier", multiplier); //invio moltiplicatore
           			startActivity(intent);
           			finish();
                   
        	    }
        	    else{
        	    	creaToast("Connessione non disponibile");
        	    }
               }
        });
	 }
	private void creaToast(String s){
		Toast toast=Toast.makeText(this,s,Toast.LENGTH_LONG);
    	toast.show();
	}
	/*public void onBackPressed() {
		finish();
		
	}*/
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
