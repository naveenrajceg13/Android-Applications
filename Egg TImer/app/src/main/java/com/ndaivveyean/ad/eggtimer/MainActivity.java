package com.ndaivveyean.ad.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Boolean counterIsActive=false;
    CountDownTimer countDownTimer;
    Button controllerButton;
    public void reset()
    {
        timerTextView.setText("0:00");
        timerSeekBar.setProgress((int) 30);
        controllerButton.setText("Go");

    }
    public void controlTimer(View view)
    {
        counterIsActive=!counterIsActive;
        controllerButton =(Button)findViewById(R.id.controllerBotton);
        if(counterIsActive) {
            controllerButton.setText("Stop");
            int total_seconds = timerSeekBar.getProgress();
            countDownTimer=new CountDownTimer(total_seconds * 1000 + 100, 1000) {

                @Override
                public void onFinish() {
                    timerTextView.setText("0:00");
                    timerSeekBar.setProgress((int) 0);
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mplayer.start();
                    reset();
                }

                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer((int) (millisUntilFinished / 1000));
                    timerSeekBar.setProgress((int) millisUntilFinished / 1000);
                }
            }.start();
        }
        else
        {
            controllerButton.setText("Go");
            countDownTimer.cancel();
        }
    }
    public void updateTimer(int progress)
    {
        int minutes=(int)progress/60;
        int seconds=progress%60;
        String secondString = Integer.toString(seconds);
        if(seconds<10)
        {
            secondString="0"+Integer.toString(seconds);
        }
        String timer=Integer.toString(minutes)+":"+secondString;
        timerTextView.setText(timer);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        timerSeekBar=(SeekBar)findViewById(R.id.timerSeekBar);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timerTextView = (TextView)findViewById(R.id.timerTextView);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
