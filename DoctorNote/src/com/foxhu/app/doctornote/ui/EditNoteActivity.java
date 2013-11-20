package com.foxhu.app.doctornote.ui;

import com.foxhu.app.doctornote.R;
import com.foxhu.app.doctornote.db.NoteDB;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
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
public class EditNoteActivity extends Activity {
	private Context mContext;
	private EditText mNameText;
	private EditText mPrescriptionText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this; 
		setContentView(R.layout.activity_edit_note);
		mNameText = (EditText) this.findViewById(R.id.notename);
		mPrescriptionText = (EditText) this.findViewById(R.id.noteprescription);
		getActionBar().setDisplayHomeAsUpEnabled(true);//设置返回按钮
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_note, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_save:
			String name = mNameText.getText().toString();
			String prescription = mPrescriptionText.getText().toString();
			if (name.isEmpty()){
				Toast.makeText(mContext, R.string.name_empty,Toast.LENGTH_SHORT).show();
			}else{
				NoteDB.getInstance(getApplicationContext()).insertNote(name, prescription);
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
