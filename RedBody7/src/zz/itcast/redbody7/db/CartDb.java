/**
 * @date 2016-4-23 上午12:52:20
 */
package zz.itcast.redbody7.db;

/**
 * @author ma
 *
 * @date 2016-4-23 上午12:52:20
 */
public interface CartDb {
	/**
	 * 数据库名
	 */
	String DB_NAME = "cart_info.db";
	/**
	 * 数据库版本
	 */
	int DB_VERSION = 1;

	/**
	 * 购物车
	 * @author ma
	 *
	 * @date 2016-4-23 上午12:53:45
	 */
	public interface Cart{
		/**
		 * 表名
		 */
		String TABLE_NAME = "cart_info";
		/**
		 * 列 _id
		 */
		String COLUMN_ID = "_id";
		
		/**
		 * 用户id
		 */
		String COLUMN_USER_ID = "user_id";
		/**
		 * 商品ID
		 */
		String COLUMN_GOODS_ID = "goods_id";
		
		/**
		 * 商品数量
		 */
		String COLUMN_COUNT = "count";
		
		/**
		 * 商品属性ID
		 */
		String COLUMN_ATTR_ID = "arrt_id";
		
		/**
		 * 执行sql语句
		 */
		String CREATE_TABLE_SQL = " create table "+ TABLE_NAME+"( "
									+ COLUMN_ID + " integer primary key autoincrement,"
									+ COLUMN_USER_ID + " integer ,"
									+ COLUMN_GOODS_ID + " integer , "
									+ COLUMN_COUNT + " integer , "
									+ COLUMN_ATTR_ID + " integer)";
		
	}
}
