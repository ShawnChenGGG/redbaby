package zz.itcast.redbody7.dao;

import zz.itcast.redbody7.MyConstant.MyConstants;
import zz.itcast.redbody7.utils.LogPrint;
import zz.itcast.redbody7.utils.MyHttpUtils;
import zz.itcast.redbody7.utils.MyHttpUtils.OnPostRequestCallBack;
import zz.itcast.redbody7.utils.MyUtils;

import com.lidroid.xutils.http.RequestParams;

public class AddFavorites {

	/**
	 * 用户id
	 */
	private int userid;
	/**
	 * 商品id
	 */
	private int pid;

	/**
	 * 添加购物车的后半部分URL
	 */
	private final String ADDFAVORITES = "/addfavorites";

	/**
	 * 判断请求网络成功之后，解析json数据是否解析成功
	 */
	private boolean isSuccess;

	public AddFavorites(int pid, int userid) {

		this.pid = pid;
		this.userid = userid;

	}

	/**
	 * 把商品添加到收藏夹，前提是用户已经登录
	 * 
	 * @return 返回true，表示添加成功，返回false，表示添加失败
	 */
	public boolean addFavoritesImpl() {

		isSuccess(false);

		RequestParams params = new RequestParams();

		params.addBodyParameter("userid", userid + "");
		params.addBodyParameter("pid", pid + "");

		MyHttpUtils myHttpUtils = new MyHttpUtils(MyConstants.BASE_URL
				+ ADDFAVORITES, params,true);

		/**
		 * 用张贺封装的方法，请求网络数据
		 */
		myHttpUtils.setPostRequestCallBack(new OnPostRequestCallBack() {

			/**
			 * 请求网络成功之后调用
			 */
			@Override
			public void onPostSuccess(String result) {

				if (MyUtils.checkJson(result)) {

					isSuccess(true);
					LogPrint.logI("lsj", "addFavoritesImpl---json字符串校验成功");

				} else {

					isSuccess(false);
					LogPrint.logI("lsj", "addFavoritesImpl---json字符串校验失败");
				}

			}
		});

		return isSuccess;
	}

	private boolean isSuccess(boolean isSuccess) {

		return this.isSuccess = isSuccess;
	}

}
