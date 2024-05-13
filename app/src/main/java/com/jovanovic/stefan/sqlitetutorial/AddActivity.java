// Declare the package for the current class
package com.jovanovic.stefan.sqlitetutorial;

// Import necessary Android packages
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

// Define the AddActivity class, which extends AppCompatActivity
public class AddActivity extends AppCompatActivity {

    // Declare variables for EditText and Button
    EditText title_input, author_input, pages_input;
    Button add_button;

    // This method is called when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for this activity (activity_add.xml)
        setContentView(R.layout.activity_add);

        // Initialize EditText and Button variables by finding their corresponding views in the layout
        title_input = findViewById(R.id.title_input);
        author_input = findViewById(R.id.author_input);
        pages_input = findViewById(R.id.pages_input);
        add_button = findViewById(R.id.add_button);

        // Set a click listener on the "Add" button
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an instance of the MyDatabaseHelper class, passing the context of the AddActivity
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);

                // Call the addBook method on the MyDatabaseHelper instance, passing the input values
                myDB.addBook(
                        title_input.getText().toString().trim(),
                        author_input.getText().toString().trim(),
                        Integer.valueOf(pages_input.getText().toString().trim())
                );
                finish();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager inputManager = (InputMethodManager) getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        return true ;
    }
}
