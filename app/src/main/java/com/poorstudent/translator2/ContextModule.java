package com.poorstudent.translator2;

import android.content.Context;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;


@Module
public class ContextModule {
    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @ContextModule.ApplicationContext
    @TranslateFragment.SingletonScope
    @Provides
    public Context context(){
        return context.getApplicationContext();
    }

    @Qualifier
    public @interface ApplicationContext{}
}
