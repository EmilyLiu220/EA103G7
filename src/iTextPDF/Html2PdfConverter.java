package iTextPDF;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

/**
 * A POJO responsible for converting xhtml string to pdf
 * 
 * @author Jimmy Liao
 *
 */
public class Html2PdfConverter {
	private final int defaultBuffer;
	private final String realContextPath;
	private final String cssFolder;
	private final String fontFolder;
	/**
	 * 依照所傳入的參數產生一個物件實體。
	 * <p>
	 * 若CSS當中有設定字型，則iText套件預設會從系統字型當中去尋找有無符合者，若有擇使用，若無則該字型的文字會變成空白。
	 * 為了達到跨平台的目的，實際上在使用時應該把所用到的字型都包進專案，然後設定iText不要去系統尋找字型。
	 * 這樣就可以將專案對系統字型的相依性移除，不用找字型也可以增加執行時的效率。
	 * @param realContextPath - 專案所在的系統實際路徑，可以藉由ServletContext.getRealPath()方法取得後傳入
	 * @param cssFolder - 放置CSS檔案的目錄名稱(相對於專案根目錄的相對路徑)，用來動態載入CSS時使用。
	 * @param fontFolder - 放置自型檔的目錄名稱(相對於專案根目錄的相對路徑)，用來載入字型時使用。
	 */
	public Html2PdfConverter(String realContextPath, String cssFolder, String fontFolder) {
		this(1024, realContextPath, cssFolder, fontFolder);
	}
	
	/**
	 * 依照所傳入的參數產生一個物件實體。
	 * <p>
	 * 若CSS當中有設定字型，則iText套件預設會從系統字型當中去尋找有無符合者，若有擇使用，若無則該字型的文字會變成空白。
	 * 為了達到跨平台的目的，實際上在使用時應該把所用到的字型都包進專案，然後設定iText不要去系統尋找字型。
	 * 這樣就可以將專案對系統字型的相依性移除，不用找字型也可以增加執行時的效率。
	 * @param defaultBuffer - 預設PDF檔案緩衝大小，若不夠會自行增大。設定一個與輸出檔案預期大小接近的數字可以增加執行效能。
	 * @param realContextPath - 專案所在的系統實際路徑，可以藉由ServletContext.getRealPath()方法取得後傳入
	 * @param cssFolder - 放置CSS檔案的目錄名稱(相對於專案根目錄的相對路徑)，用來動態載入CSS時使用。
	 * @param fontFolder - 放置自型檔的目錄名稱(相對於專案根目錄的相對路徑)，用來載入字型時使用。
	 */
	public Html2PdfConverter(int defaultBuffer, String realContextPath, String cssFolder, String fontFolder) {
		super();
		this.defaultBuffer = defaultBuffer;
		this.realContextPath = realContextPath;
		this.cssFolder = cssFolder;
		this.fontFolder = fontFolder;
	}
	/**
	 * 利用較高階的API來進行PDF的轉換，程式碼Step1-5為官方範例所標示的步驟，基本上本方法內容與官方範例相同。
	 * @param html - 要被轉換成PDF的XHTML
	 * @return 產製出的PDF檔案
	 * @throws IOException - 若產製過程中與IO相關流程出現錯誤
	 * @throws DocumentException - 若產製過程中初始化PdfWriter出現錯誤
	 */
	public byte[] simpleConvert(String html) throws DocumentException, IOException {
		printHtmlToConsole(html);

		ByteArrayOutputStream baos = new ByteArrayOutputStream(defaultBuffer);
		ByteArrayInputStream bain = new ByteArrayInputStream(html.getBytes("UTF-8"));

		/** Step 1 **/
		Document document = new Document();

		/** Step 2 **/
		PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);

		/** Step 3 **/
		document.open();

		/** Step 4 **/
		XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, bain, Charset.forName("UTF-8"));

		/** Step 5 **/
		document.close();

		byte[] pdf = baos.toByteArray();
		baos.close();

		System.out.println();
		System.out.println("*** Pdf creation via simpleConvert successed ***");
		return pdf;
	}
	/**
	 * 利用較低階的API來進行PDF的轉換，程式碼Step1-5為官方範例所標示的步驟，基本上本方法內容與官方範例相同。
	 * <p>
	 * 詳細說明請見程式碼中的註解。
	 * @param html - 要被轉換成PDF的XHTML
	 * @return 產製出的PDF檔案
	 * @throws IOException - 若產製過程中與IO相關流程出現錯誤
	 * @throws DocumentException - 若產製過程中初始化PdfWriter出現錯誤
	 */
	public byte[] detailedConvert(String html) throws IOException, DocumentException {

		printHtmlToConsole(html);

		ByteArrayInputStream bain = new ByteArrayInputStream(html.getBytes("UTF-8"));
		ByteArrayOutputStream baos = new ByteArrayOutputStream(defaultBuffer);

		/** Step 1 **/
		Document document = new Document();

		/** Step 2 **/
		PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);

		/** Step 3 **/
		document.open();

		/** Step 4 **/
		// CSS
		CSSResolver cssResolver =
				XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
		CssFile cssFile = XMLWorkerHelper.getCSS(new FileInputStream(realContextPath +"/"+ cssFolder + "/pdf-extra.css"));
		// 額外載入一個CSS檔案
		cssResolver.addCss(cssFile);

		// HTML
		// 這裡可以呼叫 new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS)來達到阻止程式去系統找字型的效果。
		XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider();
		// 註冊字型，這裡因為沒有設定成不要去系統找字型所以這行拿掉也無所謂。
		fontProvider.register(realContextPath +"/"+ fontFolder + "/Monaco.ttf",
				"Monaco");
		CssAppliers appliers = new CssAppliersImpl(fontProvider);
		HtmlPipelineContext pipelineContext = new HtmlPipelineContext(appliers);
		pipelineContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

		// Pipelines
		PdfWriterPipeline writerPipeline = new PdfWriterPipeline(document, pdfWriter);
		HtmlPipeline htmlPipeline = new HtmlPipeline(pipelineContext, writerPipeline);
		CssResolverPipeline cssResolverPipeline = new CssResolverPipeline(cssResolver, htmlPipeline);
		
		// Parse XHTML
		XMLWorker worker = new XMLWorker(cssResolverPipeline, true);
		XMLParser p = new XMLParser(worker);
		p.parse(bain, Charset.forName("UTF-8"));

		/** Step 5 **/
		document.close();

		byte[] pdf = baos.toByteArray();
		baos.close();

		System.out.println();
		System.out.println("*** Pdf creation via detailedConvert() successed ***");
		return pdf;

	}
	/**
	 * 毫無反應就是把要被轉換的XHTML印到Console上
	 * @param html - 要被印的XHTML
	 */
	private void printHtmlToConsole(String html) {
		System.out.println("*** Create pdf from the html below ***");
		html = html == null ? html : html.trim();
		System.out.println(html);
	}
}
