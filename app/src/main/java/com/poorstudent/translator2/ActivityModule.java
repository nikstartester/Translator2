package com.poorstudent.translator2;

import android.app.Activity;
import android.content.Context;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;


@Module
public class ActivityModule {
    private Context context;

    public ActivityModule(Activity context) {
        this.context = context;
    }

    @ActivityModule.ActivityContext
    @TranslateFragment.SingletonScope
    @Provides
    public Context context(){
        return context;
    }

    @Qualifier
    public @interface ActivityContext{}
}
