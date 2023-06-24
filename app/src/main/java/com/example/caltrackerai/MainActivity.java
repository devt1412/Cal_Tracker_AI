package com.example.caltrackerai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button addb, addl, adds, addd;
    private Button sendButton;
    private ChatGPTClient chatGPTClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addb = findViewById(R.id.addbreakfast);


        sendButton = findViewById(R.id.calbt);

        chatGPTClient = new ChatGPTClient("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");

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "I ate 4 aloo parathas 2 glass of milk, calculate the total calories and return the number";
                new SendMessageTask().execute(message);
            }
        });
    }

    private class SendMessageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String[] params) {
            try {
                return chatGPTClient.sendPromptAndGetResponse(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return "Error occurred"+e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            sendButton.setText(result);
        }

    }
}
