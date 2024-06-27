package com.example.fududelivery.Chat;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fududelivery.Login.UserSessionManager;
import com.example.fududelivery.R;
import com.example.fududelivery.Service.ConvertDateToTime;

import java.util.List;
import java.util.Objects;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_SENDER = 0;
    private static final int VIEW_TYPE_RECEIVER = 1;

    UserSessionManager userSessionManager;

    Context context;
    List<Chat> chatList;

    public ChatAdapter(Context context, List<Chat> chatList) {
        this.context = context;
        this.chatList = chatList;
        userSessionManager = new UserSessionManager(context);
    }

    @Override
    public int getItemViewType(int position) {
        return Objects.equals(chatList.get(position).getChatSender(), userSessionManager.getUserInformation()) ? VIEW_TYPE_SENDER : VIEW_TYPE_RECEIVER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENDER) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_sender, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat_receiver, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        if (holder instanceof SenderViewHolder) {
            ((SenderViewHolder) holder).bind(chat);
        } else if (holder instanceof ReceiverViewHolder) {
            ((ReceiverViewHolder) holder).bind(chat);
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    static class SenderViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvNameSender;
        AppCompatTextView tvMessageSender;
        AppCompatTextView tvTimeSender;

        public SenderViewHolder(View itemView) {
            super(itemView);
            tvNameSender = itemView.findViewById(R.id.tv_name_sender);
            tvMessageSender = itemView.findViewById(R.id.tv_body_sender);
            tvTimeSender = itemView.findViewById(R.id.tv_time);
        }

        public void bind(Chat chat) {
            tvNameSender.setText(chat.getSenderName());
            tvMessageSender.setText(chat.getBodyMessage());
            tvTimeSender.setText(ConvertDateToTime.ConvertTimeToDate(chat.getTime()));
        }
    }

    static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView tvNameReceiver;
        AppCompatTextView tvMessageReceiver;
        AppCompatTextView tvTimeReceiver;

        public ReceiverViewHolder(View itemView) {
            super(itemView);
            tvNameReceiver = itemView.findViewById(R.id.tv_name_receive);
            tvMessageReceiver = itemView.findViewById(R.id.tv_body_receive);
            tvTimeReceiver = itemView.findViewById(R.id.tv_time_receive);
        }

        public void bind(Chat chat) {
            tvNameReceiver.setText(chat.getSenderName());
            tvMessageReceiver.setText(chat.getBodyMessage());
            tvTimeReceiver.setText(ConvertDateToTime.ConvertTimeToDate(chat.getTime()));
        }
    }
}
