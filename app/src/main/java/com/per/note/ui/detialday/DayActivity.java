package com.per.note.ui.detialday;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.per.note.R;
import com.per.note.db.SqliteManage;
import com.per.note.utils.DialogUtils;
import com.per.note.utils.LogUtils;
import com.per.note.utils.OrderUtils;
import com.per.note.utils.SqliteUtils;

public class DayActivity extends DayBaseActivity {
    private boolean isOrderDay = true;
    private ListView mLv;
    private ViewPager mVp;
    private DayAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();

    }

    private void initView() {
        mVp = (ViewPager) findViewById(R.id.detail_vp);
        mLv = (ListView) findViewById(R.id.detail_lv);
        OrderUtils.orderDay(mMsgDayList);
        mAdapter = new DayAdapter(mMsgDayList, this);
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
            return mMsgDayList.size();
        }

        // 返回值就是当前位置显示的Fragment
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new DetialFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("msg", mMsgDayList.get(position));
            fragment.setArguments(bundle);
            return fragment;
        }
    };

    public void onClick(View v) {
        if (v == null) return;
        switch (v.getId()) {
            case R.id.dayactivity_ib_delete:
                if (mMsgDayList.size() == 0) return;
                DialogUtils.show(DayActivity.this, "确定要删除该条记录么！", new DialogUtils.DialogCallBack() {
                    @Override
                    public void doListener() {
                        delete();
                    }
                });
                break;
            case R.id.dayactivity_ib_order:
                isOrderDay = !isOrderDay;
                if (isOrderDay) {
                    OrderUtils.orderDay(mMsgDayList);
                    updataList("按时间排序");
                } else {
                    OrderUtils.orderMoney(mMsgDayList);
                    updataList("按金额排序");
                }
                break;
            default:
                break;
        }
    }

    private void delete() {
        LogUtils.i("DayActivity", "==========" + mVp.getCurrentItem());
        SqliteUtils.updata(DayActivity.this, mMsgDayList.get(mVp.getCurrentItem()).getCount(),
                -1 * mMsgDayList.get(mVp.getCurrentItem()).getInout() * mMsgDayList.get(mVp.getCurrentItem()).getMoney());

        SqliteManage.getInstance(this).delteItem("inout", "time=?",
                new String[]{mMsgDayList.get(mVp.getCurrentItem()).getTime() + ""});

        initParentData();
        initView();
    }

    private void updataList(String msg) {
        mVp.setCurrentItem(0);
        mAdapter.setmPosition(0);
        mAdapter.notifyDataSetChanged();
        mVpAdapter.notifyDataSetChanged();
        mVp.setAdapter(mVpAdapter);
        mLv.setSelection(0);
        Toast.makeText(DayActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
