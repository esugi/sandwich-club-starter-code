package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    private static String JSON_NAME = "name";
    private static String JSON_MAIN_NAME = "mainName";
    private static String JSON_AKA = "alsoKnownAs";
    private static String JSON_ORIGIN = "placeOfOrigin";
    private static String JSON_DESCRIPTION = "description";
    private static String JSON_IMAGE = "image";
    private static String JSON_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json)
    {
        try {
            JSONObject jsonSandwich = new JSONObject(json);

            JSONObject name = jsonSandwich.optJSONObject(JSON_NAME);
            String mainName = name.optString(JSON_MAIN_NAME);
            List<String> alsoKnownAs = jsonArrayToList(name, JSON_AKA);

            String placeOfOrigin = jsonSandwich.optString(JSON_ORIGIN);
            String description = jsonSandwich.optString(JSON_DESCRIPTION);
            String image = jsonSandwich.optString(JSON_IMAGE);
            List<String> ingredients = jsonArrayToList(jsonSandwich, JSON_INGREDIENTS);

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }

    // TODO: private method comment
    private static List<String> jsonArrayToList (JSONObject object, String name)
    {
        List<String> list = new ArrayList<>();
        JSONArray jsonArray = object.optJSONArray(name);
        for (int i = 0; i < jsonArray.length(); i++)
        {
            list.add(jsonArray.optString(i));
        }
        return list;
    }
}
