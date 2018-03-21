package com.example.abhiyash.snap2know;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.cloud.translate.Translate;
//import com.google.cloud.translate.TranslateOptions;
//import com.google.cloud.translate.Translation;
//import com.google.cloud.translate.Detection;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    TextView tv;
    Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv=(TextView)findViewById(R.id.textView);
        b1=(Button)findViewById(R.id.button4);
        b2=(Button)findViewById(R.id.button5);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        Intent it1=getIntent();
        Bundle ob=it1.getExtras();
        String text=ob.getString("text");
        tv.setText(text);
      /*  Translate translate = TranslateOptions.getDefaultInstance().getService();

        // The text to translate

        // Translates some text into Russian
        Translation translation =
                translate.translate(
                        text,
                        Translate.TranslateOption.sourceLanguage("en"),
                        Translate.TranslateOption.targetLanguage("ru"));


        Toast.makeText(this, "Translated text is"+translation.getTranslatedText(), Toast.LENGTH_SHORT).show();
        //System.out.printf("Translation: %s%n", translation.getTranslatedText());*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button4:
                //Search button
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                //String term = editTextInput.getText().toString();

                intent.putExtra(SearchManager.QUERY, tv.getText().toString());
                startActivity(intent);
                break;
            case R.id.button5:
                //Translate button
                break;
        }
    }
}
