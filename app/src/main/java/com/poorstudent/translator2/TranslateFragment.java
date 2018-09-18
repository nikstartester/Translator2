package com.poorstudent.translator2;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Scope;

import dagger.Component;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class TranslateFragment extends Fragment {

    public static final int CODE_EMPTY_TEXT = 19;
    private static final String YA_KEY = "trnsl.1.1.20170315T193453Z.7c1821f3822cef3f.4d1413816eea108051adea6787e8831cb226fb1f";
    public static final String TAG = "Translator2: ";

    private EditText mEdtTextToTranslate;
    private Button mBtnTest;

    private String mOldText = "";

    private YaTranslatorApi mYaTranslatorApi;
    private YaLanguagesApi mYaLanguagesApi;

    private Timer mTimer;

    private OnTranslateFinish mOnTranslateFinish;

    public TranslateFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        YaTranslatorComponent daggerTranslatorComponent = DaggerTranslateFragment_YaTranslatorComponent
                .builder()
                .contextModule(new ContextModule(getActivity()))
                .build();

        mYaTranslatorApi = daggerTranslatorComponent.getYaTranslator();
        mYaLanguagesApi = daggerTranslatorComponent.getYaLanguages();

        mTimer = new Timer();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_translate, container, false);

        mEdtTextToTranslate = v.findViewById(R.id.edt_textToTranslate);
        mBtnTest = v.findViewById(R.id.btn_toTranslate);

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                String currText = mEdtTextToTranslate.getText().toString();

                if(currText.equals("") && !mOldText.equals("")) mOnTranslateFinish
                        .onTranslateFinish(CODE_EMPTY_TEXT, null);

                if(isValidateToTranslate(currText)) {
                    translateText(currText, "en-ru");
                    mOldText = currText;
                }
            }
        }, 0L, (long) 500);

        mBtnTest.setOnClickListener(v1 -> mYaLanguagesApi.getLanguages("ru")
                .enqueue(new Callback<Languages>() {
                    @Override
                    public void onResponse(Call<Languages> call, Response<Languages> response) {
                        if(response.isSuccessful()){

                            Log.d(TAG, "onResponse: " + response.message());
                            Log.d(TAG, "onResponse: Languages: dirs(0): " + response.body().getDirs().get(0));
                            //Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();

                        }else {
                            Log.d(TAG, "onResponse not Successful: " + response.message());
                            Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Languages> call, Throwable t) {
                        Log.d(TAG, "onFailure:" + t.getMessage());
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnTranslateFinish = ((OnTranslateFinish)context);
    }

    private boolean isValidateToTranslate(String text){
        return !mOldText.equals(text);
    }

    private void translateText(String text, String lang){
        mYaTranslatorApi.translateText(text, lang)
                .enqueue(new Callback<TranslatedText>() {
                    @Override
                    public void onResponse(@NonNull Call<TranslatedText> call, @NonNull Response<TranslatedText> response) {
                        if(response.isSuccessful()){
                            Log.d(TAG, "onResponse: " + response.body().getText());

                            mOnTranslateFinish.onTranslateFinish(response.body());
                        }
                        else {
                            Log.d(TAG, "onResponse: " + response.toString());
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<TranslatedText> call, @NonNull Throwable t) {
                    }
                });
    }


    interface YaTranslatorApi {
        @GET("translate?key=" + YA_KEY)
        Call<TranslatedText> translateText(@Query("text") String text,
                                           @Query("lang") String lang);
    }

    interface YaLanguagesApi {
        @GET("getLangs?key=" + YA_KEY)
        Call<Languages> getLanguages(@Query("ui") String uiLang);
    }

    public interface OnTranslateFinish{
        void onTranslateFinish(@NonNull TranslatedText translatedText);
        void onTranslateFinish(int code, TranslatedText translatedText);
    }

    @SingletonScope
    @Component(modules = TranslatorModule.class)
    interface YaTranslatorComponent{
        YaTranslatorApi getYaTranslator();
        YaLanguagesApi getYaLanguages();
    }

    @Scope
    @Retention(RetentionPolicy.CLASS)
    @interface SingletonScope{}
}
