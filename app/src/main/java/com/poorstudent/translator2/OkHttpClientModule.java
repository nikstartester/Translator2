package com.poorstudent.translator2;

import android.content.Context;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

import static com.poorstudent.translator2.ContextModule.*;

@Module(includes = ContextModule.class)
public class OkHttpClientModule {
    @Provides
    public OkHttpClient okHttpClient(Cache cache){
        return new OkHttpClient()
                .newBuilder()
                .cache(cache)
                .build();
    }

    @Provides
    public Cache cache(File cacheFile){
        return new Cache(cacheFile, 10 * 1000 * 1000); //10 MB
    }

    @Provides
    public File file(@ApplicationContext Context context){
        File file = new File(context.getCacheDir(), "translatedCache");
        file.mkdirs();
        return file;
    }
}
