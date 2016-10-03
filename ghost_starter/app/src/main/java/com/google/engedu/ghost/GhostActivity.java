package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.TreeSet;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();
    private TextView textbox;
    private TextView ghostStatus;
    private TextView userWonStatus;
    private TextView computerWonStatus;
    private Button challangeButton;
    private int userWon;
    private int computerWon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        textbox=(TextView)findViewById(R.id.ghostText);
        ghostStatus=(TextView)findViewById(R.id.gameStatus);
        challangeButton=(Button)findViewById(R.id.button);
        userWonStatus=(TextView)findViewById(R.id.textView2);
        computerWonStatus=(TextView)findViewById(R.id.textView3);
        challangeButton.setVisibility(View.INVISIBLE);
        userWon=0;
        computerWon=0;
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new FastDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        onStart(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
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

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText(null);
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        userWon=0;
        computerWon=0;
        computerWonStatus.setText("Computer won:" + computerWon);
        userWonStatus.setText("User won "+userWon);
        challangeButton.setVisibility(View.INVISIBLE);
        return true;
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(userTurn)
        {
            char c = (char) event.getUnicodeChar();
            String s = (String) textbox.getText();
            s = s + c;
            textbox.setText(s);
            userTurn = false;
            if(s.length()>=dictionary.MIN_WORD_LENGTH)
                challangeButton.setVisibility(View.VISIBLE);
            ghostStatus.setText("Computer Turn");
            computerTurn();
        }
        computerWonStatus.setText("Computer won:" + computerWon);
        userWonStatus.setText("User won "+userWon);
        return super.onKeyUp(keyCode, event);
    }

    public void userChallange(View view)
    {
        String ss=(String)textbox.getText();
        boolean chalangeStatus=dictionary.isWord((String)textbox.getText());
        if(chalangeStatus)
        {
                 userTurn=false;
                 ghostStatus.setText("User Turn");
                 userWon++;
        }
        else
        {
                 userTurn=true;
                 ghostStatus.setText("User Turn");
                  userWon++;
                 computerWon++;
        }

        textbox.setText(null);
        computerWonStatus.setText("Computer won:" + computerWon);
        userWonStatus.setText("User won "+userWon);
        if(userTurn)
            computerTurn();
    }

    private void computerTurn() {
        Random rn=new Random();
        if((textbox.getText()!=null)&&((String)textbox.getText()).length()>=0) {
            boolean chalangeStatus = dictionary.isWord((String) textbox.getText());
            if (!chalangeStatus) {
                String s = dictionary.getAnyWordStartingWith((String) textbox.getText());
                if (s == null) {
                    if (textbox.getText().length() < dictionary.MIN_WORD_LENGTH) {
                        int value = rn.nextInt(26);
                        char c = (char) (value + 97);
                        textbox.setText(String.valueOf(c));
                        ghostStatus.setText("User Turn");
                        userTurn = true;
                        if (s.length() >= dictionary.MIN_WORD_LENGTH)
                            challangeButton.setVisibility(View.VISIBLE);
                    } else {
                        computerWon++;
                        textbox.setText(null);
                        userTurn=true;
                        ghostStatus.setText("User Turn");
                    }
                } else {
                    if (s.length() >= dictionary.MIN_WORD_LENGTH)
                        challangeButton.setVisibility(View.VISIBLE);
                    textbox.setText(s);
                    ghostStatus.setText("User Turn");
                    userTurn = true;
                }
            } else {
                userTurn=true;
                ghostStatus.setText("User Turn");
                textbox.setText(null);
                computerWon++;
            }
        }
        else
        {
            int value = rn.nextInt(26);
            char c = (char) (value + 97);
            textbox.setText(textbox.getText() + String.valueOf(c));
            ghostStatus.setText("User Turn");
            userTurn = true;
            if (textbox.getText().length() >= dictionary.MIN_WORD_LENGTH)
                challangeButton.setVisibility(View.VISIBLE);
        }
        computerWonStatus.setText("Computer won:" + computerWon);
        userWonStatus.setText("User won "+userWon);
    }

    public void winResults()
    {

    }
}
