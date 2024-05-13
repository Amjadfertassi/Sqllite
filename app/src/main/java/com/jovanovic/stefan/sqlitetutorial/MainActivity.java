// Declare the package for the current class
package com.jovanovic.stefan.sqlitetutorial;

// Import necessary Android packages
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

// Define the MainActivity class, which represents the main screen of the application
public class MainActivity extends AppCompatActivity {

    // Declare UI elements and variables
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageview;
    TextView no_data;
    MyDatabaseHelper myDB;
    ArrayList<String> book_id, book_title, book_author, book_pages;
    CustomAdapter customAdapter;

    // This method is called when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for this activity (activity_main.xml)
        setContentView(R.layout.activity_main);

        // Initialize UI elements by finding their corresponding views in the layout
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        empty_imageview = findViewById(R.id.empty_imageview);
        no_data = findViewById(R.id.no_data);

        // Set a click listener on the "Add" button to open the AddActivity
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        // Initialize the database helper and arrays to store book data
        myDB = new MyDatabaseHelper(MainActivity.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();

        // Retrieve data from the database and store it in arrays
        storeDataInArrays();

        // Initialize the custom adapter and set it to the RecyclerView
        customAdapter = new CustomAdapter(MainActivity.this, this, book_id, book_title, book_author, book_pages);
        recyclerView.setAdapter(customAdapter);

        // Set the layout manager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    // This method is called when an activity launched by this one finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If the requestCode is 1, recreate the activity to refresh the data
        if (requestCode == 1) {
            recreate();
        }
    }

    // Retrieve data from the database and store it in arrays
    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();

        // If there is no data, show appropriate views; otherwise, populate arrays
        if (cursor.getCount() == 0) {
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }

            // Hide empty views
            empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
    }

    // Create options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Handle options menu item selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_all) {
            // Display a confirmation dialog when the "Delete All" option is selected
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    // Display a confirmation dialog for deleting all data
    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all Data?");

        // Set positive button action to delete all data and refresh the activity
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                myDB.deleteAllData();

                // Refresh Activity
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Set negative button action (do nothing on cancel)
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing
            }
        });

        // Create and show the AlertDialog
        builder.create().show();
    }
}
