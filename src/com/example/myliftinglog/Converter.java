package com.example.myliftinglog;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Converter extends ActionBarActivity {

	public boolean switched = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_converter);
		
		//
		final EditText top = (EditText) findViewById(R.id.editText1);
		final EditText bottom = (EditText) findViewById(R.id.editText2);
		final TextView bottom2 = (TextView) findViewById(R.id.textView4);
		final TextView top2 = (TextView) findViewById(R.id.textView2);
		
		bottom2.setText("kg");
		top2.setText("lb");
		
		
		Button convert = (Button) findViewById(R.id.button1);
		ImageButton button = (ImageButton) findViewById(R.id.imageButton1);
			
		//If the switch button is clicked, switch the kg and the lb
		button. setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(switched == false){
					switched = true;
					top2.setText("lb");
					bottom2.setText("kg");
				}
				
				else if(switched == true){
					switched = false;
					top2.setText("kg");
					bottom2.setText("lb");
				}
				
		}	
	});

		
		convert.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub	
				if(top.getText().toString().matches("")){
					Toast.makeText(getBaseContext(),"Please enter correct amount values!", 
	        				Toast.LENGTH_SHORT).show();	
				}
				else{
					//if the button is switched, convert from lb to kg
					if (switched == true){
						String pounds = top.getText().toString();
						Float lb = Float.parseFloat(pounds);
						Float kg = (float) (lb/2.20462);
						bottom.setText(kg.toString());
	
					}
					//if the button is not switched, convert from kg to lb
					else if(switched == false){
						String kilograms = top.getText().toString();
						Float a = Float.parseFloat(kilograms);
						Float b = (float) (a*2.20462);
						bottom.setText(b.toString());
					}		
				}			
			}
		});
	}

	
	
}
