package com.foxhu.app.doctornote.ui;

import com.foxhu.app.doctornote.db.NoteDB;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
/**
 * DoctorNote
 * @author Foxhu
 * @version 1.0
 *
 */
public class SearchableActivity extends ListActivity {
	private Context mContext;
	//private NoteDB mNoteDB;
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent(this,ModiNoteActivity.class);
		intent.putExtra(MainActivity.EXTRA_NOTEID, id);//传递记录的id
		this.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = this;
		//mNoteDB = new NoteDB(mContext, NoteDB.DATABASE_NAME, null, NoteDB.VERSION);
		getActionBar().setDisplayHomeAsUpEnabled(true);//设置返回按钮
		handleIntent(getIntent());
	}

	private void handleIntent(Intent intent) {
		// TODO Auto-generated method stub
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            fillList(query);
        }
	}

	private void fillList(String query) {
		// TODO Auto-generated method stub
		Cursor result = NoteDB.getInstance(getApplicationContext()).queryNoteByName(query);
		String[] from = new String[]{NoteDB.COLUMN_NAME};
		int[] to = new int[]{android.R.id.text1};
		SimpleCursorAdapter records = new SimpleCursorAdapter(mContext,android.R.layout.simple_list_item_1,result,from,to,0);
		setListAdapter(records);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		handleIntent(intent);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case android.R.id.home:
			finish();
            break;
		default:
            break;
		}
		return super.onOptionsItemSelected(item);
	}

	

}
