package zz.itcast.redbody7.bean;

import java.util.List;

public class CommentBean {
	
	public int list_count;
	
	public String response;
	
	public List<Comment> comment;
	
	public class Comment{
		
		public String content;
		
		public String time;
		
		public String title;
		
		public String username;
	}
}
