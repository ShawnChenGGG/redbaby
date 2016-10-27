package zz.itcast.redbody7.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyAdressOpenHelper extends SQLiteOpenHelper {

	public MyAdressOpenHelper(Context context) {
		super(context, "redboy.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
