package com.jwhh.bookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private TextView mtvError;
    private ProgressBar mProgressBar;
    private RecyclerView mrcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booklistactivity_main);
        mtvError = (TextView) findViewById(R.id.tvError);
        mProgressBar = (ProgressBar) findViewById(R.id.pbIndicator);
        mrcView = (RecyclerView) findViewById(R.id.rcView);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mrcView.setLayoutManager(linearLayout);
        Intent intent = getIntent();
        String query = intent.getStringExtra("QueryResult");
        URL bookUrl;
        try {
            if (query == null) {
                bookUrl = AppUtils.buildUrl("cooking");
            } else {
                bookUrl = new URL(query);
            }
            new BooksQueryTask().execute(bookUrl);
        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
                URL bookURL = AppUtils.buildUrl(query);
                new BooksQueryTask().execute(bookURL);

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        return false;
    }

    class BooksQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String result = null;
            try {
                result = AppUtils.getJSON(searchURL);
            }catch (IOException e) {
                Log.e("Error", e.toString());
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mProgressBar.setVisibility(View.INVISIBLE);
            try {
                if (result == null) {
                    mtvError.setVisibility(View.VISIBLE);
                    mrcView.setVisibility(View.INVISIBLE);
                } else {
                    mtvError.setVisibility(View.INVISIBLE);
                    mrcView.setVisibility(View.VISIBLE);
                    ArrayList<Books> books = AppUtils.getBooksFromJSON(result);
                    BookAdapter bookAdapter = new BookAdapter(books);
                    mrcView.setAdapter(bookAdapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.advance_search_action:
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}


