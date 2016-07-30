package com.zl.pokemap.betterpokemap;

import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Utils{
    public static List<LatLng> generateLatLng(LatLng center){
        return generateLatLng(center, 2, 0.0025);
    }

    public static int getPokemonResourceId(Context context, boolean useHires, int number){
        String uri = (useHires?"s":"p") + number;
        int resourceID = context.getResources().getIdentifier(uri, "drawable", context.getPackageName());
        return resourceID;
    }

    public static String getLocalizedPokemonName(String name, Context context){
        if(name == null || context == null){ return name; }

        try{
            String sname = name.toLowerCase();
            int id = context.getResources().getIdentifier(sname, "string", context.getPackageName());
            return id != 0? context.getResources().getString(id) : name;
        }catch (Exception e){}
        return name;
    }


    public static List<LatLng> generateLatLng(final LatLng center, int steps, double delta){

        int size = steps*2+1;
        List<LatLng> ret = new ArrayList<>();
        for(int i=0; i< size; i++){
            for(int  j=0; j< size; j++){
                double lat = center.latitude - ((i - steps)*delta);
                double lng = center.longitude - ((j - steps)*delta);
                LatLng ll = new LatLng(lat, lng);
                ret.add(ll);
            }
        }

        Collections.sort(ret, new Comparator<LatLng>() {
            @Override
            public int compare(LatLng l1, LatLng l2) {
                float[] results = {0f,0f,0f};
                Location.distanceBetween(l1.latitude, l1.longitude, center.latitude, center.longitude, results);
                float ld1 = results[0];
                Location.distanceBetween(l2.latitude, l2.longitude, center.latitude, center.longitude, results);
                float ld2 = results[0];
                return Float.compare(ld1, ld2);
            }
        });
        return ret;
    }
}