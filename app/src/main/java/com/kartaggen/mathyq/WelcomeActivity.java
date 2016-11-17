package com.kartaggen.mathyq;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    private EditText mNickname;
    private TextView mHighscore;
    private Button mEnterGame;
    MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.inspire);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
        mNickname = (EditText)findViewById(R.id.nicknameText);
        mEnterGame = (Button)findViewById(R.id.enterGameButton);
        mHighscore = (TextView)findViewById(R.id.highscore);

        if(mHighscore.getText().toString()!="0") {
            refreshHighScore();
        }
        clicky();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshHighScore();
        clicky();
    }
    protected void clicky(){
        mNickname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String name = mNickname.getText().toString();
                    startGame(name);
                    handled = true;
                }
                return handled;
            }
        });
        mEnterGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNickname.getText().toString();
                startGame(name);
            }
        });
    }
    private void startGame(String name) {
        Intent intent = new Intent(this, LevelActivity.class);
        intent.putExtra(getString(R.string.key_nickname), name);

        startActivity(intent);
    }
    private void refreshHighScore(){
        SharedPreferences prefs = this.getSharedPreferences("prefData", Context.MODE_PRIVATE);
        int highScore = prefs.getInt("highScore", 0);
        if(highScore>Integer.parseInt(mHighscore.getText().toString())){
            mHighscore.setText(highScore+"");
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        mediaPlayer.pause();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = null;

    }
}
