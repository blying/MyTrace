package com.bignerdranch.android.mytrace;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initData();
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
                    intent.setClass(RecentTraceActivity.this,AddPointActivity.class);
                    startActivity(intent);
                }
                return false;

            }
        });

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
