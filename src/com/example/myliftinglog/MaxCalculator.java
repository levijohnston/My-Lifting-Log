package com.example.myliftinglog;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MaxCalculator extends ActionBarActivity{

	//One rep max calculator
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_max_calculator);
		
		final EditText weight1 = (EditText) findViewById(R.id.editText1);
		final EditText reps1 = (EditText) findViewById(R.id.editText2);
		final EditText max = (EditText) findViewById(R.id.editText3);
		Button calculate = (Button) findViewById(R.id.button1);
		
		calculate.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub	
				
				
				if(weight1.getText().toString().matches("") || reps1.getText().toString().matches("")){
					Toast.makeText(getBaseContext(),"Please enter correct amount of weight and reps!", 
	        				Toast.LENGTH_SHORT).show();

				}
				else{
					Float weight = Float.parseFloat(weight1.getText().toString());
					int reps = Integer.parseInt(reps1.getText().toString());
					if (reps > 20){
						Toast.makeText(getBaseContext(),"Please enter less than 20 reps", 
		        				Toast.LENGTH_SHORT).show();
					}
					else if (reps == 0){
						Toast.makeText(getBaseContext(),"Please enter valid number of reps", 
		        				Toast.LENGTH_SHORT).show();
					}
					//Perform calculation if there is a valid number of reps
					else{
						double repMax = weight/(1.0278-(0.0278*reps));
						
						String max1 = String.valueOf(repMax);
						max.setText(String.format("%.2f",repMax));
					}
				}
				
				
			}
			
			
		});


		
		
		

		

	}

}
