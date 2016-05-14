package com.fatel.mamtv1;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PostureActivity extends AppCompatActivity {
    int mode=1;
    int imgId=0;
    TextView txtDes;
    ImageView imgView;
    AnimationDrawable frameAnimation;
    Button previous;
    Button home;
    Button next;
    View pre;
    int exerciseImg;
    String exerciseDes;
    ArrayList<Posture> postureMode;
    public static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
        {
            mode = bundle.getInt("value");
        }
        setContentView(R.layout.activity_posture);
        txtDes=(TextView) findViewById(R.id.txt);
        imgView=(ImageView) findViewById(R.id.img);
        home = (Button) findViewById(R.id.homebtn);
        next = (Button) findViewById(R.id.nextbtn);
        previous = (Button) findViewById(R.id.previousbtn);
        context=getApplicationContext();

        final PostureCollection postureCollection = PostureCollection.getInstance(this);
        postureMode = postureCollection.getPostureMode(mode, context);
        exerciseImg = (postureMode.get(imgId)).getImage();
        exerciseDes = (postureMode.get(imgId)).getDescription();
        txtDes.setText(exerciseDes);
        imgView.setBackgroundResource(exerciseImg);
        // Get the background, which has been compiled to an AnimationDrawable object.
        frameAnimation = (AnimationDrawable) imgView.getBackground();
        // Start the animation (looped playback by default).
        frameAnimation.start();
        checkVisible();

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (imgId >= 0) {
                    frameAnimation.stop();
                    if(imgId>0)
                        imgId--;
                    checkVisible();
                    exerciseImg = (postureMode.get(imgId)).getImage();
                    exerciseDes = (postureMode.get(imgId)).getDescription();
                    txtDes.setText(exerciseDes);
                    imgView.setBackgroundResource(exerciseImg);
                    // Get the background, which has been compiled to an AnimationDrawable object.
                    frameAnimation = (AnimationDrawable) imgView.getBackground();
                    // Start the animation (looped playback by default).
                    frameAnimation.start();

                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (imgId < postureMode.size()) {
                    frameAnimation.stop();
                    if(imgId < postureMode.size()-1)
                        imgId++;
                    checkVisible();
                    exerciseImg = (postureMode.get(imgId)).getImage();
                    exerciseDes = (postureMode.get(imgId)).getDescription();
                    txtDes.setText(exerciseDes);
                    imgView.setBackgroundResource(exerciseImg);
                    // Get the background, which has been compiled to an AnimationDrawable object.
                    frameAnimation = (AnimationDrawable) imgView.getBackground();
                    // Start the animation (looped playback by default).
                    frameAnimation.start();


                }
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameAnimation.stop();
                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_posture, menu);
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
    public void checkVisible(){
        if (imgId == 0) {
            previous.setVisibility(View.INVISIBLE);
        }
        else {
            previous.setVisibility(View.VISIBLE);
        }
        if(imgId == postureMode.size()-1) {
            next.setVisibility(View.INVISIBLE);
        }
        else{
            next.setVisibility(View.VISIBLE);
        }
    }
}
