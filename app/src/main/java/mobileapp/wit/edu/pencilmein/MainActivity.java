package mobileapp.wit.edu.pencilmein;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {

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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameEditText.getText().toString();
                password = passwordEditText.getText().toString();

                if (username == null || password == null) {

                } else {
                    Authenticator.setDefault(new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password.toCharArray());
                        }
                    });
                    new MakeNetworkCall().execute(url, "Get");
                }
            }
        });
    }

    private InputStream ByGetMethod(String ServerURL) {

        InputStream DataInputStream = null;
        try {

            URL url = new URL(ServerURL);
            HttpURLConnection cc = (HttpURLConnection)
                    url.openConnection();
            //set timeout for reading InputStream
            cc.setReadTimeout(150000);
            // set timeout for connection
            cc.setConnectTimeout(150000);
            //set HTTP method to GET
            cc.setRequestMethod("GET");
            //set it to true as we are connecting for input
            cc.setDoInput(true);
            String testString = username + ":" + password;
            final String basicAuth = "Basic " + Base64.encodeToString(testString.getBytes(), Base64.NO_WRAP);
            cc.setRequestProperty ("Authorization", basicAuth);
            //reading HTTP response code
            int response = cc.getResponseCode();
            Log.v("Status Code", Integer.toString(response));
            //if response code is 200 / OK then read Inputstream
            if (response == HttpURLConnection.HTTP_OK) {
                DataInputStream = cc.getInputStream();
            }

        } catch (Exception e) {
            Log.e("ByGetMethod", "Error in GetData", e);
        }
        return DataInputStream;

    }

    private String ConvertStreamToString(InputStream stream) {

        InputStreamReader isr = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder response = new StringBuilder();

        String line = null;
        try {

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

        } catch (IOException e) {
            Log.e("ConvertStreamToString", "Error in ConvertStreamToString", e);
        } catch (Exception e) {
            Log.e("ConvertStreamToString", "Error in ConvertStreamToString", e);
        } finally {

            try {
                stream.close();

            } catch (IOException e) {
                Log.e("ConvertStreamToString", "Error in ConvertStreamToString", e);

            } catch (Exception e) {
                Log.e("ConvertStreamToString", "Error in ConvertStreamToString", e);
            }
        }
        return response.toString();

    }

    private class MakeNetworkCall extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... arg) {

            InputStream is = null;
            String URL = arg[0];
            Log.d("MakeNetworkCall", "URL: " + URL);
            String res = "";

            is = ByGetMethod(URL);

            if (is != null) {
                res = ConvertStreamToString(is);
            } else {
                res = "Something went wrong";
            }
            return res;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Log.d("MakeNetworkCall", "Result: " + result);
        }
    }

}