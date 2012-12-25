package joe.warehouse.manager.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang.StringUtils
{

	/**
	 * if value is null or begin with $ return true else return false. <br>
	 * <br>
	 * for example:
	 * <p>
	 * StringUtils.isParameterNull(" ") = false;<br>
	 * StringUtils.isParameterNull("") = false;<br>
	 * StringUtils.isParameterNull("test") = false;<br>
	 * StringUtils.isParameterNull(null) = true;<br>
	 * StringUtils.isParameterNull("$ims") = true;<br>
	 * </p>
	 * 
	 * @param s
	 * @return s or null
	 */
	public static boolean isParameterNull(String s)
	{
		if (s == null || s.startsWith("$"))
			return true;
		return false;
	}

	/**
	 * if value is null or begin with $ return null else return itself. <br>
	 * <br>
	 * for example:
	 * <p>
	 * StringUtils.isParameterNull(" ") = "";<br>
	 * StringUtils.isParameterNull("") = "";<br>
	 * StringUtils.isParameterNull("test") = "test";<br>
	 * StringUtils.isParameterNull(" test ") = "test";<br>
	 * StringUtils.isParameterNull(null) = null;<br>
	 * StringUtils.isParameterNull("$ims") = null;<br>
	 * </p>
	 * 
	 * @param s
	 * @return
	 */
	public static String getParameter(String s)
	{
		if (s == null || s.startsWith("$"))
		{
			return null;
		}
		return s.trim();
	}

	/**
	 * format new line. <br>
	 * default width = 80. <br>
	 * 
	 * @param srcStr
	 * @return
	 */
	public static String formatStringNewline(String srcStr)
	{
		if (srcStr == null)
			return "";
		return formatStringNewline(srcStr, 80);
	}

	/**
	 * format new line. <br>
	 * 
	 * @param srcStr
	 * @param length
	 * @return
	 */
	public static String formatStringNewline(String srcStr, int length)
	{
		if (srcStr == null || srcStr.equals(""))
			return "";
		String returnStr = "";
		String tempStr;
		int num = srcStr.length() / length + 1;
		for (int i = 0; i < num; i++)
		{
			if ((i + 1) * length > srcStr.length())
			{
				tempStr = srcStr.substring(i * length);
			}
			else
			{
				tempStr = srcStr.substring(i * length, (i + 1) * length) + "\n";
			}
			returnStr += tempStr;
		}
		return returnStr;
	}

	/**
	 * replace word in the text with substitution appeared first,<br>
	 * the word is described by pattern.<br>
	 * use JDK regexp
	 * 
	 * @param text
	 * @param pattern
	 * @param substitution
	 * @see org.apache.commons.lang.StringUtils#replace(String, String, String) not use regexp
	 * @return
	 */
	public static String substituteWord(String text, String pattern, String substitution)
	{
		Pattern p = Pattern.compile(pattern);
		return p.matcher(text).replaceFirst(substitution);
	}

	/**
	 * replace word in the text with substitution,<br>
	 * the word is described by pattern.<br>
	 * use JDK regexp
	 * 
	 * @param text
	 * @param pattern
	 * @param substitution
	 * @see org.apache.commons.lang.StringUtils#replace(String, String, String) not use regexp
	 * @return
	 */
	public static String substituteAll(String text, String pattern, String substitution)
	{
		Pattern p = Pattern.compile(pattern);
		return p.matcher(text).replaceAll(substitution);
	}

	public static final String COMMA_URL_STR = ",";
	public static final String ENTER_URL_STR = "\r";
	public static final String NL_URL_STR = "\n";
	public static final String ENTER_NL_STR = "\r\n";
	public static final String PERIOD_STR = ".";

	/**
	 * %20 indicate space %0D indicate Enter %2C indicate comma
	 * 
	 * @param text
	 * @return list
	 */
	public static List accessTextArea(String type, String text)
	{
		List list = new ArrayList();
		String word = null;
		if (type.equals("concept") || type.equals("dnl"))
		{
			list.add(text);
			return list;
		}

		text = text.replaceAll(ENTER_NL_STR, COMMA_URL_STR);
		text = text.replaceAll(ENTER_URL_STR, COMMA_URL_STR);
		text = text.replaceAll(NL_URL_STR, COMMA_URL_STR);

		String[] wordArray = text.split(COMMA_URL_STR);
		for (int i = 0; i < wordArray.length; i++)
		{
			word = wordArray[i].trim();
			if (!word.equals(""))
				list.add(word);
		}
		return list;
	}

	public static List accessUploadFile(String type, String path) throws Exception
	{
		return null;
	}

	public static List accessLocalFile(String type, String path) throws Exception
	{
		try
		{
			StringBuffer text = new StringBuffer();
			BufferedReader bfr = new BufferedReader(new FileReader(path));
			String line = null;
			while ((line = bfr.readLine()) != null)
			{
				text.append(line);
				text.append(ENTER_NL_STR);
			}
			text.append(NL_URL_STR);
			bfr.close();
			return accessTextArea(type, text.toString());
		}
		catch (FileNotFoundException fnfe)
		{
			fnfe.printStackTrace();
			throw new Exception("In StringUtils accessLocalFile File not found for " + path);
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
			throw new Exception("In StringUtils IO exception for " + path);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("In StringUtils File access exception for " + path);
		}
	}

	public static List accessNetFile(String type, String urlString) throws Exception
	{
		try
		{
			StringBuffer text = new StringBuffer();
			URL url = new URL(urlString);
			BufferedReader bfr = new BufferedReader(new InputStreamReader(url.openStream()));
			String line = null;
			while ((line = bfr.readLine()) != null)
			{
				text.append(line);
				text.append(ENTER_NL_STR);
			}
			bfr.close();
			return accessTextArea(type, text.toString());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
		}
	}

	public static String preProcessString(String requestStr)
	{
		requestStr = requestStr.toLowerCase();

		requestStr = requestStr.replaceAll("Can't", "Can not");
		requestStr = requestStr.replaceAll("can't", "can not");
		requestStr = requestStr.replaceAll("Won't", "will not");
		requestStr = requestStr.replaceAll("won't", "will not");
		requestStr = requestStr.replaceAll("Shan't", "shall not");
		requestStr = requestStr.replaceAll("shan't", "shall not");
		requestStr = requestStr.replaceAll("n't", " not");
		requestStr = requestStr.replaceAll("'ll", " will");
		requestStr = requestStr.replaceAll("'re", " are");
		requestStr = requestStr.replaceAll("'ve", " have");
		requestStr = requestStr.replaceAll("'m", " am");
		requestStr = requestStr.replaceAll("'d", " would");

		requestStr = requestStr.replaceAll("never", "not");
		requestStr = requestStr.replaceAll("hardly", "not");
		requestStr = requestStr.replaceAll("rarely", "not");
		requestStr = requestStr.replaceAll("seldom", "not");
		requestStr = requestStr.replaceAll("scarcely", "not");

		requestStr = requestStr.replaceAll("please", "");
		requestStr = requestStr.replaceAll("totally", "");
		requestStr = requestStr.replaceAll("radical", "");
		requestStr = requestStr.replaceAll("indeed", "");
		requestStr = requestStr.replaceAll("truly", "");
		
		requestStr = requestStr.replaceAll("damn", "");
		requestStr = requestStr.replaceAll("rotten", "");
		requestStr = requestStr.replaceAll("cockamamie", "");
		requestStr = requestStr.replaceAll("freaking", "");
		requestStr = requestStr.replaceAll("goddamn", "");
		requestStr = requestStr.replaceAll("goddam", "");
		requestStr = requestStr.replaceAll("Goddamned", "");
		requestStr = requestStr.replaceAll("lousy", "");

		requestStr = requestStr.trim();

		if (requestStr.indexOf(" ") < 0)
		{
			return requestStr.toLowerCase();
		}

		String[] strArray = requestStr.split("\\.");
		String reqSentence = "";
		for (String string : strArray)
		{
			reqSentence = reqSentence + getFirstUpperCaseString(string.trim()) + ".";
		}
		return reqSentence;
	}

	public static String getFirstUpperCaseString(String string)
	{
		return string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
	}
}
