package com.ndaivveyean.ad.tictactao;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int activePlayer = 0;
    int [] gameState= {2,2,2,2,2,2,2,2,2};
    boolean twoplayer=true;
    boolean player_play=true;
    MediaPlayer winplayer=MediaPlayer.create(this,R.raw.cheer);
    MediaPlayer drawplayer=MediaPlayer.create(this,R.raw.kidlaugh);
    MediaPlayer loseplayer=MediaPlayer.create(this,R.raw.mine);
    public void reset(View view)
    {
        ImageView current;
        activePlayer = 0;
        for(int i=0;i<gameState.length;i++)
        {
            gameState[i]=2;
            String temp="imageView"+new Integer(i).toString();
            int resid=getResources().getIdentifier(temp,"id",getPackageName());
            current= (ImageView)findViewById(resid);
            current.setImageDrawable(null);
        }
        if(twoplayer) {
            if(player_play) {
                int resid = getResources().getIdentifier("currentTurn", "id", getPackageName());
                ImageView current1 = (ImageView) findViewById(resid);
                current1.setImageResource(R.drawable.yellow);
                activePlayer=0;
            }
            else
            {
                int resid = getResources().getIdentifier("currentTurn", "id", getPackageName());
                ImageView current1 = (ImageView) findViewById(resid);
                current1.setImageResource(R.drawable.red);
                activePlayer=1;
            }
        }
        else
        {
             if(!player_play)
             {
                 boolean success=false;
                 int computerPosition=0;
                     for (; !success; ) {
                         Random r = new Random();

                         int n = 7 - 0 + 1;
                         int i = r.nextInt(8 - 0) + 0;
                         if (gameState[i] == 2) {
                             success = true;
                             computerPosition = i;
                         }
                     }
                 gameState[computerPosition] = 5;
                 String temp = "imageView" + new Integer(computerPosition).toString();
                 int resid = getResources().getIdentifier(temp, "id", getPackageName());
                 ImageView current1 = (ImageView) findViewById(resid);
                 current1.setTranslationY(-1000f);
                 current1.setImageResource(R.drawable.red);
                 current1.animate().translationYBy(1000).rotation(360).setDuration(1000);
             }
            int resid = getResources().getIdentifier("currentTurn", "id", getPackageName());
            ImageView current1 = (ImageView) findViewById(resid);
            current1.setImageResource(R.drawable.yellow);
            activePlayer=0;
        }
    }
    public void withcomputer(View view)
    {
        Button current;
        if(twoplayer) {
            twoplayer = false;
            Button temp = (Button) view;
            temp.setBackgroundColor(Color.GREEN);
            int resid = getResources().getIdentifier("twoplayer", "id", getPackageName());
            current = (Button) findViewById(resid);
            current.setBackgroundColor(Color.LTGRAY);
            reset(view);
        }
    }
    public void twoplayer(View view)
    {
        Button current;
        if(!twoplayer) {
            twoplayer = true;
            Button temp = (Button) view;
            temp.setBackgroundColor(Color.GREEN);
            int resid = getResources().getIdentifier("computer", "id", getPackageName());
            current = (Button) findViewById(resid);
            current.setBackgroundColor(Color.LTGRAY);
            reset(view);
        }
    }
    public void dropIn(View view)
    {
        boolean checkwining=false;
        ImageView counter = (ImageView)view;
        int counterTag=Integer.parseInt(counter.getTag().toString());
        if(gameState[counterTag]==2) {
            gameState[counterTag]=activePlayer;
            counter.setTranslationY(-1000f);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                counter.animate().translationYBy(1000).rotation(360).setDuration(1000);
                if(twoplayer) {
                    activePlayer = 1;
                }
                else
                {
                    activePlayer=5;
                }
                checkwining=checkWinningPosition(counterTag,gameState);
                if(checkwining)
                {
                    if(twoplayer)
                    {
                        player_play=!player_play;
                        displayResult("Player Yellow Won, Please play again!!");
                        winplayer.start();
                        return ;
                    }
                    else
                    {
                        player_play=!player_play;
                        displayResult("You Won, Please play again!!");
                        winplayer.start();
                        return ;
                    }
                }
            }
            else if(activePlayer==1){
                counter.setImageResource(R.drawable.red);
                counter.animate().translationYBy(1000).rotation(360).setDuration(1000);
                if(twoplayer)
                activePlayer = 0;
                checkwining=checkWinningPosition(counterTag,gameState);
                if(checkwining) {
                    counter.animate().translationYBy(1000).rotation(360).setDuration(1000);
                    player_play=!player_play;
                    displayResult("Player Red Won, Please play again!!");
                    loseplayer.start();
                    return ;
                }
            }

            if(checkComplete(gameState))
            {
                player_play=!player_play;
                displayResult("Draw, Please play again!!");
                drawplayer.start();
                return ;
            }
            if(twoplayer)
            {
                if(activePlayer==1) {
                    int resid = getResources().getIdentifier("currentTurn", "id", getPackageName());
                    ImageView current1 = (ImageView) findViewById(resid);
                    current1.setImageResource(R.drawable.red);
                }
                else
                {
                    int resid = getResources().getIdentifier("currentTurn", "id", getPackageName());
                    ImageView current1 = (ImageView) findViewById(resid);
                    current1.setImageResource(R.drawable.yellow);
                }
            }
            else {
                if (!checkComplete(gameState)) {
                    boolean success = false;
                    boolean oppwinning=false;
                    boolean computer_winning=false;
                    int computerPosition = 0;

                        for (int i = 0; i < gameState.length; i++) {
                            if (gameState[i] == 2) {
                                gameState[i] = 5;
                                checkwining = checkWinningPosition(i, gameState);
                                if (checkwining) {
                                    computer_winning = true;
                                    computerPosition = i;
                                    gameState[i] = 5;
                                    break;
                                }
                                gameState[i]=2;
                            }
                        }

                    if(!computer_winning) {
                        for (int i = 0; i < 9; i++) {
                            if (gameState[i] == 2) {
                                gameState[i] = 0;
                                checkwining = checkWinningPosition(i, gameState);
                                if (checkwining) {
                                    oppwinning = true;
                                    computerPosition = i;
                                    gameState[i] = 2;
                                    break;
                                }
                                gameState[i] = 2;
                            }
                        }
                    }

                    if(!computer_winning && !oppwinning) {
                        for (; !success; ) {
                            Random r = new Random();

                            int n = 7 - 0 + 1;
                            int i = r.nextInt(8 - 0) + 0;
                            if (gameState[i] == 2) {
                                success = true;
                                computerPosition = i;
                            }
                        }
                    }
                    gameState[computerPosition] = 5;
                    String temp = "imageView" + new Integer(computerPosition).toString();
                    int resid = getResources().getIdentifier(temp, "id", getPackageName());
                    ImageView current = (ImageView) findViewById(resid);
                    current.setTranslationY(-1000f);
                    current.setImageResource(R.drawable.red);
                    current.animate().translationYBy(1000).rotation(360).setDuration(1000);
                    checkwining = checkWinningPosition(computerPosition, gameState);
                    if (checkwining) {
                        player_play=!player_play;
                        displayResult("Computer Won, Please play again!!");
                        loseplayer.start();
                        return ;
                    }
                    activePlayer = 0;
                }
                if (checkComplete(gameState)) {
                    player_play=!player_play;
                    displayResult("Draw, Please play again!!");
                    drawplayer.start();
                    return ;
                }
            }
        }

    }
    public void displayResult(String result)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Game Over");
        builder.setMessage(result);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               reset(null);
            }
        });
        // Create the AlertDialog object and return it
        builder.create().show();
    }
    public boolean checkComplete(int[] gameState)
    {
        for (int i=0;i<gameState.length;i++)
        {
            if(gameState[i]==2)
                return false;
        }
        return true;
    }
    public boolean checkWinningPosition(int counterTag,int[] gameState)
    {
        int cur_element=gameState[counterTag];


               if(counterTag+2<=8 && (counterTag!=2) && (counterTag!=5)&& (counterTag!=1) && (counterTag!=4)) {
                   if (gameState[counterTag + 1] == gameState[counterTag + 2] && gameState[counterTag + 1] == cur_element) {
                       return true;
                   }
               }
               if(counterTag-2>=0 && (counterTag!=3) && (counterTag!=6) ) {

                   if (gameState[counterTag - 1] == gameState[counterTag - 2] && gameState[counterTag - 1] == cur_element) {
                       return true;
                   }
               }
               if((counterTag+1<=8 && counterTag-1>=0) && ( (counterTag==1) || (counterTag==4) || (counterTag==7))) {
                   System.out.println(gameState[counterTag - 1]+" "+gameState[counterTag + 1 ]+" "+cur_element);
                   if (gameState[counterTag + 1] == gameState[counterTag - 1] && gameState[counterTag - 1] == cur_element) {
                       return true;
                   }
               }
               if(counterTag==0 || counterTag==2 || counterTag==1)
               {
                   if (gameState[counterTag + 3] == gameState[counterTag + 6] && gameState[counterTag + 3] == cur_element) {
                       return true;
                   }
               }
               if(counterTag==3 || counterTag==5 || counterTag==4)
               {
                   if (gameState[counterTag + 3] == gameState[counterTag - 3] && gameState[counterTag - 3] == cur_element) {
                        return true;
                    }
               }
               if(counterTag==6 || counterTag==8 || counterTag==7)
               {
                    if (gameState[counterTag - 3] == gameState[counterTag - 6] && gameState[counterTag - 3] == cur_element) {
                           return true;
                    }
               }

               if(counterTag%2==0)
               {
                   if(counterTag==2)
                   {
                       if (gameState[counterTag + 2] == gameState[counterTag + 4] && gameState[counterTag + 2] == cur_element) {
                           return true;
                       }
                   }
                   else if(counterTag==4)
                   {
                       if (gameState[counterTag + 2] == gameState[counterTag - 2] && gameState[counterTag + 2] == cur_element) {
                           return true;
                       }
                   }
                   else if(counterTag==6)
                   {
                       if (gameState[counterTag - 2] == gameState[counterTag - 4] && gameState[counterTag - 2] == cur_element) {
                           return true;
                       }
                   }
                   else if(counterTag==8)
                   {
                       if (gameState[counterTag - 4] == gameState[counterTag - 8] && gameState[counterTag - 4] == cur_element) {
                           return true;
                       }
                   }
                   else if(counterTag==0)
                   {
                       if (gameState[counterTag + 4] == gameState[counterTag + 8] && gameState[counterTag + 4] == cur_element) {
                           return true;
                       }
                   }
               }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        int resid=getResources().getIdentifier("twoplayer","id",getPackageName());
        Button current= (Button)findViewById(resid);
        current.setBackgroundColor(Color.GREEN);
        resid=getResources().getIdentifier("currentTurn","id",getPackageName());
        ImageView current1= (ImageView)findViewById(resid);
        current1.setImageResource(R.drawable.yellow);

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
