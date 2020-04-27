package org.opensrp.service;



import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.jackson.JsonProcessingException;
import org.joda.time.DateTime;
import org.opensrp.util.FileCreator;
import org.opensrp.util.JsonParser;
import org.opensrp.util.NetClientGet;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * @author muhammad.ahmed@ihsinformatics.com
 *  Created on 17-September, 2015
 */
@Service
public class XlsFormDownloaderService {
	private NetClientGet netClientGet;
	private FileCreator fileCreator;
	private JsonParser jsonParser;
	
	private byte[] formJson=null; 
	public XlsFormDownloaderService() {
	netClientGet=new NetClientGet();
	fileCreator=new FileCreator();
	
	jsonParser=new JsonParser();
	}

	public static void main(String[] args) {
		try {

			/*System.out.println(DateTime.now().getWeekOfWeekyear());
			new XlsFormDownloaderService().downloadFormFiles("D:\\opensrpVaccinatorWkspc\\forms", 
					"maimoonak", "opensrp", JustForFun.Form, "crvs_verbal_autopsy", "156735");
			
			new XlsFormDownloaderService().downloadFormFiles("D:\\opensrpVaccinatorWkspc\\forms", 
					"maimoonak", "opensrp", JustForFun.Form, "crvs_death_notification", "156734");
			
			new XlsFormDownloaderService().downloadFormFiles("D:\\opensrpVaccinatorWkspc\\forms", 
					"maimoonak", "opensrp", JustForFun.Form, "crvs_birth_notification", "156733");
			
			new XlsFormDownloaderService().downloadFormFiles("D:\\opensrpVaccinatorWkspc\\forms", 
					"maimoonak", "opensrp", JustForFun.Form, "crvs_pregnancy_notification", "156721");
			
			new XlsFormDownloaderService().downloadFormFiles("D:\\opensrpVaccinatorWkspc\\forms", 
					"maimoonak", "opensrp", JustForFun.Form, "new_member_registration", "148264");
			
			
			new XlsFormDownloaderService().downloadFormFiles("D:\\opensrpVaccinatorWkspc\\forms", 
					"maimoonak", "opensrp", JustForFun.Form, "family_registration_form", "148263");
			*/
			/*new XlsFormDownloaderService().downloadFormFiles("D:\\opensrpVaccinatorWkspc\\forms", 
					"maimoonak", "opensrp", JustForFun.Form, "vaccine_stock_position", "151804");
*/			

			
			/*new XlsFormDownloaderService().downloadFormFiles("D:\\opensrpVaccinatorWkspc\\forms", 
					"maimoonak", "opensrp", JustForFun.Form, "child_vaccination_enrollment", "135187");
			//-------------------------			
			new XlsFormDownloaderService().downloadFormFiles("D:\\opensrpVaccinatorWkspc\\forms", 
					"maimoonak", "opensrp", JustForFun.Form, "child_vaccination_followup", "135199");
			//---------------------------
			new XlsFormDownloaderService().downloadFormFiles("D:\\opensrpVaccinatorWkspc\\forms", 
					"maimoonak", "opensrp", JustForFun.Form, "woman_tt_enrollement_form", "135200");
			//----------------------------
			new XlsFormDownloaderService().downloadFormFiles("D:\\opensrpVaccinatorWkspc\\forms", 
					"maimoonak", "opensrp", JustForFun.Form, "woman_tt_followup_form", "135203");
			
			*/
			
			
			/*new XlsFormDownloaderService().downloadFormFiles("D:\\opensrpVaccinatorWkspc\\forms", 
					"maimoonak", "opensrp", JustForFun.Form, "offsite_child_vaccination_followup", "115138");
			
			
			new XlsFormDownloaderService().downloadFormFiles("D:\\opensrpVaccinatorWkspc\\forms", 
					"maimoonak", "opensrp", JustForFun.Form, "offsite_woman_followup_form", "115135");*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String formatXML(String input)
    {
        try
        {
            final InputSource src = new InputSource(new StringReader(input));
            final Node document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().parse(src).getDocumentElement();

            final DOMImplementationRegistry registry = DOMImplementationRegistry
                    .newInstance();
            final DOMImplementationLS impl = (DOMImplementationLS) registry
                    .getDOMImplementation("LS");
            final LSSerializer writer = impl.createLSSerializer();

            writer.getDomConfig().setParameter("format-pretty-print",
                    Boolean.TRUE);
            writer.getDomConfig().setParameter("xml-declaration", false);

            return writer.writeToString(document);
        } catch (Exception e)
        {
            e.printStackTrace();
            return input;
        }
    }
	
	public String format(String unformattedXml) {
        try {
            final org.w3c.dom.Document document = parseXmlFile(unformattedXml);

            @SuppressWarnings("deprecation")
			OutputFormat format = new OutputFormat();
            format.setLineWidth(380);
            //format.setIndenting(true);
            format.setIndent(2);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);

            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private org.w3c.dom.Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
	
	public boolean downloadFormFiles(String directory,String username ,String formPath, String password,String formId, String formPk) throws IOException{
		
		String xmlData=netClientGet.convertToString("", formPath, formId);
		String modelData=netClientGet.getModel(xmlData);
		String formData=fileCreator.prettyFormat(netClientGet.getForm(xmlData));
		
		modelData=format(modelData);
		
		formData = formData.replaceAll("selected\\(", "contains(");
		formData = formData.replaceAll("<span.*lang=\"openmrs_code\".*</span>", "");
		formData = formData.replaceAll("<option value=\"openmrs_code\">openmrs_code</option>", "");
		
		formJson=netClientGet.downloadJson(username,password,  formPk);
		
		//formData=fileCreator.prettyFormat(formData);
		System.out.println(getFormDefinition());
		fileCreator.createFile("form_definition.json", fileCreator.osDirectorySet(directory)+formId, getFormDefinition().getBytes());
		return fileCreator.createFormFiles(fileCreator.osDirectorySet(directory)+formId, formId, formData.getBytes(), modelData.getBytes(), formJson);
	}
	
	public String getFormDefinition() throws JsonProcessingException, IOException{
		if(formJson==null){
			return "Data not found on server . Please retry again !";
			
		}
		return jsonParser.getFormDefinition(formJson);
		
	}	
}
