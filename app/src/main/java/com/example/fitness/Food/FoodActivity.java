package com.example.fitness.Food;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitness.MainActivity;
import com.example.fitness.R;

public class FoodActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food);
        // Toast.makeText(this, "FoodActivity", Toast.LENGTH_SHORT).show();


        listView = findViewById(R.id.listview);
        String[] tstory = getResources().getStringArray(R.array.title_story);
        final String[] dstory = getResources().getStringArray(R.array.details_story);
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.rows_of_food_activity,R.id.textview, tstory);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String t = dstory[position];
            Intent intent = new Intent(FoodActivity.this, FoodDetailsActivity.class);
            intent.putExtra("story", t);
            startActivity(intent);
        });
    }

    public void onclickfoodback(View view) {
        Intent intent = new Intent(FoodActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // Do nothing to disable back press
    }
}
