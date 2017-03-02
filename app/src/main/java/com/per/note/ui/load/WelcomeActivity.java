package com.per.note.ui.load;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.per.note.R;
import com.per.note.bean.Version;
import com.per.note.ui.main.MainActivity;
import com.per.note.utils.AppUtils;
import com.per.note.utils.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class WelcomeActivity extends AppCompatActivity {
    //public static String SDPATH=Environment.getExternalStorageDirectory().getAbsolutePath();
    private ViewPager mViewPaer;
    private LinearLayout mIndications;

    private int mIndex = 0;
    private int[] drawables = new int[]{
            R.drawable.s1, R.drawable.s2, R.drawable.s3, R.drawable.s4
    };
    private View[] pagerViews = new View[drawables.length];

    private void initView() {

        for (int i = 0; i < drawables.length; i++) {
            pagerViews[i] = new View(this);
            pagerViews[i].setBackgroundResource(drawables[i]);
            if (i == drawables.length - 1) {
                pagerViews[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        writeReadFlag();
                        finish();
                    }
                });
            }
            View view = new View(this);
            if (i == 0) view.setBackgroundResource(R.drawable.page_now);
            else view.setBackgroundResource(R.drawable.page);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(30, 30);
            lp.leftMargin = (10);
            view.setLayoutParams(lp);
            mIndications.addView(view, i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mViewPaer = (ViewPager) findViewById(R.id.welcome_viewpager);
        mIndications = (LinearLayout) findViewById(R.id.indicators_LinearLayout);
        initView();
        mViewPaer.setAdapter(new WelcomeAdapter());
        mViewPaer.addOnPageChangeListener(lister);
    }

    private void writeReadFlag() {
        File file = null;
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            file = new File(this.getFilesDir() + File.separator + "config" + File.separator + LoadingActivity.VCONFNAME);
            if (!file.exists()) file.createNewFile();
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            int code = AppUtils.getVersionCode(this);
            Version version = new Version(code);
            oos.writeObject(version);
            LogUtils.i("WelcomeActivity", "write");
            oos.flush();
        } catch (FileNotFoundException e) {
            LogUtils.i("WelcomeActivity", "error1");
            e.printStackTrace();
        } catch (IOException e) {
            LogUtils.i("WelcomeActivity", "error2");
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class WelcomeAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return drawables.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == (View) object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pagerViews[position]);
            return pagerViews[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private ViewPager.OnPageChangeListener lister = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mIndications.getChildAt(mIndex).setBackgroundResource(R.drawable.page);
            mIndications.getChildAt(position).setBackgroundResource(R.drawable.page_now);
            mIndex = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
