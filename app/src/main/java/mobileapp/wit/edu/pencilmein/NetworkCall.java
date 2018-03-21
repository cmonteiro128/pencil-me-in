package mobileapp.wit.edu.pencilmein;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkCall {

    private String username;
    private String password;

    private String TAG = "NetworkCall";

    NetworkCall(String username, String password, String url) {
        this.username = username;
        this.password = password;
        new MakeNetworkCall().execute(url, "Get");
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
            new StorageHandler(result).saveJSON();
        }
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
            cc.setRequestProperty("Authorization", basicAuth);
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
}