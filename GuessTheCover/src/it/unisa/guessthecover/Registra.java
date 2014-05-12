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

public class Registra extends Activity {
	
	private DbAdapter dbHelper = new DbAdapter(this);
	
	 @Override
     protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_registra);
           
           Log.d("registra","bottone non ancora premuto");
           
           Button registra=(Button)findViewById(R.id.registra_button);
           registra.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.d("registra","bottone premuto");
					//leggo dal form
					final EditText edit_email = (EditText)findViewById(R.id.email_form);
		            final EditText edit_nickname = (EditText)findViewById(R.id.nickname_form);
		            final EditText edit_password = (EditText)findViewById(R.id.password_form);
		            final EditText edit_confpass = (EditText)findViewById(R.id.confpass_form);
		            int point=0;
		            Log.d("registra","letto dal form");
					//leggi db online
		            
		            //scrivi db online
		            
		            //scrivi db locale
		            Log.d("registra","dbHelper.open();");
		            dbHelper.open();
		            Log.d("registra","dbHelper.createContact(edit_email.getText().toString(), edit_nickname.getText().toString(), edit_password.getText().toString(), point);");
		            dbHelper.createContact(edit_email.getText().toString(), edit_nickname.getText().toString(), edit_password.getText().toString(), point);
		            Log.d("registra","dbHelper.close();");
		            dbHelper.close();
		            
		            Log.d("registra","intent MainActivity");
		            Intent intent;
		            intent = new Intent(Registra.this, MainActivity.class);
		            startActivity(intent);
		            
				}
			});
     }
}
