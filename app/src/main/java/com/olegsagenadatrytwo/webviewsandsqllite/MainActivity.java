package com.olegsagenadatrytwo.webviewsandsqllite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainSavingToData";
    private WebView myWebView;
    private EditText name;
    private EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myWebView = (WebView) findViewById(R.id.webView);
        name = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.number);


        WebViewClient client = new WebViewClient();
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(client);

        myWebView.loadUrl("https://developer.android.com");
    }

    public void saveContact(View view) {
        String nameString = name.getText().toString();
        String numberString = number.getText().toString();
        MyContact myContact = new MyContact(nameString, numberString);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        long saved = databaseHelper.saveNewContact(myContact);
        if(saved == -1){
            Toast.makeText(this, "FAILED", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Successfully saved!!!", Toast.LENGTH_SHORT).show();

        }

    }

    public void getContact(View view) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<MyContact> rerurnedContacts = databaseHelper.getContacts();
        for(int i = 0; i < rerurnedContacts.size(); i++){
            Log.d(TAG, "getContact: " + "Name: " + rerurnedContacts.get(i).getName() + ", " +
            "Number: " + rerurnedContacts.get(i).getNumber());
        }
    }
}
