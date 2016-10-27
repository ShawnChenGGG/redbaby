package zz.itcast.redbody7.utils;

import android.util.Log;

/**
 * 打印日志工具类
 * @author Administrator
 *
 */
public class LogPrint {

	/**
	 * 日志开关
	 */
	private static boolean isBug= true;
	
	public static void logI(String tag,String msg){
		if(isBug){
			Log.i(tag, msg);
		}
	}
}
