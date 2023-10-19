package com.g12.faunalencyclopedia;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * @author UID: u7532171 Name: Vaibhav Nohria
 */


public class LogoutState implements Authentication_state{

    private final Context context;

    public LogoutState(Context context) {
        this.context = context;
    }

    @Override
    public void handling_login(String username, String password) {
        /**
         * @author UID:u7630167 Name: Yihang Zhu
         */
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener((Activity) context, task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = mAuth.getCurrentUser();
                Intent intent = new Intent(context, ListActivity.class);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "Email or password is incorrect.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
