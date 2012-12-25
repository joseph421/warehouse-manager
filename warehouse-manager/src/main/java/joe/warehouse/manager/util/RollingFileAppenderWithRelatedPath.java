package joe.warehouse.manager.util;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

import org.apache.log4j.RollingFileAppender;

public class RollingFileAppenderWithRelatedPath extends RollingFileAppender {

	public void setFile(String file) 
	{
		String abstractPath = "";
		URL this_url = this.getClass().getClassLoader().getResource("/");
		if (this_url != null)
			abstractPath = this_url.getPath();
		else
			abstractPath = System.getProperty("user.dir");

		int classindex = abstractPath.indexOf("WEB-INF/classes");
		String localPath = "";
		if (classindex > 0) {
			localPath = abstractPath.replaceAll("WEB-INF/classes",
					"WEB-INF/logs/");
		}
		System.out.println("log path=" + localPath);
		super.setFile(localPath + file);
	}
}
