package com.example.abhiyash.snap2know;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

//import com.google.cloud.translate.Translate;
//import com.google.cloud.translate.TranslateOptions;
//import com.google.cloud.translate.Translation;
//import com.google.cloud.translate.Detection;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    TextView tv,tv2;
    EditText e;
    Button b1,b2,b3,b4,b5;
    Spinner langsp;
    String language,lang,text,translatedtext,s,query;
    String result="";
    File f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //tv=(TextView)findViewById(R.id.textView);
        e=(EditText)findViewById(R.id.editText);
        b1=(Button)findViewById(R.id.button4);
        b2=(Button)findViewById(R.id.button5);
        b3=(Button)findViewById(R.id.button7);
         b4=(Button)findViewById(R.id.button8);
         b5=(Button)findViewById(R.id.button9);
         b1.setOnClickListener(this);
        b2.setOnClickListener(this);
         b3.setOnClickListener(this);
         b4.setOnClickListener(this);
         b5.setOnClickListener(this);
         tv=(TextView)findViewById(R.id.textView);
        tv2=(TextView)findViewById(R.id.textView2);
         langsp=(Spinner)findViewById(R.id.spinner1);
         langsp.setOnItemSelectedListener(this);

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
                List<String> categories2 = new ArrayList<String>();
                categories2.add("Google");

                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);

                // Drop down layout style - list view with radio button
                dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                langsp.setAdapter(dataAdapter2);
                langsp.setVisibility(View.VISIBLE);
                tv.setText("Where do you want to search?");
                tv.setVisibility(View.VISIBLE);
                b3.setVisibility(View.VISIBLE);

                break;
            case R.id.button5:
                //Translate button
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
                langsp.setVisibility(View.VISIBLE);
                tv.setText("Select language");
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
                    tv2.setText(translatedtext);
                }
                else if(language.equals("French"))
                {
                    lang="fr";
                    translatedtext=lt.translateTo(v,lang);
                    Toast.makeText(this, ""+translatedtext, Toast.LENGTH_SHORT).show();
                    tv2.setText(translatedtext);
                }
                else if(language.equals("Hindi"))
                {
                    lang="hi";
                    translatedtext=lt.translateTo(v,lang);
                    Toast.makeText(this, ""+translatedtext, Toast.LENGTH_SHORT).show();
                    tv2.setText(translatedtext);
                }
                else if(language.equals("Russian"))
                {
                    lang="ru";
                    translatedtext=lt.translateTo(v,lang);
                    Toast.makeText(this, ""+translatedtext, Toast.LENGTH_SHORT).show();
                    tv2.setText(translatedtext);
                }
                else if(language.equals("Google")) {
                    if (e.getText().toString().equals(null)) {
                        Toast.makeText(this, "Enter something to search", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                        //String term = editTextInput.getText().toString();
                        String o="";
                        String sentence = e.getText().toString();
                        sentence = sentence.replaceAll("[^a-zA-Z0-9 ]", "");
                        String[] words = sentence.split(" ");
                        int len;
                        len = words.length;
                        System.out.println(len);
                        String[] candidatewords = new String[len];
                        String[] stopwords={"a","about","above","after","again","against","all","am","an","and","any","are","aren't","as","at","be",
                                "because","been","before","being","below","between","both","but","by","can't","cannot","could","coudn't","did","didn't",
                                "do","does","doesn't","doing","don't","down","during","each","few","from","further","had","hadn't","has","hasn't","have",
                                "haven't","having","he","he'd","he'll","her","here","here's","hers","herself","him","himself","his","how","how's","i","i'd",
                                "i'll","i'm","if","in","into","is","isn't","it","it's","its","itself","let's","me","more","most","mustn't","my","myself",
                                "no","nor","not","off","of","once","only","or","other","ought","our","ourselves","out","over","own","same","shan't","she",
                                "she'd","she'll","she's","should","shouldn't","so","some","such","than","that","that's","the","their","theirs","them",
                                "themselves","then","there","there's","these","they","they'd","they'll","they're","they've","this","those","through",
                                "to","too","under","until","up","very","was","wasn't", "we","we'd","we'll","we're","we've","were","weren't","what",
                                "what's","when","when's","where","where's","which","while","who","who's","whom","why","why's","with","won't",
                                "would","wouldn't","you","you'd","you'll","you're","you've","your","yours","yourself","yourselves"};
                        int ch, flag = 0, count = 0;

                        try {
                            for (String w : words)
                            {
                                flag = 0;
                                for(String a:stopwords)
                                {
                                    if(a.equalsIgnoreCase(w))
                                    {
                                        flag++;
                                    }
                                }
                                System.out.println(4);
                                if (flag == 0)
                                {
                                    candidatewords[count] = w;
                                    count++;
                                    o += w.toString();
                                }
                            }

                            System.out.println(o);
                            for(String a:candidatewords)
                            {
                                KeyWordExtract kw = new KeyWordExtract();
                                String ss = kw.ext(a);
                                if (ss.equals("null")) {

                                } else {
                                    result+=" "+ss;
                                }
                            }
                        }catch(Exception e){
                            System.out.println(e);
                        }

                        intent.putExtra(SearchManager.QUERY, result);
                        startActivity(intent);
                    }
                }
                else
                {
                    lang="es";
                    translatedtext=lt.translateTo(v,lang);
                    Toast.makeText(this, ""+translatedtext, Toast.LENGTH_SHORT).show();
                    tv2.setText(translatedtext);
                }
                break;
            case R.id.button8:
                s = e.getText().toString();
                // Destination Folder and File name
                String timestamp= new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                String FILE = Environment.getExternalStorageDirectory().toString()
                        +"/PDF/"+""+timestamp+".pdf";
                f=new File(FILE);
                // Create New Blank Document
                    Document document = new Document(PageSize.A4);
                    // Create Directory in External Storage
                    String root = Environment.getExternalStorageDirectory().toString();
                    File myDir = new File(root + "/PDF");
                    myDir.mkdirs();
                        // Create Pdf Writer for Writting into New Created Document
                try {
                    PdfWriter.getInstance(document, new FileOutputStream(FILE));
                    }
                    catch (DocumentException e) {
                    e.printStackTrace();
                    }
                    catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                        // Open Document for Writting into document
                document.open();
                        // User Define Method
                addMetaData(document);
                try {
                    addTitlePage(document);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
                        // Close Document after writting all content
                document.close();

                Toast.makeText(this, "Pdf Created", Toast.LENGTH_SHORT).show();
                b5.setVisibility(View.VISIBLE);
                break;
            case R.id.button9:
                Intent sentin=new Intent();
                sentin.setAction(Intent.ACTION_SEND);
                sentin.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(f));
                sentin.setType("application/pdf");
                startActivity(sentin);
        }
    }

    private void addTitlePage(Document document) throws DocumentException {
        Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD
                | Font.UNDERLINE, BaseColor.GRAY);
        Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);


        Paragraph prProfile = new Paragraph();
        prProfile.setFont(smallBold);
        prProfile.add("\n"+s);

        prProfile.setFont(smallBold);
        document.add(prProfile);

        // Create new Page in PDF
        document.newPage();
    }

    private void addMetaData(Document document) {

        document.addTitle("MyPdf");
        document.addSubject("Person Info");
        document.addKeywords("Personal, Education, Skills");
        document.addAuthor("TAG");
        document.addCreator("TAG");
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        language=parent.getItemAtPosition(position).toString();
        Toast.makeText(this, "Selected option "+language, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
