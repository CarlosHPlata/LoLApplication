package com.kingskull.lolapplication.controllers.utils;

import java.util.Random;

/**
 * Created by Carlos on 11/10/2015.
 */
public class MixedUtils {

    public static String getRandomHexString(int numchars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numchars);
    }

}
