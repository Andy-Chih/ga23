package com.g12.faunalencyclopedia;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.g12.faunalencyclopedia.Data.Animal;
import com.g12.faunalencyclopedia.Data.DataHolder;
import com.g12.faunalencyclopedia.Data.DataLoader;
import com.g12.faunalencyclopedia.Search.AVLTree;

/**
 * @author UID: u7532171 Name: Vaibhav Nohria
 * @author UID:u7630167 Name: Yihang Zhu
 * @author UID:u7601139 Name: Qingyue Ren
 * @author UID:u7574003 Name: Andy Chih
 */


public class MainActivity extends AppCompatActivity {
    // Andrew: Added a handler and a runnable to load data from Firebase
    private static final long INTERVAL = 60000;
    private Handler dataLoadingHandler = new Handler();
    private DataLoader dataLoader = new DataLoader(this);;
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
                    Toast.makeText(MainActivity.this, "Failed to load data: " + exception.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
            dataLoadingHandler.postDelayed(this, INTERVAL);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Ensure you remove any callbacks to prevent memory leaks
        dataLoadingHandler.removeCallbacks(dataLoadingRunnable);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signup = findViewById(R.id.signUp);
        Button login = findViewById(R.id.login);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, com.g12.faunalencyclopedia.signup.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // This is a simple demo to show how to get data
        TextView text = findViewById(R.id.load);
        Button button = findViewById(R.id.toList);

        dataLoadingHandler.post(dataLoadingRunnable);

        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ListActivity.class);
            //intent.putExtra("DATASET", dataset);
            startActivity(intent);
        });

    }
}