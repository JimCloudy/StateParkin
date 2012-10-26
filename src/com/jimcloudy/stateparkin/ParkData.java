package com.jimcloudy.stateparkin;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ParkData {
	private static final String TAG = ParkData.class.getSimpleName();
	static final String DATABASE = "StateParkin.db";
	static final int VERSION = 1;
	static final String TABLE = "parkRating";
	static final String C_ID = "_id";
	static final String C_PHOTO = "photo";
	static final String C_FACILITIES = "facilities";	
	static final String C_TRAILS = "trails";
	static final String C_TENT = "tent";
	static final String C_CABIN = "cabin";
	static final String C_CAMPER = "camper";
	static final String C_FISHING = "fishing";
	static final String C_BIRD = "bird";
	static final String C_LIKES = "likes";
	static final String C_DISLIKES = "dislikes";

	private static final String GET_ALL_ORDER_BY = C_ID;
	private static final String[] DB_TEXT_COLUMNS = { C_ID, C_PHOTO, C_FACILITIES, C_TRAILS, C_TENT, C_CABIN, C_CAMPER, C_FISHING, C_BIRD, C_LIKES, C_DISLIKES };

	Context context;

	class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context context) {
		    super(context, DATABASE, null, VERSION);
		}

	    @Override
	    public void onCreate(SQLiteDatabase db) {
	      String sql = "CREATE TABLE parkRating ( _id text primary key , photo text, facilities int, trails int, tent int, cabin int, camper int, fishing int, bird int, likes text, dislikes text );";
	      db.execSQL(sql);
	    }

	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	      db.execSQL("drop table " + TABLE);
	      this.onCreate(db);
	    }
	  }

	final DbHelper dbHelper;

	public ParkData(Context c){
		this.dbHelper = new DbHelper(c);
		context = c;
	}

	public void close(){
		this.dbHelper.close();
	}

	public void insertOrIgnore(ContentValues values){
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();

		try{		
			 db.insertWithOnConflict(TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
		}
		finally{
			db.close();
		}
	}

//	public Cursor getParkRating(){
//		SQLiteDatabase db = this.dbHelper.getReadableDatabase();
//		return db.query(TABLE, null, C_ID, null, null, null, GET_ALL_ORDER_BY);
//	}

	public Cursor getParkRating(String[] park) {
	    SQLiteDatabase db = this.dbHelper.getReadableDatabase();
	    try{
	    	Cursor cursor = db.query(TABLE, DB_TEXT_COLUMNS, C_ID + "=?", park, null, null, null);
	    	return cursor.moveToFirst() ? cursor : null;
	    }
	    finally{
	    	db.close();
	    }
	  }

	public boolean updateParkRating(String[] park, ContentValues values) {
	    SQLiteDatabase db = this.dbHelper.getWritableDatabase();
	    boolean flag;
	    try {
	    	db.update(TABLE, values, C_ID + "=?", park);
	    	flag = true;
	    } 
	    catch(Exception e){
	    	e.printStackTrace();
	    	flag = false;
	    }
	    finally {
	      db.close();
	    }
	    return flag;
	  }

	public void deleteParkRating(List<String> parks) {
	    SQLiteDatabase db = this.dbHelper.getWritableDatabase();
	    try {
	    	for(String park : parks)
	    	{
	    		String[] arg = new String[]{park};
	    		db.delete(TABLE, C_ID + "=?", arg);
	    	}
	    } 
	    catch(Exception e){
	    	e.printStackTrace();
	    }
	    finally {
	      db.close();
	    }
	  }

	public void delete() {
	    SQLiteDatabase db = dbHelper.getWritableDatabase();
   	    db.delete(TABLE, null, null);
	    db.close();
	  }
}