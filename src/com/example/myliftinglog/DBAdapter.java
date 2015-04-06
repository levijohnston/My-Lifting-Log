package com.example.myliftinglog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	//Rows for workout table
	public static final String KEY_ROWID = "_id";
	public static final String KEY_DATE = "date";
	
	//Rows for exercise table
	public static final String KEY_ROWID_CHILD = "_id";
	public static final String KEY_ROWID_PARENT = "parentId";
	public static final String KEY_EXERCISE = "name";
	public static final String KEY_WEIGHT = "weight";
	public static final String KEY_REPS = "reps";
	public static final String KEY_SETS = "sets";
	public static final String KEY_NOTES = "notes";
	
	
	
	

	//Create name and both database tables
	private static final String DATABASE_NAME = "WorkoutDB";
	private static final String DATABASE_TABLE = "workout";
	private static final String DATABASE_TABLE2 = "exercise";
	
	private static final int DATABASE_VERSION = 5;
	
	//Initialize the tables
	private static final String DATABASE_CREATE_SQL =	
	"CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + " (" + KEY_ROWID +  " INTEGER PRIMARY KEY AUTOINCREMENT, "
	+ KEY_DATE + " TEXT" + ")";
	
	
	private static final String DATABASE_CREATE_SQL2 = 
		"CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE2 + " (" + KEY_ROWID_CHILD +  " INTEGER PRIMARY KEY AUTOINCREMENT, "
		+ KEY_ROWID_PARENT + " LONG," + KEY_EXERCISE + " TEXT,"
		+ KEY_WEIGHT + " TEXT," + KEY_REPS + " TEXT," + KEY_SETS + " TEXT," 
		+ KEY_NOTES + " TEXT " + 
		 ")";	
	
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	public DBAdapter(Context ctx){
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
		
	}
	
	public DBAdapter open(){
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		DBHelper.close();
	}
	
	//Insert a workout into the workout table
	public long insertWorkout(String date){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_DATE, date);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	//Insert an exercise into the exercise table
	public long insertExercise(String name, String weight, String sets, String reps, String notes, Long id){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_EXERCISE, name);
		initialValues.put(KEY_WEIGHT, weight);
		initialValues.put(KEY_REPS, reps);
		initialValues.put(KEY_SETS, sets);
		initialValues.put(KEY_NOTES, notes);
		initialValues.put(KEY_ROWID_PARENT, id);

		
		return db.insert(DATABASE_TABLE2, null, initialValues);
	}
	
	//Delete a workout
	public boolean deleteRowWorkout(long rowId){
		return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}
	
	//Delete an exercise
	public boolean deleteRowExercise(long rowId){
		return db.delete(DATABASE_TABLE2, KEY_ROWID + "=" + rowId, null) > 0;
	}
	

	
	//Query to get the workout Id when given the date
	public Long getWorkoutID(String date){
		Cursor c = db.rawQuery("SELECT * from workout where " + KEY_DATE + " = '" + date + "'", null);
	    int count= c.getCount();
	   
	    c.moveToFirst();
	    int id = Integer.parseInt(c.getString(0));
	    long id2 = id;
	    return id2 ;
	}
	

	//Check to see if a workout exists, given the date
	public boolean ifExists(String fieldValue) {
	    String Query = "Select * from workout where " + KEY_DATE + " = '" + fieldValue + "'";
	    Cursor cursor = db.rawQuery(Query, null);
	    if (cursor.moveToFirst()) {
	    	Log.d("EXISTS!", "THIS ROW EXISTS");
	    	Log.d(null, cursor.getString(1));
        	return true;
	    } else {
	    	 Log.d("DOESN'T EXISTS!", "THIS ROW DOESNT EXIST");
	    	 
      	   return false;
	    }
	            	
	           
	}
	
	//Get all the rows of an exercise when the given workout ID and exercise ID match
	public Cursor getAllRowsExercise(long id){
		Cursor c = db.rawQuery("SELECT * from exercise WHERE " + KEY_ROWID_PARENT + " =  '" + id + "'", null);
	    int count= c.getCount();
	    c.moveToFirst();
		return c;
	}
	
	//Get specific workout when given the id
	public Cursor getWorkout(long rowId){
		Cursor mCursor = 
				db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_DATE},
				KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
		
	}
	
	//Get specific exercise when given the id
	public Cursor getExercise(long rowId){
		Cursor mCursor = 
				db.query(true, DATABASE_TABLE2, new String[] {KEY_ROWID_CHILD, KEY_EXERCISE, KEY_WEIGHT, KEY_REPS, KEY_SETS, KEY_NOTES},
				KEY_ROWID_CHILD + "=" + rowId, null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
		
	}
	
	//Update a workout
	public boolean updateWorkout(long rowId, String date) {
		String where = KEY_ROWID + "=" + rowId;
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_DATE, date);
		
		// Insert it into the database.
		return db.update(DATABASE_TABLE, newValues, where, null) != 0;
	}
	
	//Update an exercise
	public boolean updateExercise(long rowId, String exercise, String weight, String sets, String reps, String notes, long id) {
		String where = KEY_ROWID_CHILD + "= '" + rowId + "'";
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_EXERCISE, exercise);
		newValues.put(KEY_WEIGHT, weight);
		newValues.put(KEY_REPS, reps);
		newValues.put(KEY_SETS, sets);
		newValues.put(KEY_NOTES, notes);
		newValues.put(KEY_ROWID_PARENT, id);
		//newValues.put(KEY_POSITION, position);
		
		// Insert it into the database.
		return db.update(DATABASE_TABLE2, newValues, where, null) != 0;
	}
	
	//Delete all workouts
	public void deleteAllWorkout() {
			db = DBHelper.getWritableDatabase();
		    db.execSQL("delete from "+ DATABASE_TABLE);		    
		    //db.execSQL("delete from sqlite_sequence where name='workout'");
		    db.close();
	}
	
	//Delete all exercise
	public void deleteAllExercise() {
		db = DBHelper.getWritableDatabase();
	    db.execSQL("delete from " + DATABASE_TABLE2);
	   // db.execSQL("delete from sqlite_sequence where name='exercise'");
	    db.close();
}
	

	
	private static class DatabaseHelper extends SQLiteOpenHelper{
		DatabaseHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(DATABASE_CREATE_SQL);
			db.execSQL(DATABASE_CREATE_SQL2);
		
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE2);
			onCreate(db);
		}	
	}
}
