package com.example.newomikuji;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity implements SensorEventListener {

    private Vibrator vibrator;

    private SensorManager manager;

    //おみくじ棚の配列
    private OmikujiParts[] omikujiShelf = new OmikujiParts[20];

    private OmikujiBox omikujibox = new OmikujiBox();

    @Override
    protected void onPause() {
        super.onPause();
        this.manager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Sensor> sensors = this.manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (0 < sensors.size()) {
            this.manager.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.omikuji);

        this.vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        this.manager = (SensorManager) getSystemService(SENSOR_SERVICE);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean value = pref.getBoolean("button", false);

        Button btn = (Button) findViewById(R.id.button);
        btn.setVisibility(value ? View.VISIBLE : View.INVISIBLE);

        this.omikujibox.setImageView((ImageView) findViewById(R.id.imageView));

        //おみくじ棚の準備
        for (int i = 0; i < 20; i++) {
            this.omikujiShelf[i] = new OmikujiParts(R.drawable.result2, R.string.contents1);
        }
        this.omikujiShelf[0].drawID = R.drawable.result1;
        this.omikujiShelf[0].fortuneID = R.string.contents2;

        this.omikujiShelf[1].drawID = R.drawable.result3;
        this.omikujiShelf[1].fortuneID = R.string.contents9;

        this.omikujiShelf[2].fortuneID = R.string.contents3;
        this.omikujiShelf[3].fortuneID = R.string.contents4;
        this.omikujiShelf[4].fortuneID = R.string.contents5;
        this.omikujiShelf[5].fortuneID = R.string.contents6;

//        TextView tv = (TextView) findViewById(R.id.hello_view);
//        //くじ番号の取得
//        Random rnd = new Random();
//        int number = rnd.nextInt(20);
//        //おみくじ棚の準備
//        String[] omikujiShelf = new String[20];
//        for (int i = 0; i < 20; i++) {
//            omikujiShelf[i] = "吉";
//        }
//        omikujiShelf[0] = "大吉";
//        omikujiShelf[19] = "凶";
//
//        //おみくじ棚から取得
//        String str = omikujiShelf[number];
//
//        tv.setText(str);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        Toast toast = Toast.makeText(this,item.getTitle(),Toast.LENGTH_LONG);
//        toast.show();
        if (item.getItemId() == R.id.item1) {
            Intent intent = new Intent(this, OmikujiPreferenceActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (0 < this.omikujibox.getNumber()) {

                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                if (pref.getBoolean("vibration", true)) {
                    this.vibrator.vibrate(50);
                }
                this.drawResult();
            }
        }
        return super.onTouchEvent(event);
    }

    public void drawResult() {

        //おみくじ棚から取得
        OmikujiParts op = omikujiShelf[omikujibox.getNumber()];

        //レイアウトを運勢表示に変更
        setContentView(R.layout.fortune);

        //画像とテキストを変更
        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
        imageView.setImageResource(op.drawID);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setTextColor(Color.BLACK);
        textView.setText(op.fortuneID);
    }

    public void onButtonClick(View v) {

        this.omikujibox.shake();

//        TranslateAnimation translate = new TranslateAnimation(0,0,0,-100);//画像のアニメーション
//        translate.setRepeatMode(Animation.REVERSE);//繰り返しモード
//        translate.setRepeatCount(5);//繰り返し回数
//        translate.setDuration(100);//アニメーション時間
//
//        ImageView imageview = (ImageView)findViewById(R.id.imageView);
//
//        RotateAnimation rotate = new RotateAnimation(0,-36,imageview.getWidth() / 2,imageview.getHeight() / 2);
//        rotate.setDuration(200);
//
//        AnimationSet set = new AnimationSet(true);
//        set.addAnimation(rotate);
//        set.addAnimation(translate);
//
//        imageview.startAnimation(set);
//        ImageView image = new ImageView(this);
//        image.setImageResource(R.drawable.result1);
//        setContentView(image);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (this.omikujibox.chkShake(event)) {
            if (this.omikujibox.getNumber() < 0) {
                this.omikujibox.shake();
            }
        }

//        float value = event.values[0];
//        if (7 < value) {
//            Toast toast = Toast.makeText(this,"加速度：" + value, Toast.LENGTH_LONG);
//            toast.show();
//        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
