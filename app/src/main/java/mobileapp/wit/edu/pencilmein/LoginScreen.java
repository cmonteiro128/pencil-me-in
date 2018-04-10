package mobileapp.wit.edu.pencilmein;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import io.paperdb.Paper;

public class LoginScreen extends AppCompatActivity {

    TextView TxtResult;
    private String username;
    private String password;
    private int status;

    private String url = "https://pencil-me-in-scraper.herokuapp.com/classlist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.buttonLogin);

        final EditText usernameEditText = (EditText) findViewById(R.id.editTextUsername);
        final EditText passwordEditText = (EditText) findViewById(R.id.editTextPassword);

        //Initialize Paper
        Paper.init(getApplicationContext());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();
                // passwordEditText.setText("");

                if (username != null && password != null) {
                    new NetworkCall(username, password, url);
                }

                StorageHandler storage = new StorageHandler();
                List<StorageHandler.ClassListData> classes= storage.retrieveClassListObject();

                if(classes == null) {
                    passwordEditText.setError("PencilMeIn could not find data for this user. Please check your credentials and try again.");
                }
                else {
                    Intent intent = new Intent(v.getContext(), TaskView.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }




}