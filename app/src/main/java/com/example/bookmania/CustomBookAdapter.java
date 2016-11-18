package com.example.bookmania;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Charmy PC on 2016-11-16.
 */

public class CustomBookAdapter extends ArrayAdapter<Books> {

    private ArrayList<Books> items;


    public CustomBookAdapter(Context context, int textViewResourceId, ArrayList<Books> items) {
        super(context,textViewResourceId, items);
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v == null){
            Activity activity = (Activity)getContext();
            LayoutInflater inflater = activity.getLayoutInflater();
            v = inflater.inflate(R.layout.list_item,null);
        }

        Books b = items.get(position);

        if(b != null)
        {
            TextView tt = (TextView)v.findViewById(R.id.toptext);
            TextView bt = (TextView)v.findViewById(R.id.bottomtext);
            ImageView img = (ImageView)v.findViewById(R.id.list_image);

            if(tt != null){
                tt.setText(b.getTitle());
            }
            if(bt != null){
                bt.setText("$" + b.getPrice());
            }
            if(img != null){
                String image = b.getImage();
                img.setImageBitmap(decodeBitmap(image));
            }
        }
        return v;
    }

    private Bitmap decodeBitmap(String image)
    {
        byte[] bytes;
        bytes = Base64.decode(image,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap;
    }
}
