package com.sensors.activities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sensors.R;
import com.sensors.adapters.MyPagerAdapter;

import java.util.Vector;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mProximity;

    private ViewPager viewPager;
    private Vector<View> pages;
    private LayoutInflater _layoutInflater;
    private View page1, page2, page3;
    MyPagerAdapter adapter;

    private Integer i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get an instance of the sensor service, and use that to get an instance of
        // a particular sensor.
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        i=0;
        initialize();
    }

    private void initialize() {

        viewPager = (ViewPager) findViewById(R.id.view);
        _layoutInflater = getLayoutInflater();
        pages = new Vector<View>();

        page1 = _layoutInflater.inflate(R.layout.page_one_layout, null);
        page2 = _layoutInflater.inflate(R.layout.page_two_layout, null);
        page3 = _layoutInflater.inflate(R.layout.page_three_layout, null);

        adapter = new MyPagerAdapter(this,pages);

        pages.add(page1);
        pages.add(page2);
        pages.add(page3);

        viewPager.setAdapter(adapter);

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

    @Override
    public void onSensorChanged(SensorEvent event) {
        float distance = event.values[0];

        Log.e("Data -->", String.valueOf(distance));
if(distance==3)
{
    if(i>(pages.size()-2))
    {
        i=0;
    }
    else
    {
        i++;
    }

}

        viewPager.setCurrentItem(i);
        Log.e("i == ",String.valueOf(i));


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

        Log.e("Accuracy Data -->", String.valueOf(accuracy));

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
