package com.jwhh.bookapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.jwhh.bookapp.databinding.ActivityBookDetialsBinding;

public class BookDetials extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detials);
        //Get the item transferred via Intent from the other activity;
        Books books = getIntent().getParcelableExtra("Books");
        ActivityBookDetialsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_book_detials);
        binding.setBooks(books);
    }
}
