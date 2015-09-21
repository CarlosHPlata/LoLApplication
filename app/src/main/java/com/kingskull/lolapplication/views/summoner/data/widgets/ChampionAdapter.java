package com.kingskull.lolapplication.views.summoner.data.widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.api.restfull.connections.SILVERCODING;
import com.kingskull.lolapplication.controllers.SummonerUtils;
import com.kingskull.lolapplication.models.pojos.ranked.ChampionRankedStat;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 13/09/2015.
 */
public class ChampionAdapter extends RecyclerView.Adapter<ChampionAdapter.MyViewHolder> {

    private final SummonerUtils utils;
    private LayoutInflater inflater;
    private List<ChampionRankedStat> champions = new ArrayList<ChampionRankedStat>();
    private Context context;

    private ItemClickListener mItemClickListener;

    public ChampionAdapter(Context context, List<ChampionRankedStat> champions){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.champions = champions;
        utils = new SummonerUtils();
    }

    public void setOnItemClickListener(ItemClickListener listener){
        this.mItemClickListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.single_champion_row, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {
        ChampionRankedStat stat = champions.get(i);

        holder.championKills.setText( utils.getKills(stat) );
        holder.championDeaths.setText( utils.getDeaths(stat) );
        holder.championAssists.setText( utils.getAssists(stat) );

        Ion.with(holder.championBackground).placeholder(context.getResources().getDrawable(R.drawable.dummie))
                .error(context.getResources().getDrawable(R.drawable.dummie))
                .load(SILVERCODING.CHAMPIONS_MINIIMAGES_URL + stat.getId() + ".png");
    }

    @Override
    public int getItemCount() {
        return this.champions.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView championBackground;
        TextView championKills, championDeaths, championAssists;

        public MyViewHolder(View itemView) {
            super(itemView);
            championBackground = (ImageView) itemView.findViewById(R.id.background_champion);
            championKills = (TextView) itemView.findViewById(R.id.kills_champion);
            championDeaths = (TextView) itemView.findViewById(R.id.deaths_single_champion);
            championAssists = (TextView) itemView.findViewById(R.id.assist_champion);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (mItemClickListener != null)
                mItemClickListener.onItemClick(v, getAdapterPosition());
        }

    }

    public interface ItemClickListener {
        public void onItemClick(View v, int position);
    }
}
