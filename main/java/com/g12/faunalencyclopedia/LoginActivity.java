package com.g12.faunalencyclopedia;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.g12.faunalencyclopedia.Data.Animal;
import com.g12.faunalencyclopedia.Data.DataHolder;
import com.g12.faunalencyclopedia.Data.DataLoader;
import com.g12.faunalencyclopedia.Search.AVLTree;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author UID: u7532171 Name: Vaibhav Nohria
 */


public class LoginActivity extends AppCompatActivity {

    private EditText usernameField;
    private EditText passwordField;

    private Authentication_state authState;
    /**
     * @author UID:u7630167 Name: Yihang Zhu
     */
    // Andrew: Added a handler and a runnable to load data from Firebase
    private static final long INTERVAL = 120000;
    private Handler dataLoadingHandler = new Handler();
    private DataLoader dataLoader = new DataLoader(this);
    private Runnable dataLoadingRunnable = new Runnable(){

        @Override
        public void run() {
            System.out.println("Loading data...");
            dataLoader.loadDataSet(new DataLoader.OnDataLoadedCallback() {

                @Override
                public void onSuccess(AVLTree<Animal> dataset) {
                    System.out.println("Data loaded successfully");
                    DataHolder.getInstance().setDataset(dataset);

                }

                @Override
                public void onFailure(Exception exception) {
                    Toast.makeText(LoginActivity.this, "Failed to load data: " + exception.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
            dataLoadingHandler.postDelayed(this, INTERVAL);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataLoadingHandler.removeCallbacks(dataLoadingRunnable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);

        Button buttonLogin = findViewById(R.id.login);
        Button openSignuppage = findViewById(R.id.signup);

        authState = new LogoutState(this);

        dataLoadingHandler.post(dataLoadingRunnable);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                authState.handling_login(username, password);

            }
        });
        openSignuppage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, signup.class);
                startActivity(intent);
            }
        });
    }

}