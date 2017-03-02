package com.per.note.ui.counttransfer;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.per.note.R;
import com.per.note.db.SqliteManage;
import com.per.note.ui.input.InputActivity;
import com.per.note.ui.selectcount.SelectCountActivity;
import com.per.note.utils.SqliteUtils;
import com.umeng.analytics.MobclickAgent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TransferCountActivity extends AppCompatActivity {
    private int mDay, mMonth, mYear, mWeek;
    private LinearLayout mLlMoney, mLlInCount, mLlOutCount, mLlTime;
    private TextView mTvMoney, mTvInCount, mTvOutCount, mTvTime;
    private EditText mOther;
    private ImageButton mBtOk, mReturn;
    private Calendar mCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_count);
        initView();
        initData();

    }

    private void initView() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        ((TextView) findViewById(R.id.baseactivity_tv_title)).setText("转账/存取款");
        mLlMoney = (LinearLayout) findViewById(R.id.transfer_count_ll_money);
        mLlInCount = (LinearLayout) findViewById(R.id.transfer_count_ll_in);
        mLlOutCount = (LinearLayout) findViewById(R.id.transfer_count_ll_out);
        mLlTime = (LinearLayout) findViewById(R.id.transfer_count_ll_time);

        mTvMoney = (TextView) findViewById(R.id.transfer_count_tv_money);
        mTvInCount = (TextView) findViewById(R.id.transfer_count_tv_in);
        mTvOutCount = (TextView) findViewById(R.id.transfer_count_tv_out);
        mTvTime = (TextView) findViewById(R.id.transfer_count_tv_time);
        mBtOk = (ImageButton) findViewById(R.id.baseactivity_ib_ok);
        mReturn = (ImageButton) findViewById(R.id.baseactivity_ib_return);

        mOther = (EditText) findViewById(R.id.transfer_count_et_note);

        mLlMoney.setOnClickListener(mListener);
        mLlInCount.setOnClickListener(mListener);
        mLlOutCount.setOnClickListener(mListener);
        mLlTime.setOnClickListener(mListener);
        mBtOk.setOnClickListener(mListener);
        mReturn.setOnClickListener(mListener);

    }

    private void initData() {
        mCalendar = Calendar.getInstance(Locale.CHINA);//创建一个日历对象
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        mWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
        mTvTime.setText(mYear + "年" + mMonth + "月" + mDay + "日");
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == null) return;
            switch (v.getId()) {
                case R.id.transfer_count_ll_money:
                    startActivityForResult(new Intent(TransferCountActivity.this, InputActivity.class), 0);
                    break;
                case R.id.transfer_count_ll_in:
                    startActivityForResult(new Intent(TransferCountActivity.this, SelectCountActivity.class), 1);
                    break;
                case R.id.transfer_count_ll_out:
                    startActivityForResult(new Intent(TransferCountActivity.this, SelectCountActivity.class), 2);
                    break;
                case R.id.transfer_count_ll_time:
                    new DatePickerDialog(TransferCountActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                              int dayOfMonth) {
                            //月份默认从0开始
                            mYear = year;
                            mMonth = monthOfYear + 1;
                            mDay = dayOfMonth;
                            Calendar c = Calendar.getInstance(Locale.CHINA);
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                c.setTime(format.parse(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth));
                                mWeek = c.get(Calendar.DAY_OF_WEEK);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            mTvTime.setText(mYear + "年" + mMonth + "月" + mDay + "日");
                        }
                    },
                            mCalendar.get(Calendar.YEAR),
                            mCalendar.get(Calendar.MONTH),
                            mCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    break;
                case R.id.baseactivity_ib_ok:
                    if ("00.00".equals(mTvMoney.getText().toString())) {
                        Toast.makeText(TransferCountActivity.this, "金额不能为0", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mTvInCount.getText().toString().length() == 0 || mTvOutCount.getText().toString().length() == 0) {
                        Toast.makeText(TransferCountActivity.this, "账户不能为空", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (mTvInCount.getText().toString().equals(mTvOutCount.getText().toString())) {
                        Toast.makeText(TransferCountActivity.this, "账户不能相同", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    insertData();
                    finish();
                    break;
                case R.id.baseactivity_ib_return:
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    private void insertData() {
        ContentValues valuesIn = new ContentValues();
        valuesIn.put("year", mYear);
        valuesIn.put("month", mMonth);
        valuesIn.put("day", mDay);
        valuesIn.put("week", mWeek);
        valuesIn.put("money", mTvMoney.getText().toString());
        valuesIn.put("inout", 1);
        valuesIn.put("class", "转账/存取款");
        valuesIn.put("count", mTvInCount.getText().toString());
        valuesIn.put("time", System.currentTimeMillis() + "");
        valuesIn.put("other", mOther.getText().toString());
        SqliteManage.getInstance(this).insertItem("inout", valuesIn);

        SqliteUtils.updata(TransferCountActivity.this, mTvInCount.getText().toString(),
                Double.parseDouble(mTvMoney.getText().toString()));
//
        ContentValues valuesOut = new ContentValues();
        valuesOut.put("year", mYear);
        valuesOut.put("month", mMonth);
        valuesOut.put("day", mDay);
        valuesOut.put("week", mWeek);
        valuesOut.put("money", mTvMoney.getText().toString());
        valuesOut.put("inout", -1);
        valuesOut.put("class", "转账/存取款");
        valuesOut.put("count", mTvOutCount.getText().toString());
        valuesOut.put("time", System.currentTimeMillis() + "");
        valuesOut.put("other", mOther.getText().toString());
        SqliteManage.getInstance(this).insertItem("inout", valuesOut);

        SqliteUtils.updata(TransferCountActivity.this, mTvOutCount.getText().toString(),
                -1 * Double.parseDouble(mTvMoney.getText().toString()));

        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    String msgmoney = data.getStringExtra("msgmoney");
                    if (msgmoney != null && msgmoney.length() != 0)
                        mTvMoney.setText(msgmoney);
                    break;
                case 1:
                    mTvInCount.setText(data.getStringExtra("msgcount"));
                    break;
                case 2:
                    mTvOutCount.setText(data.getStringExtra("msgcount"));
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
