package joe.warehouse.manager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;

import com.caucho.hessian.*;
import com.caucho.hessian.server.HessianServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import joe.warehouse.manager.util.XmlUtils;

import org.apache.log4j.Logger;


public class WarehouseManagerServlet extends HessianServlet
{
	private static final long serialVersionUID = 1L;
	// private static final Logger log = Logger.getLogger(ConfigAdminServletTest.class);
	private static String realPath = "";
	private static final Logger log = Logger.getLogger(WarehouseManagerServlet.class);

	private static String ACTION_SEARCHCONFIG = "searchConfig";
	private static String ACTION_SEARCHTYPE = "searchType";
	private static String ACTION_SAVECONFIG = "saveConfig";
	private static String ACTION_SAVETYPE = "saveType";
	private static String ACTION_DELETECONFIG = "deleteConfig";
	private static String ACTION_DELETETYPE = "deleteType";

	private static String CONFIGADMIN_URL = null;
	private static String CONFIGADMIN_METHOD = null;
	

	public WarehouseManagerServlet()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PrintWriter out = null;

		ServletOutputStream outStream = null;

		String action = request.getParameter("action");
		String customerip = request.getRemoteAddr();
		String customername = request.getRemoteHost();
		String name = request.getParameter("name");
		String type = request.getParameter("type");
		String names = request.getParameter("names");
		String types = request.getParameter("types");
		String avaliable = request.getParameter("avaliable");
		String info = request.getParameter("info");
		String comment = request.getParameter("comment");
		String saveFlag = request.getParameter("saveFlag");
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		ServletException curException = null;

		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		out = response.getWriter();
		String responseString = "";
		Object responseObj = null;
		
		if (action == null)
			return;

		try {
			// doc = getTreeNodeTestResponse("telecom", "0");
			if (action == null || "".equals(action.trim())) {
				return;
			}

			if ("getNerTypes".equalsIgnoreCase(action)) {
				// get ner Types
//				responseObj = getNerTypesJson(action);
			}else if ("getRegNersByType".equalsIgnoreCase(action)) {
//				responseObj = getRegNersByType(type);
								
			}
		}
		catch(Exception ex){			
			ex.printStackTrace();
			log.error("RetrieveDataServlet.doGet() ", ex);
			curException = new ServletException(ex);
		}

		
		if (responseObj != null) {
			responseString = responseObj.toString();
			log.info("Retrieve " + action + " ===> " + responseString);
		}
		
		out.println(responseString);
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException
	{
		doGet(request, response);
	}
	
	public void init(ServletConfig config) throws ServletException {
		String urlFilePath = config.getInitParameter("serviceUrl");
		if (null == urlFilePath) {
			log.error("TrainingServlet.init() Can not get config file url.");
		} else {
			try {				
				String realPath = config.getServletContext().getRealPath(
						urlFilePath);
				log.info("config file real path ->" + realPath);
				XmlUtils doc = new XmlUtils(new File(realPath));
				
				CONFIGADMIN_URL = doc.getNodeText("service_url/service/url");
				CONFIGADMIN_METHOD = doc
						.getNodeText("service_url/service/parser_method");

//				Service.initWSDL(CONFIGADMIN_URL);
//				termnetService = Service.getInstance();
//				nerServiceSoap = Service.getInstance().getServiceSoap();
			} catch (Exception ex) {
				log.error("webService.init() Can not open connection." + ex);
				ex.printStackTrace();
			}
		}
	}


}
