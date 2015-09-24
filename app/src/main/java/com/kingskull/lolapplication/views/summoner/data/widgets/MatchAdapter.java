package com.kingskull.lolapplication.views.summoner.data.widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingskull.lolapplication.R;

import java.util.List;

/**
 * Created by cherrera on 9/24/2015.
 */
public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchVIewHolder> {

    private MatchAdapter.ItemClickListener mItemClickListener;

    private Context context;
    private List matchHistory;
    private LayoutInflater inflater;

    public MatchAdapter(Context context, List matchHistory){
        this.context = context;
        this.matchHistory = matchHistory;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public MatchVIewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_champion_card, parent, false);
        MatchVIewHolder holder = new MatchVIewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MatchVIewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return this.matchHistory.size();
    }

    public void setOnItemClickListener(MatchAdapter.ItemClickListener listener){
        this.mItemClickListener = listener;
    }

    class MatchVIewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public MatchVIewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }

    public interface  ItemClickListener {
        void onItemClick(View v, int position);
    }
}
