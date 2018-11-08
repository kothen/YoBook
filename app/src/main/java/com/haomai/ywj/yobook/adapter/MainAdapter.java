package com.haomai.ywj.yobook.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haomai.ywj.yobook.R;
import com.haomai.ywj.yobook.bean.TreeBean;

import java.util.List;

/**
 * Created by Administrator on 2018/10/25.
 */


public class MainAdapter extends BaseAdapter {

    private Context context;
    private List<TreeBean.AddressEntity> list;
    private int position = 0;
    private Holder hold;

    public MainAdapter(Context context, List<TreeBean.AddressEntity> list) {
        this.context = context;
        this.list = list;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int arg0, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = View.inflate(context, R.layout.item_mainlist, null);
            hold = new Holder(view);
            view.setTag(hold);
        } else {
            hold = (Holder) view.getTag();
        }
        hold.img.setImageResource(Integer.parseInt(list.get(arg0).getCustId()));
        hold.txt.setText(list.get(arg0).getName());
        hold.layout.setBackgroundColor(0xffffff);
        if (arg0 == position) {
            hold.layout.setBackgroundColor(0xFFEBEBEB);
        }
        return view;
    }

    public void setSelectItem(int position) {
        this.position = position;
    }

    public int getSelectItem() {
        return position;
    }

    private static class Holder {
        LinearLayout layout;
        ImageView img;
        TextView txt;

        public Holder(View view) {
            txt = (TextView) view.findViewById(R.id.mainitem_txt);
            img = (ImageView) view.findViewById(R.id.mainitem_img);
            layout = (LinearLayout) view.findViewById(R.id.mainitem_layout);
        }
    }
}
