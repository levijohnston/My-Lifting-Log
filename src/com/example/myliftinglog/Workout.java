package com.example.myliftinglog;




import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Workout extends ActionBarActivity{

	DBAdapter myDb;
	String workoutId;
	String date1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout);
		
		//Get the date from the calendar date selected
		 date1 = (String)getIntent().getStringExtra("date");
		 TextView date = (TextView) findViewById(R.id.textView1);
		 date.setText(date1);
	
	    //Call the method
		populateListViewFromDB();		
	}
	
	//Filling the list view from the database
    private void populateListViewFromDB(){
    	
        try {
 			openDB();
 		} catch (Exception e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 			System.out.println("CANNOUT OPEN DATABASE");
 		}

      //Create cursor and simple cursor adapter to put the id and title into the listview
        openDB();
        
        ListView listView = (ListView) findViewById(R.id.listView1); 
        //Get the Workout ID
        final Long WorkoutID = (Long)getIntent().getLongExtra("WorkoutID", 0);  
        //Create cursor with all exercises
        final Cursor cursor = myDb.getAllRowsExercise(WorkoutID);
        closeDB();
        
        //Create Cursor adapter
        String[] from = new String []{myDb.KEY_EXERCISE, myDb.KEY_WEIGHT, myDb.KEY_SETS};
    	int[] to = new int[]{R.id.textView1, R.id.textView2, R.id.textView3};     	
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.item_layout, cursor, from, to, 0);
        listView.setAdapter(adapter);
        
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
 			@Override
 			public void onItemClick(AdapterView<?> parent, View viewClicked, 
 					int position, long idInDB) { 		
 					//Call method
 					onExerciseClick(idInDB, WorkoutID, date1);
 			}
         });   
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
	
    
    //On back button pressed, send to last activity and put extras
    @Override
    public void onBackPressed() {
    	String date2 = (String)getIntent().getStringExtra("date2");
   	 	Long ID = (Long)getIntent().getLongExtra("ID", 0);
   	 	
   	 	TextView date = (TextView) findViewById(R.id.textView1);
   	 	date.setText(date2);
   	 	
    	Intent intent = new Intent(getBaseContext(), Calendar.class);
    	intent.putExtra("date", date2);
		startActivity(intent);
		
   	 	
	   	
    }
    
    

    
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.workout_action, menu);
	        return true;
	    }
	 
	 @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	        int id = item.getItemId();
	   	 	switch (item.getItemId()) {

	        //If the add icon is pressed, start the next activity
	   	 	case R.id.add_icon:
	   	    	Long exerciseId = (Long)getIntent().getLongExtra("ExerciseId", 0);
		   	 	Long WorkoutID = (Long)getIntent().getLongExtra("WorkoutID", 0);
				String date1 = (String)getIntent().getStringExtra("date");
		   	 	
	   	 		Intent intent = new Intent(Workout.this,AddExercise.class);
	   	 		intent.putExtra("date", date1);
	   	 		intent.putExtra("ID", WorkoutID);
	   	 		
	   	 		startActivity(intent);
	   	 	
	   	 		
	        }
	        return super.onOptionsItemSelected(item);
	    }
	 
	 //If a certain exercise is clicked, start the View Exercise activity and send the values
	 public void onExerciseClick(long idInDB, long id, String date){

	    	
	    	Intent intent = new Intent(getBaseContext(), ViewExercise.class);
	    	Long exerciseID = (Long)getIntent().getLongExtra("childID", 0);
	    	String name = (String)getIntent().getStringExtra("ExerciseName");
	    	
			intent.putExtra("childID", idInDB);
	    	intent.putExtra("ExerciseName", name);
   	 		intent.putExtra("ID2", id);
   	 		intent.putExtra("date2", date);
	    	startActivity(intent);	
	    				


	 }

			

	
}
