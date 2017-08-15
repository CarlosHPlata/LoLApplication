package com.kingskull.lolapplication.views.syncronize.summoner.widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.models.database.entities.SummonerEntitie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 30/09/2015.
 */
public class SummonerAdapter extends RecyclerView.Adapter<SummonerAdapter.SummonerHolder>{

    private LayoutInflater inflater;
    private Context context;

    private List<SummonerEntitie> summoners = new ArrayList<SummonerEntitie>();

    private ItemCLickListener mItemClickListener;
    private ItemLongClickListener mItemLongClickListener;

    public SummonerAdapter(Context context, List<SummonerEntitie> summoners){
        this.summoners = summoners;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.summoners = summoners;
    }

    public void deleteItem(int position){
        summoners.remove(position);
        notifyItemRemoved(position);
    }

    public void setOnItemCLickListener(ItemCLickListener listener){
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener (ItemLongClickListener listener){
        this.mItemLongClickListener = listener;
    }

    @Override
    public SummonerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.summoner_row, parent, false);
        SummonerHolder holder = new SummonerHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SummonerHolder holder, int position) {
        SummonerEntitie summoner = summoners.get(position);
        holder.summonerName.setText( summoner.getSummonername().toUpperCase() );
        holder.region.setText( summoner.getRegion().toUpperCase() );
    }

    @Override
    public int getItemCount() {
        return this.summoners.size();
    }

    public List<SummonerEntitie> getSummoners() {
        return summoners;
    }

    public void setSummoners(List<SummonerEntitie> summoners) {
        this.summoners = summoners;
    }

    class SummonerHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {

        TextView summonerName;
        TextView region;

        public SummonerHolder(View itemView) {
            super(itemView);
            summonerName = (TextView) itemView.findViewById(R.id.summoner_name_of_list);
            region = (TextView) itemView.findViewById(R.id.region_summoner);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null){
                mItemClickListener.onItemClick(view, summoners.get( getAdapterPosition() ));
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if ( mItemLongClickListener != null ){
                mItemLongClickListener.onLongItemClick(view, summoners.get( getAdapterPosition() ));
            }
            return true;
        }
    }

    public interface ItemCLickListener {
        void onItemClick(View v, SummonerEntitie summoner);
    }

    public interface ItemLongClickListener {
        void onLongItemClick(View v, SummonerEntitie summoner);
    }

}
