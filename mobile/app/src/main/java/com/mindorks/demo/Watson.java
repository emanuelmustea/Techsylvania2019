package com.mindorks.demo;

import android.content.Context;
import android.os.AsyncTask;

import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.developer_cloud.assistant.v2.Assistant;
import com.ibm.watson.developer_cloud.assistant.v2.model.CreateSessionOptions;
import com.ibm.watson.developer_cloud.assistant.v2.model.MessageInput;
import com.ibm.watson.developer_cloud.assistant.v2.model.MessageOptions;
import com.ibm.watson.developer_cloud.assistant.v2.model.MessageResponse;
import com.ibm.watson.developer_cloud.assistant.v2.model.SessionResponse;
import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.SynthesizeOptions;

public class Watson
{
    private Assistant watsonAssistant;
    private SessionResponse watsonAssistantSession;
    private Context mContext;
    public Watson(Context context)
    {
        mContext = context;
        watsonAssistant = new Assistant("2018-11-08", new IamOptions.Builder()
                .apiKey(mContext.getString(R.string.assistant_apikey))
                .build());

        watsonAssistant.setEndPoint("https://gateway-syd.watsonplatform.net/assistant/api");
    }

    public String getMessageFromWatson(String message)
    {
        try {
            if (watsonAssistantSession == null) {
                CreateSessionOptions.Builder session = new CreateSessionOptions.Builder().assistantId(mContext.getString(R.string.assistant_id));


                ServiceCall<SessionResponse> call = watsonAssistant.createSession(session.build());
                String tt = watsonAssistant.getEndPoint();
                //watsonAssistant.setEndPoint("https://gateway-syd.watsonplatform.net/assistant/api");
                watsonAssistantSession = call.execute();

            }

            MessageInput input = new MessageInput.Builder()
                    .text(message)
                    .build();
            MessageOptions options = new MessageOptions.Builder()
                    .assistantId(mContext.getString(R.string.assistant_id))
                    .input(input)
                    .sessionId(watsonAssistantSession.getSessionId())
                    .build();
            MessageResponse response = watsonAssistant.message(options).execute();

            String resp = response.getOutput().getGeneric().get(0).getText();



            return resp;
        }
        catch(Exception ee)
        {
            ee.printStackTrace();
        }

        return null;
    }



}
