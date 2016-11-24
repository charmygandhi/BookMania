package com.example.bookmania;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends Fragment implements View.OnClickListener {
    private FirebaseAuth auth;
    private EditText etEmail,etPassword,etConfirmPassword;
    private Button btnRegister;

    public RegisterActivity() {
        auth = FirebaseAuth.getInstance();
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        etEmail = (EditText)getActivity().findViewById(R.id.etEmail);
        etPassword = (EditText)getActivity().findViewById(R.id.etPassword);
        etConfirmPassword = (EditText)getActivity().findViewById(R.id.etConfirmPassword);
        btnRegister = (Button)getActivity().findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        registerUser();
    }

    private void registerUser() {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(getActivity(),"Enter email address!",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(getActivity(),"Enter password!",Toast.LENGTH_LONG).show();
            return;
        }

        if(! TextUtils.equals(password,confirmPassword)){
            Toast.makeText(getActivity(),"Password mismatch!",Toast.LENGTH_LONG).show();
            return;
        }

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful()){
                  getFragmentManager().beginTransaction().replace(R.id.flContent,new SellBooks()).commit();
              }
              else{
                Toast.makeText(getActivity(),"Registration Error",Toast.LENGTH_LONG).show();
              }
            }
        });
    }
}
