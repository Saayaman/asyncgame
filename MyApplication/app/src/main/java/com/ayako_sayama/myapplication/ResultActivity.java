package com.ayako_sayama.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "ResultActivity";

    TextView result;
    TextView writeScoreOne;
    TextView writeScoreTwo;
    Button nextGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        Log.i(TAG, "The Scores: "+ b.get("p1Score"));

        result = (TextView)findViewById(R.id.resultTxt);
        writeScoreOne = (TextView)findViewById(R.id.pOneScore);
        writeScoreTwo = (TextView)findViewById(R.id.pTwoScore);
        nextGame = (Button)findViewById(R.id.nextGame);


        int scoreOne = b.getInt("p1Score");
        int scoreTwo = b.getInt("p2Score");

        if (scoreOne > scoreTwo){
            result.setText("Player One Wins!!");
        }else if (scoreTwo > scoreOne){
            result.setText("Player Two Wins!");
        } else {
            result.setText("Its a Tie!");
        }

        writeScoreOne.setText(scoreOne+"");
        writeScoreTwo.setText(scoreTwo+"");

    }

    public void newGame(View view) {
        Intent newGame = new Intent(this, MainActivity.class);
        startActivity(newGame);
    }

    public void toTop(View view) {
        Intent toTop = new Intent(this, OpeningActivity.class);
        startActivity(toTop);
    }
}
