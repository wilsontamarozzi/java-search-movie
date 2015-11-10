package util;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.Configuracao;
import model.Video;

public class ManipuladorXMLUtil {

	public static void gravaDadosXML(List<Video> arquivos) {

		try {
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("arquivos");
			doc.appendChild(rootElement);

			int i = 0;
			for (Video v : arquivos) {
				i++;

				Element staff = doc.createElement("video");
				rootElement.appendChild(staff);

				Attr attr = doc.createAttribute("id");
				attr.setValue(String.valueOf(i));
				staff.setAttributeNode(attr);

				Element nome = doc.createElement("nome");
				nome.appendChild(doc.createTextNode(v.getNome()));
				staff.appendChild(nome);

				Element tamanho = doc.createElement("tamanho");
				tamanho.appendChild(doc.createTextNode(String.valueOf(v.getTamanho())));
				staff.appendChild(tamanho);

				Element dataUltimaModificacao = doc.createElement("dataultimamodificacao");
				dataUltimaModificacao.appendChild(doc.createTextNode(String.valueOf(v.getDataUltimaModificacao())));
				staff.appendChild(dataUltimaModificacao);

				Element diretorioPath = doc.createElement("diretoriopath");
				diretorioPath.appendChild(doc.createTextNode(v.getDiretorioPath()));
				staff.appendChild(diretorioPath);
				
				Element path = doc.createElement("path");
				path.appendChild(doc.createTextNode(String.valueOf(v.getPath())));
				staff.appendChild(path);

				Element thumbnailPath = doc.createElement("thumbnailpath");
				thumbnailPath.appendChild(doc.createTextNode(String.valueOf(v.getThumbnailPath())));
				staff.appendChild(thumbnailPath);

			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(Configuracao.getArquivoFilelistXmlPath()));

			transformer.transform(source, result);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	public static void leDadosXML(List<Video> arquivos) {
				
		try {
			
			File fXmlFile = new File(Configuracao.getArquivoFilelistXmlPath());
			
			if(!fXmlFile.exists()) {
				fXmlFile.getParentFile().mkdirs();
				fXmlFile.createNewFile();
			} else {		
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(fXmlFile);
		
					doc.getDocumentElement().normalize();
		
					NodeList nList = doc.getElementsByTagName("video");
		
					for (int temp = 0; temp < nList.getLength(); temp++) {
		
						Node nNode = nList.item(temp);
		
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		
							Element eElement = (Element) nNode;
		
							Video arquivo = new Video();
							
							arquivo.setVideoId(Integer.parseInt(eElement.getAttribute("id")));
							arquivo.setNome(eElement.getElementsByTagName("nome").item(0).getTextContent());
							arquivo.setTamanho(Double.parseDouble(eElement.getElementsByTagName("tamanho").item(0).getTextContent()));
							arquivo.setDiretorioPath(eElement.getElementsByTagName("diretoriopath").item(0).getTextContent());
							arquivo.setPath(eElement.getElementsByTagName("path").item(0).getTextContent());
							arquivo.setThumbnailPath(eElement.getElementsByTagName("thumbnailpath").item(0).getTextContent());
							
							arquivos.add(arquivo);
						}
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
