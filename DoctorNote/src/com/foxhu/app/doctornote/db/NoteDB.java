package com.foxhu.app.doctornote.db;

import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
/**
 * DoctorNote
 * @author Foxhu
 * @version 1.0
 *
 */
public class NoteDB extends SQLiteOpenHelper {
	public static NoteDB mInstance = null;
	public static final int VERSION = 1;
	public static final String DATABASE_NAME = "DoctorNote";
	
	private static final String TABLE_NOTE_NAME = "Note";
	private static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_PRESCRIPTION = "prescription";
	private static final String COLUMN_CREATETIME = "createtime";

  
	private static final String DATABASE_NOTE_CREATE = "create table Note(_id integer primary key autoincrement, "
														+ "name text not null,"
														+ "prescription text not null,"
														+ "createtime text not null" + ");";
	
	public NoteDB(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	//自定义构造函数，只需要传递Activity对象给它即可
	public NoteDB(Context context) {
        this(context, DATABASE_NAME, null, VERSION); 
    }
	 /**
     * 单例模式
     */
    public static NoteDB getInstance(Context context) {
            if (mInstance == null) {
                    mInstance = new NoteDB(context);
            }
            return mInstance;
    }
    
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_NOTE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	/**
	 * 获取全部数据
	 * @param db
	 * @return
	 */
	public Cursor getAllNote(){
		return this.getReadableDatabase().query(TABLE_NOTE_NAME, new String[] {COLUMN_ID,COLUMN_NAME,COLUMN_PRESCRIPTION,COLUMN_CREATETIME}, null, null, null, null, "_id desc");
		
	}
	
	/**
	 * 插入数据
	 * @param name
	 * @param prescription
	 * @return
	 */
	public long insertNote(String name,String prescription){
		ContentValues values = new ContentValues();
		Calendar calendar = Calendar.getInstance();  
		String created = calendar.get(Calendar.YEAR) + "年" + calendar.get(Calendar.MONTH) + "月"  + calendar.get(Calendar.DAY_OF_MONTH) + "日";
		values.put(COLUMN_NAME, name);
		values.put(COLUMN_PRESCRIPTION, prescription);
		values.put(COLUMN_CREATETIME, created);
		return this.getWritableDatabase().insert(TABLE_NOTE_NAME, null, values);
	}
	
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	public int deleteNote(long id){
		return this.getWritableDatabase().delete(TABLE_NOTE_NAME, "_id=?", new String[] { String.valueOf(id) });
	}
	
	/**
	 * 根据id更新数据
	 * @param id
	 * @param name
	 * @param prescription
	 * @return
	 */
	public int updateNote(long id,String name,String prescription){
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME, name);
		values.put(COLUMN_PRESCRIPTION, prescription);
		return this.getWritableDatabase().update(TABLE_NOTE_NAME, values, "_id=?", new String[] { String.valueOf(id) });
	}
	
	/**
	 * 根据姓名查询数据
	 * @param name
	 * @return
	 */
	public Cursor queryNoteByName(String name){
		return this.getReadableDatabase().query(TABLE_NOTE_NAME, null, "name like ?", new String[] {"%"+name+"%"}, null, null, "_id desc");
	}
	
	/**
	 * 根据id查询详细信息
	 * @param id
	 * @return
	 */
	public Cursor queryNoteById(long id){
		Cursor mcursor = this.getReadableDatabase().query(TABLE_NOTE_NAME, null, "_id=?", new String[] { String.valueOf(id) }, null, null, null,"1");
		if (mcursor != null && mcursor.getCount() !=0){
			mcursor.moveToFirst();
			return mcursor;
		}else{
			return null;
		}
	}
	/**
	 * 获取note表全部记录数
	 * @return
	 */
	public long getNoteCount(){
		SQLiteStatement statement = this.getReadableDatabase().compileStatement("select count(*) from " + TABLE_NOTE_NAME);
		return statement.simpleQueryForLong();
	}
	
	/**
	 * 根据姓名查询记录数
	 * @param name
	 * @return
	 */
	public long getNoteCountByName(String name) {
		SQLiteStatement statement = getReadableDatabase().compileStatement(
				"select count(*) from " + TABLE_NOTE_NAME + " where name=" + name);
		return statement.simpleQueryForLong();
	}
}
