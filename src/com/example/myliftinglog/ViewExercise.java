package com.example.myliftinglog;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ViewExercise extends ActionBarActivity {
	
	DBAdapter myDb;
	String date;
	Long ID;
	Long exerciseId;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_exercise);
		
		final EditText exercise = (EditText) findViewById(R.id.editExercise);
		final EditText weight = (EditText) findViewById(R.id.editWeight);
		final EditText reps = (EditText) findViewById(R.id.editReps);
		final EditText sets = (EditText) findViewById(R.id.editSets);
		final EditText notes = (EditText) findViewById(R.id.editText1);
		
		Button update = (Button) findViewById(R.id.button1);
   	    ID = (Long)getIntent().getLongExtra("ID2", 0);
   	 	date = (String)getIntent().getStringExtra("date2");
   	 	
    	final String name = (String)getIntent().getStringExtra("ExerciseName");		
    	exerciseId = (Long)getIntent().getLongExtra("childID", 0);

		openDB();
		
		//Get the exercise and its values, and set the text fields equal to them
		Cursor cursor = myDb.getExercise(exerciseId);		
		final String exercise1 = cursor.getString(1);
		final String weight1 = cursor.getString(2);
		final String reps1 = cursor.getString(3);
		final String sets1 = cursor.getString(4);
		final String notes1 = cursor.getString(5);
		
		exercise.setText(exercise1);
		weight.setText(weight1);
		reps.setText(reps1);
		sets.setText(sets1);
		notes.setText(notes1);
		
		update.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub		
				try {
					openDB();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 
				final String updateName =  exercise.getText().toString();
				final String updateWeight =  weight.getText().toString();
				final String updateReps =  reps.getText().toString();
				final String updateSets =  sets.getText().toString();
				final String updateNotes =  notes.getText().toString();
				
				//Update the exercise with the new values
				boolean cursor1 = myDb.updateExercise(exerciseId, updateName, updateWeight, updateSets, updateReps, updateNotes, ID);
				Intent i = new Intent(ViewExercise.this, Workout.class);
				i.putExtra("date", date);
				i.putExtra("WorkoutID", ID);
				startActivity(i);
			}			
		});
	}
	
	//Back button pressed method
	@Override
	public void onBackPressed() {
		Intent i = new Intent(ViewExercise.this, Workout.class);
		i.putExtra("date", date);
		i.putExtra("WorkoutID", ID);
		startActivity(i);
	}
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.exercise_action, menu);
        
       
        return true;
    }
    
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
   	 	switch (item.getItemId()) {
		
			
			//Remove exercises
			case R.id.remove_icon:
				Log.d("DELETED", "DELETED");
				final AlertDialog.Builder builder = new AlertDialog.Builder(ViewExercise.this);
				builder.setMessage("Are you sure want to delete this exercise?");

				//Dialog box
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					     // User clicked OK button
						boolean delete = myDb.deleteRowExercise(exerciseId);
						Intent i = new Intent(ViewExercise.this, Workout.class);
						i.putExtra("date", date);
						i.putExtra("WorkoutID", ID);
						Toast.makeText(getBaseContext(),"Exercise Deleted!", 
		        				Toast.LENGTH_SHORT).show();
						startActivity(i);    
					      }
					 });
				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				   public void onClick(DialogInterface dialog, int id) {
				   	
				   }
				
				}); 
				AlertDialog dialog = builder.create();
				dialog.show();
   	 	}
        return super.onOptionsItemSelected(item);
    }
	 
	 //Open the database
    private void openDB() {
		myDb = new DBAdapter(this);
		myDb.open();
	}
    
    //Close database
    private void closeDB() {
		myDb.close();
	}
	

}
