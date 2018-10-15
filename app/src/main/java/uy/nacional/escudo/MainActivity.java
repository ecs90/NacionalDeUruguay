package uy.nacional.escudo;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Timer timer;
    LinearLayout li;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        setContentView(R.layout.activity_main);

        li = (LinearLayout)findViewById(R.id.activity_main);
        li.setBackgroundColor(Color.parseColor("#003366"));

        timer = new Timer();

        MyTimerTask myTimerTask=new MyTimerTask();
        //schedule to change background color every 5 seconds
        timer.schedule(myTimerTask,5000,5000);
    }

    public class MyTimerTask extends TimerTask {
        String[] colors = {
                "#003366", //Dark Blue
                "#fe1c0c", //Red
                "#ffffff"

        };
        int count = 1;

        public String getColor(int bgColor){
            return colors[bgColor];
        }

        @Override
        public void run() {
            //Since we want to change something which is on hte UI
            //so we have to run on UI thread..
            runOnUiThread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void run() {
                    int color = Color.parseColor(getColor(count));
                    li.setBackgroundColor(color);
                    getWindow().setStatusBarColor(color);

                    if (count < 2) {
                        count++;

                    } else {
                        count = 0;
                    }
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //you have to stop the timer when is your activity has stopped
        //otherwise it will keep running in the background
        timer.cancel();
    }
}
