package com.example.bookmania;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

public class BookDescription extends AppCompatActivity {

    TextView txtTitle,txtPrice,txtDescription, txtAddress;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_description);

        Intent intent = getIntent();

        txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtPrice = (TextView)findViewById(R.id.txtPrice);
        txtDescription = (TextView)findViewById(R.id.txtDescription);
        txtAddress = (TextView)findViewById(R.id.txtAddress);
        imageView = (ImageView)findViewById(R.id.viewImage);

        String title = intent.getStringExtra("title");
        String price = intent.getStringExtra("price");
        String description = intent.getStringExtra("description");
        String address = intent.getStringExtra("address");
        String image = intent.getStringExtra("image");

        txtTitle.setText(title);
        txtPrice.setText("$" + price);
        txtDescription.setText(description);
        txtAddress.setText(address);
        imageView.setImageBitmap(decodeBitmap(image));

    }

    private Bitmap decodeBitmap(String image)
    {
        byte[] bytes;
        bytes = Base64.decode(image,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap;
    }
}
