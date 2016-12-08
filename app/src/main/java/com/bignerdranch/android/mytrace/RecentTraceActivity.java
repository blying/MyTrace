package com.bignerdranch.android.mytrace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bly on 2016/11/28.
 */
public class RecentTraceActivity extends AppCompatActivity {
    private RecyclerView mRecentRecyclerView;
    private List<String> mTraceNameString=new ArrayList<>();
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    Toolbar mTbHeadBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initData();
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        mTbHeadBar=(Toolbar)findViewById(R.id.tbHeadBar);
        mRecentRecyclerView=(RecyclerView)findViewById( R.id.recentRecyclerView);
        mRecentRecyclerView.setAdapter(new RecentRecyclerViewAdapter(mTraceNameString));
        mRecentRecyclerView.setLayoutManager(new GridLayoutManager(this,2));


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

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTraceName.setText(mTraceNameList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTraceNameList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTraceName;
        Button mLikesButton;
        TextView mMarkTextView;
        public MyViewHolder(View itemView) {
            super(itemView);
            mImageView=(ImageView)itemView.findViewById(R.id.imageView);
            mTraceName=(TextView)itemView.findViewById(R.id.traceName);
            mLikesButton=(Button)itemView.findViewById(R.id.likesButton);
            mMarkTextView=(TextView)itemView.findViewById(R.id.markTextView);
        }
    }
}


}
