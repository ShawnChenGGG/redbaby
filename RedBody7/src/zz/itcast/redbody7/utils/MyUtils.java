package zz.itcast.redbody7.utils;

import org.json.JSONException;
import org.json.JSONObject;

import zz.itcast.redbody7.MyConstant.MyConstants;

import android.content.Context;

/**
 * 自定义的工具类
 * 
 * @author 李胜杰
 * 
 */
public class MyUtils {

	/**
	 * 用户id，默认为0，表示没有用户登录，若不为0，表示已经有用户登录
	 */
	public static int userId = 0;

	/**
	 * 登录的用户名，为null表示没有用户登录，不为null表示有用户登录
	 */
	public static String userName = null;

	/**
	 * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 网络连接成功之后，校验自己请求的参数列表数据是否请求错误
	 * 
	 * @param json
	 *            要校验的json数据
	 * @return 校验成功，返回true，校验失败返回false
	 */
	public static boolean checkJson(String json) {
			
		try {
			JSONObject jo = new JSONObject(json);
			String response = jo.getString("response");

			if ("error".equals(response)) {

				JSONObject error = jo.getJSONObject("error");
				String text = error.getString("text");

				LogPrint.logI(MyConstants.TAG_REDBODY7,
						"checkJson--联网成功--但是数据库返回的json中的response为error： "
								+ text);

				return false;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return true;

	}

}
