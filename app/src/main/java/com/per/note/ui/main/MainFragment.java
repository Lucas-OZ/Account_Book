package com.per.note.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.per.note.R;
import com.per.note.db.SqliteManage;
import com.per.note.ui.detialday.DayActivity;
import com.per.note.ui.detialmonth.MonthActivity;
import com.per.note.ui.detialyear.YearActivity;
import com.per.note.ui.record.RecordActivity;
import com.per.note.utils.FormatUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 22762 on 2016/1/3.
 */
public class MainFragment extends Fragment {
    private Button mBt_add;
    private View mView;
    private TextView mTvYears, mTime, mTvDay, mTvMonth, mTvYear, mTvWeek, mTvDayin, mTvDayout, mTvDaytotal, mTvMonthin,
            mTvMonthout, mTvMonthtotal, mTvYearin, mTvYearout, mTvYeartotal, mTvTotalin, mTvTotalout, mTvTotal;
    private LinearLayout mLlDay, mLlMonth, mLlYear;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_main, null);
        mView = view;
        initView(view);
        setmTime();
        setData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setDataCout();
    }

    private void initView(View view) {
        mBt_add = (Button) view.findViewById(R.id.mainactivity_bt_add);
        mTvYears = (TextView) view.findViewById(R.id.mainactivity_tv_years);
        mTime = (TextView) view.findViewById(R.id.mainactivity_tv_time);
        mTvDay = (TextView) view.findViewById(R.id.mainactivity_tv_day);
        mTvMonth = (TextView) view.findViewById(R.id.mainactivity_tv_month);
        mTvYear = (TextView) view.findViewById(R.id.mainactivity_tv_year);
        mTvWeek = (TextView) view.findViewById(R.id.mainactivity_tv_week);

        mTvDayin = (TextView) view.findViewById(R.id.mainactivity_tv_dayin);
        mTvDayout = (TextView) view.findViewById(R.id.mainactivity_tv_dayout);
        mTvDaytotal = (TextView) view.findViewById(R.id.mainactivity_tv_daytotal);

        mTvMonthin = (TextView) view.findViewById(R.id.mainactivity_tv_monthin);
        mTvMonthout = (TextView) view.findViewById(R.id.mainactivity_tv_monthout);
        mTvMonthtotal = (TextView) view.findViewById(R.id.mainactivity_tv_monthtotal);

        mTvYearin = (TextView) view.findViewById(R.id.mainactivity_tv_yearin);
        mTvYearout = (TextView) view.findViewById(R.id.mainactivity_tv_yearout);
        mTvYeartotal = (TextView) view.findViewById(R.id.mainactivity_tv_yeartotal);

        mLlDay = (LinearLayout) view.findViewById(R.id.main_ll_itemday);
        mLlMonth = (LinearLayout) view.findViewById(R.id.main_ll_itemmonth);
        mLlYear = (LinearLayout) view.findViewById(R.id.main_ll_itemyear);
        mTvTotalin = (TextView) view.findViewById(R.id.mainactivity_tv_tallin);
        mTvTotalout = (TextView) view.findViewById(R.id.mainactivity_tv_tallout);
        mTvTotal = (TextView) view.findViewById(R.id.mainactivity_tv_tall);

        mLlDay.setOnClickListener(mListener);
        mLlMonth.setOnClickListener(mListener);
        mLlYear.setOnClickListener(mListener);

        mBt_add.setOnClickListener(mListener);
    }

    private void setData() {
        String[] weeks = new String[]{"星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance(Locale.CHINA);//创建一个日历对象
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        int m = calendar.get(Calendar.MONTH) + 1;
        int y = calendar.get(Calendar.YEAR);
        mTvYear.setText(y + "");
        mTvMonth.setText(m + "");
        mTvDay.setText(d + "");
        mTvWeek.setText(weeks[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
        mTvYears.setText(y + "年" + m + "月" + d + "日");
    }

    private void setDataCout() {
        SqliteManage.QueryResult result = SqliteManage.getInstance(getActivity()).query("inout", null, null /*"day=?,month=?,year=?",
                new String[]{mTvDay.getText().toString(), mTvMonth.getText().toString(), mTvYear.getText().toString()}*/);
        if (result.cursor == null) return;
        float totalin = 0, totalout = 0, yearin = 0, yearout = 0, monthin = 0, monthout = 0, dayin = 0, dayout = 0;
        while (result.cursor.moveToNext()) {
            float money = result.cursor.getFloat(result.cursor.getColumnIndex("money"));
            float inout = result.cursor.getFloat(result.cursor.getColumnIndex("inout"));
            if (inout == 1) totalin += money;
            if (inout == -1) totalout += money;
            if (result.cursor.getInt(result.cursor.getColumnIndex("year")) == Integer.parseInt(mTvYear.getText().toString())) {
                if (inout == 1) yearin += money;
                if (inout == -1) yearout += money;
                if (result.cursor.getInt(result.cursor.getColumnIndex("month")) == Integer.parseInt(mTvMonth.getText().toString())) {
                    if (inout == 1) monthin += money;
                    if (inout == -1) monthout += money;
                    if (result.cursor.getInt(result.cursor.getColumnIndex("day")) == Integer.parseInt(mTvDay.getText().toString())) {
                        if (inout == 1) dayin += money;
                        if (inout == -1) dayout += money;
                    }
                }
            }
        }
        mTvDayin.setText((FormatUtils.format2d(dayin)));
        mTvDayout.setText(FormatUtils.format2d(dayout));
        mTvDaytotal.setText(FormatUtils.format2d(dayin - dayout));
        if (dayin > dayout) {
            mTvDaytotal.setTextColor(getResources().getColor(R.color.text_in_color));
        } else {
            mTvDaytotal.setTextColor(getResources().getColor(R.color.text_out_color));
        }

        mTvMonthin.setText(FormatUtils.format2d(monthin));
        mTvMonthout.setText(FormatUtils.format2d(monthout));
        mTvMonthtotal.setText(FormatUtils.format2d(monthin - monthout));
        if (monthin > monthout) {
            mTvMonthtotal.setTextColor(getResources().getColor(R.color.text_in_color));
        } else {
            mTvMonthtotal.setTextColor(getResources().getColor(R.color.text_out_color));
        }

        mTvYearin.setText(FormatUtils.format2d(yearin));
        mTvYearout.setText(FormatUtils.format2d(yearout));
        mTvYeartotal.setText(FormatUtils.format2d(yearin - yearout));
        if (monthin > monthout) {
            mTvYeartotal.setTextColor(getResources().getColor(R.color.text_in_color));
        } else {
            mTvYeartotal.setTextColor(getResources().getColor(R.color.text_out_color));
        }

        //mTvDayin.setText("收入：\t" + totalin + "\t元\t支出：\t" + totalout + "\t元\t合计：\t" + (totalin - totalout) + "元");
        mTvTotalout.setText(FormatUtils.format2d(totalout));
        mTvTotalin.setText(FormatUtils.format2d(totalin));
        mTvTotal.setText(FormatUtils.format2d(totalin - totalout));
        if (totalin > totalout) {
            mTvTotal.setTextColor(getResources().getColor(R.color.text_in_color));
        } else {
            mTvTotal.setTextColor(getResources().getColor(R.color.text_out_color));
        }
        result.cursor.close();
        result.db.close();
    }

    private void setmTime() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        ((Activity) (mTime.getContext())).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTime.setText(simpleTime(System.currentTimeMillis()));
                            }
                        });
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private String simpleTime(Long lo) {
        SimpleDateFormat time = new SimpleDateFormat(/*"yyyy-MM-dd*/" hh:mm:ss");
        Date date = new Date(lo);
        return time.format(date);
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == null) return;
            Intent intent = null;
            switch (v.getId()) {
                case R.id.mainactivity_bt_add:
                    intent = new Intent(getActivity(), RecordActivity.class);
                    break;
                case R.id.main_ll_itemday:
                    intent = new Intent(getActivity(), DayActivity.class);
                    break;
                case R.id.main_ll_itemmonth:
                    intent = new Intent(getActivity(), MonthActivity.class);
                    break;
                case R.id.main_ll_itemyear:
                    intent = new Intent(getActivity(), YearActivity.class);
                    break;
                default:

                    break;
            }
            startActivity(intent);
        }
    };
}
