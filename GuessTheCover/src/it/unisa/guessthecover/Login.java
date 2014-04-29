package it.unisa.guessthecover;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Login extends Activity {
	 @Override
     protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_login);
           
           Button button1=(Button)findViewById(R.id.button1);
           button1.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
               	Intent intent;
       			intent = new Intent(Login.this, Gioco.class);
       			
       			startActivity(intent);
               }
           });
     }
}
