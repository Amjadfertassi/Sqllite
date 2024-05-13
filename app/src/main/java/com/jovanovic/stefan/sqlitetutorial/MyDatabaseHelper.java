package com.jovanovic.stefan.sqlitetutorial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;

// Database helper class for managing SQLite database operations related to a book library.
class MyDatabaseHelper extends SQLiteOpenHelper {

    // Context of the application using the database.
    private Context context;

    // Database information constants.
    private static final String DATABASE_NAME = "BookLibrary.db";
    private static final int DATABASE_VERSION = 1;

    // Table information constants.
    private static final String TABLE_NAME = "my_library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "book_title";
    private static final String COLUMN_AUTHOR = "book_author";
    private static final String COLUMN_PAGES = "book_pages";

    // Constructor for creating an instance of the database helper.
    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Method called when the database is created for the first time.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL query to create the 'my_library' table with specified columns.
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_PAGES + " INTEGER);";
        db.execSQL(query);
    }

    // Method called when the database needs to be upgraded.
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop the existing table and call onCreate to recreate it.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Method to add a new book to the database.
    void addBook(String title, String author, int pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_PAGES, pages);

        // Insert the values into the 'my_library' table.
        long result = db.insert(TABLE_NAME, null, cv);

        // Display a toast message based on the success or failure of the operation.
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to retrieve all data from the 'my_library' table.
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        // Execute the query and return the resulting cursor.
        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    // Method to update an existing book in the database.
    void updateData(String row_id, String title, String author, String pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_PAGES, pages);

        // Update the specified row in the 'my_library' table based on the row ID.
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});

        // Display a toast message based on the success or failure of the operation.
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to delete a specific row from the 'my_library' table.
    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});

        // Display a toast message based on the success or failure of the operation.
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to delete all data from the 'my_library' table.
    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();

        // Execute an SQL query to delete all rows from the 'my_library' table.
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
