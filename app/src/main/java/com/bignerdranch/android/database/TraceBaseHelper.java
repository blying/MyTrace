package com.bignerdranch.android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by bly on 2016/12/16.
 */

public class TraceBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;//数据库版本
    private static final String DATABASE_NAME = "traceBase.db";//数据库名称

    public static final String TRACETABLE="traceTable";//足迹表
    public static final String TRACEID="traceId";//属性列
    public static final String TRACENAME="traceName";
    public static final String CREATETIME="createTime";
    public static final String PERSONNAME="personName";
    public static final String LIKENUMBER="likeNumber";
    public static final String TRACEIMAGE="traceImage";
    public static final String HEADPORTRAIT="headPortrait";
    public static final String TYPE="type";//收藏是1  本地足迹是2

    public static final String POINTSTABLE="pointsTable";//坐标点表 用traceId将两个表关联
    public static final String LATITUDE = "latitude";//属性列
    public static final String LONGITUDE = "longitude";
    public static final String TEXTSTRING = "textString";
    public static final String PHOTOS = "photos";
    public static final String VOICE = "voice";

    public TraceBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //按照属性创建表
        String createTraceTable="create table " + TRACETABLE + "("+  TRACEID + "," + TRACENAME + "," + CREATETIME + "," + PERSONNAME + "," + LIKENUMBER + "," + TRACEIMAGE + "," + HEADPORTRAIT + ","+TYPE+")";
        db.execSQL(createTraceTable);
        String createPointsTable="create table " +POINTSTABLE + "(" +  TRACEID + "," +LATITUDE + "," +LONGITUDE + "," + TEXTSTRING + "," + PHOTOS + "," + VOICE + ")";
        db.execSQL(createPointsTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //如果表存在就删除
        db.execSQL("drop if table exists traceTable");
        db.execSQL("drop if table exists  pointsTable");
        onCreate(db);
    }

    /**
     * 查询数据库里的全部内容
     * @return
     */
    public Cursor select_trace(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(true,TRACETABLE,null,null,null,null,null,null,null);
        return cursor;
    }

    /**
     * 查询TRACETABLE表里type为 1 的所有数据
     * @return
     */
    public Cursor query_mark(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(true,TRACETABLE,null,"type=?",new String[]{"1"},null,null,null,null);
        return cursor;
    }

    /**
     * 查询所有点
     */
    public Cursor select_points(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(POINTSTABLE,null,null,null,null,null,null);
        return cursor;
    }


/**
 * 插入trace数据
 */
    public long insertTrace(String traceId, String traceName, String createTime, String personName ,String likeNumber, String traceImage,String headPortrait,String type){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TRACEID,traceId);
        cv.put(TRACENAME,traceName);
        cv.put(CREATETIME,createTime);
        cv.put(PERSONNAME,personName);
        cv.put(LIKENUMBER,likeNumber);
        cv.put(TRACEIMAGE,traceImage);
        cv.put(HEADPORTRAIT,headPortrait);
        cv.put(TRACEID,traceId);
        cv.put(TYPE,type);
        long row=db.insert(TRACETABLE,null,cv);
        return row;
    }

    /**
     * 插入点数据
     */
    public long insertPoints(String traceId,String latitude,String longitude,String textString,String photos,String voice){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(TRACEID,traceId);
        cv.put(LATITUDE,latitude);
        cv.put(LONGITUDE,longitude);
        cv.put(TEXTSTRING,textString);
        cv.put(PHOTOS,photos);
        cv.put(VOICE,voice);
        long row=db.insert(POINTSTABLE,null,cv);
        return row;
    }

    /**
     * 删除trace数据
     * @param traceId
     */
    public void deleteTrace(String traceId){//删除轨迹 应该删除相应的 点
        SQLiteDatabase db=this.getWritableDatabase();
        String delete="DELETE FROM"+TRACETABLE+"WHERE "+TRACEID+"='"+traceId+"'";
        db.execSQL(delete);
    }



}
