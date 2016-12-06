package com.example.bookmania;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends Fragment implements View.OnClickListener {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText etEmail,etPassword;
    private Button btnLogin,btnSignUp;
    UserSessionManager sessionManager;

    public LoginActivity() {
        // Required empty public constructor
        auth = FirebaseAuth.getInstance();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        auth = FirebaseAuth.getInstance();

        etEmail = (EditText)getActivity().findViewById(R.id.etEmail);
        etPassword = (EditText)getActivity().findViewById(R.id.etPassword);
        btnLogin = (Button)getActivity().findViewById(R.id.btnLogin);
        btnSignUp = (Button)getActivity().findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if(email.isEmpty()){
            etEmail.setError("Please enter email id!");
            return;
        }

        if(password.isEmpty()){
            etPassword.setError("Please enter your password!");
            return;
        }

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(!task.isSuccessful()){
                    Toast.makeText(getActivity(),"Login failed! Please check you email and password",Toast.LENGTH_LONG).show();
                }
                else{
                    saveUser();
                    Toast.makeText(getActivity(),"Logged in Successfully!",Toast.LENGTH_LONG).show();
                    getFragmentManager().beginTransaction().replace(R.id.flContent,new BrowseBooks()).commit();
                    getActivity().setTitle("Browse Books");
                }
            }
        });
    }

    private void saveUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            String uid = user.getUid();
            sessionManager = new UserSessionManager(getContext());
            sessionManager.createUserLoginSession(uid);

        }
    }
}
