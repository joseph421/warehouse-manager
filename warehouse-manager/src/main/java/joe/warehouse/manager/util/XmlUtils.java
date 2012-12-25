package joe.warehouse.manager.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlUtils
{
	/**
	 * unescape
	 */
	private static final String HUMAN[] = { "&", "\"", "<", ">" };

	/**
	 * escape
	 */
	private static final String XMLS[] = { "&amp;", "&quot;", "&lt;", "&gt;" };

	protected Document document = null;

	public XmlUtils()
	{

	}

	/**
	 * @param file
	 * @throws DocumentException
	 */
	public XmlUtils(File file) throws DocumentException
	{
		createDocument(file);
	}

	/**
	 * @param url
	 * @throws DocumentException
	 */
	public XmlUtils(URL url) throws DocumentException
	{
		createDocument(url);
	}

	/**
	 * @param doc
	 */
	public XmlUtils(Document doc)
	{
		this.document = doc;
	}

	/**
	 * @param str
	 * @throws DocumentException
	 */
	public XmlUtils(String str) throws DocumentException
	{
		createDocument(str);
	}

	/**
	 * @author chen
	 * @throws DocumentException
	 */
	public void createDocument(File file) throws DocumentException
	{
		SAXReader reader = new SAXReader();
		this.document = reader.read(file);
	}

	/**
	 * @param element
	 */
	public void createDocument(Element element)
	{
		Document doc = DocumentHelper.createDocument(element);
		this.document = doc;
	}

	/**
	 * @param url
	 * @throws DocumentException
	 */
	public void createDocument(URL url) throws DocumentException
	{
		SAXReader reader = new SAXReader();
		this.document = reader.read(url);
	}

	/**
	 * @param xmlString
	 * @throws DocumentException
	 */
	public void createDocument(String xmlString) throws DocumentException
	{
		this.document = DocumentHelper.parseText(xmlString);
	}

	/**
	 * @param nodePath
	 * @return
	 */
	public Element addSubNode(String nodePath)
	{
		if (this.document == null)
			return null;

		return XmlUtils.addSubElement(this.document.getRootElement(), nodePath);
	}

	/**
	 * @return
	 */
	public Element getRootElement()
	{
		if (this.document == null)
			return null;

		return this.document.getRootElement();
	}

	/**
	 * @param el
	 * @param inputPath
	 * @param key
	 * @param newValue
	 */
	public static void setNodeAttribute(Element el, String inputPath, String key, String newValue)
	{
		if (null == el || null == inputPath)
		{
			return;
		}

		Element subel = ((Element) el.selectSingleNode(inputPath));
		if (null == subel)
		{
			return;
		}
		subel.addAttribute(key, newValue);
	}

	/**
	 * @param el
	 * @param inputPath
	 * @param newValue
	 */
	public static void setNodeAttribute(Element el, String inputPath, String newValue)
	{
		setNodeAttribute(el, inputPath, "value", newValue);
	}

	/**
	 * @param doc
	 * @param xpath
	 * @return
	 */
	public void setNodeAttribute(String inputPath, String key, String newValue)
	{
		String xpath = pathValidate(inputPath);
		if (!isNode(xpath))
			return;
		Element element = (Element) this.document.selectSingleNode(xpath);
		element.attribute(key).setValue(newValue);
	}

	/**
	 * @param doc
	 * @param xpath
	 * @param node
	 * @param value
	 * @return
	 */
	public void setNodeAttribute(String inputPath, String value)
	{
		setNodeAttribute(inputPath, "value", value);
	}

	/**
	 * @param doc
	 * @param xpath
	 * @return
	 */
	public void setNodeContent(String inputPath, String newValue)
	{
		String xpath = pathValidate(inputPath);
		if (!isNode(xpath))
			return;
		Node node = this.document.selectSingleNode(xpath);
		node.setText(newValue);
	}

	/**
	 * @param xpath
	 * @return
	 * @date May 17, 2007
	 * @auther chenbaowen
	 * @see
	 */
	public boolean isNode(String xpath)
	{
		boolean isHave = false;
		List list = null;
		if (this.document == null || this.document.equals("") || xpath == null || xpath.equals(""))
			return isHave;

		list = this.document.selectNodes(xpath);
		if (list != null && list.size() > 0)
			isHave = true;

		return isHave;
	}

	/**
	 * @param nodePath
	 * @return
	 */
	public Element getSingleNode(String nodePath)
	{
		if (this.document == null)
			return null;

		return (Element) this.document.selectSingleNode(nodePath);
	}

	/**
	 * Return all node element. every part is a Element. Example: <adapter> <input value="test"> <id value="aa" /> <qq
	 * value="bb" /> <ww value="cc" /> </input> <input value="ss"> <aa value="dd" /> <bb value="ww" /> <xx value="qq" />
	 * </input> </adapter> If inputPath is equal "adapter/input", Return " <input value="test">... </input> <input
	 * value="ss">... </input>"
	 * 
	 * @author chenbaowen. Created Time: Oct 11, 2006
	 * @param nodePath
	 * @return Returns the ArrayList .
	 * @see
	 */
	public List getNodes(String nodePath)
	{
		String xpath = pathValidate(nodePath);
		return this.document.selectNodes(xpath);
	}

	/**
	 * Return all subNode of single element. every part is a Element. Example: <adapter><input value="test"> <id
	 * value="aa" /> <qq value="bb" /> <ww value="cc" /> </input> </adapter> If inputPath is equal "adapter/input",
	 * Return " <id value="aa" /> <qq value="bb" /> <ww value="cc" />"
	 * 
	 * @author chenbaowen. Created Time: Oct 11, 2006
	 * @param nodePath
	 * @return Returns the ArrayList .
	 * @see
	 */
	public ArrayList getSubNodes(String nodePath)
	{
		ArrayList listTemp = new ArrayList();
		String xpath = pathValidate(nodePath);
		List list = this.document.selectNodes(xpath);
		Iterator iter = list.iterator();
		while (iter.hasNext())
		{
			Element element = (Element) iter.next();
			List subList = element.elements();
			Iterator subIter = subList.iterator();
			while (subIter.hasNext())
			{
				Element subElement = (Element) subIter.next();
				listTemp.add(subElement);
			}
		}
		return listTemp;
	}

	/**
	 * Return part subNode of single element. every part is a Element. Find a sub Element when attribute =
	 * attributeValue below appointed path; * Example: <adapter><input value="test"> <id value="aa" /> <qq value="bb" />
	 * <ww value="cc" /> <mm value="bb" /> </input> </adapter> If inputPath is equal "adapter/input", attributeName is
	 * equal "value",attributeValue is equal "bb", Return " <qq value="bb" /> <mm value="bb" />" *
	 * 
	 * @param inputPath
	 * @param attribute
	 * @param attributeValue
	 * @return ArrayList
	 * @auther chenbaowen. Create May 16, 2007
	 * @see
	 */
	public ArrayList getSubNodes(String inputPath, String attributeName, String attributeValue)
	{
		ArrayList stateNodeList = this.getSubNodes(inputPath);
		ArrayList temp = new ArrayList();
		Iterator iter = stateNodeList.iterator();
		while (iter.hasNext())
		{
			Element appNode = (Element) iter.next();
			String tempValue = appNode.attributeValue(attributeName);
			if (!attributeValue.equalsIgnoreCase(tempValue))
			{
				temp.add(appNode);
			}
		}

		for (int i = 0, j = temp.size(); i < j; i++)
		{
			stateNodeList.remove(temp.get(i));
		}

		return stateNodeList;
	}

	/**
	 * Return single node of single element. Find a element when attribute = attributeValue below appointed path; *
	 * Example: <adapter><input value="test"> <id value="aa" /> <qq value="bb" /> <ww value="cc" /> <mm value="bb" />
	 * </input> <input value="bb"> <id value="aa" /> <qq value="bb" /> </input> </adapter> If inputPath is equal
	 * "adapter/input", attributeName is equal "value",attributeValue is equal "bb", Return " <input value="bb">...
	 * </input>" NOT recommend Uuse if sub node is multi.
	 * 
	 * @param path
	 * @param attribute
	 * @param attributeValue
	 * @return Element
	 * @auther chenbaowen.Create May 16, 2007
	 * @see
	 */
	public Element getNodesElement(String inputPath, String attribute, String attributeValue)
	{
		List stateNodeList = this.getNodes(inputPath);
		Iterator iter = stateNodeList.iterator();
		while (iter.hasNext())
		{
			Element appNode = (Element) iter.next();
			String tempValue = appNode.attributeValue(attribute);
			if (attributeValue.equalsIgnoreCase(tempValue))
			{
				return appNode;
			}
		}
		return null;
	}

	/**
	 * Return single subNode of single element. Find a sub Element when attribute = attributeValue below appointed path; *
	 * Example: <adapter> <input value="test"> <id value="aa" /> <qq value="bb" /> <ww value="cc" /> <mm value="bb" />
	 * </input> </adapter> If inputPath is equal "adapter/input", attributeName is equal "value",attributeValue is equal
	 * "bb", Return " <qq value="bb" /> NOT recommend Uuse if sub node is multi.
	 * 
	 * @param path
	 * @param attribute
	 * @param attributeValue
	 * @return Element
	 * @auther chenbaowen.Create May 16, 2007
	 * @see
	 */
	public Element getSubNodesElement(String inputPath, String attribute, String attributeValue)
	{
		List stateNodeList = this.getSubNodes(inputPath);
		Iterator iter = stateNodeList.iterator();
		while (iter.hasNext())
		{
			Element appNode = (Element) iter.next();
			String tempValue = appNode.attributeValue(attribute);
			if (attributeValue.equalsIgnoreCase(tempValue))
			{
				return appNode;
			}
		}
		return null;
	}

	/**
	 * Return element String. Example: <adapter><input value="test"> <id value="aa" /> <qq value="bb" id="11" /> <ww
	 * value="cc" /> </input> <test value="test" id="55"> <id value="cc" id="22" /> <qq value="aa" /> <ww value="ee"
	 * id="33" /> </test> </adapter> If inputPath is equal "adapter/test",attribute is "id", attributeValue is "55".
	 * Return String:" <test value="test" id="55"> <id value="cc" id="22" /> <qq value="aa" /> <ww value="ee" id="33" />
	 * </test>"
	 * 
	 * @param path
	 * @param attribute
	 * @param attributeValue
	 * @return
	 * @date May 17, 2007
	 * @auther chenbaowen
	 * @see
	 */
	public String getNodesElementString(String path, String attribute, String attributeValue)
	{
		Document doc = null;

		Element elem = this.getNodesElement(path, attribute, attributeValue);
		doc = this.buildNewDocument(elem);
		if (doc != null)
			return doc.asXML();

		return null;
	}

	/**
	 * Return element String. Example: <adapter><input value="test"> <id value="aa" /> <qq value="bb" id="11" /> <ww
	 * value="cc" /> </input> <test value="test" id="55"> <id value="cc" id="22" /> <qq value="aa" /> <ww value="ee"
	 * id="33" /> </test> </adapter> If inputPath is equal "adapter/test",attribute is "id", attributeValue is "33".
	 * Return String: " <ww value="ee" id="33" />"
	 * 
	 * @param path
	 * @param attribute
	 * @param attributeValue
	 * @return
	 * @date May 17, 2007
	 * @auther chenbaowen
	 * @see
	 */
	public String getSubNodesElementString(String path, String attribute, String attributeValue)
	{
		Document doc = null;

		Element elem = this.getSubNodesElement(path, attribute, attributeValue);
		doc = this.buildNewDocument(elem);
		if (doc != null)
			return doc.asXML();

		return null;
	}

	/**
	 * @param inputPath
	 * @return
	 * @date May 17, 2007
	 * @auther chenbaowen
	 * @see
	 */
	public String getNodeText(String inputPath)
	{
		String value = "";
		String xpath = pathValidate(inputPath);
		if (!isNode(xpath))
			return value;

		Node node = this.document.selectSingleNode(xpath);
		value = node.getText();

		return value;
	}

	/**
	 * key="value" ignored case
	 * 
	 * @param inputPath
	 * @return String
	 * @date Jul 20, 2007
	 * @auther chen bao wen
	 * @see
	 */
	public String getNodeAttributeValue(String inputPath)
	{
		return getNodeAttributeValue(inputPath, "value");
	}

	/**
	 * key ignored case
	 * 
	 * @param
	 * @return String
	 * @author chenbaowen 2007-1-4
	 * @see
	 */
	public String getNodeAttributeValue(String inputPath, String key)
	{
		String xpath = pathValidate(inputPath);
		if (!isNode(xpath))
			return null;

		Element element = (Element) this.document.selectSingleNode(xpath);
		return XmlUtils.getAttributeValue(element);
	}

	/**
	 * @param inputPath
	 * @return
	 * @date Jun 19, 2008
	 * @auther dong
	 * @see
	 */
	public HashMap<String, String> getSubNodeMap(String inputPath)
	{
		List list = null;
		HashMap<String, String> hash = new HashMap<String, String>();
		String xpath = pathValidate(inputPath);

		list = this.document.selectNodes(xpath);
		Iterator iter = list.iterator();
		while (iter.hasNext())
		{
			Element element = (Element) iter.next();
			List subList = element.elements();

			Iterator subIter = subList.iterator();
			while (subIter.hasNext())
			{
				Element subElement = (Element) subIter.next();
				String key = subElement.getName();

				String value = subElement.getText();

				hash.put(key, value);
			}
		}

		return hash;
	}

	/**
	 * @param inputPath
	 * @return
	 * @date Jun 19, 2008
	 * @auther dong
	 * @see
	 */
	public List getSubNodeValue(String inputPath)
	{

		List list = null;
		List listTemp = new ArrayList();

		String xpath = pathValidate(inputPath);
		list = this.document.selectNodes(xpath);
		Iterator iter = list.iterator();
		while (iter.hasNext())
		{
			Element element = (Element) iter.next();
			List subList = element.elements();

			Iterator subIter = subList.iterator();
			while (subIter.hasNext())
			{
				Element subElement = (Element) subIter.next();

				String temp = subElement.getText();
				if (temp == null || temp.trim().equalsIgnoreCase(""))
					continue;
				listTemp.add(temp);

			}
		}

		return listTemp;
	}

	/**
	 * Get all leaf element. Example: <adapter><input value="test"> <id value="aa" /> <qq value="bb" /> <ww value="cc" />
	 * </input> <test value="test"> <id value="cc" /> <qq value="aa" /> <ww value="ee" /> </test> </adapter> return
	 * ArrayList: <id value="aa" /> <qq value="bb" /> <ww value="cc" /> <id value="cc" /> <qq value="aa" /> <ww
	 * value="ee" />
	 * 
	 * @author chenbaowen. Created Time: Oct 18, 2006
	 * @return ArrayList
	 * @return Returns the ArrayList .
	 * @see
	 */
	public ArrayList getAllLeafElement()
	{
		ArrayList array = new ArrayList();
		Element root = this.document.getRootElement();
		return getAllLeafElement(root, array);
	}

	/**
	 * Get all leaf element. Example: <adapter><input value="test"> <id value="aa" /> <qq value="bb" /> <ww value="cc" />
	 * </input> <test value="test"> <id value="cc" /> <qq value="aa" /> <ww value="ee" /> </test> </adapter> If
	 * inputPath is equal "adapter/test" return ArrayList: <id value="cc" /> <qq value="aa" /> <ww value="ee" />
	 * 
	 * @author chenbaowen Created Time: Oct 18, 2006
	 * @param inputPath
	 * @return
	 * @return Returns the ArrayList .
	 * @see
	 */
	public ArrayList getAllLeafElement(String inputPath)
	{
		ArrayList array = new ArrayList();
		List list = null;
		String xpath = pathValidate(inputPath);
		list = this.document.selectNodes(xpath);
		Iterator iter = list.iterator();
		while (iter.hasNext())
		{
			Element element = (Element) iter.next();
			array = getAllLeafElement(element, array);
		}
		return array;
	}

	/**
	 * @author chenbaowen Created Time: Oct 18, 2006
	 * @param root
	 * @param array
	 * @return
	 * @return Returns the ArrayList .contains every Element.
	 * @see
	 */
	private ArrayList getAllLeafElement(Element root, ArrayList array)
	{

		if (root == null)
			return array;

		List list = root.elements();
		Iterator iter = list.iterator();
		Element subElement = null;
		while (iter.hasNext())
		{
			subElement = (Element) iter.next();
			if (subElement.elements().size() == 0)
			{
				array.add(subElement);
				continue;
			}
			array = getAllLeafElement(subElement, array);
			continue;
		}
		return array;

	}

	/**
	 * Get part leaf element. Example: <adapter><input value="test"> <id value="aa" /> <qq value="bb" id="11" /> <ww
	 * value="cc" /> </input> <test value="test" id="55"> <id value="cc" id="22" /> <qq value="aa" /> <ww value="ee"
	 * id="33" /> </test> </adapter>* Return ArrayList: <qq value="bb" id="11" /> <test value="test" id="55"> <id
	 * value="cc" id="22" /> <ww value="ee" id="33" />
	 * 
	 * @author chenbaowen Created Time: Oct 19, 2006
	 * @return
	 * @return Returns the ArrayList .
	 * @see
	 */
	public ArrayList getAllIdAttributeElement()
	{
		ArrayList array = new ArrayList();
		Element root = this.document.getRootElement();
		return getAllIdAttributeElements(root, array);
	}

	/**
	 * Get part leaf element. Example: <adapter><input value="test"> <id value="aa" /> <qq value="bb" id="11" /> <ww
	 * value="cc" /> </input> <test value="test" id="55"> <id value="cc" id="22" /> <qq value="aa" /> <ww value="ee"
	 * id="33" /> </test> </adapter> If inputPath is equal "adapter/test" return ArrayList: <test value="test" id="55">
	 * <id value="cc" id="22" /> <ww value="ee" id="33" />
	 * 
	 * @author chenbaowen Created Time: Oct 19, 2006
	 * @param inputPath
	 * @return
	 * @return Returns the ArrayList .
	 * @see
	 */
	public ArrayList getAllIdAttributeElement(String inputPath)
	{
		ArrayList array = new ArrayList();
		List list = null;
		String xpath = pathValidate(inputPath);
		list = this.document.selectNodes(xpath);
		Iterator iter = list.iterator();
		while (iter.hasNext())
		{
			Element element = (Element) iter.next();
			array = getAllIdAttributeElements(element, array);
		}
		return array;
	}

	/**
	 * Get part leaf element. Example: <adapter><input value="test"> <id value="aa" /> <qq value="bb" id="11" /> <ww
	 * value="cc" /> </input> <test value="test" id="55"> <id value="cc" id="22" /> <qq value="aa" /> <ww value="ee"
	 * id="33" /> </test> </adapter> return ArrayList: <qq value="bb" id="11" /> <test value="test" id="55">
	 * 
	 * @author chenbaowen Created Time: Oct 19, 2006
	 * @return Returns the ArrayList .
	 * @see
	 */
	public ArrayList getPartIdAttributeElement()
	{
		ArrayList array = new ArrayList();
		Element root = this.document.getRootElement();
		return getPartIdAttributeElements(root, array);
	}

	/**
	 * Get part leaf element. Example: <adapter><input value="test"> <id value="aa" /> <qq value="bb" id="11" /> <ww
	 * value="cc" /> </input> <test value="test" id="55"> <id value="cc" id="22" /> <qq value="aa" /> <ww value="ee"
	 * id="33" /> </test> </adapter> If inputPath is equal "adapter/test" return ArrayList: <test value="test" id="55">
	 * 
	 * @author chenbaowen Created Time: Oct 19, 2006
	 * @param inputPath
	 * @return Returns the ArrayList .
	 * @see
	 */
	public ArrayList getPartIdAttributeElement(String inputPath)
	{
		ArrayList array = new ArrayList();
		List list = null;
		String xpath = pathValidate(inputPath);
		list = this.document.selectNodes(xpath);
		Iterator iter = list.iterator();
		while (iter.hasNext())
		{
			Element element = (Element) iter.next();
			array = getPartIdAttributeElements(element, array);
		}
		return array;
	}

	/**
	 * @author chenbaowen Created Time: Oct 11, 2006
	 * @param element
	 * @return Returns the HashMap .
	 * @see Convert element to hashMap. element name is value of key "_elementName"
	 */
	public HashMap<String, String> parseAttributeToMap(Element element)
	{
		HashMap<String, String> hash = new HashMap<String, String>();
		String name = element.getName();
		hash.put("_elementName", name);
		List list = element.attributes();
		Iterator iter = list.iterator();
		while (iter.hasNext())
		{
			Attribute attr = (Attribute) iter.next();
			String key = attr.getName();
			String value = attr.getValue();
			hash.put(key, value);

		}
		return hash;
	}

	/**
	 * @author chenbaowen Created Time: Oct 11, 2006
	 * @param element
	 * @return Returns the HashMap .
	 * @see Convert element to hashMap. element name is value of key "_elementName"
	 */
	public void parseElementToMap(Map<String, Object> map, Element element)
	{
		if (element == null)
			return;

		if (map == null)
			map = new HashMap<String, Object>();

		String name = element.getName();
		if (element.elements().size() == 0)
		{
			map.put(name, XmlUtils.getAttributeValue(element));
		}
		else
		{
			Map<String, Object> subMap = new HashMap<String, Object>();
			map.put(name, subMap);
			List list = element.elements();
			for (int i = 0; i < list.size(); i++)
			{
				Element subElement = (Element) list.get(i);
				parseElementToMap(subMap, subElement);
			}
		}
	}

	/**
	 * @author chenbaowen Created Time: Oct 11, 2006
	 * @param element
	 * @return Returns the HashMap .
	 * @see Convert element to hashMap. element name is value of key "_elementName"
	 */
	public void parseElementToMap(Map<String, Object> map, Element element, String keyAttribute, String valueAttribute)
	{
		if (element == null)
			return;

		if (map == null)
			map = new HashMap<String, Object>();

		String name = element.attributeValue(keyAttribute);
		if (name == null || name == "")
			name = element.getName();

		if (element.elements().size() == 0)
		{
			String value = element.attributeValue(valueAttribute);
			if (value == null || value == "")
				value = XmlUtils.getAttributeValue(element);
			map.put(name, value);
		}
		else
		{
			Map<String, Object> subMap = new HashMap<String, Object>();
			map.put(name, subMap);
			List list = element.elements();
			for (int i = 0; i < list.size(); i++)
			{
				Element subElement = (Element) list.get(i);
				parseElementToMap(subMap, subElement, keyAttribute, valueAttribute);
			}
		}
	}

	/**
	 * @param doc
	 * @param xpath
	 * @param node
	 * @return
	 */
	public void deleteNode(String inputPath)
	{
		String xpath = pathValidate(inputPath);
		if (!isNode(xpath))
			return;

		int index = ((xpath.lastIndexOf("/") != -1) ? xpath.lastIndexOf("/") : 0);
		String path = xpath.substring(0, index);
		String delNode = xpath.substring(index + 1);

		List list = this.document.selectNodes(path);
		Iterator iter = list.iterator();
		while (iter.hasNext())
		{
			Element topElement = (Element) iter.next();
			Iterator iterator = topElement.elementIterator();
			while (iterator.hasNext())
			{
				Element titleElement = (Element) iterator.next();
				if (titleElement.getName().equals(delNode))
				{
					topElement.remove(titleElement);
				}
			}
		}
	}

	/**
	 * @param doc
	 * @param xpath
	 * @param node
	 * @return
	 */
	public void deleteNodeAttribute(String inputPath, String key)
	{
		String xpath = pathValidate(inputPath);
		if (!isNode(xpath))
			return;

		Element element = (Element) this.document.selectSingleNode(xpath);
		element.remove(element.attribute(key));
	}

	/**
	 * @param xpath
	 * @return
	 */
	private String pathValidate(String inputPath)
	{
		if (inputPath == null || inputPath.equals(""))
			return "";
		if (inputPath.endsWith("/")) // move last "/"
			inputPath = inputPath.substring(0, inputPath.length() - 1);

		return inputPath;
	}

	/**
	 * @author chenbaowen Created Time: Oct 18, 2006
	 * @param element
	 * @return Returns the Document .
	 * @see
	 */
	public Document buildNewDocument(Element element)
	{
		Document subDoc = null;
		if (element == null)
			return subDoc;
		subDoc = DocumentHelper.createDocument();
		Element subElement = (Element) element.clone();
		subDoc.add(subElement);

		return subDoc;
	}

	/**
	 * @param daemonConfig
	 * @param daemon
	 * @date May 16, 2007
	 * @auther chenbaowen
	 * @see
	 */
	public void updateElementAttribute(Element daemonConfig, Element daemon)
	{
		List listAttribute = daemon.attributes();
		daemonConfig.setAttributes(listAttribute);
	}

	/**
	 * @param
	 * @return String
	 * @author chenbaowen Dec 12, 2006
	 * @throws IOException
	 * @see
	 */
	public String formatXmlStr(Document doc) throws IOException
	{
		StringWriter writer = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writeXml;
		writeXml = new XMLWriter(writer, format);
		writeXml.write(doc);
		return writer.toString().replaceAll("\n", "\r\n");
	}

	/**
	 * save document to file
	 * 
	 * @param file
	 * @throws IOException
	 */
	public void saveAsFile(String path) throws IOException
	{
		FileWriter out = null;
		try
		{
			out = new FileWriter(new File(path));
			this.document.write(out);
		}
		catch (IOException e)
		{
			throw e;
		}
		finally
		{
			if (out != null)
				out.close();
		}
	}

	/**
	 * @return
	 */
	public Document getDocument()
	{
		return document;
	}

	/**
	 * @param document
	 */
	public void setDocument(Document document)
	{
		this.document = document;
	}

	/**
	 * return xml as text
	 */
	public String toString()
	{
		if (this.document == null)
			return null;
		return this.document.asXML();
	}

	/**
	 * @param
	 * @return void
	 * @author chenbaowen Dec 11, 2006
	 * @throws IOException
	 * @throws DocumentException
	 * @see
	 */
	public static void writeFormatXml(String xml) throws IOException, DocumentException
	{
		XmlUtils processor = new XmlUtils();
		processor.createDocument(xml);
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writeXml;
		writeXml = new XMLWriter(format);
		writeXml.write(processor.getDocument());
	}

	/**
	 * @param
	 * @return String
	 * @author chenbaowen Dec 12, 2006
	 * @throws IOException
	 * @throws DocumentException
	 * @see
	 */
	public static String formatXmlPretty(String xml) throws IOException, DocumentException
	{
		XmlUtils processor = new XmlUtils();
		processor.createDocument(xml);
		if (processor.getDocument() == null)
			return null;

		StringWriter writer = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		xmlWriter.write(processor.getDocument());

		return writer.toString().replaceAll("\n", "\r\n");
	}

	/**
	 * format xml compact
	 * 
	 * @param xml
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static String formatXmlCompact(String xml) throws IOException, DocumentException
	{
		XmlUtils processor = new XmlUtils();
		processor.createDocument(xml);
		if (processor.getDocument() == null)
			return null;

		StringWriter writer = new StringWriter();
		OutputFormat format = OutputFormat.createCompactFormat();
		XMLWriter xmlWriter = new XMLWriter(writer, format);
		xmlWriter.write(processor.getDocument());

		return writer.toString();
	}

	/**
	 * parser element content into a map
	 * 
	 * @param el
	 * @return
	 */
	public static Map<String, Object> elementToMap(Element element)
	{
		Map<String, Object> map = new HashMap<String, Object>();

		if (element == null)
			return map;

		String name = element.getName();
		if (element.elements().size() == 0)
		{
			map.put(name, getAttributeValue(element));
		}
		else
		{
			Map<String, Object> subMap = new HashMap<String, Object>();
			map.put(name, subMap);
			List list = element.elements();
			for (int i = 0; i < list.size(); i++)
			{
				Element subElement = (Element) list.get(i);
				subMap.putAll(elementToMap(subElement));
			}
		}

		return map;
	}

	/**
	 * @param request
	 * @return Map
	 * @author chenbaowen 2007-2-6
	 * @throws DocumentException
	 * @see
	 */
	public static Properties leafElementToMap(String xml) throws DocumentException
	{
		Properties prop = new Properties();
		XmlUtils xmlutil = new XmlUtils();
		xmlutil.createDocument(xml);
		List list = xmlutil.getAllLeafElement();
		Iterator iter = list.iterator();
		while (iter.hasNext())
		{
			Element temp = (Element) iter.next();
			prop.put(temp.getName(), getAttributeValue(temp));
		}

		return prop;
	}

	/**
	 * unescape xml
	 * 
	 * @param in
	 * @return
	 */
	public static String unescapeXml(String in)
	{
		if (StringUtils.isEmpty(in))
			return in;
		return StringUtils.replaceEach(in, XMLS, HUMAN);
	}

	/**
	 * escape xml
	 * 
	 * @param in
	 * @return
	 * @date Jun 19, 2008
	 * @auther dong
	 * @see
	 */
	public static String escapeXml(String in)
	{
		if (StringUtils.isEmpty(in))
			return in;
		return StringUtils.replaceEach(in, HUMAN, XMLS);
	}

	/**
	 * @param doc
	 * @return
	 * @date Oct 9, 2007
	 * @auther chen bao wen
	 * @see
	 */
	public static ArrayList getAllIdAttributeElement(Document doc)
	{
		ArrayList array = new ArrayList();
		Element root = doc.getRootElement();
		return getAllIdAttributeElements(root, array);
	}

	/**
	 * @author chenbaowen Created Time: Oct 19, 2006
	 * @param root
	 * @param array
	 * @return Returns the ArrayList .
	 * @see
	 */
	public static ArrayList getAllIdAttributeElements(Element root, ArrayList array)
	{
		if (root == null)
			return array;

		List list = root.elements();
		Iterator iter = list.iterator();
		Element subElement = null;
		while (iter.hasNext())
		{
			subElement = (Element) iter.next();
			if (subElement.attributeValue("id") != null)
			{
				array.add(subElement);
			}
			array = getAllIdAttributeElements(subElement, array);
		}

		return array;
	}

	/**
	 * @author chenbaowen Created Time: Oct 19, 2006
	 * @param root
	 * @param array
	 * @return
	 * @return Returns the ArrayList .
	 * @see
	 */
	public static ArrayList getPartIdAttributeElements(Element root, ArrayList array)
	{
		if (root == null)
			return array;

		List list = root.elements();
		Iterator iter = list.iterator();
		Element subElement = null;
		while (iter.hasNext())
		{
			subElement = (Element) iter.next();
			if (subElement.attributeValue("id") != null)
			{
				array.add(subElement);
				continue;
			}
			array = getAllIdAttributeElements(subElement, array);
		}
		return array;
	}

	/**
	 * @param doc
	 * @return
	 * @date Oct 9, 2007
	 * @auther chen bao wen
	 * @see
	 */
	public static ArrayList getPartIdAttributeElement(Document doc)
	{
		ArrayList array = new ArrayList();
		Element root = doc.getRootElement();
		return getPartIdAttributeElements(root, array);
	}

	/**
	 * get sub nodes. node format is 'node1/node2/node3', return sub nodes of 'node3'
	 * 
	 * @param el
	 * @param nodePath
	 * @return
	 */
	public static List getSubElements(Element el, String nodePath)
	{
		if (el == null || nodePath == null || nodePath.trim().length() == 0)
			return null;

		return el.selectNodes(nodePath);
	}

	/**
	 * @param el
	 * @param nodePath
	 * @return
	 */
	public static List getSubElementsForSingleSubElement(Element el, String nodePath)
	{
		Element subEl = getSingleSubElement(el, nodePath);
		if (subEl != null)
			return subEl.elements();
		return null;
	}

	/**
	 * Add node. Example: <adapter><input value="test"> <id value="aa" /> <qq value="bb" /> <ww value="cc" /> </input>
	 * </adapter> If inputPath is equal "adapter/input/test". Return xml: <adapter><input value="test"> <id value="aa" />
	 * <qq value="bb" /> <ww value="cc" /><test/></input> </adapter> If node is have,Not execute any operation.
	 * 
	 * @param el
	 * @param nodePath
	 * @see
	 */
	public static Element addSubElement(Element el, String nodePath)
	{
		if (el == null || nodePath == null || nodePath.trim().length() == 0)
			return null;

		String[] splits = nodePath.split("/");
		Element parentEl = el;
		Element subEl = null;
		for (int i = 0; i < splits.length; i++)
		{
			subEl = parentEl.element(splits[i]);
			if (subEl == null)
			{
				subEl = parentEl.addElement(splits[i]);
				parentEl = subEl;
			}
		}

		return subEl;
	}

	/**
	 * @param doc
	 * @param xpath
	 * @param node
	 * @return
	 */
	public static void removeSubElements(Element el, String nodePath)
	{
		List list = XmlUtils.getSubElements(el, nodePath);
		if (list == null)
			return;

		for (int i = 0; i < list.size(); i++)
		{
			Element subEl = (Element) list.get(i);
			subEl.getParent().remove(subEl);
		}
	}

	/**
	 * get single sub node. node format is 'node1/node2/node3', return 'node3'
	 * 
	 * @param el
	 * @param nodePath
	 * @return
	 */
	public static Element getSingleSubElement(Element el, String nodePath)
	{
		if (el == null || nodePath == null || nodePath.trim().length() == 0)
			return null;

		return (Element) el.selectSingleNode(nodePath);
	}

	/**
	 * @param el
	 * @param nodePath
	 * @return
	 */
	public static Element removeSingleSubElement(Element el, String nodePath)
	{
		Element subEl = XmlUtils.getSingleSubElement(el, nodePath);
		if (subEl != null)
			subEl.getParent().remove(subEl);

		return subEl;
	}

	/**
	 * default attribute key "value",ignored case.
	 * 
	 * @param element
	 * @return
	 * @date Jun 19, 2008
	 * @auther dong
	 * @see
	 */
	public static String getAttributeValue(Element el)
	{
		if (el == null)
			return "";

		String value = el.attributeValue("value");
		if (value == null)
		{
			value = el.attributeValue("VALUE");
		}
		if (value == null)
		{
			return "";
		}
		return value;
	}

	/**
	 * @param element
	 * @param attributeKey
	 * @return
	 * @date Jun 19, 2008
	 * @auther dong
	 * @see
	 */
	public static String getAttributeValue(Element element, String attributeKey)
	{
		String value = element.attributeValue(attributeKey);
		if (value == null)
			value = element.attributeValue(attributeKey.toUpperCase());

		if (value == null)
			value = element.attributeValue(attributeKey.toLowerCase());

		return value;
	}

	/**
	 * set 'value' attribute value;
	 * 
	 * @param el
	 * @param value
	 */
	public static void setAttributeValue(Element el, String value)
	{
		if (el == null)
			return;

		Attribute attr = el.attribute("value");
		if (attr == null)
			attr = el.attribute("VALUE");

		if (attr == null)
			el.addAttribute("value", value);
		else
			attr.setValue(value);
	}

	/**
	 * set attribute value;
	 * 
	 * @param el
	 * @param value
	 */
	public static void setAttributeValue(Element el, String attribute, String value)
	{
		if (el == null)
			return;

		Attribute attr = el.attribute(attribute);
		if (attr == null)
			attr = el.attribute(attribute);

		attr.setValue(value);
	}

	/**
	 * @param el
	 * @param nodePath
	 * @return
	 */
	public static Attribute removeAttribute(Element el, String attribute)
	{
		if (el == null)
			return null;

		Attribute attr = el.attribute(attribute);
		if (attr != null)
			el.remove(attr);

		return attr;
	}

	/**
	 * get sub node attribute value
	 * 
	 * @param el
	 * @param nodePath
	 * @return
	 */
	public static String getSubElementAttributeValue(Element el, String nodePath)
	{
		Element subEl = getSingleSubElement(el, nodePath);
		return getAttributeValue(subEl);
	}

	/**
	 * get sub node attribute value
	 * 
	 * @param el
	 * @param nodePath
	 * @return
	 */
	public static String getSubElementAttributeValue(Element el, String nodePath, String attribute)
	{
		Element subEl = getSingleSubElement(el, nodePath);
		return getAttributeValue(subEl, attribute);
	}

	/**
	 * @param el
	 * @param nodePath
	 * @return
	 */
	public static Attribute removeSubElementAttribute(Element el, String nodePath, String attribute)
	{
		Element subEl = getSingleSubElement(el, nodePath);
		return removeAttribute(subEl, attribute);
	}
}
