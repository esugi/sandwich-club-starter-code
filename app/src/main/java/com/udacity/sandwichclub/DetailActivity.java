package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;
/*
    TODO:
    1. Check functionality
    2. Make it pretty UI, List Display
    3. Comment
    4. Double check rubrics
    5. Turn in
 */

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String DEFAULT_TEXT = "-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich s) {

        TextView tv_description = findViewById(R.id.description_tv);
        TextView tv_alsoKnownAs = findViewById(R.id.also_known_tv);
        TextView tv_ingredients = findViewById(R.id.ingredients_tv);
        TextView tv_origin = findViewById(R.id.origin_tv);

        if(s.getDescription().isEmpty()) {
            tv_description.setText(DEFAULT_TEXT);
        } else {
            tv_description.setText(s.getDescription());
        }

        if(s.getAlsoKnownAs().isEmpty()) {
            tv_alsoKnownAs.setText(DEFAULT_TEXT);
        } else {
            String aka = s.getAlsoKnownAs().toString();
            tv_alsoKnownAs.setText(aka.substring(1,aka.length()-1));
        }

        if(s.getIngredients().isEmpty()) {
            tv_ingredients.setText(DEFAULT_TEXT);
        } else {
            String ingredients = s.getIngredients().toString();
            tv_ingredients.setText(formatListMultiLines(ingredients));
        }

        if(s.getPlaceOfOrigin().isEmpty()) {
            tv_origin.setText(DEFAULT_TEXT);
        } else {
            tv_origin.setText(s.getPlaceOfOrigin());
        }
    }

    private String formatListMultiLines(String stringList)
    {
        stringList = stringList.substring(1, stringList.length()-1);
        //return stringList.replaceAll(", ", "\n");
        return stringList;
    }
}
