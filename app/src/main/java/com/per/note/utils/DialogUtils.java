package com.per.note.utils;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.per.note.R;

public class DialogUtils {
    public static void show(Context context, String alert, final DialogCallBack callBack) {

        //创建AlertDialog 的Builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("温馨提示")//设置对话框标题
                .setIcon(context.getResources().getDrawable(R.drawable.ic_logoss))//设置对话框的图标
                .setMessage(alert)//设置对话框的内容
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //点击确定按钮要做的操作
                        callBack.doListener();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击取消按钮要做的操作
                        dialog.dismiss();
                    }
                });
        builder.create().show();//创建AlertDialog对象
    }

    public interface DialogCallBack {
        void doListener();
    }
}
