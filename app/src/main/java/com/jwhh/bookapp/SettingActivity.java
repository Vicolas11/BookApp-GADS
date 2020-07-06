package com.jwhh.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        final EditText etTitle, etAuthor, etPublisher, etISBN;
        Button searchBtn;
        etTitle = (EditText) findViewById(R.id.etTitle);
        etAuthor = (EditText) findViewById(R.id.etAuthor);
        etPublisher = (EditText) findViewById(R.id.etPublisher);
        etISBN = (EditText) findViewById(R.id.etISBN);
        searchBtn = (Button) findViewById(R.id.searchBtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString().trim();
                String author = etAuthor.getText().toString().trim();
                String publisher = etPublisher.getText().toString().trim();
                String isbn = etISBN.getText().toString().trim();
                if (title.isEmpty() && author.isEmpty() && publisher.isEmpty() && isbn.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter Search", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        URL queryUrl = AppUtils.buildSearchUrl(title, author, publisher, isbn);
                        Intent intent = new Intent(getApplicationContext(), BookListActivity.class);
                        intent.putExtra("QueryResult", queryUrl.toString());
                        startActivity(intent);
                    } catch (Exception e) {
                        Log.d("Error", e.getMessage());
                    }
                }
            }
        });
    }
}
