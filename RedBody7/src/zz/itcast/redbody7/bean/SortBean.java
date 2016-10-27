package zz.itcast.redbody7.bean;

import java.util.List;

public class SortBean {
	/**
	 * 所有数据源
	 */
	public List<Data> data;
	/**
	 * 数据个数
	 */
	public int list_count;
	/**
	 * 响应类型
	 */
	public String response;
	/**
	 * 所有数据bean
	 * @author wx
	 *
	 */
	public class Data{
		/**
		 * 一级菜单名字
		 */
		public String names;
		/**
		 * 一级菜单id
		 */
		public int id;
		/**
		 * 二级菜单数据源
		 */
		public List<Childs> childs;
		/**
		 * 二级菜单数据
		 * @author wx
		 *
		 */
		public class Childs{
			/**
			 * 打开数据列表的url
			 */
			public String link ;
			/**
			 * 二级菜单名字
			 */
			public String name ;
		}
	}
}
