package com.kingskull.lolapplication.api.restfull.Utils;

import android.content.Context;

import com.kingskull.lolapplication.models.pojos.Summoner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Usuario on 12/09/2015.
 */
public class SummonerStream {

    private Context context;

    SummonerStream(Context context){
        this.context = context;
    }


    public void save(Summoner summoner) throws IOException {
        FileOutputStream fos = context.openFileOutput( summoner.getId()+"", Context.MODE_PRIVATE );
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(summoner);
        os.close();
        fos.close();
    }


    public Summoner load(long id) throws IOException, ClassNotFoundException {
        FileInputStream fis = context.openFileInput(id + "");
        ObjectInputStream is = new ObjectInputStream(fis);
        Summoner summoner = (Summoner) is.readObject();
        is.close();
        fis.close();

        return summoner;
    }
}
