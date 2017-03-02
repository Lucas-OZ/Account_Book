package com.per.note.ui.detialmonth;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.per.note.R;
import com.per.note.bean.MsgMonth;

import java.util.Collections;
import java.util.Comparator;

public class MonthActivity extends MonthBaseActivity {
    private ListView mLv;
    private MonthAdapter mAdapter;
    private ViewPager mVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();

    }

    private void initView() {

        ((ImageButton) findViewById(R.id.dayactivity_ib_delete)).setVisibility(View.GONE);
        ((ImageButton) findViewById(R.id.dayactivity_ib_order)).setVisibility(View.GONE);

        Collections.sort(mMsgMonthList, mOrderMonth);

        mLv = (ListView) findViewById(R.id.detail_lv);
        mVp = (ViewPager) findViewById(R.id.detail_vp);
        mAdapter = new MonthAdapter(mMsgMonthList, MonthActivity.this);
        mLv.setAdapter(mAdapter);
        mVp.setAdapter(mVpAdapter);
    }

    private void setListener() {
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setmPosition(position);
                mAdapter.notifyDataSetChanged();
                mVp.setCurrentItem(position);
            }
        });

        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mAdapter.setmPosition(position);
                mAdapter.notifyDataSetChanged();
                mLv.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private FragmentStatePagerAdapter mVpAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        // 展示的page个数
        @Override
        public int getCount() {
            return mMsgMonthList == null ? 0 : mMsgMonthList.size();
        }

        // 返回值就是当前位置显示的Fragment
        @Override
        public Fragment getItem(int position) {
            MonthDetailFragment fragment = new MonthDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("msg", mMsgMonthList.get(position));
            fragment.setArguments(bundle);
            return fragment;
        }
    };
    private Comparator<MsgMonth> mOrderMonth = new Comparator<MsgMonth>() {
        @Override
        public int compare(MsgMonth lhs, MsgMonth rhs) {
            if (lhs.getYear() > rhs.getYear()) {
                return -1;
            } else if (lhs.getYear() < rhs.getYear()) {
                return 1;
            } else {
                if (lhs.getMonth() > rhs.getMonth()) {
                    return -1;
                } else if (lhs.getMonth() < rhs.getMonth()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    };
}