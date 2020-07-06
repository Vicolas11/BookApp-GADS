package com.jwhh.bookapp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class AppUtils {

    private static final String BASE_API_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final String QUERY_PARAMETER_KEY = "q";
    private static final String KEY = "key";
    private static final String API_KEY = "AIzaSyBEp3nV0Rnl4GEBEq041aAXYkYFb8Pcd4U";
    private static final String TITLE = "intitle:";
    private static final String AUTHOR = "inauthor:";
    private static final String PUBLISHER = "inpublisher:";
    private static final String ISBN = "isbn:";

    public static URL buildUrl(String title) {
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY, title)
                .appendQueryParameter(KEY, API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildSearchUrl(String title, String author, String publisher, String isbn) {
        URL url = null;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            if(!title.isEmpty()) stringBuilder.append(TITLE + title + "+");
            if(!author.isEmpty()) stringBuilder.append(AUTHOR + author + "+");
            if(!publisher.isEmpty()) stringBuilder.append(PUBLISHER + publisher + "+");
            if(!isbn.isEmpty()) stringBuilder.append(ISBN + isbn + "+");
            stringBuilder.setLength(stringBuilder.length()-1);
            String string = stringBuilder.toString();
            Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAMETER_KEY, string)
                    .appendQueryParameter(KEY,API_KEY)
                    .build();
            url = new URL(uri.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getJSON(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String result = null;
        try {
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");
            result = scanner.hasNext() ? scanner.next() : "";
        } catch (Exception e) {
            Log.d("Error", e.toString());
            return null;
        } finally {
            connection.disconnect();
        }
        return result;
    }

    public static ArrayList<Books> getBooksFromJSON(String json) {
        //List the keys you want to access in from the JSON file
        final String ID = "id";
        final String TITLE = "title";
        final String SUBTITLE = "subtitle";
        final String AUTHORS = "authors";
        final String PUBLISHER = "publisher";
        final String PUBLISHED_DATE = "publishedDate";
        final String ITEMS = "items";
        final String VOLUME_INFO = "volumeInfo";
        final String DESCRIPTION = "description";
        final String IMAGELINK = "imageLinks";
        final String THUMBNAIL = "thumbnail";
        ArrayList<Books> bookList = new ArrayList<Books>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(ITEMS);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject bookObject = jsonArray.getJSONObject(i);
                JSONObject volumeObject = bookObject.getJSONObject(VOLUME_INFO);
                JSONObject imageLinkObject = null;
                if (volumeObject.has(IMAGELINK)) {
                    imageLinkObject = volumeObject.getJSONObject(IMAGELINK);
                }
                int numberOfAuthors;
                try {
                    numberOfAuthors = volumeObject.getJSONArray(AUTHORS).length();
                } catch (Exception e) {
                    numberOfAuthors = 0;
                }

                String[] authors = new String[numberOfAuthors];
                for (int j = 0; j < numberOfAuthors; j++) {
                    authors[j] = volumeObject.getJSONArray(AUTHORS).get(j).toString();
                }
                Books books = new Books(
                        bookObject.getString(ID),
                        volumeObject.getString(TITLE),
                        (volumeObject.isNull(SUBTITLE)? "" : volumeObject.getString(SUBTITLE)),
                        authors,
                        (volumeObject.isNull(PUBLISHER)? "" : volumeObject.getString(PUBLISHER)),
                        (volumeObject.isNull(PUBLISHED_DATE)? "" : volumeObject.getString(PUBLISHED_DATE)),
                        (volumeObject.isNull(DESCRIPTION)? "" : volumeObject.getString(DESCRIPTION)),
                        (imageLinkObject == null) ? "" : imageLinkObject.getString(THUMBNAIL));

                bookList.add(books);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bookList;
    }
}
