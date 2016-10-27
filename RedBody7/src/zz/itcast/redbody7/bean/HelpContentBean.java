package zz.itcast.redbody7.bean;

import java.util.List;

public class HelpContentBean {

	public String response;
	public List<HelpContentData> help;

	public class HelpContentData {
		public String content;
		public String title;
	}
}
