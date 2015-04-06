package com.example.myliftinglog;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.CalendarView.OnDateChangeListener;


//Opens the calendar activity
public class Calendar extends ActionBarActivity {

	DBAdapter myDb;
	Long ID;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_calendar);
	        
	        //Start Calendar
	        CalendarView calendar = (CalendarView) findViewById(R.id.calendar);
	        calendar.setShowWeekNumber(false);
	        calendar.setFirstDayOfWeek(2);
	        calendar.setOnDateChangeListener(new OnDateChangeListener() {
	
	            @Override
	            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
	            	
	            	//When date is pressed, get the date
	            	 String year1 = Integer.toString(year);
					 String month1 = Integer.toString(month+1);
					 String day1 = Integer.toString(day);
					 String date = month1 + "/" + day1 + "/" + year1;
					 
					 
	            	 openDB();
	            	 
	            	 //If the workout doesn't exist, insert it into the database, and start the activity
	            	 if( myDb.ifExists(date) == false){
	            		 
	            		 ID = myDb.insertWorkout(date);           		 
	            		// Cursor row = myDb.getRowID(date);
	            		 Intent intent = new Intent(getBaseContext(), Workout.class);
	 	            	 intent.putExtra("WorkoutID", ID);
	 	            	 intent.putExtra("date", date);
			           	 startActivity(intent);
	            	 }
	            	 //If the workout already does exist, then don't create a new insert a new row but start the 
	            	 //activity with that certain day
	            	 else{
	 	            	Intent intent = new Intent(getBaseContext(), Workout.class);	            	 
	 	            	intent.putExtra("WorkoutID", myDb.getWorkoutID(date));
	            		intent.putExtra("date", date);
	 	            	startActivity(intent);
	            	 }
					 closeDB();  	
	            }
	        }); 
	 }	 
	 //When the back button is pressed in the calendar activity, go to the main menu
		public void onBackPressed() {				
				Intent i = new Intent(Calendar.this, MainMenu.class);
				startActivity(i);
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
