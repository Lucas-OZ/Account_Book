package com.per.note.ui.input;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.per.note.R;
import com.umeng.analytics.MobclickAgent;

public class InputActivity extends AppCompatActivity {
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        initView();
    }

    private void initView() {
        mTv = (TextView) findViewById(R.id.inputactivity_tv_show);

    }

    public void keyClick(View v) {
        if (v == null) return;
        switch (v.getId()) {
            case R.id.key_x:
                mTv.setText(null);
                break;
            case R.id.key_ok:
                Intent intent = getIntent();
                intent.putExtra("msgmoney", mTv.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
        if (mTv.getText().toString().length() == 8) return;
        if (mTv.getText().toString().contains(".")) {
            if (!mTv.getText().toString().endsWith(".")) {
                String[] array = mTv.getText().toString().split("\\.");
                if (array[1] != null)
                    if (array[1].length() == 2) return;
            }
        } else {
            if (mTv.getText().toString() != null && mTv.getText().toString().length() != 0 &&  Double.parseDouble(mTv.getText().toString()) > 100000) {
                return;
            }
        }
        switch (v.getId()) {
            case R.id.key_0:
                if (mTv.getText().toString().length() != 0) {
                    mTv.append("0");
                }
                break;
            case R.id.key_1:
                mTv.append("1");
                break;
            case R.id.key_2:
                mTv.append("2");
                break;
            case R.id.key_3:
                mTv.append("3");
                break;
            case R.id.key_4:
                mTv.append("4");
                break;
            case R.id.key_5:
                mTv.append("5");
                break;
            case R.id.key_6:
                mTv.append("6");
                break;
            case R.id.key_7:
                mTv.append("7");
                break;
            case R.id.key_8:
                mTv.append("8");
                break;
            case R.id.key_9:
                mTv.append("9");
                break;
            case R.id.key_point:
                if (mTv.getText().toString().length() != 0)
                    if (!mTv.getText().toString().contains(".")) mTv.append(".");
                break;
            default:
                break;
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
