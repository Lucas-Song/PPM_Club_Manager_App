package com.example.lucassong.clubsandwich;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;

    private EditText usernameEt;
    private EditText passwordEt;

    String username = "";

    Activity activity;
    Context context;
    FragmentActivity fragmentActivity;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        activity = LoginActivity.this;
        context = LoginActivity.this;
        fragmentActivity = LoginActivity.this;
        sharedPref = context.getSharedPreferences(getString(R.string.username_pref_file_key), Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        loginButton = findViewById(R.id.loginButton);

        usernameEt = findViewById(R.id.etUsername);
        passwordEt = findViewById(R.id.etPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = usernameEt.getText().toString();
                String password = passwordEt.getText().toString();

                Login login = new Login();
                login.execute(username, password);
            }
        });
    }

    public class Login extends AsyncTask<String, Void, String> {
        AlertDialog alertDialog;

        @Override
        protected String doInBackground(String... params) {
            String login_url = "http://clubsatfcuc.xyz/login.php";

            try {
                String user_name = params[0];
                String password = params[1];
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                        +URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedReader.readLine())!= null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Login Status");
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("OK")) {
                ConnectionHandler connectionHandler = new ConnectionHandler();
                connectionHandler.downloadData(activity, context, fragmentActivity);

                editor.putString(getString(R.string.username_pref_file_key), username);
                editor.commit();
                startActivity(new Intent(LoginActivity.this, TimelineActivity.class));
            }
            else
            {
                alertDialog.setMessage(result);
                alertDialog.show();
            }
        }
    }
}