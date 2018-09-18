package com.poorstudent.translator2;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import static com.poorstudent.translator2.TranslateFragment.CODE_EMPTY_TEXT;

public class MainActivity extends AppCompatActivity implements TranslateFragment.OnTranslateFinish {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        FragmentTransaction ftrans = getSupportFragmentManager().beginTransaction();

        ftrans.replace(R.id.container_textToTranslate, new TranslateFragment());
        ftrans.replace(R.id.container_primary, new TranslateResultFragment());
        ftrans.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onTranslateFinish(int code, TranslatedText translatedText) {
        if(code == CODE_EMPTY_TEXT){
            showEmptyTranslatedText();
        }else onTranslateFinish(translatedText);
    }

    @Override
    public void onTranslateFinish(@NonNull TranslatedText translatedText) {
        Toast.makeText(getApplicationContext(), translatedText.getText().get(0),
                Toast.LENGTH_SHORT).show();

        showTranslatedText(translatedText);
    }

    private void showTranslatedText(@NonNull TranslatedText translatedText){
        TranslateResultFragment resultFragment = getTranslatedResultFragment();
        if(resultFragment != null) resultFragment.showTranslatedText(translatedText);
    }
    private void showEmptyTranslatedText(){
        TranslateResultFragment resultFragment = getTranslatedResultFragment();
        if(resultFragment != null) resultFragment.showEmptyTranslatedText();
    }

    private TranslateResultFragment getTranslatedResultFragment(){
        for(Fragment fragment : getSupportFragmentManager().getFragments()){
            if(fragment instanceof TranslateResultFragment){
                return (TranslateResultFragment)fragment;
            }
        }
        return null;
    }
}
