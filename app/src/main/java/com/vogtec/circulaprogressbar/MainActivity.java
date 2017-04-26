package com.vogtec.circulaprogressbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        CircleProgressBar circleProgressBar = (CircleProgressBar) findViewById(R.id.circle_pb);
//        circleProgressBar.animationToProgress(0,605);
//        CircleView circleView = (CircleView) findViewById(R.id.circle_view);
//        circleView.startCustomAnimation();
//        DashboardView2 dashboardView2 = (DashboardView2) findViewById(R.id.dashboardview2);
//        dashboardView2.setCreditValueWithAnim(600);
        DashboardView dashboardView = (DashboardView) findViewById(R.id.dashboardview);
        dashboardView.setSpeed(59);

    }
}
