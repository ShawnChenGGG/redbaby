/**
 * @date 2016-4-23 上午1:14:51
 */
package zz.itcast.redbody7.dao;

import java.util.ArrayList;
import java.util.List;

import zz.itcast.redbody7.bean.CartBean;
import zz.itcast.redbody7.db.CartDb;
import zz.itcast.redbody7.db.CartDbHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

/**
 * @author ma
 * 
 * @date 2016-4-23 上午1:14:51
 */
public class CartDao {

	private CartDao(Context ctx) {
		helper = new CartDbHelper(ctx);
	}

	private static CartDao dao = null;

	public static synchronized CartDao getInstance(Context ctx) {
		if (dao == null) {
			dao = new CartDao(ctx);
		}
		return dao;
	}

	private CartDbHelper helper = null;

	/**
	 * 添加购物车
	 * 
	 * @date 2016-4-23 上午1:20:19
	 */
	public void addCart(int userId, int goodsId, int goodsCount, String attrId) {
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(CartDb.Cart.COLUMN_USER_ID, userId);
		values.put(CartDb.Cart.COLUMN_GOODS_ID, goodsId);
		values.put(CartDb.Cart.COLUMN_COUNT, goodsCount);
		values.put(CartDb.Cart.COLUMN_ATTR_ID, attrId);
		db.insert(CartDb.Cart.TABLE_NAME, null, values);
	}

	/**
	 * 删除商品,产出成功返回true,失败返回false
	 * 
	 * @date 2016-4-23 上午1:21:15
	 */
	public boolean deleteGoods(int goodsId) {
		SQLiteDatabase db = helper.getWritableDatabase();
		int rows = db.delete(CartDb.Cart.TABLE_NAME,
				CartDb.Cart.COLUMN_GOODS_ID + " = ?", new String[] { goodsId
						+ "" });
		if (rows > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 通过用户userId,查找id集合
	 * 
	 * @date 2016-4-23 上午1:34:29
	 */
	public List<CartBean> findGoodsFromUerId(int userId) {
		List<CartBean> carts = null;
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from " + CartDb.Cart.TABLE_NAME
				+ " where " + CartDb.Cart.COLUMN_USER_ID + " = ?",
				new String[] { userId + "" });
		if (cursor != null && cursor.getCount() > 0) {
			carts = new ArrayList<CartBean>();
			while (cursor.moveToNext()) {
				CartBean cart = new CartBean();
				int goodsId = cursor.getInt(cursor
						.getColumnIndex(CartDb.Cart.COLUMN_GOODS_ID));
				int goodsCount = cursor.getInt(cursor
						.getColumnIndex(CartDb.Cart.COLUMN_COUNT));
				int attrsId = cursor.getInt(cursor
						.getColumnIndex(CartDb.Cart.COLUMN_ATTR_ID));
				cart.userId = userId;
				cart.goodsId = goodsId;
				cart.count = goodsCount;
				cart.attrId = attrsId + "";
				carts.add(cart);
			}
			cursor.close();
		}
		return carts;
	}

	/**
	 * 获得sku列表ID
	 * 
	 * @date 2016-4-23 上午1:34:29
	 */
	public List<CartBean> findGoodsInfo() {
		List<CartBean> carts = null;
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from " + CartDb.Cart.TABLE_NAME,
				null);
		if (cursor != null && cursor.getCount() > 0) {
			carts = new ArrayList<CartBean>();
			while (cursor.moveToNext()) {
				CartBean cart = new CartBean();
				int goodsId = cursor.getInt(cursor
						.getColumnIndex(CartDb.Cart.COLUMN_GOODS_ID));
				int goodsCount = cursor.getInt(cursor
						.getColumnIndex(CartDb.Cart.COLUMN_COUNT));
				int attrsId = cursor.getInt(cursor
						.getColumnIndex(CartDb.Cart.COLUMN_ATTR_ID));
				cart.goodsId = goodsId;
				cart.count = goodsCount;
				cart.attrId = attrsId + "";
				carts.add(cart);
			}
			cursor.close();
		}
		return carts;
	}

	// 根据商品id获取该商品的数量
	/**
	 * 根据商品的id查找商品的数量
	 * 
	 * @return 0表示有商品,其它表示商品的具体数目
	 * @date 2016-4-23 上午11:16:45
	 */
	public int getGoodsCountByGoodsid(int goodsId) {
		int count = 0;
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(
				"select count from cart_info where goods_id = ?",
				new String[] { goodsId + "" });
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				count = cursor.getInt(0);
			}
			cursor.close();
		}
		return count;
	}

	/**
	 * 添加购物车 。高级版
	 * 
	 * @date 2016-4-23 上午1:20:19
	 */
	public void addCartHight(int userId, int goodsId, int goodsCount,
			String attrId) {

		if (goodsCount == 0 || TextUtils.isEmpty(attrId)
				|| Integer.parseInt(attrId) == 0) {
			return;
		}

		SQLiteDatabase db = helper.getWritableDatabase();

		int count = 0;
		Cursor cursor = db
				.rawQuery(
						"select count from cart_info where goods_id = ? and arrt_id = ? and user_id = ?",
						new String[] { goodsId + "", attrId, userId + "" });

		// 数据库中不存在这个商品时执行
		if (cursor != null && cursor.moveToNext()) {
			count = cursor.getInt(0);

			ContentValues values = new ContentValues();

			values.put(CartDb.Cart.COLUMN_COUNT, goodsCount + count);

			db.update(CartDb.Cart.TABLE_NAME, values,
					" goods_id = ? and arrt_id = ? and user_id = ?",
					new String[] { goodsId + "", attrId, userId + "" });

			cursor.close();

		} else {// 数据库中存在这个商品时执行

			ContentValues values = new ContentValues();

			values.put(CartDb.Cart.COLUMN_USER_ID, userId);
			values.put(CartDb.Cart.COLUMN_GOODS_ID, goodsId);
			values.put(CartDb.Cart.COLUMN_COUNT, goodsCount);
			values.put(CartDb.Cart.COLUMN_ATTR_ID, attrId);
			db.insert(CartDb.Cart.TABLE_NAME, null, values);

		}

	}

}
