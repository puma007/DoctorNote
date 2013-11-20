package com.foxhu.app.doctornote.ui;

import com.foxhu.app.doctornote.R;
import com.foxhu.app.doctornote.db.NoteDB;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
/**
 * DoctorNote
 * @author Foxhu
 * @version 1.0
 *
 */
public class MainActivity extends ListActivity implements OnItemLongClickListener{
	public final static String EXTRA_NOTEID = "com.foxhu.app.doctornote.NOTEID"; 
	private final static int ACTIVITY_CREATE = 0;//设置activity返回码
	private final static int ACTIVITY_MODI = 1;//设置activity返回码
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.activity_main);
		//mNoteDB = new NoteDB(getApplicationContext(), NoteDB.DATABASE_NAME, null, NoteDB.VERSION);
		new LoadAsyncTask().execute();
		getListView().setOnItemLongClickListener(this);
	}
	
	//android.os.AsyncTask<Params, Progress, Result>
	private class LoadAsyncTask extends AsyncTask<Void,Void,Cursor>{

		@Override
		protected Cursor doInBackground(Void... arg0) {
			Cursor cursor = NoteDB.getInstance(getApplicationContext()).getAllNote();
			return cursor;
		}

		@Override
		protected void onPostExecute(Cursor result) {
			super.onPostExecute(result);
			//构造方法
			//public SimpleCursorAdapter (Context context, int layout, Cursor c, String[] from, int[] to, int flags)
			String[] from = new String[]{NoteDB.COLUMN_NAME};
			int[] to = new int[]{android.R.id.text1};
			SimpleCursorAdapter records = new SimpleCursorAdapter(mContext,android.R.layout.simple_list_item_1,result,from,to,0);
			setListAdapter(records);
		}
		
		
	}
	
	
	/**
	 * 处理单击事件,单击条目打开编辑窗口
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this,ModiNoteActivity.class);
		intent.putExtra(EXTRA_NOTEID, id);//传递记录的id
		this.startActivityForResult(intent, ACTIVITY_MODI);
		
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		//设置查询
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		//searchView.setSubmitButtonEnabled(true);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_edit:
			Intent intent = new Intent(mContext, EditNoteActivity.class);
            //startActivity(intent);
			this.startActivityForResult(intent, ACTIVITY_CREATE);
            break;
		default:
            break;
		}
		return false;
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		//重新加载数据
		new LoadAsyncTask().execute();
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		new LoadAsyncTask().execute();
	}


	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			final long id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
										.setTitle(R.string.delete_title)
										.setMessage(R.string.delete_body)
										.setNegativeButton(R.string.delete_cancel, null)
										.setPositiveButton(R.string.delete_ok, new OnClickListener(){
											@Override
											public void onClick(DialogInterface dialog, int which) {
	                                                if (NoteDB.getInstance(getApplicationContext()).deleteNote(id)> 0) {
	                                                        new LoadAsyncTask().execute();
	                                                        Toast.makeText(mContext, R.string.delete_success,
	                                                                        Toast.LENGTH_SHORT).show();
	                                                } else {
	                                                        Toast.makeText(mContext, R.string.delete_fail,
	                                                                        Toast.LENGTH_SHORT).show();
	                                                }
	                                        }
											
										});
		
		builder.create().show();
		
		// TODO Auto-generated method stub
		return false;
	}

}
