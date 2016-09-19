package com.ndaivveyean.ad.dicegame;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    public int userScores=0;
    public int computerScores=0;
    public int userPlayedTurn=0;
    public int lastUserScore=0;
    public int lastComputerScore=0;
    TextView userScore;
    TextView computerScore;
    TextView turnIs;
    TextView whoWon;
    Button rollButton;
    Button holdButton;
    ImageView ImageDice;
    RelativeLayout secondaryLayout;
    static int minimum=1;
    static int maximum=6;
    boolean computerTurn=false;

    public void userRoll(View view)
    {


        Random rn = new Random();
        int randomNum =  rn.nextInt(6)+1;
        String imageToShow="dice"+new Integer(randomNum).toString();
        Log.i("user",imageToShow);
        int resid = getResources().getIdentifier(imageToShow, "drawable", getPackageName());
        ImageDice.setImageResource(resid);
        ImageDice.animate().rotation(360).setDuration(1000).alpha(1.0f).rotationYBy(360);
        userPlayedTurn=userPlayedTurn+1;
        userScores = userScores + randomNum;
        if(randomNum==1)
        {
            Log.i("last User Score",new Integer(lastUserScore).toString());
            userScores=lastUserScore;
        }
        userScore.setText("User Score: " + new Integer(userScores).toString());
        if(randomNum==1) {
            computerRoll(view);
        }
        if(userScores>=100)
        {
            whoWon.setText("User Won");
            secondaryLayout.setVisibility(View.VISIBLE);
        }
    }
    public void computerRoll(View view)
    {
        rollButton.setVisibility(View.INVISIBLE);
        holdButton.setVisibility(View.INVISIBLE);
        turnIs.setText("Coputer Turn");
        Log.i("numbercomputerturns",new Integer(userPlayedTurn).toString());
        for(int i=0;i<userPlayedTurn;i++)
        {
            for(int ii=0;ii<1000000000*1000000;ii++)
            {

            }

            Random rn = new Random();

            int randomNum =  rn.nextInt(6)+1;
            String imageToShow="dice"+new Integer(randomNum).toString();

            if(randomNum!=1)
            {
                computerScores=computerScores+randomNum;
                if(i==userPlayedTurn-1)
                {
                    lastComputerScore=computerScores;
                }
            }
            else
            {
                computerScores=lastComputerScore;
            }
            int resid = getResources().getIdentifier(imageToShow, "drawable", getPackageName());
            ImageDice.setImageResource(resid);
            computerScore.setText("Computer Score: "+new Integer(computerScores).toString());
            ImageDice.animate().rotation(360).setDuration(1000).alpha(0.6f).rotationYBy(360);
            Log.i("computer",imageToShow);
            if((randomNum==1))
            {
                break;
            }
            if(computerScores>=100) {
                whoWon.setText("Computer Won");
                secondaryLayout.setVisibility(View.VISIBLE);
            }
        }

        userPlayedTurn=0;
        turnIs.setText("User Turn");
        rollButton.setVisibility(View.VISIBLE);
        holdButton.setVisibility(View.VISIBLE);

    }

    public void reset(View view)
    {
        userScores=0;
        computerScores=0;
        lastComputerScore=0;
        lastUserScore=0;
        userPlayedTurn=0;
        turnIs.setText("User Turn");
        userScore.setText("User Score: 0");
        computerScore.setText("Computer Score: 0");
        ImageDice.setImageResource(R.drawable.dice1);
        secondaryLayout.setVisibility(View.INVISIBLE);
    }
    public void holdUser(View view)
    {
        lastUserScore=userScores;
        turnIs.setText("Computer Turn");
        computerRoll(view);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userScore=(TextView)findViewById(R.id.textView1);
        computerScore=(TextView)findViewById(R.id.textView2);
        rollButton=(Button)findViewById(R.id.button1);
        holdButton=(Button)findViewById(R.id.button2);
        ImageDice=(ImageView)findViewById(R.id.imageView);
        turnIs=(TextView)findViewById(R.id.whoPlay);
        whoWon=(TextView)findViewById(R.id.whoWon);
        secondaryLayout=(RelativeLayout)findViewById(R.id.SecondLayout);
        secondaryLayout.setVisibility(View.INVISIBLE);
        turnIs.setText("User Turn");
    }

    private Boolean exit=false;

    @Override
    public void onBackPressed() {
        if(exit)
        {
            finish();
        }
        else
        {
            Toast.makeText(this,"Press Back again to Exit.",Toast.LENGTH_LONG).show();
            exit=true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit=false;
                }
            },5*1000);
        }
    }
}
