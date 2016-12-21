package com.bignerdranch.android.mytrace;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bignerdranch.android.database.TraceBaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bly on 2016/11/28.
 */
public class RecentTraceActivity extends AppCompatActivity {
    private RecyclerView mRecentRecyclerView;
    private RecentRecyclerViewAdapter adapter;
    private List<String> mTraceNameString=new ArrayList<>();
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    Toolbar mTbHeadBar;
    private TraceBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initData();
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        mTbHeadBar=(Toolbar)findViewById(R.id.tbHeadBar);
        mRecentRecyclerView=(RecyclerView)findViewById( R.id.recentRecyclerView);
        adapter=new RecentRecyclerViewAdapter(mTraceNameString);
        mRecentRecyclerView.setAdapter(adapter);
        mRecentRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        db=new TraceBaseHelper(this);

        NavigationView navigationView=(NavigationView)findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getTitle().equals("创建足迹")){
                    Intent intent=new Intent();
                    intent.setClass(RecentTraceActivity.this,CreateTrace.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawers();

                }
                if (item.getTitle().equals("排行榜")){
                    //调用获取排行榜请求
                    //添加recyclerView的数据

                   adapter.update(mTraceNameString);
                    mDrawerLayout.closeDrawers();


                }
                if (item.getTitle().equals("我的收藏")){
                    //调用本地数据库
                    //添加recyclerView的数据

                    mDrawerLayout.closeDrawers();
                    Cursor cursor;
                    cursor=db.select_trace();
                    List<String>mNameString=new ArrayList<>();
                    while(cursor.moveToNext()){
                        if (cursor.getString(7).equals("1"))
                            mNameString.add(cursor.getString(1));
                      //  Log.d("test",cursor.getString(8));
                        adapter.update(mNameString);
                        Log.d("test",mNameString.toString());
                    }
                    cursor.close();
                }
                if (item.getTitle().equals("本地足迹")){
                    //在地图上显示所有足迹

                    mDrawerLayout.closeDrawers();
                }
                if(item.getTitle().equals("查询足迹")){
                    Intent intent=new Intent();
                    intent.setClass(RecentTraceActivity.this,SearchTraceActivity.class);
                    startActivity(intent);
                    mDrawerLayout.closeDrawers();
                }
                return false;
            }
        });
        initToolBarAndDrawableLayout();
    }
    public void initData(){
        mTraceNameString.add("轨迹1");
        mTraceNameString.add("轨迹2");
        mTraceNameString.add("轨迹3");
        mTraceNameString.add("轨迹4");
        mTraceNameString.add("轨迹5");
        mTraceNameString.add("轨迹6");
        mTraceNameString.add("轨迹7");
    }
    private void initToolBarAndDrawableLayout() {
        setSupportActionBar(mTbHeadBar);
        /*以下俩方法设置返回键可用*/
        getSupportActionBar().setHomeButtonEnabled(true);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*设置标题文字不可显示*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mTbHeadBar,R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //Toast.makeText(RecentTraceActivity.this, "open", Toast.LENGTH_SHORT).show();
              }
                    @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                //Toast.makeText(RecentTraceActivity.this, "close", Toast.LENGTH_SHORT).show();
                }
            };
        /*mMyDrawable.setDrawerListener(mToggle);不推荐*/
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();/*同步状态*/
       }

class RecentRecyclerViewAdapter extends RecyclerView.Adapter<RecentRecyclerViewAdapter.MyViewHolder>{
    List<String> mTraceNameList;
    public RecentRecyclerViewAdapter(List<String> mTraceNameList) {
        this.mTraceNameList=mTraceNameList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       MyViewHolder holder=new MyViewHolder(LayoutInflater.from(RecentTraceActivity.this).inflate(R.layout.recyclerview_cardview_item,parent,false));
        return holder;
    }
    public  void update(List<String> mTraceNameList){
        this.mTraceNameList=mTraceNameList;
        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mTraceName.setText(mTraceNameList.get(position));
        //喜欢的checkBox的监听事件
        holder.mLikesButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    //发送喜爱值加一

                }else{
                    //发送喜爱值减一

                }
            }
        });
        //收藏字样监听
        holder.mMarkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //将信息存到本地数据库中，之后用traceId获取
                holder.mMarkTextView.setTextColor(Color.GRAY);
                holder.mMarkTextView.setClickable(false);
                //数据库中插入一条数据
                db.insertTrace("110","测试轨迹0","20161221","bly","111","image1","image2","1");

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(RecentTraceActivity.this,ShowTrace.class);
                intent.putExtra("number",position);
                startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTraceNameList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTraceName;
        CheckBox mLikesButton;
        TextView mMarkTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView=(ImageView)itemView.findViewById(R.id.imageView);
            mTraceName=(TextView)itemView.findViewById(R.id.traceName);
            mLikesButton=(CheckBox)itemView.findViewById(R.id.likesButton);
            mMarkTextView=(TextView)itemView.findViewById(R.id.markTextView);
        }
    }
}

}
