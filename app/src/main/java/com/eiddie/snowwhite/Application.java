package com.eiddie.snowwhite;


import com.tsengvn.typekit.Typekit;

public class Application extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance().addNormal(Typekit.createFromAsset(this, "fonts/NanumBarunpenR.ttf"));
    }
}
