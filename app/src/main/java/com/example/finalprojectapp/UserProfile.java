package com.example.finalprojectapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UserProfile extends AppCompatActivity {
    protected static String CURRENT_BOOK_ID;

    @Override
    public void onBackPressed() {
        if (MainActivity.backAllowed) {
            super.onBackPressed();
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // on search btn click, go to search bar page
        ImageButton search_btn = findViewById(R.id.searchButton);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, SearchBar.class);
                startActivity(intent);
            }
        });

        // on setting btn click, go to setting page

        ImageButton setting_btn = findViewById(R.id.settingButton);

        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, AppSettings.class);
                startActivity(intent);
            }
        });

        Log.d("User Profile", "trying image");

        TextView welcome_user = findViewById(R.id.welcome_txt);

        welcome_user.setText("Welcome " + Login.CURRENT_FIRST_NAME + "!");

        // get loved books list
        int loved_books = findViewById(R.id.loved_books_layout).getId();
        addBookList(loved_books, "loved");

        // get liked books
        int liked_books = findViewById(R.id.liked_books_layout).getId();
        addBookList(liked_books, "liked");

        // get disliked
        int disliked_books = findViewById(R.id.disliked_books_layout).getId();
        addBookList(disliked_books, "disliked");

        // get want to read
        int wanted_books = findViewById(R.id.want_books_layout).getId();
        addBookList(wanted_books, "want to read");

        // set book total
        String books_read = MainActivity.USER_BOOK_DB.getTotalBooksRead(Login.CURRENT_USER);

        TextView total_books = findViewById(R.id.bookRead_total);
        total_books.setText(books_read);


    }

    @Override
    public void onRestart() {
        super.onRestart();

        setContentView(R.layout.activity_user_profile);

        // on search btn click, go to search bar page
        ImageButton search_btn = findViewById(R.id.searchButton);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, SearchBar.class);
                startActivity(intent);
            }
        });

        // on setting btn click, go to setting page

        ImageButton setting_btn = findViewById(R.id.settingButton);

        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, AppSettings.class);
                startActivity(intent);
            }
        });

        Log.d("User Profile", "trying image");

        TextView welcome_user = findViewById(R.id.welcome_txt);

        welcome_user.setText("Welcome " + Login.CURRENT_FIRST_NAME + "!");

        // get loved books list
        int loved_books = findViewById(R.id.loved_books_layout).getId();
        addBookList(loved_books, "loved");

        // get liked books
        int liked_books = findViewById(R.id.liked_books_layout).getId();
        addBookList(liked_books, "liked");

        // get disliked
        int disliked_books = findViewById(R.id.disliked_books_layout).getId();
        addBookList(disliked_books, "disliked");

        // get want to read
        int wanted_books = findViewById(R.id.want_books_layout).getId();
        addBookList(wanted_books, "want to read");

        // set book total
        String books_read = MainActivity.USER_BOOK_DB.getTotalBooksRead(Login.CURRENT_USER);

        TextView total_books = findViewById(R.id.bookRead_total);
        total_books.setText(books_read);
    }

    private void addBookList(int list_id, String list) {

        LinearLayout book_layout = findViewById(list_id);

        LinearLayout.LayoutParams book_params = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1));
        book_params.setMargins(30, 20, 30, 30);

        ArrayList<ArrayList<String>> book_info = MainActivity.USER_BOOK_DB.getList(list);
        TableLayout[] book_table = new TableLayout[book_info.size()];
        TableRow[] book_table_rows = new TableRow[3* book_info.size()];
        ImageButton[] book_images = new ImageButton[book_info.size()];


        Log.d("Search bar", "adding books");

        for (int i = 0; i < book_info.size(); i++) {
            Log.d("Search bar", "cycling through book table");
            String title = book_info.get(i).get(0);
            String author = book_info.get(i).get(1);
            String image_id = book_info.get(i).get(2);

            Log.d("Search bar", title + ", " + author +", "+ image_id);

            String book_image = MainActivity.BOOK_IMAGE_DB.getImage(image_id);

            String uri = "@drawable/" + book_image;

            Log.d("Search bar", "getting image at " + uri);

            int imageResource = getResources().getIdentifier(uri, null, getPackageName());

            Log.d("Search bar", "adding table");


            book_table[i] = new TableLayout(getApplicationContext());
            book_table[i].setLayoutParams(book_params);



            book_table_rows[i] = new TableRow(getApplicationContext());
            book_table_rows[i+1] = new TableRow(getApplicationContext());
            book_table_rows[i+2] = new TableRow(getApplicationContext());



            book_table_rows[i].setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 0));
            book_table_rows[i+1].setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 0));
            book_table_rows[i+2].setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 0));

            Log.d("Search bar", "adding rows");

            // set book image row
            // Load a bitmap from the drawable folder
            Bitmap bMap = BitmapFactory.decodeResource(getResources(),imageResource);
            // Resize the bitmap to 150x100 (width x height)
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 450, 500, true);

            // set image button to go to book page
            book_images[i] = new ImageButton(this);
            book_images[i].setImageBitmap(bMapScaled);
            book_images[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // save current book_id
                    UserProfile.CURRENT_BOOK_ID = image_id;
                    Intent intent = new Intent(UserProfile.this, BookPage.class);
                    startActivity(intent);
                }
            });
            book_table_rows[i].addView(book_images[i]);

            Log.d("Book image ", String.valueOf(book_images[i].getId()));


            // set title row
            TextView book_title = new TextView(this);
            book_title.setText(title);
            book_table_rows[i+1].addView(book_title);

            // set author row
            TextView book_author = new TextView(this);
            book_author.setText(author);
            book_table_rows[i+2].addView(book_author);



            book_layout.addView(book_table[i]);
            book_table[i].addView(book_table_rows[i]);
            book_table[i].addView(book_table_rows[i+1]);
            book_table[i].addView(book_table_rows[i+2]);



        }
    }
}