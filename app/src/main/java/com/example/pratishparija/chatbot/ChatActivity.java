package com.example.pratishparija.chatbot;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.pratishparija.chatbot.adapter.MessageListAdapter;
import com.example.pratishparija.chatbot.model.BaseMessage;
import com.example.pratishparija.chatbot.network.Connector;
import com.example.pratishparija.chatbot.network.VolleyListener;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ChatActivity extends AppCompatActivity implements VolleyListener {
    private RecyclerView mMessageRecycler;
    private MessageListAdapter mMessageAdapter;
    private List<BaseMessage> messageList = new ArrayList<>();
    private EditText mEditText;
    private Button sendButton;
    Connector connector = new Connector();
    private static final int POST = Request.Method.POST;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mMessageRecycler = findViewById(R.id.reyclerview_message_list);
        mEditText = findViewById(R.id.edittext_chatbox);
        sendButton = findViewById(R.id.button_chatbox_send);

        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageAdapter = new MessageListAdapter(this, messageList);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        mMessageRecycler.setAdapter(mMessageAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                messageList.add(new BaseMessage(mEditText.getText().toString(), "Send"));
                mMessageAdapter.notifyDataSetChanged();
                try {
                    call();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mEditText.getText().clear();


            }
        });


    }


    private void call() throws JSONException {
        connector.connect("https://api.recast.ai/build/v1/dialog", POST, createBody(), ChatActivity.this, new HashMap<String, String>(0));

    }

    private JSONObject createBody() throws JSONException {
        JSONObject body = getBody();
        body.put("message", getMessage());
        return body;
    }

    private JSONObject getBody() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("conversation_id", 1);
        return jsonObject;
    }

    private JSONObject getMessage() throws JSONException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content", mEditText.getText().toString());
        jsonObject.put("type", "text");
        return jsonObject;
    }


    @Override
    public void onResponseReceived(String URL, Object obj) {
        JSONObject mainObject;
        try {
            mainObject = new JSONObject(obj.toString());
            JSONObject results = mainObject.optJSONObject("results");
            JSONArray array = results.optJSONArray("messages");
            if (array.length() > 0) {
                for (int d = 0; d < array.length(); d++) {
                    JSONObject msg = array.optJSONObject(d);
                    messageList.add(new BaseMessage(msg.optString("content"), "Recieve"));
                    mMessageAdapter.notifyDataSetChanged();
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
