package com.per.note.ui.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;
import com.per.note.R;
import com.per.note.bean.MsgDay;
import com.per.note.db.SqliteManage;
import com.per.note.utils.AppUtils;
import com.per.note.utils.SDUtils;

import java.io.File;

/**
 * Created by 22762 on 2016/1/24.
 */
public class MoreFragment extends Fragment {
    private TextView mTv_verson, mTv_clear, mTv_tellme;
    private ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_more, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        String version = AppUtils.getVersion(getActivity());
        dialog = new ProgressDialog(getActivity());
        mTv_verson = (TextView) view.findViewById(R.id.fragment_more_tv_verson);
        mTv_clear = (TextView) view.findViewById(R.id.fragment_more_tv_clear);
        mTv_tellme = (TextView) view.findViewById(R.id.fragment_more_tv_tellme);

        mTv_verson.setText("版本更新" + "(" + version + ")");

        mTv_verson.setOnClickListener(mListener);
        mTv_clear.setOnClickListener(mListener);
        mTv_tellme.setOnClickListener(mListener);
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == null) return;
            switch (v.getId()) {
                case R.id.fragment_more_tv_verson:
                    dialog.show();
                    BDAutoUpdateSDK.uiUpdateAction(getActivity(), new MyUICheckUpdateCallback());
                    break;
                case R.id.fragment_more_tv_clear:
                    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                        //  /storage/emulated/0/appcache/imgcache/
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                                + File.separator + "appX.DownloadApp.hiyo";
                        File file = new File(path);
                        file.mkdirs();
                        deleFile(file);
                        Toast.makeText(getActivity(), "清除完成", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.fragment_more_tv_tellme:
                    initData();
                    break;
                default:
                    break;
            }
        }
    };

    private class MyUICheckUpdateCallback implements UICheckUpdateCallback {
        @Override
        public void onCheckComplete() {
            dialog.dismiss();
        }
    }

    private void deleFile(File file) {
        if (file.isDirectory()) {
            File f[] = file.listFiles();
            for (int i = 0; i < f.length; i++) {
                if (f[i].isDirectory()) {
                    deleFile(f[i]);
                } else {
                    f[i].delete();
                }
            }
        }
        file.delete();
    }

    private void initData() {
        SqliteManage.QueryResult result = SqliteManage.getInstance(getActivity()).query("inout", null, null);
        if (result.cursor == null) return;
        SDUtils.delete("天天记.csv");
        SDUtils.save("天天记.csv", "年," + "月," + "日," + "星期," + "收支," + "记录时间," + "金额," + "分类," + "账户," + "备注");
        while (result.cursor.moveToNext()) {
            MsgDay msgDay = new MsgDay(result.cursor);
            SDUtils.save("天天记.csv", msgDay.toString());
        }
        Toast.makeText(getActivity(), "成功保存在天天记文件夹", Toast.LENGTH_SHORT).show();
    }

}