package com.jovanovic.stefan.sqlitetutorial;
// Import necessary Android packages and libraries
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// CustomAdapter class extending RecyclerView.Adapter with a specified view holder
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    // Declare member variables for the activity, context, and ArrayLists holding book information
    private Context context;
    private Activity activity;
    private ArrayList book_id, book_title, book_author, book_pages;

    // Constructor for the CustomAdapter class
    CustomAdapter(Activity activity, Context context, ArrayList book_id, ArrayList book_title, ArrayList book_author,
                  ArrayList book_pages){
        this.activity = activity;
        this.context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_pages = book_pages;
    }

    // Create and return a new MyViewHolder when needed
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each list item and return the corresponding ViewHolder
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    // Bind data to the views inside the ViewHolder
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        // Set text values for book ID, title, author, and pages
        holder.book_id_txt.setText(String.valueOf(book_id.get(position)));
        holder.book_title_txt.setText(String.valueOf(book_title.get(position)));
        holder.book_author_txt.setText(String.valueOf(book_author.get(position)));
        holder.book_pages_txt.setText(String.valueOf(book_pages.get(position)));

        // Set an OnClickListener for the entire item view in the RecyclerView
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the UpdateActivity and pass book information as extras
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(book_id.get(position)));
                intent.putExtra("title", String.valueOf(book_title.get(position)));
                intent.putExtra("author", String.valueOf(book_author.get(position)));
                intent.putExtra("pages", String.valueOf(book_pages.get(position)));
                // Start the UpdateActivity with startActivityForResult, expecting a result
                activity.startActivityForResult(intent, 1);
            }
        });
    }


    // Return the total number of items in the data set
    @Override
    public int getItemCount() {
        return book_id.size();
    }

    // ViewHolder class for holding the views of each item in the RecyclerView
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView book_id_txt, book_title_txt, book_author_txt, book_pages_txt;
        LinearLayout mainLayout;

        // Constructor for the ViewHolder
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize TextViews and LinearLayout from the layout
            book_id_txt = itemView.findViewById(R.id.book_id_txt);
            book_title_txt = itemView.findViewById(R.id.book_title_txt);
            book_author_txt = itemView.findViewById(R.id.book_author_txt);
            book_pages_txt = itemView.findViewById(R.id.book_pages_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);

            // Animate the mainLayout using translate animation
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
