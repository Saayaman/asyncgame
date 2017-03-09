package com.ayako_sayama.myapplication;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ayako_sayama on 2017-03-08.
 */

public class Player {
    private static final String TAG = "MainActivity";
    GuessRandom guessRandom;
    int randomAnswer;
    int rate = 1000;
    int minValue = 0;

    int max = 10000000;
    int min = 1;

    View player;

    View btnOne;
    View btnTwo;
    View btnThree;
    View btnFour;

    Button btnNext;

    TextView score;
    TextView cal;
    int sccore = 0;

    int valueOne;
    int valueTwo;
    int valueThree;
    int valueFour;

    public Player(View rootView) {
        this.player = rootView;
        sccore = 0;

        btnOne = player.findViewById(R.id.btnOne);
        btnTwo = player.findViewById(R.id.btnTwo);
        btnThree = player.findViewById(R.id.btnThree);
        btnFour = player.findViewById(R.id.btnFour);

        btnNext = (Button) player.findViewById(R.id.btnNext);
        btnNext.setVisibility(View.INVISIBLE);

        score = (TextView) player.findViewById(R.id.txtScore);
        cal = (TextView) player.findViewById(R.id.calculation);
    }

    public int getScore() {
        return sccore;
    }

    public void setClick() {

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNext.setVisibility(View.INVISIBLE);
                enAbleButtons();
                shuffleNumbers();
                randomAnswer();
            }
        });
        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessRandom = new GuessRandom(1);
                btnOne.setPressed(true);
                guessRandom.execute();
            }
        });
        btnTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessRandom = new GuessRandom(2);
                btnTwo.setPressed(true);
                guessRandom.execute();
            }
        });
        btnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessRandom = new GuessRandom(3);
                guessRandom.execute();
            }
        });
        btnFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guessRandom = new GuessRandom(4);
                guessRandom.execute();
            }
        });
    }

    private void enAbleButtons() {
        btnOne.setEnabled(true);
        btnTwo.setEnabled(true);
        btnThree.setEnabled(true);
        btnFour.setEnabled(true);
    }

    public void randomAnswer() {
        randomAnswer = (int) (Math.random() * max + min);
        Log.i(TAG, "onCreate: "+ randomAnswer);
    }

    public void shuffleNumbers() {

        int parts = 4;
        Integer[] array = new Integer[parts];
        int size = array.length;
        Log.i(TAG, "array.length is: "+size);

        int x = 0;

        for(int i = 0; i < parts; i++){
            x = x + (max/parts);
            int t = (int) (Math.random()*parts);

            while (array[t] != null){
                t = (int) (Math.random()*parts);
                Log.i(TAG, "Random t: while loop "+t);
            }
            array[t] = x;
            Log.i(TAG, "array["+t+"] is "+array[t]);
        }

        valueOne = array[0];
        valueTwo = array[1];
        valueThree = array[2];
        valueFour = array[3];
    }

    public class GuessRandom extends AsyncTask<Void, String, Integer> {

        private int type = 0;
        private int targetNum;

        GuessRandom(int type){
            this.type = type;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            switch (type) {
                case 1:targetNum = valueOne;
                    btnOne.setEnabled(false);
                    break;
                case 2: targetNum = valueTwo;
                    btnTwo.setEnabled(false);
                    break;
                case 3: targetNum = valueThree;
                    btnThree.setEnabled(false);
                    break;
                case 4: targetNum = valueFour;
                    btnFour.setEnabled(false);
                    break;
                default: break;
            }

            minValue = targetNum - (max/4);
            Log.i(TAG, "onPreExecute: minValue= "+minValue+ " targetNum= "+targetNum);
        }

        @Override
        protected void onPostExecute(Integer aLong) {
            super.onPostExecute(aLong);
            Log.i(TAG, "aLong is: "+aLong);
            if(aLong == randomAnswer){
                cal.setText("Found Answer! "+randomAnswer);
                sccore++;
                score.setText(sccore +"");
                btnNext.setVisibility(View.VISIBLE);

            } else {
                cal.setText("No answer here! Try next button.");
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            cal.setText("Calculating..." + values[0]);
        }
        @Override
        protected Integer doInBackground(Void... params) {

            int i;

            outerloop:
            for (i=minValue; i < targetNum; i++){

                if (i % rate == 0){
                    publishProgress(" " + i);
                }
                if (i == randomAnswer){
                    break outerloop;
                }
            }

            return i;
        }

    }
}
