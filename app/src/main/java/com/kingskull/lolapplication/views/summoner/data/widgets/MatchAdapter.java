package com.kingskull.lolapplication.views.summoner.data.widgets;

import android.content.Context;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.api.restfull.connections.RIOT;
import com.kingskull.lolapplication.api.restfull.connections.SILVERCODING;
import com.kingskull.lolapplication.models.pojos.match.MatchReference;
import com.kingskull.lolapplication.views.widgets.ResizableImageView;
import com.koushikdutta.ion.Ion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cherrera on 9/24/2015.
 */
public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchVIewHolder> {

    private MatchAdapter.ItemClickListener mItemClickListener;

    private Context context;
    private List<MatchReference> matchHistory = new ArrayList<MatchReference>();
    private LayoutInflater inflater;

    public MatchAdapter(Context context, List<MatchReference> matchHistory){
        this.context = context;
        this.inflater = LayoutInflater.from(context);

        this.matchHistory = matchHistory;
    }

    @Override
    public MatchVIewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_match_row, parent, false);
        MatchVIewHolder holder = new MatchVIewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MatchVIewHolder holder, int position) {
        MatchReference match = matchHistory.get(position);

        switch (match.getQueue()){
            case RIOT.SOLO_Q:
                holder.matchType.setText("Solo Q");
                break;
            case RIOT.TEAM_3X3:
                holder.matchType.setText("Team 3v3");
                break;
            case RIOT.TEAM_5X5:
                holder.matchType.setText("Team 5v5");
                break;
            case RIOT.NEW_RANKED:
                holder.matchType.setText("Ranked 5v5");
                break;
            default:
                holder.matchType.setText("Ranked");
                break;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String date = dateFormat.format(new Date(match.getTimestamp()));
        holder.date.setText(date);

        switch (match.getLane()){
            case RIOT.MID:
            case RIOT.MIDDLE:
                holder.positionMap.setImageDrawable( context.getResources().getDrawable( R.drawable.ic_minimap_mid ) );
                break;
            case RIOT.TOP:
                holder.positionMap.setImageDrawable( context.getResources().getDrawable( R.drawable.ic_minimap_top) );
                break;
            case RIOT.BOT:
            case RIOT.BOTTOM:
                if ( match.getRole() == "DUO_SUPPORT" ){
                    holder.positionMap.setImageDrawable( context.getResources().getDrawable( R.drawable.ic_minimap_supp ) );
                } else {
                    holder.positionMap.setImageDrawable( context.getResources().getDrawable( R.drawable.ic_minimap_carry ) );
                }
                break;
            case RIOT.JUNGLE:
                holder.positionMap.setImageDrawable( context.getResources().getDrawable( R.drawable.ic_minimap_jung ) );
                break;
            default:
                holder.positionMap.setImageDrawable( context.getResources().getDrawable( R.drawable.ic_minimap_jung ) );
        }

        Ion.with (holder.championBackground).placeholder(context.getResources().getDrawable(R.drawable.dummie))
                .error(context.getResources().getDrawable(R.drawable.dummie))
                .load(SILVERCODING.CHAMPIONS_MINIIMAGES_URL + match.getChampion() + ".png");


    }

    @Override
    public int getItemCount() {
        return this.matchHistory.size();
    }

    public void setOnItemClickListener(MatchAdapter.ItemClickListener listener){
        this.mItemClickListener = listener;
    }

    class MatchVIewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView championBackground, positionMap;
        TextView matchType, date;

        public MatchVIewHolder(View itemView){
            super(itemView);
            championBackground = (ImageView) itemView.findViewById(R.id.bacground_match_champion);
            positionMap = (ImageView) itemView.findViewById(R.id.position_map);
            matchType = (TextView) itemView.findViewById(R.id.ranked_text);
            date = (TextView) itemView.findViewById(R.id.timestamp);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null){
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    public interface  ItemClickListener {
        void onItemClick(View v, int position);
    }
}
