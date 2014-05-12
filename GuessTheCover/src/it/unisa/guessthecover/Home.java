package it.unisa.guessthecover;

import database.DbAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Home extends Activity {
	private DbAdapter dbHelper; 
	private Cursor cursor;
	
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		Log.d("home", "sono appena entrato nella home");  
		 
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        
        Log.d("Home","sto per fare la roba sul bd");
	    dbHelper = new DbAdapter(getApplicationContext());
	    Log.d("Home","apro il bd");
	    dbHelper.open();
	    Log.d("Home","riempio il cursore");
        cursor = dbHelper.fetchAllContacts();
        
        Log.d("Home","vado sul primo");
        cursor.moveToFirst();
        Log.d("Home","prendo il nick");
        String nick = cursor.getString( cursor.getColumnIndex(DbAdapter.KEY_NICKNAME) );
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
       			
       			startActivity(intent);
               }
        });
	 }
}
