package com.per.note.ui.detialyear;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.per.note.R;
import com.per.note.bean.MsgMonth;
import com.per.note.bean.MsgYear;
import com.per.note.utils.FormatUtils;
import com.per.note.utils.LogUtils;

import java.util.Collections;
import java.util.Comparator;

public class YearActivity extends YearBaseActivity {
    private ListView mLv;
    private ViewPager mVp;
    private YearAdapter mLvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
    }

    private void initView() {
        Collections.sort(mMsgYearList, mOrderYear);

        ((ImageButton) findViewById(R.id.dayactivity_ib_delete)).setVisibility(View.GONE);
        ((ImageButton) findViewById(R.id.dayactivity_ib_order)).setVisibility(View.GONE);
        mLv = (ListView) findViewById(R.id.detail_lv);
        mVp = (ViewPager) findViewById(R.id.detail_vp);
        mVp.setAdapter(mVpAdapter);

        mLvAdapter = new YearAdapter(mMsgYearList, this);
        mLv.setAdapter(mLvAdapter);
    }

    private void setListener() {
        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mLvAdapter.setmPosition(position);
                mLvAdapter.notifyDataSetChanged();
                mVp.setCurrentItem(position);
            }
        });

        mVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mLvAdapter.setmPosition(position);
                mLvAdapter.notifyDataSetChanged();
                mLv.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private PagerAdapter mVpAdapter = new PagerAdapter() {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = LayoutInflater.from(YearActivity.this).inflate(R.layout.fragment_monthdetail, null);
            ListView lv = (ListView) v.findViewById(R.id.fragment_monthdetail_vp);
            TextView tv = (TextView) v.findViewById(R.id.fragment_monthDetail_tv);
            tv.setText(mMsgYearList.get(position).getYear() + "年");
            container.addView(v);
            lv.setAdapter(new ListViewAdapter(position));
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getCount() {
            return mMsgYearList.size();
        }
    };

    class ListViewAdapter extends BaseAdapter {
        private int Iposition;

        public ListViewAdapter(int position) {
            this.Iposition = position;
        }

        @Override
        public int getCount() {
            return mMsgYearList == null ? 0 : mMsgYearList.get(Iposition).getCountMsgList().size() + mMsgYearList.get(Iposition).getClassMsgList().size() + 3;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = LayoutInflater.from(YearActivity.this).inflate(R.layout.item_month_vp_list, null);
                vh.tv_title = (TextView) convertView.findViewById(R.id.item_vp_tv_title);
                vh.tv_left = (TextView) convertView.findViewById(R.id.item_vp_tv_left);
                vh.tv_right = (TextView) convertView.findViewById(R.id.item_vp_tv_right);
                vh.ll_title = (LinearLayout) convertView.findViewById(R.id.item_vp_ll_title);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            MsgYear msg = mMsgYearList.get(Iposition);
            LogUtils.i("MonthActivity", mMsgYearList.size() + ":" + msg.getCountMsgList().size() + ":" + msg.getClassMsgList().size());
            if (position == 0) {
                vh.ll_title.setVisibility(View.VISIBLE);
                vh.tv_title.setText("总收支");
                vh.tv_left.setText("合计");
                FormatUtils.setText(vh.tv_right, msg.getTotalin() - msg.getTotalout(), YearActivity.this);
            } else if (position == 1) {
                vh.ll_title.setVisibility(View.GONE);
                vh.tv_left.setText("总收入");
                FormatUtils.setText(vh.tv_right, msg.getTotalin(), YearActivity.this);
            } else if (position == 2) {
                vh.ll_title.setVisibility(View.GONE);
                vh.tv_left.setText("总支出");
                FormatUtils.setText(vh.tv_right, -1 * msg.getTotalout(), YearActivity.this);
            } else if (position == 3) {
                vh.ll_title.setVisibility(View.VISIBLE);
                vh.tv_title.setText("账户收支");
            } else if (position == msg.getCountMsgList().size() + 3) {
                vh.ll_title.setVisibility(View.VISIBLE);
                vh.tv_title.setText("分类收支");
            } else {
                vh.ll_title.setVisibility(View.GONE);
            }
            if (position > 2 && position < msg.getCountMsgList().size() + 3) {
                vh.tv_left.setText(msg.getCountMsgList().get(position - 3).countName);
                double money = msg.getCountMsgList().get(position - 3).money;
                FormatUtils.setText(vh.tv_right, money, YearActivity.this);
            } else if (position > msg.getCountMsgList().size() + 2 &&
                    position < msg.getCountMsgList().size() + msg.getClassMsgList().size() + 3) {
                vh.tv_left.setText(msg.getClassMsgList().get(position - msg.getCountMsgList().size() - 3).className);
                double money = msg.getClassMsgList().get(position - msg.getCountMsgList().size() - 3).money;
                FormatUtils.setText(vh.tv_right, money, YearActivity.this);
            }
            return convertView;
        }
    }

    class ViewHolder {
        TextView tv_title, tv_left, tv_right;
        LinearLayout ll_title;
    }

    private Comparator<MsgYear> mOrderYear = new Comparator<MsgYear>() {
        @Override
        public int compare(MsgYear lhs, MsgYear rhs) {
            if (lhs.getYear() > rhs.getYear()) {
                return -1;
            } else {
                return 1;
            }
        }
    };
}