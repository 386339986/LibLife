package com.neu.liblife.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.neu.liblife.R;
import com.neu.liblife.main.User;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private List<User> userDataset;
    private Context context;

    public UserListAdapter(Context context, List<User> userDataset) {
        this.userDataset = userDataset;
        this.context = context;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_recyclerview_item, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.userHeadTop.setImageResource(userDataset.get(position).getHeadId());
        holder.username_top.setText(userDataset.get(position).getUserName());

    }

    @Override
    public int getItemCount() {
        return userDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView userHeadTop;
        TextView username_top;
        public ViewHolder(View itemView) {
            super(itemView);
            userHeadTop= itemView.findViewById(R.id.user_top);
            username_top = itemView.findViewById(R.id.username_top);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            //
        }


    }


}
