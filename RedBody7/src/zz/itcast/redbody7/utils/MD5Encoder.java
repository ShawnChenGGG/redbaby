package zz.itcast.redbody7.utils;

import java.security.MessageDigest;

/**
 * md5工具类
 * 
 * @author 李胜杰
 * 
 */
public class MD5Encoder {

	/**
	 * 传递一个字符串数据，返回一个加密过的MD5值
	 * 
	 * @param string
	 *            要加密字符串
	 * @return 返回一个已经加密的MD5值
	 */
	public static String encode(String string) {
		try {
			byte[] hash = MessageDigest.getInstance("MD5").digest(
					string.getBytes("UTF-8"));
			StringBuilder hex = new StringBuilder(hash.length * 2);
			for (byte b : hash) {
				if ((b & 0xFF) < 0x10) {
					hex.append("0");
				}
				hex.append(Integer.toHexString(b & 0xFF));
			}
			return hex.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
