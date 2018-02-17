package com.example.pdg.calulatorapi22;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/**
 * Created by PDG on 06/02/2018.
 */

public class mAdapter extends RecyclerView.Adapter<mAdapter.AdapterViewHolder> {

    List<Player> playerList;
    Context c;

    public mAdapter(Context c, List<Player> playerList) {
        this.playerList = playerList;
        this.c = c;
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_layout, parent, false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {
        Player player = playerList.get(position);

        int icon = player.getIcon();
        switch (icon) {
            case 0:
                holder.pic.setImageDrawable(holder.view.getResources().getDrawable(R.drawable.ic_face_black_24dp, null));
                break;
            case 1:
                holder.pic.setImageDrawable(holder.view.getResources().getDrawable(R.drawable.ic_grade_black_24dp, null));
                break;
            default:
                break;
        }

        holder.name.setText(player.getName());
        holder.score.setText(player.getScore());
        holder.time.setText(player.getTime());
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public TextView score;
        public TextView time;
        public ImageView pic;
        public View view;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            this.pic = itemView.findViewById(R.id.imageRanking);
            this.name = itemView.findViewById(R.id.userNameRanking);
            this.score = itemView.findViewById(R.id.scoreRanking);
            this.time = itemView.findViewById(R.id.timeRanking);
        }
    }



}
