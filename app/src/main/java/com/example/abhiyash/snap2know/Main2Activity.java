package com.example.abhiyash.snap2know;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//import com.google.cloud.translate.Translate;
//import com.google.cloud.translate.TranslateOptions;
//import com.google.cloud.translate.Translation;
//import com.google.cloud.translate.Detection;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    TextView tv;
    EditText e;
    Button b1,b2,b3;
    Spinner langsp;
    String language,lang,text,translatedtext;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //tv=(TextView)findViewById(R.id.textView);
        e=(EditText)findViewById(R.id.editText);
        b1=(Button)findViewById(R.id.button4);
        b2=(Button)findViewById(R.id.button5);
        b3=(Button)findViewById(R.id.button7);
         b1.setOnClickListener(this);
        b2.setOnClickListener(this);
         b3.setOnClickListener(this);
         tv=(TextView)findViewById(R.id.textView);
        langsp=(Spinner)findViewById(R.id.spinner1);
         langsp.setOnItemSelectedListener(this);
         List<String> categories = new ArrayList<String>();
         categories.add("German");
         categories.add("French");
         categories.add("Spanish");
         categories.add("Hindi");
         categories.add("Russian");

         // Creating adapter for spinner
         ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

         // Drop down layout style - list view with radio button
         dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

         // attaching data adapter to spinner
         langsp.setAdapter(dataAdapter);
        Intent it1=getIntent();
        Bundle ob=it1.getExtras();
         text=ob.getString("text");
        e.setText(text+"");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button4:
                //Search button
                if(e.getText().toString().equals(null))
                {
                    Toast.makeText(this, "Enter something to search", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                    //String term = editTextInput.getText().toString();
                    intent.putExtra(SearchManager.QUERY, e.getText().toString());
                    startActivity(intent);
                }
                break;
            case R.id.button5:
                //Translate button
                langsp.setVisibility(View.VISIBLE);
                tv.setVisibility(View.VISIBLE);
                b3.setVisibility(View.VISIBLE);
                break;
            case R.id.button7:
                    //actual translation will happen here
                LanguageTranslation lt=new LanguageTranslation(this);
                if(language.equals("German"))
                {
                    lang="de";
                    translatedtext=lt.translateTo(v,lang);
                    Toast.makeText(this, ""+translatedtext, Toast.LENGTH_SHORT).show();
                }
                else if(language.equals("French"))
                {
                    lang="fr";
                    translatedtext=lt.translateTo(v,lang);
                    Toast.makeText(this, ""+translatedtext, Toast.LENGTH_SHORT).show();
                }
                else if(language.equals("Hindi"))
                {
                    lang="hi";
                    translatedtext=lt.translateTo(v,lang);
                    Toast.makeText(this, ""+translatedtext, Toast.LENGTH_SHORT).show();
                }
                else if(language.equals("Russian"))
                {
                    lang="ru";
                    translatedtext=lt.translateTo(v,lang);
                    Toast.makeText(this, ""+translatedtext, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    lang="es";
                    translatedtext=lt.translateTo(v,lang);
                    Toast.makeText(this, ""+translatedtext, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        language=parent.getItemAtPosition(position).toString();
        Toast.makeText(this, "Selected language "+language, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
