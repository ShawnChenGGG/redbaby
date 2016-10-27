/**
 * @date 2016-4-24 下午8:31:35
 */
package zz.itcast.redbody7.bean;

import java.util.List;

/**
 * @author ma
 * 
 * @date 2016-4-24 下午8:31:35
 */
public class InvoiceBean {
	public List<InvoiceList> invoiceList;

	public class InvoiceList {
		public String content;
		public String title;

		@Override
		public String toString() {
			return "InvoiceList [content=" + content + ", title=" + title + "]";
		}

	}

	@Override
	public String toString() {
		return "InvoiceBean [invoiceList=" + invoiceList + "]";
	}

}
