/**
 * @date 2016-4-26 上午11:59:47
 */
package zz.itcast.redbody7.bean;

import java.util.List;

/**
 * @author ma
 * 
 * @date 2016-4-26 上午11:59:47
 */
public class FollowInfoBean {

	public List<FollowInfo> followInfo;

	public class FollowInfo {
		public String followinfo;

		@Override
		public String toString() {
			return "FollowInfo [followinfo=" + followinfo + "]";
		}

	}

	@Override
	public String toString() {
		return "FollowInfoBean [followInfo=" + followInfo + "]";
	}

}
