package zz.itcast.redbody7.bean;

import java.util.ArrayList;
import java.util.List;

public class BrandMenu {
	/**
	 * 返回请求的类型
	 */
	public String response;
	/**
	 * 返回版本号
	 */
	public String version;
	/**
	 * 所有条目的集合,包括一级菜单,二级菜单,三级菜单内容
	 */
	public ArrayList<Category> category;
	
	public class Category{
		/**
		 * 每级菜单条目对应的ID;
		 */
		public String id;
		/**
		 * 为true 的时候代表可以打开商品列表,
		 * 
		 */
		public boolean isleafnode;
		/**
		 * 为true 的时候代表可以打开对应的商品列表,
		 * 和上面一个意思
		 */
		public boolean leafNode;
		/**
		 * 表示为第几级菜单的条目
		 */
		public int level;
		/**
		 * 当前条目的名字
		 */
		public String name;
		/**
		 * 代表是哪一个ID的子条目;
		 */
		public int printID;
		/**
		 * 代表是哪一个ID的子条目;
		 * 和上面一个意思
		 */
		public int parentid;
		 /**
		  * 调局图标对应的url;
		  * 1级菜单拥有
		  * 
		  */
		public String pic;
		 /**
		  * 分类简介,一级菜单拥有
		  */
		public String t;
	}
}
