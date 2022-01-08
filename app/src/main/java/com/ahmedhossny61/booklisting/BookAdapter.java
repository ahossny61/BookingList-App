package com.ahmedhossny61.booklisting;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Activity context, ArrayList<Book> androidBook) {
        super(context, 0, androidBook);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ListItemView = convertView;
        if (ListItemView == null) {
            ListItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }
        Book CurrentBook = getItem(position);

        TextView Book_title = (TextView) ListItemView.findViewById(R.id.book_title);
        Book_title.setText(CurrentBook.getTitle()+"");

        TextView Book_author = (TextView) ListItemView.findViewById(R.id.book_author);
        Book_author.setText(CurrentBook.getAuthor());

        ImageView image = (ImageView) ListItemView.findViewById(R.id.Book_image);
       // Picasso.get().Load(CurrentBook.getImage_url()).into(image);
        if(CurrentBook.getImage_url()!=null)
          Picasso.with(this.getContext()).load(CurrentBook.getImage_url()).into(image);
        return ListItemView;

    }
}
