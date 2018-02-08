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
        Player contacto = playerList.get(position);
        int icon = contacto.getIcon();
        switch (icon) {
            case 0:
                holder.pic.setImageDrawable(holder.v.getResources().getDrawable(R.drawable.ic_face_black_24dp, null));
                break;
            case 1:
                holder.pic.setImageDrawable(holder.v.getResources().getDrawable(R.drawable.ic_grade_black_24dp, null));
                break;
            default:
                break;
        }

        holder.name.setText(contacto.getName());
        holder.score.setText(contacto.getScore());
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder
    {
        public TextView name;
        public TextView score;
        public ImageView pic;
        public View v;

        public AdapterViewHolder(View itemView) {
            super(itemView);
            this.v = itemView;
            this.pic = itemView.findViewById(R.id.imageContact);
            this.name = itemView.findViewById(R.id.nameContact);
            this.score = itemView.findViewById(R.id.phoneContact);
        }
    }
}
