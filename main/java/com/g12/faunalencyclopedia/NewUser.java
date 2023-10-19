package com.g12.faunalencyclopedia;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * @author UID: u7532171 Name: Vaibhav Nohria
 */


public class NewUser implements UserFactory{

    public User createUser(String username, String email, String password){
        // Andrew: Also upload users to the firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                FirebaseUser user = mAuth.getCurrentUser();
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                user.updateProfile(profileUpdates);
            }else{
                System.out.println("User not created");
            }
        });
        return new User(username, email, password);
    }
}
