package com.example.finalprojectapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class BookSearchFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("Book Search frag", "in book search frag");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_search, container, false);
        SearchBar m = (SearchBar) getActivity();

        TextView search_txt = m.findViewById(R.id.searchBarText);
        String searh_entry_txt = search_txt.getText().toString();

        LinearLayout book_layout = view.findViewById(R.id.book_layout);
        LinearLayout.LayoutParams book_params = (new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        book_params.setMargins(30, 20, 30, 20);

        ArrayList<ArrayList<String>> book_info = MainActivity.BOOK_DB.getBookbyTitle(searh_entry_txt);
        TableLayout[] book_table = new TableLayout[book_info.size()];
        TableRow[] book_table_rows = new TableRow[3 * book_info.size()];
        ImageButton[] book_images = new ImageButton[book_info.size()];


        Log.d("Search bar", "adding books");

        for (int i = 0; i < book_info.size(); i++) {
            Log.d("Search bar", "cycling through book table");
            String title = book_info.get(i).get(0);
            String author = book_info.get(i).get(1);
            String image_id = book_info.get(i).get(2);

            Log.d("Search bar", title + ", " + author + ", " + image_id);

            String book_image = MainActivity.BOOK_IMAGE_DB.getImage(image_id);

            String uri = "@drawable/" + book_image;

            Log.d("Search bar", "getting image at " + uri);

            int imageResource = getResources().getIdentifier(uri, null, m.getPackageName());

            Log.d("Search bar", "adding table");


            book_table[i] = new TableLayout(m.getApplicationContext());
            book_table[i].setLayoutParams(book_params);


            book_table_rows[i] = new TableRow(m.getApplicationContext());
            book_table_rows[i + 1] = new TableRow(m.getApplicationContext());
            book_table_rows[i + 2] = new TableRow(m.getApplicationContext());


            book_table_rows[i].setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 0));
            book_table_rows[i + 1].setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 0));
            book_table_rows[i + 2].setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 0));

            Log.d("Search bar", "adding rows");

            // set book image row
            // Load a bitmap from the drawable folder
            Bitmap bMap = BitmapFactory.decodeResource(getResources(), imageResource);
            // Resize the bitmap to 150x100 (width x height)
            Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 450, 500, true);

            // set image button to go to book page
            book_images[i] = new ImageButton(getActivity().getApplicationContext());
            book_images[i].setImageBitmap(bMapScaled);
            book_images[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // save current book_id
                    UserProfile.CURRENT_BOOK_ID = image_id;
                    Intent intent = new Intent(getActivity(), BookPage.class);
                    startActivity(intent);
                }
            });
            book_table_rows[i].addView(book_images[i]);

            Log.d("Book image ", String.valueOf(book_images[i].getId()));


            // set title row
            TextView book_title = new TextView(getActivity().getApplicationContext());
            book_title.setText(title);
            book_table_rows[i + 1].addView(book_title);

            // set author row
            TextView book_author = new TextView(getActivity().getApplicationContext());
            book_author.setText(author);
            book_table_rows[i + 2].addView(book_author);


            book_layout.addView(book_table[i]);
            book_table[i].addView(book_table_rows[i]);
            book_table[i].addView(book_table_rows[i + 1]);
            book_table[i].addView(book_table_rows[i + 2]);



        }
        return view;
    }

}