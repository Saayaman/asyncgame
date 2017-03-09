package com.ayako_sayama.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "preferences";
    TextView time;
    Player syncOne;
    Player syncTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTimer() ;

        syncOne = new Player(findViewById(R.id.playerOne));
        syncOne.randomAnswer();
        syncOne.shuffleNumbers();
        syncOne.setClick();


        syncTwo = new Player(findViewById(R.id.playerTwo));
        syncTwo.randomAnswer();
        syncTwo.shuffleNumbers();
        syncTwo.setClick();
    }

    private void setTimer() {
        time = (TextView)findViewById(R.id.txtTime);

        //ここでshared preferencesを読み込む！
        SharedPreferences prefs = getSharedPreferences("user",MODE_PRIVATE);
        int gameTime = prefs.getInt("user",14);

        // gametime = 20だとしたら、20000で20秒という意味
        final CountDownTimer timer = new CountDownTimer(gameTime*1000, 1000) {
            public void onTick(long millisUntilFinished) {
                time.setText("Time: "+ millisUntilFinished / 1000);
            }
            public void onFinish() {
                time.setText("Time UP!!");
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                int p1Score = syncOne.getScore();
                int p2Score = syncTwo.getScore();
                intent.putExtra("p1Score", p1Score);
                intent.putExtra("p2Score", p2Score);
                startActivity(intent);
            }
        };
        timer.start();
    }

}
