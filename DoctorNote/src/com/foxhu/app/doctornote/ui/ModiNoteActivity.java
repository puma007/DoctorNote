package com.foxhu.app.doctornote.ui;

import com.foxhu.app.doctornote.R;
import com.foxhu.app.doctornote.db.NoteDB;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
/**
 * DoctorNote
 * @author Foxhu
 * @version 1.0
 *
 */
public class ModiNoteActivity extends Activity {
	private Context mContext;
	//private NoteDB mNoteDB;
	private EditText mNameText;
	private EditText mPrescriptionText;
	private long noteId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this; 
		setContentView(R.layout.activity_modi_note);
		//mNoteDB = new NoteDB(mContext, NoteDB.DATABASE_NAME, null, NoteDB.VERSION);
		Intent intent = getIntent();
		noteId = intent.getLongExtra(MainActivity.EXTRA_NOTEID, 0);
		Cursor cursor = NoteDB.getInstance(getApplicationContext()).queryNoteById(noteId);//根据接收的id查询记录详细信息
		mNameText = (EditText) this.findViewById(R.id.modi_notename);
		mPrescriptionText = (EditText) this.findViewById(R.id.modi_noteprescription);
		
		mNameText.setText(cursor.getString(cursor.getColumnIndexOrThrow(NoteDB.COLUMN_NAME)));
		mPrescriptionText.setText(cursor.getString(cursor.getColumnIndexOrThrow(NoteDB.COLUMN_PRESCRIPTION)));
		getActionBar().setDisplayHomeAsUpEnabled(true);//设置返回按钮
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modi_note, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_modi:
			String name = mNameText.getText().toString();
			String prescription = mPrescriptionText.getText().toString();
			if (name.isEmpty()){
				Toast.makeText(mContext, R.string.name_empty,Toast.LENGTH_SHORT).show();
			}else{
				NoteDB.getInstance(getApplicationContext()).updateNote(noteId, name, prescription);
				this.finish();
			}
            break;
		case android.R.id.home:
			finish();
            break;
		default:
            break;
		}
		return false;
	}

}
