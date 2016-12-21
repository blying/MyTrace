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
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "traceBase.db";

    public static final String TRACETABLE="traceTable";
    public static final String TRACEID="traceId";
    public static final String TRACENAME="traceName";
    public static final String CREATETIME="createTime";
    public static final String PERSONNAME="personName";
    public static final String LIKENUMBER="likeNumber";
    public static final String TRACEIMAGE="traceImage";
    public static final String HEADPORTRAIT="headPortrait";
    public static final String TYPE="type";//收藏是1  本地足迹是2

    public static final String POINTSTABLE="pointsTable";

    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String TEXTSTRING = "textString";
    public static final String PHOTOS = "photos";
    public static final String VOICE = "voice";

    public TraceBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTraceTable="create table " + TRACETABLE + "("+ "_id primary key " + TRACEID + "," + TRACENAME + "," + CREATETIME + "," + PERSONNAME + "," + LIKENUMBER + "," + TRACEIMAGE + "," + HEADPORTRAIT + ","+TYPE+")";
        db.execSQL(createTraceTable);
        String createPointsTable="create table " +POINTSTABLE + "(" +  TRACEID + "," +LATITUDE + "," +LONGITUDE + "," + TEXTSTRING + "," + PHOTOS + "," + VOICE + ")";
        db.execSQL(createPointsTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop if table exists traceTable");
        db.execSQL("drop if table exists  pointsTable");
        onCreate(db);
    }

    public Cursor select_trace(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(true,TRACETABLE,null,null,null,null,null,null,null);
        return cursor;
    }

    //检查recently的足迹是否被收藏了
    public Cursor queryTraceId(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(true,TRACETABLE,new String[]{})
    }

    public Cursor select_points(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(POINTSTABLE,null,null,null,null,null,null);
        return cursor;
    }



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
    public void deleteTrace(String traceId){//删除轨迹 应该删除相应的 点
        SQLiteDatabase db=this.getWritableDatabase();
        String delete="DELETE FROM"+TRACETABLE+"WHERE "+TRACEID+"='"+traceId+"'";
        db.execSQL(delete);
    }



}
