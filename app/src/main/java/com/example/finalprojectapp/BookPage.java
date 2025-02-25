package com.example.finalprojectapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class BookPage extends AppCompatActivity {
    String add_book_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_page);

        Button add_book = findViewById(R.id.book_add_btn);

        // get current information
        ImageView book_image_view = findViewById(R.id.bookImageView);
        TextView book_title_txt = findViewById(R.id.book_title_txt);
        TextView book_author_txt = findViewById(R.id.book_author_txt);

        // hide error text
        TextView error_txt = findViewById(R.id.error_book_txt);
        error_txt.setVisibility(View.INVISIBLE);

        ArrayList<String> book_info = MainActivity.BOOK_DB.getBookbyImageID(UserProfile.CURRENT_BOOK_ID);
        String book_image_path = MainActivity.BOOK_IMAGE_DB.getImage(UserProfile.CURRENT_BOOK_ID);

        String book_title = book_info.get(0);
        String book_author = book_info.get(1);

        String uri = "@drawable/" + book_image_path;

        Log.d("Search bar", "getting image at " + uri);

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());

        // update image for current book
        // Load a bitmap from the drawable folder
        Bitmap bMap = BitmapFactory.decodeResource(getResources(),imageResource);
        // Resize the bitmap to 150x100 (width x height)
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 450, 500, true);
        book_image_view.setImageBitmap(bMapScaled);

        book_title_txt.setText(book_title);
        book_author_txt.setText(book_author);


        // spinner information
        Spinner spinner = findViewById(R.id.book_add_spinner);

        // check if book already in user lists
        String book_in_user = MainActivity.USER_BOOK_DB.checkBook(UserProfile.CURRENT_BOOK_ID);
        String[] book_list;

        if (book_in_user.equals("")) {
            book_list = new String[]{"add book", "liked", "loved", "disliked", "want to read"};
            add_book.setVisibility(View.VISIBLE);
        } else {
            book_list = new String[]{book_in_user};
            add_book.setVisibility(View.INVISIBLE);
        }


        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, book_list);
        spinner.setAdapter(spinnerArrayAdapter);

        if (spinner != null) {  //if a spinner exists.
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {   //Setting up a listener on the spinner
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {  //action for item selection.
                    String cur_select_list = spinner.getSelectedItem().toString();
                    add_book_list = cur_select_list;

                    Log.d("Spinner", String.format("=== Book list selected is: " +  cur_select_list));
                }

                public void onNothingSelected(AdapterView<?> parentView) {
                }
            });
        }



        add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (add_book_list.equals("add book")) {
                    error_txt.setVisibility(View.VISIBLE);
                } else {
                    MainActivity.USER_BOOK_DB.addInfo(Login.CURRENT_USER, book_title, book_author, add_book_list, UserProfile.CURRENT_BOOK_ID);
                }
            }
        });
    }
}