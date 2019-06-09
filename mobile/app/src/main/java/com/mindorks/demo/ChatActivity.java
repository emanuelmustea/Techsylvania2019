package com.mindorks.demo;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.speech.RecognizerIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import co.intentservice.chatui.ChatView;
import co.intentservice.chatui.models.ChatMessage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.biabe.DatabaseFunctionsGenerator.Categories;
import com.example.biabe.DatabaseFunctionsGenerator.Messages;
import com.example.biabe.DatabaseFunctionsGenerator.Models.Categorie;
import com.example.biabe.DatabaseFunctionsGenerator.Models.Message;
import com.example.biabe.DatabaseFunctionsGenerator.Transactions;
import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneHelper;
import com.ibm.watson.developer_cloud.android.library.audio.MicrophoneInputStream;
import com.ibm.watson.developer_cloud.android.library.audio.utils.ContentType;

import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {


    private Context mContext;
    private ChatView chatView;

    private boolean listening = false;
    private MicrophoneInputStream capture;
    private MicrophoneHelper microphoneHelper;
    private Watson watson;
    private com.mindorks.demo.TextToSpeech textToSpeech;

    private final int REQ_CODE_SPEECH_INPUT = 100;
    private List<Message> messages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mContext = getApplicationContext();

        watson = new Watson(mContext);
        textToSpeech = new com.mindorks.demo.TextToSpeech(mContext);

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);
        microphoneHelper = new MicrophoneHelper(this);

        chatView = (ChatView) findViewById(R.id.chat_view);
        chatView.setOnSentMessageListener(new ChatView.OnSentMessageListener() {
            @Override
            public boolean sendMessage(final ChatMessage chatMessage) {

                sendMessageToServer(chatMessage.getMessage(), 0);
                sendMessageToWatson(chatMessage.getMessage());

                return true;
            }
        });

        chatView.setTypingListener(new ChatView.TypingListener() {
            @Override
            public void userStartedTyping() {

            }

            @Override
            public void userStoppedTyping() {

            }
        });

        //to post beginning
        //sendMessageToWatson("");
        Thread thread = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    loadMessagesFromServer();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();


        //start background service
        Intent msgIntent = new Intent(this, NotificationChecker.class);
        startService(msgIntent);

    }

    private void loadMessagesFromServer()
    {
        Messages.getMessages(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                messages = response.body();
                chatView.clearMessages();
                for(Message mess:messages) {
                    chatView.addMessage(new ChatMessage(mess.getContent(), System.currentTimeMillis(), mess.getSource()==0?ChatMessage.Type.SENT:ChatMessage.Type.RECEIVED));
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    private void sendMessageToServer(String message, int source)
    {
        Messages.addMessage(new Message(message, source), new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {

            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

            }
        });
    }

    private void sendMessageToWatson(final String message)
    {
        if(messages.get(messages.size()-1).getSource() > 1 && messages.get(messages.size()-1).getSource() < 80)
            return;
        Thread thread = new Thread(new Runnable() {
            public void run() {
                String response2 = watson.getMessageFromWatson(message);
                Boolean add = true;

                if(response2.equals("Getting result to spend wisely"))
                {
                    Messages.addMessage(new Message("We have a voucher at Altex. Are you interested?",4));
                    add = false;
                }
                else if(response2.equals("Getting result to save money"))
                {
                    Transactions.getSpendingAlerts();
                    Categorie cat = null;
                    //for()

                    response2 = "We have 5% off voucher at H&M. Are you interested?";
                    add=false;
                }

                final String response = response2;
                final Boolean adding = add;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(adding)
                            chatView.addMessage(new ChatMessage(response, System.currentTimeMillis(), ChatMessage.Type.RECEIVED));
                    }
                });

                if(adding) {
                    sendMessageToServer(response, 1);
                    textToSpeech.Dictate(response);
                }
            }
        });

        thread.start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    final ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    chatView.addMessage(new ChatMessage(result.get(0), System.currentTimeMillis(), ChatMessage.Type.SENT));
                    sendMessageToWatson(result.get(0));
                    sendMessageToServer(result.get(0), 0);

                }
                break;
            }

        }
    }

    public void RecordPressed(View v)
    {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi speak something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            a.printStackTrace();
            Intent your_browser_intent = new Intent(Intent.ACTION_VIEW,

                    Uri.parse("https://market.android.com/details?id=APP_PACKAGE_NAME"));
            startActivity(your_browser_intent);
        }
    }

    private RecognizeOptions getRecognizeOptions(InputStream audio) {
        return new RecognizeOptions.Builder()
                .audio(audio)
                .contentType(ContentType.OPUS.toString())
                .model("en-US_BroadbandModel")
                .interimResults(true)
                .inactivityTimeout(2000)
                .build();
    }




}

