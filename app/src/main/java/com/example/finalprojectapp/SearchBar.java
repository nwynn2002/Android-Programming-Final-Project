package com.example.finalprojectapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class SearchBar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);

        Fragment book_search_frag = new BookSearchFragment();
        Fragment book_feed_frag = new BookSearchFeed();
        Fragment author_search_frag = new AuthorSearchFragment();
        Fragment profile_search_frag;

        Log.d("Search bar", "in search bar");

        TextView search_bar_txt = findViewById(R.id.searchBarText);
        String search_txt = search_bar_txt.getText().toString();

        if (search_txt.equals("")) {
            Log.d("Search bar - general feed", "going to general feed");
            loadFragment(book_feed_frag);
        }

        Button book_search = findViewById(R.id.bookSearchButton);

        book_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Search Bar - Book Fragment", "==== Starting fragment ====");
                loadFragment(book_search_frag);
            }
        });

        Button author_search = findViewById(R.id.authorSearchButton);

        author_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Search Bar - Book Fragment", "==== Starting fragment ====");
                loadFragment(author_search_frag);
            }
        });



    }

    // remove fragment from current frame
    private void removeFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        // remove fragment
        fragmentTransaction.remove(fragment);

        fragmentTransaction.commit();
    }

    // load current frame with fragment
    private void loadFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.book_scroll_layout, fragment);

        fragmentTransaction.commit(); // save the changes
    }

    private void showBooks(){

    }
}