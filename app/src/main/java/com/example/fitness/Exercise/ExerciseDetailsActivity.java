package com.example.fitness.Exercise;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.fitness.R;

import pl.droidsonroids.gif.GifImageView;

public class ExerciseDetailsActivity extends AppCompatActivity {

    String buttonvalue;
    TextView textView;
    GifImageView gifImageView;
    CountDownTimer countDownTimer;
    Button start, timer;
    boolean isTimerRunning = false;
    long timeLeftInMillis = 60000; // Default to 1 minute
    int currentExercise;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gifImageView = findViewById(R.id.gifimage);
        textView = findViewById(R.id.texthowtodothis);
        start = findViewById(R.id.starttimer);
        timer = findViewById(R.id.timer);

        buttonvalue = getIntent().getStringExtra("value");
        currentExercise = Integer.parseInt(buttonvalue);

        setExerciseContent(currentExercise);

        start.setOnClickListener(v -> startOrStopTimer());
    }

    private void setExerciseContent(int value) {
        switch (value) {
            case 1:
                gifImageView.setImageResource(R.drawable.exersice_1);
                textView.setText(R.string.pose1);
                break;
            case 2:
                gifImageView.setImageResource(R.drawable.exersice_2);
                textView.setText(R.string.pose2);
                break;
            case 3:
                gifImageView.setImageResource(R.drawable.exersice_3);
                textView.setText(R.string.pose3);
                break;
            case 4:
                gifImageView.setImageResource(R.drawable.exersice_4);
                textView.setText(R.string.pose4);
                break;
            case 5:
                gifImageView.setImageResource(R.drawable.exersice_5);
                textView.setText(R.string.pose5);
                break;
            case 6:
                gifImageView.setImageResource(R.drawable.exersice_6);
                textView.setText(R.string.pose6);
                break;
            case 7:
                gifImageView.setImageResource(R.drawable.exersice_7);
                textView.setText(R.string.pose7);
                break;
            case 8:
                gifImageView.setImageResource(R.drawable.exersice_8);
                textView.setText(R.string.pose8);
                break;
            case 9:
                gifImageView.setImageResource(R.drawable.exersice_9);
                textView.setText(R.string.pose9);
                break;
            case 10:
                gifImageView.setImageResource(R.drawable.exersice_10);
                textView.setText(R.string.pose10);
                break;
            case 11:
                gifImageView.setImageResource(R.drawable.exersice_11);
                textView.setText(R.string.pose11);
                break;
            case 12:
                gifImageView.setImageResource(R.drawable.exersice_12);
                textView.setText(R.string.pose12);
                break;
            case 13:
                gifImageView.setImageResource(R.drawable.exersice_13);
                textView.setText(R.string.pose13);
                break;
            case 14:
                gifImageView.setImageResource(R.drawable.exersice_14);
                textView.setText(R.string.pose14);
                break;
            case 15:
                gifImageView.setImageResource(R.drawable.exersice_15);
                textView.setText(R.string.pose15);
                break;
            default:
                Toast.makeText(this, "Invalid case value: " + value, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void startOrStopTimer() {
        if (isTimerRunning) {
            stopTimer();
        } else {
            startTimer(timeLeftInMillis);
        }
    }

    private void startTimer(long durationInMillis) {
        countDownTimer = new CountDownTimer(durationInMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                String timeFormatted = String.format("%02d:%02d", minutes, seconds);
                timer.setText(timeFormatted);
            }

            public void onFinish() {
                timer.setText("00:00");
                Toast.makeText(ExerciseDetailsActivity.this, "Exercise Finished", Toast.LENGTH_SHORT).show();
                resetStartButton();
                showRestDialog();
            }
        }.start();
        start.setText("STOP");
        isTimerRunning = true;
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        resetStartButton();
    }

    private void resetStartButton() {
        start.setText("START");
        isTimerRunning = false;
    }

    private void showRestDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_rest, null);
        builder.setView(dialogView);
        builder.setCancelable(false);

        TextView timerText = dialogView.findViewById(R.id.timer_text);
        ProgressBar progressBar = dialogView.findViewById(R.id.progress_bar);
        TextView skipText = dialogView.findViewById(R.id.skip_text);

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        new CountDownTimer(20000, 1000) {
            public void onTick(long millisUntilFinished) {
                int secondsRemaining = (int) (millisUntilFinished / 1000);
                timerText.setText(String.valueOf(secondsRemaining));
                progressBar.setProgress(secondsRemaining);
            }

            public void onFinish() {
                dialog.dismiss();
                proceedToNextExercise();
            }
        }.start();

        skipText.setOnClickListener(v -> {
            dialog.dismiss();
            proceedToNextExercise();
        });
    }

    private void proceedToNextExercise() {
        if (currentExercise < 15) {
            currentExercise++;
            Intent intent = new Intent(ExerciseDetailsActivity.this, ExerciseDetailsActivity.class);
            intent.putExtra("value", String.valueOf(currentExercise));
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "You have completed all exercises for today!", Toast.LENGTH_SHORT).show();


        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
