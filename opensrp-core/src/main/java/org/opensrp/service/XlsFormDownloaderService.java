package org.opensrp.service;



import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.io.ObjectInputStream.GetField;

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
			String formDef = new XlsFormDownloaderService().getFormDefinition("");
			System.out.print(formDef);

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

            OutputFormat format = new OutputFormat(document);
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
	
	public String getFormDefinition(String json) throws JsonProcessingException, IOException{
		json = "{\"name\":\"penutupan_anak_mapped\",\"title\":\"Penutupan Anak\",\"sms_keyword\":\"penutupan_anak_1\",\"default_language\":\"Bahasa\",\"instance\":{\"encounter_type\":\"Penutupan Anak\"},\"version\":\"201806070806\",\"id_string\":\"penutupan_anak_1\",\"type\":\"survey\",\"children\":[{\"instance\":{\"openmrs_entity_id\":\"encounter_start\",\"openmrs_entity\":\"encounter\"},\"type\":\"start\",\"name\":\"start\"},{\"instance\":{\"openmrs_entity_id\":\"encounter_date\",\"openmrs_entity\":\"encounter\"},\"type\":\"today\",\"name\":\"today\"},{\"type\":\"deviceid\",\"name\":\"deviceid\"},{\"type\":\"simserial\",\"name\":\"simserial\"},{\"type\":\"phonenumber\",\"name\":\"phonenumber\"},{\"name\":\"close_reason\",\"hint\":{\"Bahasa\":\"Pilihlah salah satu alasan yang tersedia\",\"English\":\"Select one of reasons available\"},\"bind\":{\"required\":\"yes\"},\"label\":{\"Bahasa\":\"Alasan penutupan\",\"English\":\"Reason for closure?\"},\"instance\":{\"openmrs_entity_id\":\"160417AAAAAAAAAAAAAAAAAAAAAAAAAA\",\"openmrs_entity\":\"Concept\"},\"type\":\"select one\",\"children\":[{\"instance\":{\"openmrs_code\":\"163496AAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"child_over5\",\"label\":{\"Bahasa\":\"Umur anak >5 tahun\",\"English\":\"Child's age > 5 years\"}},{\"instance\":{\"openmrs_code\":\"160415AAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"permanent_relocation\",\"label\":{\"Bahasa\":\"Relokasi (permanen)\",\"English\":\"Relocation (permanent)\"}},{\"instance\":{\"openmrs_code\":\"162076AAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"death_of_child\",\"label\":{\"Bahasa\":\"Anak meninggal\",\"English\":\"Child's death\"}},{\"name\":\"unused\",\"label\":{\"Bahasa\":\"Data tidak digunakan\",\"English\":\"Unused data\"}},{\"name\":\"others\",\"label\":{\"Bahasa\":\"Lainnya\",\"English\":\"Other\"}}]},{\"bind\":{\"relevant\":\"${close_reason}='other'\"},\"label\":{\"Bahasa\":\"Lainnya\",\"English\":\"Other\"},\"type\":\"text\",\"name\":\"close_reason_other\",\"hint\":{\"Bahasa\":\"Tuliskan alasan mengapa form ditutup\",\"English\":\"Write the reason why the form closed\"}},{\"control\":{\"appearance\":\"minimal\"},\"name\":\"child_death_cause\",\"bind\":{\"relevant\":\"${close_reason} = 'death_of_child'\"},\"label\":{\"Bahasa\":\"Penyebab kematian anak\",\"English\":\"Cause of child's death\"},\"instance\":{\"openmrs_entity_id\":\"159482AAAAAAAAAAAAAAAAAAAAAAAAAA\",\"openmrs_entity\":\"Concept\"},\"type\":\"select one\",\"children\":[{\"instance\":{\"openmrs_code\":\"226AAAAAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"sepsis\",\"label\":{\"Bahasa\":\"Sepsis\",\"English\":\"Sepsis\"}},{\"instance\":{\"openmrs_code\":\"121397AAAAAAAAAA\\nAAAAAAAAAAAAAAAA\"},\"name\":\"asphyxia\",\"label\":{\"Bahasa\":\"Asfiksia\",\"English\":\"Asphyxia\"}},{\"instance\":{\"openmrs_code\":\"116222AAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"lbw\",\"label\":{\"Bahasa\":\"berat lahir kurang (< 2.5 kg)\",\"English\":\"Low birthweight (< 2.5 kg)\"}},{\"instance\":{\"openmrs_code\":\"114100AAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"pneumonia\",\"label\":{\"Bahasa\":\"Pneumonia\",\"English\":\"Pneumonia\"}},{\"instance\":{\"openmrs_code\":\"142412AAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"diarrhea\",\"label\":{\"Bahasa\":\"Diare\",\"English\":\"Diarrhea\"}},{\"instance\":{\"openmrs_code\":\"134561AAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"measles\",\"label\":{\"Bahasa\":\"Campak\",\"English\":\"Measles\"}},{\"instance\":{\"openmrs_code\":\"115122AAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"malnutrition\",\"label\":{\"Bahasa\":\"Malnutrisi\",\"English\":\"Malnutrition\"}},{\"instance\":{\"openmrs_code\":\"154983AAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"Infeksi_pernafasan_akut\",\"label\":{\"Bahasa\":\"Infeksi pernafasan akut\",\"English\":\"Acute respiratory infection\"}},{\"instance\":{\"openmrs_code\":\"123565AAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"infeksi_pernapasan_atas\",\"label\":{\"Bahasa\":\"Infeksi Saluran Pernapasan Atas\",\"English\":\"Upper Respiratory Infection\"}},{\"instance\":{\"openmrs_code\":\"116128AAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"malaria\",\"label\":{\"Bahasa\":\"Malaria\",\"English\":\"Malaria\"}},{\"instance\":{\"openmrs_code\":\"124954AAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"tetanus_neonatorum\",\"label\":{\"Bahasa\":\"Tetanus Neonatorum\",\"English\":\"Neonatal Tetanus\"}},{\"instance\":{\"openmrs_code\":\"115368AAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"ikterus\",\"label\":{\"Bahasa\":\"Ikterus\",\"English\":\"Jaundice\"}},{\"instance\":{\"openmrs_code\":\"142591AAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"demam_berdarah\",\"label\":{\"Bahasa\":\"Demam Berdarah\",\"English\":\"Dengue Fever\"}},{\"instance\":{\"openmrs_code\":\"119975AAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"congenital_abnormality\",\"label\":{\"Bahasa\":\"Kelainan Kongenital\",\"English\":\"Congenital Abnormality\"}},{\"instance\":{\"openmrs_code\":\"119242AAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"kelainan_saluran_cerna\",\"label\":{\"Bahasa\":\"Kelainan Saluran Cerna\",\"English\":\"Gastrointestinal Abnormality\"}},{\"instance\":{\"openmrs_code\":\"160176AAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"Kelainan_syaraf\",\"label\":{\"Bahasa\":\"Kelainan Syaraf\",\"English\":\"Neurological Abnormality\"}},{\"instance\":{\"openmrs_code\":\"5622AAAAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"others\",\"label\":{\"Bahasa\":\"Lainnya\",\"English\":\"Other\"}},{\"instance\":{\"openmrs_code\":\"1067AAAAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"cause_not_identified\",\"label\":{\"Bahasa\":\"Penyebab tidak teridentifikasi\",\"English\":\"Unidentified cause\"}}]},{\"name\":\"child_death_cause_other\",\"hint\":{\"Bahasa\":\"Sebutkan alasan lainnya Jika ada\",\"English\":\"Write other reason, if available\"},\"bind\":{\"relevant\":\"${child_death_cause} = 'others'\"},\"label\":{\"Bahasa\":\"Lainnya\",\"English\":\"Other\"},\"instance\":{\"openmrs_entity\":\"Concept\"},\"type\":\"text\"},{\"instance\":{\"openmrs_entity_id\":\"1543AAAAAAAAAAAAAAAAAAAAAAAAAAAA\",\"openmrs_entity\":\"Concept\"},\"bind\":{\"required\":\"yes\",\"jr:constraintMsg\":{\"Bahasa\":\"Tanggal kematian anak hari ini atau hari-hari sebelumnya\",\"English\":\"Date of child's death today or the days before\"},\"relevant\":\"${close_reason} = 'death_of_child'\",\"constraint\":\".<=${today}\"},\"type\":\"date\",\"name\":\"child_death_date\",\"label\":{\"Bahasa\":\"Tanggal kematian anak\",\"English\":\"Date of child death\"}},{\"name\":\"place_of_death\",\"hint\":{\"Bahasa\":\"Tempat anak dikatakan meninggal\",\"English\":\"The Place where children died\"},\"bind\":{\"relevant\":\"${close_reason} = 'death_of_child'\"},\"label\":{\"Bahasa\":\"Tempat Meninggal\",\"English\":\"Location Died\"},\"instance\":{\"openmrs_entity_id\":\"160632AAAAAAAAAAAAAAAAAAAAAAAAAA\",\"openmrs_entity\":\"Concept\"},\"type\":\"text\"},{\"name\":\"referred\",\"hint\":{\"Bahasa\":\"Untuk mengetahui apakah bayi/anak tersebut dirujuk ke fasilitas lain\",\"English\":\"Information whether the baby/child was referred to other facility or not\"},\"bind\":{\"relevant\":\"${close_reason} = 'death_of_child'\"},\"label\":{\"Bahasa\":\"Apakah bayi/anak ini dirujuk?\",\"English\":\"Was this baby/child referred to other facility?\"},\"instance\":{\"openmrs_entity_id\":\"1648AAAAAAAAAAAAAAAAAAAAAAAAAAAA\",\"openmrs_entity\":\"Concept\"},\"type\":\"select one\",\"children\":[{\"instance\":{\"openmrs_code\":\"1065AAAAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"yes\",\"label\":{\"Bahasa\":\"Ya\",\"English\":\"Yes\"}},{\"instance\":{\"openmrs_code\":\"1066AAAAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"no\",\"label\":{\"Bahasa\":\"Tidak\",\"English\":\"No\"}}]},{\"name\":\"prereferral_management\",\"hint\":{\"Bahasa\":\"Tuliskan tindakan perawatan yang diberikan kepada ibu sebelum dirujuk\",\"English\":\"Describe the care management provided for the mother prior to referral\"},\"bind\":{\"relevant\":\"${referred} = 'yes'\"},\"label\":{\"Bahasa\":\"Tindakan sebelum dirujuk\",\"English\":\"Pre-referral management\"},\"instance\":{\"openmrs_entity\":\"Concept\"},\"type\":\"text\"},{\"name\":\"referral_location\",\"hint\":{\"Bahasa\":\"Untuk mengetahui bayi/anak dirujuk ke fasilitas mana\",\"English\":\"Information related to the location of baby/child's referral\"},\"bind\":{\"relevant\":\"${referred} = 'yes'\"},\"label\":{\"Bahasa\":\"Bayi/anak dirujuk ke\",\"English\":\"Location of referral\"},\"instance\":{\"openmrs_entity_id\":\"161562AAAAAAAAAA\\nAAAAAAAAAAAAAAAA\",\"openmrs_entity\":\"Concept\"},\"type\":\"text\"},{\"name\":\"confirm_child_close\",\"bind\":{\"required\":\"yes\"},\"label\":{\"Bahasa\":\"Konfirmasi penutupan anak\",\"English\":\"Confirmation of child's closure\"},\"instance\":{\"openmrs_entity\":\"n/a\"},\"type\":\"select one\",\"children\":[{\"instance\":{\"openmrs_code\":\"1065AAAAAAAAAAAAAAAAAAAAAAAAAAAA\"},\"name\":\"yes\",\"label\":{\"Bahasa\":\"Ya\",\"English\":\"Yes\"}}]},{\"instance\":{\"openmrs_entity_id\":\"encounter_end\",\"openmrs_entity\":\"encounter\"},\"type\":\"end\",\"name\":\"end\"},{\"control\":{\"bodyless\":true},\"type\":\"group\",\"children\":[{\"bind\":{\"readonly\":\"true()\",\"calculate\":\"concat('uuid:', uuid())\"},\"type\":\"calculate\",\"name\":\"instanceID\"}],\"name\":\"meta\"}]}";
		return jsonParser.getFormDefinition(json.getBytes());
		
	}	
}
