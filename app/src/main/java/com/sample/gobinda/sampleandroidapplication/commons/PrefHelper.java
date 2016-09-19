package com.sample.gobinda.sampleandroidapplication.commons;

import com.sample.gobinda.sampleandroidapplication.App;

public class PrefHelper {

   public static void setUserName(String email){
        App.getPrefs().edit().putString("user",email).commit();
    }

    public static String getUserName(){
      return App.getPrefs().getString("user",null);
    }
    public static void removeUserName() {
        App.getPrefs().edit().remove("user").commit();
    }
}
