package com.example.fitness.Exercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fitness.R;

public class ExerciseActivity extends AppCompatActivity {


    int[] newArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.toolbaar);
        setSupportActionBar(toolbar);

        newArray = new int[]{
                R.id.id1,
                R.id.id2,
                R.id.id3,
                R.id.id4,
                R.id.id5,
                R.id.id6,
                R.id.id7,
                R.id.id8,
                R.id.id9,
                R.id.id10,
                R.id.id11,
                R.id.id12,
                R.id.id13,
                R.id.id14,
                R.id.id15
        };
    }

    public void ImageButtonClicked(View view) {
        for (int i=0 ;i<newArray.length;i++){
            if(view.getId()==newArray[i]){
                int value = i+1;
                Intent start = new Intent(ExerciseActivity.this, ExerciseDetailsActivity.class);
                start.putExtra("value",String.valueOf(value));
                startActivity(start);
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}