/**
 * @date 2016-4-23 上午12:48:42
 */
package zz.itcast.redbody7.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author ma
 * 
 * @date 2016-4-23 上午12:48:42
 */
public class CartDbHelper extends SQLiteOpenHelper {

	public CartDbHelper(Context context) {
		super(context, CartDb.DB_NAME, null, CartDb.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CartDb.Cart.CREATE_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
