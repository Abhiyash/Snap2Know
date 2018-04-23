package com.example.abhiyash.snap2know;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;

/**
 * Created by Abhiyash on 19-Apr-18.
 */

public class LanguageTranslation {
    Main2Activity ob;
    String result;
    LanguageTranslation(Main2Activity ob1)
    {
    ob=ob1;
    }
    public String translateTo(View view,String lang)
    {
        String requestURL = "https://translation.googleapis.com/language/translate/v2";
        List<Pair<String, String>> params = new ArrayList<>();
        params.add(new Pair<String, String>("key", ob.getResources().getString(R.string.mykey)));
        // Set source and target languages
        params.add(new Pair<String, String>("source", "en"));
        params.add(new Pair<String, String>("target", lang));
        String[] queries = ((EditText)ob.findViewById(R.id.editText)).getText().toString().split(" ");
        for(String query:queries)
        {
            params.add(new Pair<String, String>("q", query));
        }
        Fuel.get(requestURL, params).responseString(new Handler<String>()
        {
            @Override
            public void success(@NotNull Request request, @NotNull Response response, String data)
            {
                    // Access the translations array
                try
                {
                    JSONArray translations = new JSONObject(data).getJSONObject("data").getJSONArray("translations");
                        // Loop through the array and extract the translatedText
                        // key for each item
                        result = "";
                        for (int i = 0; i < translations.length(); i++)
                        {
                            result += translations.getJSONObject(i)
                                .getString("translatedText") + "\n";
                            //Toast.makeText(ob, ""+result, Toast.LENGTH_SHORT).show();
                        }
                  //  Toast.makeText(ob, ""+result, Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    Toast.makeText(ob, "Error Occurred "+e, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void failure(@NotNull Request request, @NotNull Response response, @NotNull FuelError fuelError) { }
        });
        return result;
    }

}
