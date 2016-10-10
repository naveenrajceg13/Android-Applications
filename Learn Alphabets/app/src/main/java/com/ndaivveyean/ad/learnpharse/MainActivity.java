package com.ndaivveyean.ad.learnpharse;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String alpha="abcdefghijklmnopqrstuvwxyz";
    char[] alphabet=alpha.toCharArray();
    MediaPlayer mplayer;
    int count=1;
    private Boolean exit=false;

    @Override
    public void onBackPressed() {
         if(exit)
         {
             if(mplayer!=null)
             {
                 mplayer.reset();
             }
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
    public void alphaClicked(View view)
    {
        LinearLayout linearlay =(LinearLayout)findViewById(R.id.topLayout);
        if(mplayer!=null)
        {
            mplayer.reset();
        }
        Button current=(Button)view;
        int tag_id=Integer.parseInt(current.getTag().toString());
        if(tag_id!=26) {
            ImageView imView = (ImageView) findViewById(R.id.imageView);
            String temp1 = Character.toString(alphabet[tag_id]) + Integer.toString((count%3)+1);
            int imageresource_id = getResources().getIdentifier(temp1, "drawable", getPackageName());
            imView.setImageResource(imageresource_id);
            linearlay.setVisibility(View.VISIBLE);
            imView.animate().rotation(360).setDuration(3500).alpha(0.6f).rotationYBy(360);
            String temp = Character.toString(alphabet[tag_id]);
            int resid = getResources().getIdentifier(temp, "raw", getPackageName());
            mplayer = MediaPlayer.create(this, resid);
            mplayer.start();
        }
        else
        {
            if(mplayer!=null)
            {
                mplayer.reset();
            }
            linearlay.setVisibility(View.INVISIBLE);
        }
        //System.out.println(resid);
        count=count+1;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //this.setContentView(R.layout.activity_main);
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
