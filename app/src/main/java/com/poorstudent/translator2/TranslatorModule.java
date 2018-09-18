package com.poorstudent.translator2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = OkHttpClientModule.class)
public class TranslatorModule {
    private static final String BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";

    @Provides
    public TranslateFragment.YaTranslatorApi translatorApi(Retrofit retrofit){
        return retrofit.create(TranslateFragment.YaTranslatorApi.class);
    }
    @Provides
    public TranslateFragment.YaLanguagesApi languagesApi(Retrofit retrofit){
        return retrofit.create(TranslateFragment.YaLanguagesApi.class);
    }

    @TranslateFragment.SingletonScope
    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient,
                             GsonConverterFactory gsonConverterFactory, Gson gson){
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    public Gson gson(){
        final GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson){
        return GsonConverterFactory.create(gson);
    }

}
