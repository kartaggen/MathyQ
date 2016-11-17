package com.kartaggen.mathyq;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class LevelActivity extends AppCompatActivity {
    public static final String TAG = LevelActivity.class.getSimpleName();

    private String mNick;
    private int mScore;
    private EditText mNicknameLevelText;
    private EditText mScoreText;
    private TextView mEquationText;
    private int mNum1;
    private int mNum2;
    private int mAnswer;
    private Button mNextLevelButton;
    private EditText mAnswerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        Random rnd = new Random();

        mNicknameLevelText = (EditText) findViewById(R.id.nicknameLevelText);
        mScoreText = (EditText) findViewById(R.id.scoreText);
        mEquationText = (TextView) findViewById(R.id.equationText);
        mNextLevelButton = (Button) findViewById(R.id.nextLevelButton);
        mAnswerText = (EditText) findViewById(R.id.answerText);

        Intent intent = getIntent();
        mNick = intent.getStringExtra(getString(R.string.key_nickname));

        mScore = 0;
        if (mNick == null) {
            mNick = "No Name";
        }
        mNicknameLevelText.setText(mNick);
        mScoreText.setText("Score:" + mScore);
        mNum1 = rnd.nextInt(100);
        mNum2 = rnd.nextInt(100);
        mAnswer = mNum1 + mNum2;
        mEquationText.setText(mNum1+" + "+mNum2);
        goodNumber();

    }

    private void goodNumber(){
        mNextLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAnswerText.getText().toString() == null || mAnswerText.getText().toString().isEmpty()){
                    Toast.makeText(LevelActivity.this, "Not a valid answer", Toast.LENGTH_SHORT).show();
                    goodNumber();
                }
                else if(Integer.valueOf(mAnswerText.getText().toString()) == mAnswer) {
                    mScore++;
                    loadLevel(mScore);
                }
                else{
                    Toast.makeText(LevelActivity.this, "You have failed with a score of: " + mScore, Toast.LENGTH_LONG).show();
                    saveHighScore();
                    finish();
                }
            }
        });
    }

    private void loadLevel(int score) {

        Random rnd = new Random();

        mNicknameLevelText.setText(mNick);
        mScoreText.setText("Score:" + mScore);
        mAnswerText.setText("");
        mNum1 = rnd.nextInt(100);
        mNum2 = rnd.nextInt(100);
        mAnswer = mNum1 + mNum2;
        mEquationText.setText(mNum1+" + "+mNum2);
        goodNumber();
    }
    private void saveHighScore(){
        SharedPreferences prefs = this.getSharedPreferences("prefData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("highScore", mScore);
        editor.commit();
    }
}
