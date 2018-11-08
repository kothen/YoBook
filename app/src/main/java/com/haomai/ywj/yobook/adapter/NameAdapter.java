package com.haomai.ywj.yobook.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haomai.ywj.yobook.R;
import com.haomai.ywj.yobook.bean.FileBean;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2018/10/30.
 */

public class NameAdapter extends BaseAdapter {
    private Context context;
    private int position = 0;
    Holder hold;
    private List<FileBean> lists;

    public NameAdapter(Context context, List<FileBean> lists) {
        this.context = context;
        this.lists = lists;
    }

    public int getCount() {
        return lists.size();
    }

    public Object getItem(int position) {
        return lists.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int arg0, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = View.inflate(context, R.layout.item_filelist, null);
            hold = new Holder(view);
            view.setTag(hold);
        } else {
            hold = (Holder) view.getTag();
        }
        FileBean fileBean = lists.get(arg0);
        hold.nameTv.setText(fileBean.getFileName());
        hold.pathTv.setText(fileBean.getFilePath());
        hold.nameTv.setTextColor(0xFF666666);
        hold.pathTv.setTextColor(0xFF666666);
        if (arg0 == position) {
            hold.nameTv.setTextColor(0xFFFF8C00);
            hold.pathTv.setTextColor(0xFFFF8C00);
        }
        return view;
    }

    public void setSelectItem(int position) {
        this.position = position;

    }

    public Intent openAndroidFile(String filepath){
        Intent intent = new Intent();
        // 这是比较流氓的方法，绕过7.0的文件权限检查
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

        File file = new File(filepath);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//设置标记
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(Intent.ACTION_VIEW);//动作，查看
        intent.setDataAndType(Uri.fromFile(file), "application/x-dwg");//设置类型
        return intent;
    }

    private static class Holder {
        TextView nameTv;
        TextView pathTv;

        public Holder(View view) {
            nameTv = (TextView) view.findViewById(R.id.file_name);
            pathTv = (TextView) view.findViewById(R.id.file_path);
        }
    }
}
