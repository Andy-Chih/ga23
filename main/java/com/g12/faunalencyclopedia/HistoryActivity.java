package com.g12.faunalencyclopedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @author UID:u7630167 Name: Yihang Zhu
 */
public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();

        // Receive history data from FireStore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String email = mAuth.getCurrentUser().getEmail();

        DocumentReference userDocRef = db.collection("history").document(email);
        userDocRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    List<String> animalNames = new ArrayList<>();
                    for (Map.Entry<String, Object> record : document.getData().entrySet()){
                        String animalName = record.getKey();
                        animalNames.add(animalName);
                    }
                    ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, animalNames);
                    ListView listView = findViewById(R.id.listHistory);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener((adapterView, view, i, l) -> {
                        Intent intentToContent = new Intent(HistoryActivity.this, ContentActivity.class);
                        intentToContent.putExtra("ANIMAL", animalNames.get(i));
                        startActivity(intentToContent);
                    });
                }else{
                    System.out.println("No such document");
                }
            }else{
                System.out.println("get failed with " + task.getException());
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TextView textName = findViewById(R.id.textName);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        textName.setText(mAuth.getCurrentUser().getDisplayName());

        // Clear history
        Button clearHistory = findViewById(R.id.clearHistory);
        clearHistory.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseAuth auth = FirebaseAuth.getInstance();
            String email = auth.getCurrentUser().getEmail();
            DocumentReference userDocRef = db.collection("history").document(email);
            userDocRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    ListView listView = findViewById(R.id.listHistory);
                    ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();
                    adapter.clear();
                }
            });
        });

        // From history to list
        Button buttonToList = findViewById(R.id.buttonToList);
        buttonToList.setOnClickListener(v -> {
            Intent intentToList = new Intent(HistoryActivity.this, ListActivity.class);
            startActivity(intentToList);
        });

        // Log out
        Button buttonLogOut = findViewById(R.id.logOut);
        buttonLogOut.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intentToLogin = new Intent(HistoryActivity.this, Homepage.class);
            startActivity(intentToLogin);
        });
    }
}