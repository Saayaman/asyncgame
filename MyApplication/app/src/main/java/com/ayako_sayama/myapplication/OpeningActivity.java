package com.ayako_sayama.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

public class OpeningActivity extends AppCompatActivity {

    private NumberPicker picker;
    private Dialog dialog;
    TextView gameTime;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    int sharedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        gameTime = (TextView)findViewById(R.id.gameTime);
        prefs = getSharedPreferences("user", Context.MODE_PRIVATE);
        setNumText();
    }


    private void setNumText() {
        sharedValue = prefs.getInt("user",20);
        gameTime.setText("Gametime is "+sharedValue+ " seconds");
    }

    public void startGame(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void showDialog(View view) {
        AlertDialog.Builder numDialog = new AlertDialog.Builder(this);
        numDialog.setCancelable(false);

        picker = new NumberPicker(this);
        picker.setMinValue(10);
        picker.setMaxValue(60);
        picker.setValue(sharedValue);

        final FrameLayout parent = new FrameLayout(this);
        parent.addView(picker, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT,
                Gravity.CENTER));
        numDialog.setView(parent);

        numDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int num = picker.getValue();
                editor = prefs.edit();
                editor.putInt("user", num);
                editor.commit();
                dialog.dismiss();
                setNumText();
            }
        });

        numDialog.setNegativeButton("Cancel", null);

        //builderをcreateして、showしないといけない。
        //意味はわからなくてもいい、だだ実行しろ
        dialog = numDialog.create();
        dialog.show();

    }
}
