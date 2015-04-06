package com.example.myliftinglog;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddExercise extends ActionBarActivity {
	
	DBAdapter db = new DBAdapter(this);
	String date1;
	Long WorkoutID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_exercise);
		
		Button add = (Button) findViewById(R.id.button1);
		
		date1 = (String)getIntent().getStringExtra("date");
		

	    WorkoutID = (Long)getIntent().getLongExtra("ID", 0);
		Log.d("EXERCISE ID ===", Long.toString(WorkoutID));
		
		add.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final EditText exercise = (EditText) findViewById(R.id.editExercise);
				EditText weight = (EditText) findViewById(R.id.editWeight);
				EditText reps = (EditText) findViewById(R.id.editReps);
				EditText sets = (EditText) findViewById(R.id.editSets);
				EditText notes = (EditText) findViewById(R.id.editNotes);
				
				//notes.setMovementMethod(new ScrollingMovementMethod());


				

				
				try {
					db.open();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 int position = 0;
			 long exerciseID = db.insertExercise(exercise.getText().toString(), weight.getText().toString(), reps.getText().toString(), 
					 sets.getText().toString(), notes.getText().toString(), WorkoutID);
			 
				Log.d("CHILD ID", String.valueOf(exerciseID));
				Toast.makeText(getBaseContext(),"Exercise Added!", 
        				Toast.LENGTH_SHORT).show();

			 
			 db.close();
			 
				Intent intent = new Intent(AddExercise.this, Workout.class);
				intent.putExtra("ExerciseId", exercise.getText().toString());
				intent.putExtra("childID", exerciseID);
				Log.d("EXERCISE NAME", exercise.getText().toString());
				intent.putExtra("date", date1);
            	intent.putExtra("WorkoutID", WorkoutID);
            	
            	
				startActivity(intent);
				
				
			}
			
		});
	}
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

	
    @Override
    public void onBackPressed() {
    	
    	Intent intent = new Intent(getBaseContext(), Calendar.class);
		startActivity(intent);

    }
}
