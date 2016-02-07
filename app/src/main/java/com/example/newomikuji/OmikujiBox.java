package com.example.newomikuji;

import android.hardware.SensorEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.Random;

public class OmikujiBox implements Animation.AnimationListener {

    private long beforeTime;
    private float beforeValue;
    private int number;//くじ番号
    private ImageView imageView;

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public OmikujiBox() {
        this.number = -1;
    }

    public boolean chkShake(SensorEvent event) {

        long now_time = System.currentTimeMillis();
        long diff_time = now_time - this.beforeTime;
        float now_value = event.values[0] + event.values[1];

        if (1500 < diff_time) {

            //前回の値との差からスピードを計算
            float speed = Math.abs(now_value - this.beforeValue) / diff_time * 10000;
            this.beforeTime = now_time;
            this.beforeValue = now_value;

            //50を超えるスピードでシェイクしたとみなす
            if (50 < speed) {
                return true;
            }
        }
        return false;
    }

    public int getNumber() {
        return number;
    }

    public void shake() {
        TranslateAnimation translate = new TranslateAnimation(0, 0, 0, -100);//画像のアニメーション
        translate.setRepeatMode(Animation.REVERSE);//繰り返しモード
        translate.setRepeatCount(5);//繰り返し回数
        translate.setDuration(100);//アニメーション時間

        RotateAnimation rotate = new RotateAnimation(0, -36, this.imageView.getWidth() / 2, this.imageView.getHeight() / 2);
        rotate.setDuration(200);

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(rotate);
        set.addAnimation(translate);

        set.setAnimationListener(this);
        this.imageView.startAnimation(set);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Random rnd = new Random();
        this.number = rnd.nextInt(20);
        this.imageView.setImageResource(R.drawable.omikuji2);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
