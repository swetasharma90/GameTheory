package Parser;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;



public class XMLParser {
	public static  ArrayList<student> sList= new ArrayList();
	 
	  public static void main(String[] args) {
	 
	    try {
	 
		File fXmlFile = new File("C:/Users/Arnab/Desktop/user1.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		File fXmlFile1 = new File("C:/Users/Arnab/Desktop/studentprofile.xml");
		DocumentBuilderFactory dbFactory1 = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder1 = dbFactory.newDocumentBuilder();
		Document doc1 = dBuilder.parse(fXmlFile1);
	 
		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize();
		doc1.getDocumentElement().normalize();
		//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
	 
		NodeList nListUser = doc.getElementsByTagName("record");//piazza
		NodeList nListProf= doc1.getElementsByTagName("record");//excel
	 
		
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
		for (int temp = 0; temp < nListUser.getLength(); temp++) {
			 
			Node nNode = nListUser.item(temp);
			//System.out.println("l0 "+nListUser.getLength());
			//System.out.println("\nCurrent Element :" + nNode.getNodeName()+" # of stud="+nList.getLength());
	 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	 
				Element eElement = (Element) nNode;
	 
				//System.out.println("Staff id : " + eElement.getAttribute("id"));
				/*System.out.println("asks : " + eElement.getElementsByTagName("asks").item(0).getTextContent());
				System.out.println("days : " + eElement.getElementsByTagName("days").item(0).getTextContent());
				System.out.println("Name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
				System.out.println("mail : " + eElement.getElementsByTagName("email").item(0).getTextContent());*/
				
				String name=eElement.getElementsByTagName("name").item(0).getTextContent();
				String uid=eElement.getElementsByTagName("user_id").item(0).getTextContent();
				String email=eElement.getElementsByTagName("email").item(0).getTextContent();
				String gender="";
				String sr="";
				String deptmail="";
				String degree="";
				String dept="";
				String state="";
				String cgpa="";
				int psne=1;
				int msne=1;
				int minmax=1;
				int saddlept=1;
				int asks=Integer.parseInt(eElement.getElementsByTagName("asks").item(0).getTextContent());
				int days=Integer.parseInt(eElement.getElementsByTagName("days").item(0).getTextContent());
				int answers=Integer.parseInt(eElement.getElementsByTagName("answers").item(0).getTextContent());
				int posts=Integer.parseInt(eElement.getElementsByTagName("posts").item(0).getTextContent());
				int views=Integer.parseInt(eElement.getElementsByTagName("views").item(0).getTextContent());
				
				/*System.out.println("asks : " + eElement.getElementsByTagName("user_id").item(0).getTextContent());
				System.out.println("days : " + eElement.getElementsByTagName("days").item(0).getTextContent());
				System.out.println("Name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
				System.out.println("mail : " + eElement.getElementsByTagName("email").item(0).getTextContent());*/
				//System.out.println(nNode.toString());
				boolean flag=false;
			//	NodeList nListProf= doc1.getElementsByTagName("record");
				for (int i=0;i<nListProf.getLength();i++)
				{
					//System.out.println("l "+nListProf.getLength());
					Node pNode=nListProf.item(i);
					
					//if (pNode.getNodeType() == Node.ELEMENT_NODE)
					//{
						Element eElement1 = (Element) pNode;
						//System.out.println("hi "+eElement1.getElementsByTagName("email").item(0).getTextContent());
						if(name.equals(eElement1.getElementsByTagName("name").item(0).getTextContent()))
						{
							//System.out.println("hi "+eElement1.getElementsByTagName("SR").item(0).getTextContent());
							
						  gender=eElement1.getElementsByTagName("gender").item(0).getTextContent();
						   sr=eElement1.getElementsByTagName("SR").item(0).getTextContent();
						   deptmail=eElement1.getElementsByTagName("deptmail").item(0).getTextContent();
						    degree=eElement1.getElementsByTagName("degree").item(0).getTextContent();
							dept=eElement1.getElementsByTagName("department").item(0).getTextContent();
							state=eElement1.getElementsByTagName("state").item(0).getTextContent();
							cgpa=eElement1.getElementsByTagName("CGPA").item(0).getTextContent();
							psne=Integer.parseInt(eElement1.getElementsByTagName("psne").item(0).getTextContent());
							msne=Integer.parseInt(eElement1.getElementsByTagName("msne").item(0).getTextContent());
							minmax=Integer.parseInt(eElement1.getElementsByTagName("minmax").item(0).getTextContent());
							saddlept=Integer.parseInt(eElement1.getElementsByTagName("saddlept").item(0).getTextContent());
							
							flag=true;
							break;
							
							
						}	
						
					//}
					
					
					
					
				}
				
				student st=new student( name,
						   gender,
						   sr,
						   deptmail,
						   email,
						   uid,
						   degree,
						   dept,
						   state,
						   cgpa,
						   psne,
						   msne,
						   minmax,
						   saddlept,
						   asks,
						   days,
						   answers,
						   posts);
				
				
				sList.add(st);
				//System.out.println(st.toString());
	 
			}
		}
		
			
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	    System.out.println(sList.get(4).uid);
	  }
	 
	}