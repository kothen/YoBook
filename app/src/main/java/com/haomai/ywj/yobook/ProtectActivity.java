package com.haomai.ywj.yobook;

import android.content.Intent;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.speech.RecognizerIntent;

import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haomai.ywj.yobook.adapter.MainAdapter;
import com.haomai.ywj.yobook.adapter.MoreAdapter;
import com.haomai.ywj.yobook.adapter.NameAdapter;
import com.haomai.ywj.yobook.base.BaseActivity;
import com.haomai.ywj.yobook.bean.FileBean;
import com.haomai.ywj.yobook.bean.TreeBean;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;


public class ProtectActivity extends BaseActivity {
    //    @BindView(R.id.pro_vp)
//    ViewPager viewPager;
//    @BindView(R.id.navigation_pro)
//    BottomNavigationView bottomNavigationView;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;
    @BindView(R.id.toolBar_pro)
    Toolbar toolbar;
    @BindView(R.id.mainlist)
    ListView main_lv;
    @BindView(R.id.morelist)
    ListView more_lv;

    @BindView(R.id.filelist)
    ListView file_lv;


//    @BindView(R.id.tv_pdf)
//    TextView pdf_tv;


    private List<FileBean> itemBeanList;
    private List<TreeBean.AddressEntity> mainList;
    private MainAdapter mainAdapter;
    private MoreAdapter moreAdapter;
    private NameAdapter nameAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protect);
        ButterKnife.bind(this);
        initView();
        //初始化toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        initData();
        //UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
       // HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
//        for (Map.Entry<String, UsbDevice> entry : deviceList.entrySet()) {
//            Toast.makeText(ProtectActivity.this, entry.getKey() + "====" + entry.getValue(), Toast.LENGTH_SHORT).show();
//        }
        initListenter();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_search, menu);
        //初始化searchview
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        searchView.showVoice(true);
        searchView.setVoiceSearch(true);//启用语音识别

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void initView() {

    }
    @Override
    public void initData() {
        mainList = new ArrayList<>();
        initModle();// 添加数据
        mainAdapter = new MainAdapter(this, mainList);
        mainAdapter.setSelectItem(0);
        main_lv.setAdapter(mainAdapter);
        main_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                List<String> lists = mainList.get(position).getArea();
                initAdapter(lists);
                mainAdapter.setSelectItem(position);
                mainAdapter.notifyDataSetChanged();
            }
        });
        main_lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        initAdapter(mainList.get(0).getArea());

        more_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String s = (String) moreAdapter.getItem(position);

                moreAdapter.setSelectItem(position);
                moreAdapter.notifyDataSetChanged();
            }
        });


        itemBeanList = new ArrayList<>();
        itemBeanList.add(new FileBean("pdf图纸", "tu.pdf"));
        itemBeanList.add(new FileBean("CAD图纸", "开关图.dwg"));
        for (int i = 2;i < 20; i ++){
            itemBeanList.add(new FileBean("文件名" + i, "路径" + i));
        }

        nameAdapter = new NameAdapter(this,itemBeanList);
        file_lv.setAdapter(nameAdapter);
        file_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                nameAdapter.setSelectItem(position);
                nameAdapter.notifyDataSetChanged();

                String filePath = Environment.getExternalStorageDirectory().getPath()+"/"+itemBeanList.get(position).getFilePath();
                Intent intent=openAndroidFile(filePath);
                startActivity(intent);
            }
        });

    }

    @Override
    public void initListenter() {
//        pdf_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Intent intent=new Intent(ProtectActivity.this,PDFActivity.class);
//                //Intent intent=getPdfFileIntent(getFilesDir()+"/tu.pdf");
//                Intent intent=openAndroidFile(Environment.getExternalStorageDirectory().getPath()+"/开关图.dwg");
//                startActivity(intent);
//            }
//        });
    }

    public Intent getPdfFileIntent(String param) {

        File apkFile = new File(param);
        Toast.makeText(this,apkFile.getAbsolutePath(),Toast.LENGTH_LONG).show();
        Intent intent = new Intent("android.intent.action.VIEW");

        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + "com.haomai.ywj.yobook.fileProvider", apkFile);

            //intent.setDataAndType(contentUri, "application/x-dwg");
            intent.setDataAndType(contentUri, "application/pdf");

        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/x-dwg");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

//        intent.addCategory("android.intent.category.DEFAULT");
//        Uri uri = Uri.fromFile(new File(param));
        return intent;
    }

    public Intent openAndroidFile(String filepath){
        Toast.makeText(this,filepath,Toast.LENGTH_LONG).show();
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
        if(filepath.contains(".pdf")){
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");//设置类型
        }else if(filepath.contains(".dwg")){
            intent.setDataAndType(Uri.fromFile(file), "application/x-dwg");//设置类型
        }

        return intent;
    }



    private void initAdapter(List<String> lists) {
        moreAdapter = new MoreAdapter(this, lists);
        more_lv.setAdapter(moreAdapter);
        moreAdapter.notifyDataSetChanged();
    }


    private void initModle() {

        mainList = new ArrayList<TreeBean.AddressEntity>();
        Gson gson = new Gson();
        String json = "{\"result\":\"Y\", \"address\":[{\"name\":\"220kV郭线点04开关\",\"custId\":\""
                + R.mipmap.item_main
                + "\", \"area\":[\"图纸1\",\"说明书\",\"定值\",\"报告模板\"]},{\"name\":\"110kV点龙线点021开关\", \"custId\":\""
                + R.mipmap.item_main
                + "\",\"area\":[\"图纸2\",\"说明书2\",\"定值2\",\"报告模板2\"]}]}";

        //Toast.makeText(this,json,Toast.LENGTH_LONG).show();

        java.lang.reflect.Type type = new TypeToken<TreeBean>() {
        }.getType();
        TreeBean b = gson.fromJson(json, type);
        mainList.addAll(b.getAddress());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
