package com.example.myliftinglog;




import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


//Main Menu
public class MainMenu extends ActionBarActivity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        
        //Create ListView 
        ListView listview = (ListView) findViewById(R.id.listView1);
        String[] values = new String[] {"View Calendar", "Weight Converter", "One Rep Max Calculator"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        	
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				
				if(position == 0){
					Intent intent = new Intent(MainMenu.this, Calendar.class);
					startActivity(intent);
				}
				else if(position == 1){
					Intent intent = new Intent(MainMenu.this, Converter.class);
					startActivity(intent);
				}
				else if (position==2){
					Intent intent = new Intent(MainMenu.this, MaxCalculator.class);
					startActivity(intent);
				}
				
			}
 			
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	MenuInflater mif = getMenuInflater();
    	mif.inflate(R.menu.main_menu, menu);
    	return super.onCreateOptionsMenu(menu);
    }
    
    //Closing the app when the back button is pressed on Main Menu
	public void onBackPressed() {				
		final AlertDialog.Builder builder = new AlertDialog.Builder(MainMenu.this);
		builder.setMessage("Are you sure want to exit?");

		// Add the buttons
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) { 
			     // User clicked OK button
				int pid = android.os.Process.myPid();
		        android.os.Process.killProcess(pid);
		        //For some reason, doesn't always exit
		        System.exit(0);		      
			      }
			 });
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int id) {
		   	
		   }
		
		}); 
		AlertDialog dialog = builder.create();
		dialog.show();
	}   
}
