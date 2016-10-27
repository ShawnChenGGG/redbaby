package zz.itcast.redbody7.utils;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.util.Log;
import zz.itcast.redbody7.bean.CommodityListBean.Commodity;

public class MyCommoditySortUtils{
	/**
	 * 按照价格排序
	 */
	public final int PRICE_SORT=1;
	/**
	 * 按照销量排序
	 */
	public final int XIAOLIANG_SORT=2;
	/**
	 * 按照好评排序
	 */
	public final int GOODS_SORT=3;
	/**
	 * 按照时间排序
	 */
	public final int TIME_SORT=4;
	
	/**
	 * 获取降序的集合
	 * @param sort 从当前工具类获取排序方式 
	 * @param list 要改变排序的集合
	 * @return 返回按照指定的排序方式对应的集合
	 */
	public List<Commodity> getDownList(int sort,List<Commodity> list){
		List<Commodity> mlist=list;
		Collections.sort(mlist, new ComparatorSort(sort));
		for (Commodity commodity : mlist) {
			LogPrint.logI("MyCommoditySortUtils", ""+commodity.price);
		}
		return mlist;
	}
	
	/**
	 * 获取升序的集合
	 * @param sort 从当前工具类获取排序方式 
	 * @param list 要改变排序的集合
	 * @return 返回按照指定的排序方式对应的集合
	 */
	public List<Commodity> getUpList(int sort,List<Commodity> list){
		List<Commodity> mlist=list;
		//先降序
		Collections.sort(mlist, new ComparatorSort(sort));
		//再反过来,就是升序
		Collections.reverse(mlist);
		
		return mlist; 
	}
	

	
	/**
	 * 按照类型排序的比较器(顺序为降序排序)
	 * @author wx
	 *
	 */
	private class ComparatorSort implements Comparator<Commodity>{
		
		/**
		 * 记录要排序的方式
		 */
		private int sort;
		
		public ComparatorSort(){}
		
		public ComparatorSort(int sort){
			this.sort=sort;
		}
		
		@Override
		public int compare(Commodity jc1, Commodity jc2) {
			
			if (sort==PRICE_SORT) {
				//是jiage排序价格对应的销量降序比较器
				return (jc2.price < jc1.price ? -1 :(jc2.price == jc1.price ? 0 : 1));
			}else if (sort==TIME_SORT) {
				//如果是按照上架时间排序,返回上架时间降序
				//但是服务器没有返回上架时间,只能返回销量
				return (jc2.sales < jc1.sales ? -1 :(jc2.sales == jc1.sales ? 0 : 1));
			}else if (sort==GOODS_SORT) {
				//是好评降序的话,返回好评降序的
				return (jc2.commentcount < jc1.commentcount ? -1 :(jc2.commentcount == jc1.commentcount ? 0 : 1));
			}
			//默认返回为销量降序
			return (jc2.sales < jc1.sales ? -1 :(jc2.sales == jc1.sales ? 0 : 1));
			
		}
		
	}
	
	
}
