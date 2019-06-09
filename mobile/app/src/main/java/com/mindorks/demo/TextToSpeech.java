package com.mindorks.demo;

import android.content.Context;
import android.os.AsyncTask;

import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.SynthesizeOptions;

public class TextToSpeech {
    private SpeechToText speechService;
    private com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech textToSpeech;
    StreamPlayer streamPlayer = new StreamPlayer();

    public TextToSpeech(Context mContext)
    {
        textToSpeech = new com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech();
        textToSpeech.setIamCredentials(new IamOptions.Builder()
                .apiKey(mContext.getString(R.string.TTS_apikey))
                .build());

        textToSpeech.setEndPoint("https://gateway-syd.watsonplatform.net/text-to-speech/api");

        speechService = new SpeechToText();
        speechService.setIamCredentials(new IamOptions.Builder()
                .apiKey(mContext.getString(R.string.STT_apikey))
                .build());
        speechService.setEndPoint("https://gateway-syd.watsonplatform.net/speech-to-text/api");
    }

    public void Dictate(String message)
    {
        new SayTask().execute(message);
    }

    private class SayTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {


                streamPlayer.playStream(textToSpeech.synthesize(new SynthesizeOptions.Builder()
                        .text(params[0])
                        .voice(SynthesizeOptions.Voice.EN_GB_KATEVOICE)
                        .accept(SynthesizeOptions.Accept.AUDIO_WAV)
                        .build()).execute());
            }
            catch (Exception ee)
            {
                ee.printStackTrace();
            }
            return "Did synthesize";
        }
    }

}
