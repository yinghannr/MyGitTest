package yh.cn.com.myfresco;

import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
//        test
        setContentView(R.layout.activity_main);
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

//        Uri uri = Uri.parse("http://uatsyoa01.crpharm.cn/crpcg/dep4/swgl_4.nsf/(vwIndiDocs)/9ADA843709B0FBB648257FF40014157E/$file/131132867589271227NY.jpg");
        Uri uri = Uri.parse("http://uatoa.crpharm.cn/crhc/dep1/fwgl_1.nsf/(vwIndiDocs)/058DDFE2564055A648257FEF0030A831/$file/131128734890549472Y.png");
//        Uri uri = Uri.parse("https://raw.githubusercontent.com/facebook/fresco/gh-pages/static/logo.png");
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.my_image_view);
        draweeView.setImageURI(uri);

        initData();
    }

    private void initData() {
        testSchedule();
//        testThread();

//        getLinuxCoreInfo(this,this.getPackageName());
    }

    private void testSchedule() {
        final String format = "hh:mm:ss";
        long time = System.currentTimeMillis();
        String currentTime =  getDate(time, format);
        displayBriefMemory(currentTime);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
//                long time = System.currentTimeMillis();
//                String currentTime = getDate(time, format);
//                displayBriefMemory(currentTime);
                testSchedule();
            }
        }, 2000);
    }

    private void testThread() {
        final String format = "hh:mm:ss";
        long time = System.currentTimeMillis();
        String currentTime = getDate(time, format);
        displayBriefMemory(currentTime);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                testThread();
            }
        }, 2000);


    }

    private void displayBriefMemory(String currentTime) {

        final ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);

        Log.i("内存", currentTime + "----系统剩余内存:" + Formatter.formatFileSize(this, info.availMem));

//        Log.i("内存","系统是否处于低内存运行："+info.lowMemory);
//
//        Log.i("内存","当系统剩余内存低于"+info.threshold+"时就看成低内存运行");

    }


    /**
     * 根据传入的time 转换为指定输出的格式 ，年月日，年-月-日 小时-分-秒
     *
     * @param time   123233331111 long类型时间
     * @param format yyyy-MM-dd" 日期格式
     * @return 2015-10-11
     */
    public static String getDate(long time, String format) {
        Date date = new Date(time);
        String strs = "";
        try {
            //yyyy表示年MM表示月dd表示日
            //yyyy-MM-dd是日期的格式，比如2015-12-12如果你要得到2015年12月12日就换成yyyy年MM月dd日
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            //进行格式化
            strs = sdf.format(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }


    /**
     * 方法6：无意中看到乌云上有人提的一个漏洞，Linux系统内核会把process进程信息保存在/proc目录下，使用Shell命令去获取的他，再根据进程的属性判断是否为前台
     *
     * @param packageName 需要检查是否位于栈顶的App的包名
     */
    public static boolean getLinuxCoreInfo(Context context, String packageName) {

        List<AndroidAppProcess> processes = ProcessManager.getRunningForegroundApps(context);
        for (AndroidAppProcess appProcess : processes) {
            Log.e("进程", processes.size() + "----" + appProcess.getPackageName());

            if (appProcess.getPackageName().equals(packageName) && appProcess.foreground) {
                return true;
            }
        }
        return false;

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
