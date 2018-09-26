package com.example.pratishparija.chatbot.network;


import com.android.volley.AuthFailureError;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;


import java.util.HashMap;

import java.util.Map;

public class Connector {


    public void connect(final String URL, int POST, JSONObject body, final VolleyListener volleyListener, final HashMap<String, String> requestHeaders) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(
                POST,
                URL,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        VolleyLog.d("resp", response.toString());
                        volleyListener.onResponseReceived(URL, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("resp", "Error: " + error.getMessage());
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Token 031339e46e8471dc638739cfa9dd088f");
                return headers;
            }
        };
        BotApplication.getInstance().addToRequestQueue(jsonRequest);

    }


}
