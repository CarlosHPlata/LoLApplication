package com.kingskull.lolapplication.views.summoner.data.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.views.summoner.data.fragments.ChampionStats;
import com.kingskull.lolapplication.views.summoner.data.fragments.MatchHistory;
import com.kingskull.lolapplication.views.summoner.data.fragments.SummonerInfo;

public class CustomPagerAdapter extends FragmentPagerAdapter {

    String[] tabs = {"Matchs", "Summoner", "Champions"};
    int[] icons = {R.drawable.matchs, R.drawable.profile, R.drawable.champions};

    Context context;

    public CustomPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment result = null;

        switch (position){
            case 0:
                result = MatchHistory.newInstance();
                break;
            case 1:
                result = SummonerInfo.newInstance();
                break;
            case 2:
            default:
                result = ChampionStats.newInstance();
                break;
        }

        return result;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Drawable drawable = context.getResources().getDrawable(icons[position]);
        drawable.setBounds(0,0,36,36);

        ImageSpan imageSpan = new ImageSpan(drawable);

        SpannableString spannableString = new SpannableString(" ");
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        return spannableString;
    }

    @Override
    public int getCount() {
        return 3;
    }
}