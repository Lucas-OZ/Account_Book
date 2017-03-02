package com.per.note.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.per.note.R;
import com.per.note.bean.Count;
import com.per.note.utils.FormatUtils;

import java.util.List;

/**
 * Created by 22762 on 2016/1/6.
 */
public class CountAdapter extends BaseAdapter {
    private List<Count> data;
    private Context mContext;


    public CountAdapter(List<Count> data, Context mContext) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
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
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_swap, null);
            vh.tv_Count = (TextView) convertView.findViewById(R.id.swap_item_count);
            vh.tv_number = (TextView) convertView.findViewById(R.id.swap_item_number);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_Count.setText(data.get(position).getCount());
        vh.tv_number.setText(FormatUtils.format2d(data.get(position).getNumber()));
        return convertView;
    }

    class ViewHolder {
        TextView tv_Count, tv_number;
    }
}
