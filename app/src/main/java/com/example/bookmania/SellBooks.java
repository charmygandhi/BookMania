package com.example.bookmania;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SellBooks extends Fragment implements View.OnClickListener {


    private EditText etTitle,etPrice,etDescription,etAddress;
    private Spinner categorySpinner;
    private Button btnSell;

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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sell_books, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        etTitle = (EditText)getActivity().findViewById(R.id.etTitle);
        etDescription = (EditText)getActivity().findViewById(R.id.etDescription);
        etAddress = (EditText)getActivity().findViewById(R.id.etAddress);
        etPrice = (EditText)getActivity().findViewById(R.id.etPrice);
        categorySpinner = (Spinner)getActivity().findViewById(R.id.spinnerCategory);
        btnSell = (Button)getActivity().findViewById(R.id.btnSellBook);
        btnSell.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        FirebaseApp app = FirebaseApp.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance(app);
        DatabaseReference ref = database.getReference();

        String title = etTitle.getText().toString();
        String price = etPrice.getText().toString();
        String description = etDescription.getText().toString();
        String address = etAddress.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();

        Books books = new Books();

        books.setTitle(title);
        books.setPrice(price);
        books.setAddress(address);
        books.setCategory(category);
        books.setDescription(description);

        ref.child("Books").push().setValue(books);
    }
}
