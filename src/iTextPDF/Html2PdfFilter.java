package iTextPDF;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;

/**
 * 處理將回應HTML轉成PDF的Filter。
 */
public class Html2PdfFilter implements Filter {
	private String contextRealPath;
	private String cssFolder;
	private String fontFolder;
	private Html2PdfConverter converter;


	/**
	 * 初始化所需要的實體變數，因為其中一種轉換方法有要以程式的方式額外載入CSS檔來套用，
	 * 因此這裡藉由Filter的InitParameter取得放置CSS及字形檔的目錄名稱。
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		contextRealPath = fConfig.getServletContext().getRealPath("/");
		cssFolder = fConfig.getInitParameter("cssFolder");
		fontFolder = fConfig.getInitParameter("fontFolder");

		converter = new Html2PdfConverter(1024, contextRealPath, cssFolder, fontFolder);
	}

	/**
	 * 這個方法是決定是否產製PDF的判斷與執行地點
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 取得判別用的請求參數
		String pdfParam = request.getParameter("pdf");
		// 如果沒有要產製PDF，則進行一般正常的請求/回應處理流程，不做任何干預
		if (pdfParam == null) {
			chain.doFilter(request, response);
			return;
		}
		// 程式碼如果跑到這裡代表pdfParam != null，即需要產製PDF
		// 這時候就利用原本的response來產生一個自訂的Html2PdfServletResponse實體
		Html2PdfServletResponse wrappedResponse = new Html2PdfServletResponse((HttpServletResponse) response);
		/*** Incoming above ***/

		// 將自訂的Html2PdfServletResponse實體傳給後續請求/回應處理流程
		chain.doFilter(request, wrappedResponse);

		/*** Outgoing below ***/

		// 請求/回應處理流程結束，回到這支Filter後，從Html2PdfServletResponse實體當中取得JSP的回應內容。
		StringBuffer html = wrappedResponse.getResponseString();

		// 將JSP回應內容轉換為PDF，這裡依照請求參數的內容來判斷要呼叫哪個產製PDF的方法。
		byte[] pdf;
		try {
			if ("detail".equals(pdfParam)) {
				pdf = converter.detailedConvert(html.toString());
			} else {
				pdf = converter.simpleConvert(html.toString());
			}

		} catch (DocumentException e) {
			e.printStackTrace();
			throw new ServletException("Failed to generate pdf for the incoming request", e);
		}
		// 設定正確的Content-Type來告訴瀏覽器回應內容為PDF
		response.setContentType("application/pdf");
		// 將PDF藉由OutputStream回傳給瀏覽器
		response.getOutputStream().write(pdf);

	}

	@Override
	public void destroy() {
		// do nothing
	}

}
