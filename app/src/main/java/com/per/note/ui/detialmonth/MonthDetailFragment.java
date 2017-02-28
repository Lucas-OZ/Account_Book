package com.per.note.ui.detialmonth;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.per.note.R;
import com.per.note.bean.MsgMonth;
import com.per.note.utils.FormatUtils;

/**
 * Created by 22762 on 2016/1/25.
 */
public class MonthDetailFragment extends Fragment {
    private ListView lv;
    private TextView tv;
    private MsgMonth mMsgMonth;
    //private List<MsgMonth> mMsgMonthList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LinearLayout.inflate(getActivity(), R.layout.fragment_monthdetail, null);
        mMsgMonth = (MsgMonth) getArguments().getSerializable("msg");
        initFragmentView(view);
        return view;
    }

    private void initFragmentView(View view) {
        lv = (ListView) view.findViewById(R.id.fragment_monthdetail_vp);
        tv = (TextView) view.findViewById(R.id.fragment_monthDetail_tv);
        FragmentListViewAdapter adapter = new FragmentListViewAdapter();
        lv.setAdapter(adapter);

        tv.setText(mMsgMonth.getYear() + "年" + mMsgMonth.getMonth() + "月");
    }

    class FragmentListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mMsgMonth == null ? 0 : mMsgMonth.getCountMsgList().size() + mMsgMonth.getClassMsgList().size() + 3;
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
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_month_vp_list, null);
                vh.tv_title = (TextView) convertView.findViewById(R.id.item_vp_tv_title);
                vh.tv_left = (TextView) convertView.findViewById(R.id.item_vp_tv_left);
                vh.tv_right = (TextView) convertView.findViewById(R.id.item_vp_tv_right);
                vh.ll_title = (LinearLayout) convertView.findViewById(R.id.item_vp_ll_title);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            if (position == 0) {
                vh.ll_title.setVisibility(View.VISIBLE);
                vh.tv_title.setText("总收支");
                vh.tv_left.setText("合计");
                FormatUtils.setText(vh.tv_right, mMsgMonth.getTotalin() - mMsgMonth.getTotalout(), getActivity());
            } else if (position == 1) {
                vh.ll_title.setVisibility(View.GONE);
                vh.tv_left.setText("总收入");
                FormatUtils.setText(vh.tv_right, mMsgMonth.getTotalin(), getActivity());
            } else if (position == 2) {
                vh.ll_title.setVisibility(View.GONE);
                vh.tv_left.setText("总支出");
                FormatUtils.setText(vh.tv_right, -1 * mMsgMonth.getTotalout(), getActivity());
            } else if (position == 3) {
                vh.ll_title.setVisibility(View.VISIBLE);
                vh.tv_title.setText("账户收支");
            } else if (position == mMsgMonth.getCountMsgList().size() + 3) {
                vh.ll_title.setVisibility(View.VISIBLE);
                vh.tv_title.setText("分类收支");
            } else {
                vh.ll_title.setVisibility(View.GONE);
            }
            if (position > 2 && position < mMsgMonth.getCountMsgList().size() + 3) {
                vh.tv_left.setText(mMsgMonth.getCountMsgList().get(position - 3).countName);
                double money = mMsgMonth.getCountMsgList().get(position - 3).money;
                FormatUtils.setText(vh.tv_right, money, getActivity());
            } else if (position > mMsgMonth.getCountMsgList().size() + 2 &&
                    position < mMsgMonth.getCountMsgList().size() + mMsgMonth.getClassMsgList().size() + 3) {
                vh.tv_left.setText(mMsgMonth.getClassMsgList().get(position - mMsgMonth.getCountMsgList().size() - 3).className);
                double money = mMsgMonth.getClassMsgList().get(position - mMsgMonth.getCountMsgList().size() - 3).money;
                FormatUtils.setText(vh.tv_right, money, getActivity());
            }
            return convertView;
        }
    }

    class ViewHolder {
        TextView tv_title, tv_left, tv_right;
        LinearLayout ll_title;
    }
}

