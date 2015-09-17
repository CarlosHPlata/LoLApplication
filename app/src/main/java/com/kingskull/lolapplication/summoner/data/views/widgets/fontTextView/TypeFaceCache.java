package com.kingskull.lolapplication.summoner.data.views.widgets.fontTextView;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Usuario on 06/09/2015.
 */
public class TypeFaceCache {

    private static Map<String, Typeface> typefaceMap;

    public static Typeface getTypeFace(String name, Context context){
        Map<String, Typeface> typefaceMap = getInstance();
        Typeface font = typefaceMap.get(name);

        if (font == null){
            try{
                font = Typeface.createFromAsset(context.getAssets(), "fonts/"+name);
                typefaceMap.put(name, font);
            } catch (Exception e){
                return null;
            }
        }

        return font;
    }

    private static Map<String, Typeface> getInstance(){
        if (typefaceMap == null || typefaceMap.isEmpty()){
            typefaceMap = new HashMap<String, Typeface>();
        }
        return typefaceMap;
    }
}
