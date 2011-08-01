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

import org.apache.log4j.Logger;

public class WarehouseManagerServlet extends HessianServlet
{
	private static final long serialVersionUID = 1L;
	// private static final Logger log = Logger.getLogger(ConfigAdminServletTest.class);
	private static String realPath = "";

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

		response.setContentType("text/xml;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		out = response.getWriter();
		String responseString = "";

		out.println(responseString);
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
		IOException
	{
		doGet(request, response);
	}

}
