package com.example.bookmania;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class BrowseBooks extends Fragment {

    private ArrayList<Books> books,b;
    private ListView listView;
    private CustomBookAdapter bookAdapter;

    public BrowseBooks() {
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
        return inflater.inflate(R.layout.fragment_browse_books, container, false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView)getActivity().findViewById(R.id.listview);
        retrieve();


    }

    private void retrieve() {
        FirebaseApp app = FirebaseApp.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance(app);
        DatabaseReference ref = database.getReference();
        books = new ArrayList<>();

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().equals("Books")) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Books b = ds.getValue(Books.class);
                        books.add(b);
                    }
                }
                setListView();
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

    private void setListView() {
        bookAdapter = new CustomBookAdapter(BrowseBooks.this.getContext(), R.layout.list_item,books);
        listView.setAdapter(bookAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(BrowseBooks.this.getActivity(),BookDescription.class);
                Books book = (Books)bookAdapter.getItem(i);
                intent.putExtra("title",book.getTitle());
                intent.putExtra("price",book.getPrice());
                intent.putExtra("address",book.getAddress());
                intent.putExtra("image",book.getImage());
                intent.putExtra("description",book.getDescription());
                intent.putExtra("user",book.getUser());
                startActivity(intent);

            }
        });

    }
}
