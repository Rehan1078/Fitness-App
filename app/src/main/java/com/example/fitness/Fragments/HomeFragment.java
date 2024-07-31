package com.example.fitness.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.fitness.BMIActivity;
import com.example.fitness.Exercise.ExerciseActivity;
import com.example.fitness.Food.FoodActivity;
import com.example.fitness.R;

public class HomeFragment extends Fragment {
    Button beforeage18 , Afterage18 ;
    LinearLayout linearlayoutbeforeage18,linearlayoutafterage18,linearlayoutfood,linearLayoutBMI;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        beforeage18 = view.findViewById(R.id.startbeforeage);
        Afterage18 = view.findViewById(R.id.startafter18);
        linearlayoutbeforeage18 = view.findViewById(R.id.linearlayoutbeforeage18);
        linearlayoutafterage18 = view.findViewById(R.id.linearlayoutafterage18);
        linearlayoutfood = view.findViewById(R.id.linearlayoutfood);
        linearLayoutBMI = view.findViewById(R.id.linearlayoutBMI);

        beforeage18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeage18();
            }
        });
        Afterage18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afterage18();
            }
        });
        linearlayoutfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                food();
            }
        });
        linearlayoutafterage18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afterage18();
            }
        });
        linearlayoutbeforeage18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeage18();
            }
        });
        linearLayoutBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BMI();
            }
        });




        return view;
    }

    public void BMI() {
        startActivity(new Intent(getActivity(), BMIActivity.class));
    }

    public void food() {
        startActivity(new Intent(getActivity(), FoodActivity.class));
    }

    public void beforeage18() {
        startActivity(new Intent(getActivity(), ExerciseActivity.class));
    }

    public void afterage18() {
        startActivity(new Intent(getActivity(), ExerciseActivity.class));
    }}

