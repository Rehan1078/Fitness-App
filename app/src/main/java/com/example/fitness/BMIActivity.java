package com.example.fitness;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BMIActivity extends AppCompatActivity {
    private EditText height;
    private EditText weight;
    private TextView result;
    private TextView resultgoodorbad,textview4;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bmiactivity);

        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        textview4 = findViewById(R.id.textview4);
        result = findViewById(R.id.result);
        resultgoodorbad = findViewById(R.id.resultgoodorbad);
        btn = findViewById(R.id.btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String heightStr = height.getText().toString();
                String weightStr = weight.getText().toString();

                if (!heightStr.isEmpty() && !weightStr.isEmpty()) {
                    float h = Float.parseFloat(heightStr) / 100;
                    float w = Float.parseFloat(weightStr);

                    if (h > 0 && w > 0) {
                        float res = w / (h * h);
                        result.setText(String.format("%.2f", res));
                        updateResultEvaluation(res);
                    } else {
                        result.setText("Invalid input");
                        resultgoodorbad.setText("");
                    }
                } else {
                    result.setText("Please enter values");
                    resultgoodorbad.setText("");
                }
            }
        });
    }

    private void updateResultEvaluation(float bmi) {
        if (bmi < 18.5) {
            resultgoodorbad.setText("Underweight");

        } else if (bmi >= 18.5 && bmi < 24.9) {
            resultgoodorbad.setText("Normal weight");
        } else if (bmi >= 25 && bmi < 29.9) {
            resultgoodorbad.setText("Overweight");
        } else {
            resultgoodorbad.setText("Obesity");
        }
        textview4.setText("YOUR RESULT");
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // Disable back button
        super.onBackPressed();
        finish();
    }
}
