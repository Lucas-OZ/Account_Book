package com.per.note.ui.detialyear;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.per.note.R;
import com.per.note.bean.MsgYear;

import java.util.List;

/**
 * Created by 22762 on 2016/1/7.
 */
public class YearAdapter extends BaseAdapter {
    private int mPosition = 0;
    private List<MsgYear> mData;
    private Context mContext;

    public YearAdapter(List<MsgYear> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
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
        TextView textView;
        if (convertView == null) {
            convertView = LinearLayout.inflate(mContext, R.layout.lv_left_item, null);
            textView = (TextView) convertView.findViewById(R.id.lv_item_day);
            convertView.setTag(textView);
        } else {
            textView = (TextView) convertView.getTag();
        }
        MsgYear msgMonth = mData.get(position);

        convertView.setBackgroundColor(mContext.getResources().getColor(R.color.view_background_light));
        if (mPosition == position) {
            convertView.setBackgroundColor(mContext.getResources().getColor(R.color.background_activity));
        }
        if (msgMonth.getTotalin() - msgMonth.getTotalout() < 0) {
            textView.setTextColor(mContext.getResources().getColor(R.color.text_out_color));
        } else {
            textView.setTextColor(mContext.getResources().getColor(R.color.text_in_color));
        }
        textView.setText(msgMonth.getYear() + "年");
        return convertView;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }
}
