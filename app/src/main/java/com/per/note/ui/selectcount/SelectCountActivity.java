package com.per.note.ui.selectcount;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.per.note.R;
import com.per.note.db.SqliteManage;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

public class SelectCountActivity extends AppCompatActivity {
    private ListView mLv;
    private ArrayAdapter mAdapter;
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_out);
        initView();
    }

    private void initView() {
        mLv = (ListView) findViewById(R.id.class_lv_left);

        mList = new ArrayList<>();
        SqliteManage.QueryResult result = SqliteManage.getInstance(this).query("count", null, null);
        while (result.cursor.moveToNext()) {
            mList.add(result.cursor.getString(result.cursor.getColumnIndex("count")));
        }
        result.cursor.close();
        result.db.close();

        if (mList.size() == 0) {
            Toast.makeText(this, "请先添加账户！", Toast.LENGTH_SHORT).show();
        }

        mAdapter = new ArrayAdapter(this, R.layout.item_tv, mList);
        mLv.setAdapter(mAdapter);

        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = getIntent();
                intent.putExtra("msgcount", mList.get(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
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
