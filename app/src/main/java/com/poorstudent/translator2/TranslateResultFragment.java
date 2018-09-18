package com.poorstudent.translator2;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class TranslateResultFragment extends Fragment {

    private TextView mTvLang, mTvTranslateResult;

    public TranslateResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_translate_result, container, false);

        mTvLang = v.findViewById(R.id.tv_lang);
        mTvTranslateResult = v.findViewById(R.id.tv_translatedText);

        return v;
    }

    public void showTranslatedText(@NonNull TranslatedText translatedText){
        mTvLang.setText(translatedText.getLang());
        mTvTranslateResult.setText(translatedText.getText().get(0));
    }

    public void showEmptyTranslatedText(){
        mTvTranslateResult.setText("");
    }

}
