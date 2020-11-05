package iTextPDF;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 客製化的ServletResponse，用來擷取Response的文字。
 * <p>
 * 在Servlet環境中如果想要客製化HttpServletResponse或者是HttpServletRequest，
 * 則可以繼承HttpServletResponseWrapper或者是HttpServletRequestWrapper。
 * 因為Request和Response的實作是由容器提供，我們只使用介面，因此沒辦法直接對Request以及Response實作進行繼承來複寫方法。
 * 這兩個類別藉由包裹Request以及Response並將方法委派給所包裹的物件來達到實作介面的效果。
 * 我們可以藉由複寫這兩個類別的方法達到客製化Request以及Response的目的。
 * <p>
 * 這裡藉由複寫getWriter()方法，將原本應該要回傳的Writer抽換成接到StringWriter的PrintWriter。
 * 這樣網頁回應的內容就會被寫到類別實體所帶的StringWriter中，而不會直接回應給客戶端。
 * 之後再藉由呼叫getResponseString()來取得回應內容，作進一步的處理 (轉成PDF後回應給客戶端)。
 * 
 * @author Jimmy Liao
 *
 */
public class Html2PdfServletResponse extends HttpServletResponseWrapper {
	private PrintWriter pw;
	private StringWriter sw;

	public Html2PdfServletResponse(HttpServletResponse response) {
		super(response);
		sw = new StringWriter();
		pw = new PrintWriter(sw);
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return super.getOutputStream();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return pw;
	}
	/**
	 * 取得儲存於類別實體當中的回應文字內容
	 * @return 回應文字內容
	 */
	public StringBuffer getResponseString() {
		return sw.getBuffer();
	}

}
