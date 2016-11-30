package com.example.bookmania;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class BookDescription extends AppCompatActivity {

    TextView txtTitle,txtPrice,txtDescription, txtAddress, txtEmail;
    ImageView imageView;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_description);

        Intent intent = getIntent();

        txtTitle = (TextView)findViewById(R.id.txtTitle);
        txtPrice = (TextView)findViewById(R.id.txtPrice);
        txtDescription = (TextView)findViewById(R.id.txtDescription);
        txtAddress = (TextView)findViewById(R.id.txtAddress);
        txtEmail = (TextView)findViewById(R.id.txtEmail);
        imageView = (ImageView)findViewById(R.id.viewImage);

        String title = intent.getStringExtra("title");
        String price = intent.getStringExtra("price");
        String description = intent.getStringExtra("description");
        String address = intent.getStringExtra("address");
        String image = intent.getStringExtra("image");
        userId = intent.getStringExtra("user");

        txtTitle.setText(title);
        txtPrice.setText("$" + price);
        txtDescription.setText(description);
        txtAddress.setText(address);
        imageView.setImageBitmap(decodeBitmap(image));
        getUserProfile();
      //  String email = getUserProfile();
        //txtEmail.setText(email);

    }

    private void getUserProfile(){
        final String[] email = {null};
        FirebaseApp app = FirebaseApp.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance(app);
        DatabaseReference ref = database.getReference("Users");
        ref.orderByChild("userId").equalTo(userId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Toast.makeText(getApplication()," " + dataSnapshot.getValue(),Toast.LENGTH_LONG).show();
                Map<String,Object> value = (Map<String, Object>) dataSnapshot.getValue();
                email[0] = (String) value.get("email");
                txtEmail.setText(email[0]);
               Toast.makeText(getApplication()," " + email[0], Toast.LENGTH_LONG).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private Bitmap decodeBitmap(String image)
    {
        byte[] bytes;
        bytes = Base64.decode(image,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap;
    }
}
