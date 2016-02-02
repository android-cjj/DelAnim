package com.cjj.delanim;

import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView del_img;
    private Handler mHandler = new Handler();
    private Animation zoom;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo);

        tv = (TextView) this.findViewById(R.id.tv);

        del_img = (ImageView) this.findViewById(R.id.delete_image);
        del_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDlg();
            }
        });

        zoom = AnimationUtils.loadAnimation(this, R.anim.zoom);
    }

    private void showDeleteDlg() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确定要删除这个东东吗？");
        builder.setNegativeButton("cancel", null);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startDelAnim();
            }
        });
        builder.show();
    }

    private void startDelAnim() {
        del_img.setImageResource(R.drawable.del_btn_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) del_img.getDrawable();
        animationDrawable.start();
        int duration = 0;
        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
            duration += animationDrawable.getDuration(i);
        }
        mHandler.postDelayed(new Runnable() {
            public void run() {
                tv.startAnimation(zoom);
                zoom.setAnimationListener(new AnimListener() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        super.onAnimationEnd(animation);
                        del_img.setImageResource(R.drawable.batch_delete_back);
                    }
                });
            }
        }, duration);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
