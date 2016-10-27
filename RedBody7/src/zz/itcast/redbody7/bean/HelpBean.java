package zz.itcast.redbody7.bean;

import java.util.List;

/**
 * 帮助中心的javaBean
 * 
 * @author 李胜杰
 * 
 */
public class HelpBean {

	public String response;
	public int version;
	public List<HelpData> helpList;

	public class HelpData {
		public int id;
		public String title;
	}
}
