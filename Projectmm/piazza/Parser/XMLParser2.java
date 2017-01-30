package Parser;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;



public class XMLParser2 {
	public static  ArrayList<student> sList= new ArrayList();
	 
	  public static void main(String[] args) {
	 
	    try {
		//System.out.println(args.length);
		if (args.length != 2){
			System.out.println("pass questions/answers as arg 1, xmlfilename as arg 2");
			System.exit(0);
		}
	 
		//File fXmlFile = new File("/home/rohithdv/FEDORA_16_HOME/MAIN_HOME/WORK_DIR/MONTHLY_PROGRESS/MAY_2014/xmlparser/Parser/user1.xml");
		//File fXmlFile = new File("/home/rohithdv/FEDORA_16_HOME/MAIN_HOME/WORK_DIR/MONTHLY_PROGRESS/MAY_2014/xmlparser/Parser/class_content.xml");
		//File fXmlFile = new File("/home/rohithdv/FEDORA_16_HOME/MAIN_HOME/WORK_DIR/MONTHLY_PROGRESS/MAY_2014/xmlparser/Parser/class_content_2014_latest.xml");
		File fXmlFile = new File(args[1]);
		//File fXmlFile = new File("/home/rohithdv/FEDORA_16_HOME/MAIN_HOME/WORK_DIR/MONTHLY_PROGRESS/MAY_2014/xmlparser/test.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();
		//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	 
		NodeList nListUser = doc.getElementsByTagName("content");//piazza
	 
		
		/*System.out.println("----------------------------");
	 
		for (int temp = 0; temp < nList.getLength(); temp++) {
	 
			Node nNode = nList.item(temp);
	 
			System.out.println("\nCurrent Element :" + nNode.getNodeName()+" # of stud="+nList.getLength());
	 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	 
				Element eElement = (Element) nNode;
	 
				//System.out.println("Staff id : " + eElement.getAttribute("id"));
				System.out.println("asks : " + eElement.getElementsByTagName("asks").item(0).getTextContent());
				System.out.println("days : " + eElement.getElementsByTagName("days").item(0).getTextContent());
				System.out.println("Name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
				System.out.println("mail : " + eElement.getElementsByTagName("email").item(0).getTextContent());
	 
			}
		}
		*/
		System.out.println( nListUser.getLength());
		int tobeExcluded = 0;
		int questionFound = 0;
		int countAnswersAtleast1 = 0;
		int countAnswersAtleast2 = 0;
		int countAnswersAtleast3 = 0;
		for (int temp = 0; temp < nListUser.getLength(); temp++) {
			 
			questionFound = 0;
			countAnswersAtleast1 = 0;
			countAnswersAtleast2 = 0;
			countAnswersAtleast3 = 0;

			Node nNode = nListUser.item(temp);
			//System.out.println("l0 "+nListUser.getLength());
			System.out.println("\nCurrent Element :" + nNode.getNodeName()+" # of stud="+nListUser.getLength());
	 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				//System.out.println("node=" + nNode);
				//String name=eElement.getElementsByTagName("id").item(0).getTextContent();
				
				NodeList taglist =eElement.getElementsByTagName("tags");
			        for (int temp1 = 0; temp1 < taglist.getLength(); temp1++) {
				     Node nNode3 = taglist.item(temp1);
				     //Node idNode =eElement.getElementsByTagName("tag").item(0);
        		             if (nNode3 == null || nNode3.getFirstChild() == null) continue;
	               		     String name = nNode3.getFirstChild().getNodeValue();
			             if (name != null && name.equals("announcement")){
					tobeExcluded = 1;	
				     }
				}
				if (tobeExcluded == 0){
					Node typeofpost=eElement.getElementsByTagName("type1").item(0);//it means it refers to a question
					if (typeofpost == null) continue;

					//typeofpost=eElement.getElementsByTagName("no_answer_followup").item(0);//it means it refers to a question
					//if (typeofpost == null) continue;

					//typeofpost=eElement.getElementsByTagName("type").item(0);//it means it refers to a question
				//	if (typeofpost == null) continue;
				
					//String name = typeofpost.getFirstChild().getNodeValue();
					//System.out.println(name);
					String name;
					//System.out.println(name);
					//if (name != null && name.equals ("question")){
					//if (name != null){
					NodeList listofchanges = eElement.getElementsByTagName("change-log");
					if (listofchanges != null) {
						//System.out.println("before start");
						//System.out.println(listofchanges.getLength());
						int length1=listofchanges.getLength(); 		
					        for (int temp2 = 0; temp2 < length1; temp2++) {
							//System.out.println("in for" + "length" + length1);
			     				Node nNode1 = listofchanges.item(temp2);
							Element eElement1 = (Element) nNode1;

							Node sanswer = eElement1.getElementsByTagName("type").item(0);
							if (sanswer == null) {
								//System.out.println("test:"+ listofchanges.getLength());
								System.out.println("in continue11");
								continue;
							}
							name = sanswer.getFirstChild().getNodeValue();

							if (args[0].equals("questions")){
								if (name.equals("create")){
									Node nNode2 =eElement1.getElementsByTagName("when").item(0);
							                if (nNode2 == null) continue;
		       					                name = nNode2.getFirstChild().getNodeValue();
									System.out.println(name);
									break;
								}
							}	
							if (args[0].equals("questionswithatleastoneanswer")){
								if (name.equals("create")){
									questionFound = 1;
									continue;
								}
								if (questionFound == 1 && name.equals("s_answer") || name.equals("s_answer_update") ||name.equals("followup")){
									Node nNode2 =eElement1.getElementsByTagName("when").item(0);
							                if (nNode2 == null) continue;
		       					                name = nNode2.getFirstChild().getNodeValue();
									System.out.println(name);
									break;
								}
							}
							if (args[0].equals("questionswithatleasttwoanswers")){
								if (name.equals("create")){
									questionFound = 1;
									continue;
								}
								if (questionFound == 1 && name.equals("s_answer") || name.equals("s_answer_update") ||name.equals("followup")){
									if (countAnswersAtleast2 < 1){
									    countAnswersAtleast2++;
									    continue;
									}

									Node nNode2 =eElement1.getElementsByTagName("when").item(0);
							                if (nNode2 == null) continue;
		       					                name = nNode2.getFirstChild().getNodeValue();
									System.out.println(name);
									break;
								}
							}
							if (args[0].equals("questionswithatleastthreeanswers")){
								if (name.equals("create")){
									questionFound = 1;
									continue;
								}
								if (questionFound == 1 && name.equals("s_answer") || name.equals("s_answer_update") ||name.equals("followup")){
									if (countAnswersAtleast3 < 2){
									    countAnswersAtleast3++;
									    continue;
									}

									Node nNode2 =eElement1.getElementsByTagName("when").item(0);
							                if (nNode2 == null) continue;
		       					                name = nNode2.getFirstChild().getNodeValue();
									System.out.println(name);
									break;
								}
							}
						}	
					}
				}
				else{
				    tobeExcluded = 0;
				}
			}		
		}
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
}
