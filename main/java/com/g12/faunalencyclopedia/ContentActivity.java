package com.g12.faunalencyclopedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import com.g12.faunalencyclopedia.Data.Animal;
import com.g12.faunalencyclopedia.Data.DataHolder;
import com.g12.faunalencyclopedia.AI.ViewModel;
import com.g12.faunalencyclopedia.Search.AVLTree;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
/**
 * @author UID:u7630167 Name: Yihang Zhu
 */
public class ContentActivity extends AppCompatActivity {
    private ViewModel viewModel;
    private Handler handler = new Handler(Looper.getMainLooper());
    private int charIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        Button button = findViewById(R.id.returnButton);
        TextView commonName = findViewById(R.id.commonName);
        TextView scientificName = findViewById(R.id.scientificName);
        TextView taxonomicGroup = findViewById(R.id.taxonomicGroup);
        TextView taxonomicSubgroup = findViewById(R.id.taxonomicSubgroup);
        AVLTree<Animal> animals = DataHolder.getInstance().getDataset();
        TextView description = findViewById(R.id.descriptionText);
        String authorization = getString(R.string.authorization);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.enableNetwork();

        // Search data from the AVL tree
        Intent intent = getIntent();
        String animalName = intent.getStringExtra("ANIMAL");
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Optional<Animal> targetAnimal = animals.get(animal -> animal.getCommon_name().equals(animalName));

        if (targetAnimal.isPresent()) {
            Animal animal = targetAnimal.get();
            commonName.setText(animal.getCommon_name());
            scientificName.setText(animal.getScientific_name());
            taxonomicGroup.setText(animal.getTaxonomic_group());
            taxonomicSubgroup.setText(animal.getTaxonomic_subgroup());

            // Store data to the FireStore
            // Reference: https://firebase.google.com/docs/firestore/quickstart?authuser=0&_gl=1*1f5szli*_ga*MTE5MDAyNTI2Ni4xNjk3MjQzOTc5*_ga_CW55HF8NVT*MTY5NzI0Mzk3OS4xLjEuMTY5NzI0NDAwMi4zNy4wLjA.#java
            Map<String, Object> visited = new HashMap<>();
            visited.put(animal.getCommon_name(), 1);
            System.out.println("Start uploading data...");

            db.collection("history").document(email).set(visited, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            System.out.println("DocumentSnapshot successfully updated!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("Error updating document: " + e.getMessage());
                        }
                    });
        }

        button.setOnClickListener(view -> {
            Intent intentBack = new Intent(ContentActivity.this, ListActivity.class);
            //intentBack.putExtra("EMAIL", email);
            startActivity(intentBack);
        });

         /*Generate the content by AI
         Reference: ChatGPT:
         Me: This is my simple project... (One AI project I have already implemented)
         Me: Now I want to do the similar thing on Android Studio.
         ChatGPT: Android does not allow synchronous network operations on the main thread,
         so you'd typically use libraries like Retrofit or OkHttp to manage your HTTP requests...*/
        viewModel = new ViewModelProvider(this).get(ViewModel.class);
        viewModel.getCurrentCharacter().observe(this, response -> {
            charIndex = 0;
            handler.removeCallbacksAndMessages(null);
            simulateTypingEffect(description, response);
        });
        int ID = getResources().getIdentifier("Content", "string", getPackageName());
        String prompt = getString(ID, animalName);
        description.setText("");
        viewModel.fetchResponse(authorization, prompt);
    }

    private void simulateTypingEffect(final TextView textView, final String response) {
        if (charIndex <= response.length() - 1) {
            textView.append(String.valueOf(response.charAt(charIndex)));
            charIndex++;
            handler.postDelayed(() -> simulateTypingEffect(textView, response), 10);
        }
    }
}