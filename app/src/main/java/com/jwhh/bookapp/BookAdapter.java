package com.jwhh.bookapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    ArrayList<Books> mBooks;

    public BookAdapter(ArrayList<Books> books) {
        mBooks = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Books books = mBooks.get(position);
        holder.mtvTitle.setText(books.title);
        holder.mtvSubtitle.setText(books.subtitle);
        holder.mtvPublished.setText(books.publisher);
        holder.mtvPublishedDate.setText(books.publishedDate);
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        TextView mtvTitle, mtvSubtitle, mtvPublished, mtvPublishedDate;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            mtvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mtvSubtitle = (TextView) itemView.findViewById(R.id.tvSubtitle);
            mtvPublished = (TextView) itemView.findViewById(R.id.tvPublisher);
            mtvPublishedDate = (TextView) itemView.findViewById(R.id.tvPublishedDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Books selectedBook = mBooks.get(getAdapterPosition()); //Select Each Book for the RecyclerView
                    Toast.makeText(v.getContext(), selectedBook.title + " is selected", Toast.LENGTH_LONG).show(); //Display Book Title
                    Intent intent = new Intent(v.getContext(), BookDetials.class); //Initiate transfer portal to the other Activity
                    intent.putExtra("Books", selectedBook); //Place the item to transfer to the next Activity tag Books
                    v.getContext().startActivity(intent); //Commit the transfer
                }
            });
        }
    }
}
