package com.example.bookmania;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static android.app.Activity.RESULT_OK;


public class SellBooks extends Fragment implements View.OnClickListener {


    private EditText etTitle,etPrice,etDescription,etAddress;
    private Spinner categorySpinner;
    private Button btnSell,btnSelectPhoto;
    private ImageView viewImage;
    Books books = null;
    UserSessionManager sessionManager;
    private boolean hasImage = false;

    public SellBooks() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sessionManager = new UserSessionManager(getContext());

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sell_books, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


       //Toast.makeText(getActivity()," " + sessionManager.getUser() + " ",Toast.LENGTH_LONG).show();

        etTitle = (EditText)getActivity().findViewById(R.id.etTitle);
        etDescription = (EditText)getActivity().findViewById(R.id.etDescription);
        etAddress = (EditText)getActivity().findViewById(R.id.etAddress);
        etPrice = (EditText)getActivity().findViewById(R.id.etPrice);
        categorySpinner = (Spinner)getActivity().findViewById(R.id.spinnerCategory);
        viewImage = (ImageView)getActivity().findViewById(R.id.viewImage);

        btnSelectPhoto = (Button)getActivity().findViewById(R.id.btnselectPhoto);
        btnSelectPhoto.setOnClickListener(this);
        btnSell = (Button)getActivity().findViewById(R.id.btnSellBook);
        btnSell.setOnClickListener(this);

        books = new Books();

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnSellBook:
                addBook();
                break;
            case R.id.btnselectPhoto:
                selectImage();
                break;
        }
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo","Choose from Gallery","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(options[i].equals("Take Photo")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(),"temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent,1);
                }
                else if(options[i].equals("Choose from Gallery")){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,2);
                }
                else if(options[i].equals("Cancel")){
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for(File temp:f.listFiles()){
                    if(temp.getName().equals("temp.jpg")){
                        f = temp;
                        break;
                    }
                }
                try{
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),bitmapOptions);
                    viewImage.setImageBitmap(bitmap);

                    String path = android.os.Environment.getExternalStorageDirectory() + File.separator + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path,String.valueOf(System.currentTimeMillis())+".jpg");
                    try{
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG,85,outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            else if(requestCode == 2){
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContext().getContentResolver().query(selectedImage,filePath,null,null,null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                if(thumbnail != null){
                    hasImage = true;
                }
                Log.d("path of image ", picturePath + "");
                viewImage.setImageBitmap(thumbnail);
                encodeBitmap(thumbnail);
            }
        }
    }

    private void encodeBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        books.setImage(imageEncoded);
    }

    private void addBook() {

        FirebaseApp app = FirebaseApp.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance(app);
        DatabaseReference ref = database.getReference();

        String title = etTitle.getText().toString();
        String price = etPrice.getText().toString();
        String description = etDescription.getText().toString();
        String address = etAddress.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();

       if(title.isEmpty()) {
           etTitle.setError("Title is required");
           return;
       }

        if(price.isEmpty()) {
            etPrice.setError("Price is required");
            return;
        }

        if(address.isEmpty()){
            etAddress.setError("Address is required");
            return;
        }

        if(!hasImage){
            Toast.makeText(getActivity(),"Please upload an image!",Toast.LENGTH_SHORT).show();
            return;
        }

        books.setTitle(title);
        books.setPrice(price);
        books.setAddress(address);
        books.setCategory(category);
        books.setDescription(description);
        books.setUser(sessionManager.getUser());

        ref.child("Books").push().setValue(books);

        getFragmentManager().beginTransaction().replace(R.id.flContent,new BrowseBooks()).commit();
        getActivity().setTitle("Browse Books");
    }
}
