package com.jovial.floatingview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class FloatingViewActivity extends AppCompatActivity
        implements View.OnTouchListener , View.OnClickListener{

    private FloatingView mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floating_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mButton = new FloatingView(this);
        mButton.setOnTouchListener(this);
        mButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_floating_view, menu);
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float rawX = event.getRawX();
        float rawY = event.getRawY();
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mButton.onActionDown();
                break;
            case MotionEvent.ACTION_MOVE:
                mButton.onActionMove((int)rawX , (int)rawY);
                break;
            case MotionEvent.ACTION_UP:
                mButton.onActionUp(rawX , rawY);
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if(v instanceof FloatingView){
            mButton.showOpenView();
        }
    }
}
