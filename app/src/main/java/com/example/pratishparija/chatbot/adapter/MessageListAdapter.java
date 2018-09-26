package com.example.pratishparija.chatbot.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pratishparija.chatbot.model.BaseMessage;
import com.example.pratishparija.chatbot.R;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private Context mContext;
    private List<BaseMessage> mMessageList;
    private BaseMessage baseMessage;

    public MessageListAdapter(Context context, List<BaseMessage> messageList) {
        mContext = context;
        mMessageList = messageList;
    }

    @Override
    public int getItemViewType(int position) {
        baseMessage = mMessageList.get(position);

        if (baseMessage.getType().equalsIgnoreCase("Send")) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else if (baseMessage.getType().equalsIgnoreCase("Recieve")) {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_message_sent, viewGroup, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_message_received, viewGroup, false);
            return new ReceivedMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        baseMessage = mMessageList.get(i);
        if (viewHolder instanceof SentMessageHolder) {
            SentMessageHolder holder = (SentMessageHolder) viewHolder;
            holder.messageText.setText(baseMessage.getMessage());

        }
        if (viewHolder instanceof ReceivedMessageHolder) {
            ReceivedMessageHolder holder = (ReceivedMessageHolder) viewHolder;
            holder.messageText.setText(baseMessage.getMessage());

        }

    }

    @Override
    public int getItemCount() {
        return mMessageList == null ? 0 : mMessageList.size();
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_sent);
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_recived);

        }


    }
}