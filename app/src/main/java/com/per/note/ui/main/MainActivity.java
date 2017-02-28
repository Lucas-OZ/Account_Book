package com.per.note.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.Toast;

import com.per.note.R;
import com.per.note.utils.LogUtils;


public class MainActivity extends FragmentActivity {
    private Fragment mMainFragment, mCountFragment, currentFragment, mMoreFragment;
    private long lastPressTime;
    private RadioButton mRbMain, mRbCount, mRbMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initView();
        setListener();

    }

    private void initView() {
        mRbCount = (RadioButton) findViewById(R.id.main_rb_count);
        mRbMain = (RadioButton) findViewById(R.id.main_rb_main);
        mRbMore = (RadioButton) findViewById(R.id.main_rb_more);

        mCountFragment = new CountFragment();
        mMainFragment = new MainFragment();
        mMoreFragment = new MoreFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.mainactivity_fl_space, mMainFragment).commit();
        currentFragment = mMainFragment;
    }

    private void setListener() {
        mRbCount.setOnClickListener(mListener);
        mRbMain.setOnClickListener(mListener);
        mRbMore.setOnClickListener(mListener);
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == null) return;
            switch (v.getId()) {
                case R.id.main_rb_count:
                    addOrShowFragment(mCountFragment);
                    break;
                case R.id.main_rb_main:
                    addOrShowFragment(mMainFragment);
                    break;
                case R.id.main_rb_more:
                    addOrShowFragment(mMoreFragment);
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 按两下退出应用
     */
    @Override
    public void onBackPressed() {
        long ct = System.currentTimeMillis();
        if (ct - lastPressTime <= 2000) {
            finish();
        } else {
            Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
            lastPressTime = ct;
        }
    }

    /**
     * 添加或者显示碎片
     */
    private void addOrShowFragment(Fragment fragment) {
        if (currentFragment == fragment)
            return;
        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            LogUtils.i("MainActivity", "addfragment");
            getSupportFragmentManager().beginTransaction().hide(currentFragment).add(R.id.mainactivity_fl_space, fragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().hide(currentFragment).show(fragment).commit();
        }
        currentFragment = fragment;
    }
}
