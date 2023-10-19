package com.g12.faunalencyclopedia;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;

/**
 * @author UID: u7532171 Name: Vaibhav Nohria
 */

public class signup extends AppCompatActivity {

    private EditText emailid;
    private EditText username;
    private EditText password;
    private Button Savebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailid = findViewById(R.id.emailid);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Savebutton = findViewById(R.id.Savebutton);

        Savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Username = username.getText().toString();
                String Email = emailid.getText().toString();
                String Password = password.getText().toString();

                UserFactory userFactory;
                userFactory = new NewUser();
                User user = userFactory.createUser(Username,Email,Password);
                saveDetailsToFile(user);
                //Andrew: After signing up, enter the app directly
                Intent intent = new Intent(signup.this, ListActivity.class);
                intent.putExtra("EMAIL", user.getEmail());
                startActivity(intent);
            }
        });

       Button home_screen = findViewById(R.id.button);
       home_screen.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(signup.this, LoginActivity.class);
               startActivity(intent);
           }
       });
    }
    private void saveDetailsToFile(User user){
        String userData = user.getUsername()+","+user.getEmail()+","+user.getPassword();
        try{

            String filepath = "C:\\Users\\vaibh\\Desktop\\user_cred.txt";
            File csvFile = new File(getFilesDir(),filepath);

            FileOutputStream fileOutputStream = new FileOutputStream(csvFile);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            writer.write(userData);
            writer.close();

            Toast.makeText(this, "SignUp successful!! ", Toast.LENGTH_SHORT).show();

        }catch(IOException e){
            e.printStackTrace();

            Toast.makeText(this, "SignUp Unsuccessful. Try again! ", Toast.LENGTH_SHORT).show();
        }
    }


}