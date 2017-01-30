package Parser;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
 


public class XMLParser3 {
	//public static  ArrayList<student> sList= new ArrayList();
	public static  	HashMap<String, String> nameVsUserid = new HashMap<String, String>();
	public static  	HashMap<String, String> userIDVsName = new HashMap<String, String>();
	public static  	HashMap<String, String> UseridVsCGPA = new HashMap<String, String>();
	public static  	HashMap<String, Integer> UseridVsContributionCount = new HashMap<String, Integer>();
	public static  	HashMap<String, Integer> CGPAVsContributionCount = new HashMap<String, Integer>();
	public static  	HashMap<String, Integer> CGPAVsNumberofStudents = new HashMap<String, Integer>();
	public static  	HashMap<String, Integer> AggregatedCGPAVsNumberofStudents = new HashMap<String, Integer>();
	public static  	HashMap<String, Integer> AggregatedCGPAVsContributionCount = new HashMap<String, Integer>();
	
	 
	  public static void main(String[] args) {

		//File fXmlFile = new File("/fedora16_home/rohithdv/MAIN_HOME/WORK_DIR/MONTHLY_PROGRESS/MAY_2014/xmlparser/Parser/users_2014.xml");
		File fXmlFile = new File("/data/DATA_DIR/BACKUP_FROM_10.192.16.21/RSYNC_BACKUP_DIR_TO_10.192.16.32/MONTHLY_PROGRESS/APRIL_2015/onlineedforums-miniproject/xmlparser/Parser/users.xml");	 

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
		
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			 

			NodeList nListUser = doc.getElementsByTagName("record");//piazza

			for (int temp = 0; temp < nListUser.getLength(); temp++) {
				 
				Node nNode = nListUser.item(temp);
				//System.out.println("l0 "+nListUser.getLength());
				//System.out.println("\nCurrent Element :" + nNode.getNodeName()+" # of stud="+nList.getLength());
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {	 
					Element eElement = (Element) nNode;		
					Node namenode = eElement.getElementsByTagName("name").item(0);
					String name = namenode.getFirstChild().getNodeValue();
					Node uidnode = eElement.getElementsByTagName("user_id").item(0);
					String uid = uidnode.getFirstChild().getNodeValue();
					nameVsUserid.put(name.toLowerCase(), uid);
					userIDVsName.put(uid, name.toLowerCase());
				}
			}

			System.out.println("printing nameVsUserid");
			Iterator<String> keySetIterator = nameVsUserid.keySet().iterator();

			while(keySetIterator.hasNext()){
			  String key = keySetIterator.next();
			  System.out.println("key: " + key + " value: " + nameVsUserid.get(key));
			}

			System.out.println("printing userIDVsName");

			keySetIterator = userIDVsName.keySet().iterator();

			while(keySetIterator.hasNext()){
			  String key = keySetIterator.next();
			  System.out.println("key: " + key + " value: " + userIDVsName.get(key));
			}
			
			/*
			
			fXmlFile = new File("/data/DATA_DIR/BACKUP_FROM_10.192.16.21/RSYNC_BACKUP_DIR_TO_10.192.16.32/MONTHLY_PROGRESS/APRIL_2015/onlineedforums-miniproject/xmlparser/Parser/studentprofile.xml");	 
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
		
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			 

			nListUser = doc.getElementsByTagName("record");//piazza

			for (int temp = 0; temp < nListUser.getLength(); temp++) {
				 
				Node nNode = nListUser.item(temp);
				//System.out.println("l0 "+nListUser.getLength());
				//System.out.println("\nCurrent Element :" + nNode.getNodeName()+" # of stud="+nList.getLength());
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {	 
					Element eElement = (Element) nNode;		
					Node namenode = eElement.getElementsByTagName("name").item(0);
					String name = namenode.getFirstChild().getNodeValue();
					String userid = (String)nameVsUserid.get(name.toLowerCase());
					if (userid != null){
						Node cgpanode = eElement.getElementsByTagName("CGPA").item(0);
						String cgpa = cgpanode.getFirstChild().getNodeValue();
						UseridVsCGPA.put(userid, cgpa);
					}		
					else {
						System.out.println("userid is null. check this ! name = "+ name);
					}
				}
			}
			

			System.out.println("printing UseridVsCGPA");
			keySetIterator = UseridVsCGPA.keySet().iterator();

			while(keySetIterator.hasNext()){
			  String key = keySetIterator.next();
			  String cgpa = UseridVsCGPA.get(key);
			  System.out.println("key: " + key + " value: " + cgpa);
		          
			  if (CGPAVsNumberofStudents.get(cgpa) == null){
				CGPAVsNumberofStudents.put(cgpa, 1);	
			  }else{
				CGPAVsNumberofStudents.put(cgpa,CGPAVsNumberofStudents.get(cgpa)+1);
			  }
			}

			fXmlFile = new File("/data/DATA_DIR/BACKUP_FROM_10.192.16.21/RSYNC_BACKUP_DIR_TO_10.192.16.32/MONTHLY_PROGRESS/APRIL_2015/onlineedforums-miniproject/xmlparser/Parser/class_content.xml");
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
		
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			 

			nListUser = doc.getElementsByTagName("change-log");//piazza

			for (int temp = 0; temp < nListUser.getLength(); temp++) {
				 
				Node nNode = nListUser.item(temp);
				//System.out.println("l0 "+nListUser.getLength());
				//System.out.println("\nCurrent Element :" + nNode.getNodeName()+" # of stud="+nList.getLength());
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {	 
					Element eElement = (Element) nNode;		
					Node uidnode = eElement.getElementsByTagName("uid").item(0);
					String uid = uidnode.getFirstChild().getNodeValue();
					Integer count = (Integer)UseridVsContributionCount.get(uid);
					if (count != null){
						UseridVsContributionCount.put(uid, count+1);
					}		
					else {
						UseridVsContributionCount.put(uid, 1);
						System.out.println("entering first entry for "+ uid);
					}
				}
			}
			

			System.out.println("printing UseridVsContributionCount");

			keySetIterator = UseridVsContributionCount.keySet().iterator();
			int count = 0;
			while(keySetIterator.hasNext()){
			  String key = keySetIterator.next();
			  if (userIDVsName.get(key)== null || ((String)(userIDVsName.get(key))).equalsIgnoreCase("raj kumar maity"))
				continue;
			  if (userIDVsName.get(key)== null || ((String)(userIDVsName.get(key))).equalsIgnoreCase("sayan biswas"))
				continue;
			  if (userIDVsName.get(key)== null || ((String)(userIDVsName.get(key))).equalsIgnoreCase("arnab sen"))
				continue;
			  if (userIDVsName.get(key)== null || ((String)(userIDVsName.get(key))).equalsIgnoreCase("priyanka bhatt"))
				continue;
			  if (userIDVsName.get(key)== null || ((String)(userIDVsName.get(key))).equalsIgnoreCase("rohith d vallam"))
				continue;
			  System.out.println("key: " + key + " value: " + UseridVsContributionCount.get(key) + "; name: " + userIDVsName.get(key) +"; CGPA: " + UseridVsCGPA.get(key));
			  count = count + UseridVsContributionCount.get(key);
			  String cgpa = UseridVsCGPA.get(key);
			  if (cgpa != null){
				Integer value = CGPAVsContributionCount.get(cgpa);
				if (value == null) {
				   CGPAVsContributionCount.put (cgpa, UseridVsContributionCount.get(key));	
				}
				else{
				   value = value + UseridVsContributionCount.get(key);
				   CGPAVsContributionCount.put (cgpa, value);	
				}
			  }
			  else {
				System.out.println("CGPA is NULL!! for user: "  + key + "i.e., name: " + userIDVsName.get(key));
			  }
			}
			System.out.println("total contributions by users: " + count);
			
*/			

			fXmlFile = new File("/data/DATA_DIR/BACKUP_FROM_10.192.16.21/RSYNC_BACKUP_DIR_TO_10.192.16.32/MONTHLY_PROGRESS/APRIL_2015/onlineedforums-miniproject/xmlparser/Parser/class_content.xml");
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
		
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
			BufferedWriter writer = null;
			try {
				//create a temporary file
				//String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
				File logFile = new File("/data/DATA_DIR/BACKUP_FROM_10.192.16.21/RSYNC_BACKUP_DIR_TO_10.192.16.32/MONTHLY_PROGRESS/APRIL_2015/onlineedforums-miniproject/xmlparser/socialnetwork.txt");
	
				// This will output the full path where the file will be written to...
				//System.out.println(logFile.getCanonicalPath());
	
				writer = new BufferedWriter(new FileWriter(logFile));
				//writer.write("Hello world!");
			 			
			
			
				nListUser = doc.getElementsByTagName("change-log");//piazza
	
				String uid_creator = null;
				for (int temp = 0; temp < nListUser.getLength(); temp++) {
					 
					Node nNode = nListUser.item(temp);
					//System.out.println("l0 "+nListUser.getLength());
					System.out.println("\nCurrent Element :" + nNode.getNodeName()+" # of stud="+nListUser.getLength());
			 
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {	 
						Element eElement = (Element) nNode;
						Node typenode = eElement.getElementsByTagName("type").item(0);
						String type  = typenode.getFirstChild().getNodeValue();
						System.out.println("type = " + type);
						if (type.equals("followup")){
							Node uidnode = eElement.getElementsByTagName("uid").item(0);
							String uid = uidnode.getFirstChild().getNodeValue();
							System.out.println(" uid = " + uid);
							
							if (userIDVsName.get(uid_creator) != null && userIDVsName.get(uid) != null )
								writer.write(userIDVsName.get(uid).replace(" ", "_") + " " + userIDVsName.get(uid_creator).replace(" ", "_") + "\n");
							
							Node tonode = eElement.getElementsByTagName("to").item(0);
							String to = tonode.getFirstChild().getNodeValue();
							System.out.println(" to = " + to);
						}
						if (!type.equals("create")){
							Node uidnode = eElement.getElementsByTagName("uid").item(0);
							uid_creator = uidnode.getFirstChild().getNodeValue();
							System.out.println(" uid = " + uid_creator);							
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					// Close the writer regardless of what happens...
					writer.close();
				} catch (Exception e) {
				}
			}
			/*
			System.out.println("printing CGPAVsContributionCount");

			keySetIterator = CGPAVsContributionCount.keySet().iterator();
			count = 0;
			while(keySetIterator.hasNext()){
			  String key = keySetIterator.next();
			  System.out.println("key: " + key + " value: " + CGPAVsContributionCount.get(key) );
			  count = count + CGPAVsContributionCount.get(key);
			}
			System.out.println("total contributions based on CGPA : " + count);

			System.out.println("printing CGPAVsNumberofStudents");

			keySetIterator = CGPAVsNumberofStudents.keySet().iterator();

			while(keySetIterator.hasNext()){
			  String key = keySetIterator.next();
			  System.out.println("key: " + key + " value: " + CGPAVsNumberofStudents.get(key) );
			}



			keySetIterator = CGPAVsContributionCount.keySet().iterator();

			count = 0;
			while(keySetIterator.hasNext()){
			  String key = keySetIterator.next();
			  if (key.equals ("6-6.5") || key.equals ("6.5-7") || key.equals ("7-7.5") || key.equals ("7.5-8")) {
				  if (AggregatedCGPAVsNumberofStudents.get("6-8") == null){
					AggregatedCGPAVsContributionCount.put ("6-8", CGPAVsContributionCount.get(key));
					AggregatedCGPAVsNumberofStudents.put ("6-8", CGPAVsNumberofStudents.get(key));
				  }
				  else{
					AggregatedCGPAVsContributionCount.put ("6-8", AggregatedCGPAVsContributionCount.get("6-8")+CGPAVsContributionCount.get(key));
					AggregatedCGPAVsNumberofStudents.put ("6-8", AggregatedCGPAVsNumberofStudents.get("6-8")+CGPAVsNumberofStudents.get(key));
				  }
			 }
			 else {
				if (AggregatedCGPAVsNumberofStudents.get("Below 6") == null){
					AggregatedCGPAVsContributionCount.put ("Below 6", CGPAVsContributionCount.get(key));
					AggregatedCGPAVsNumberofStudents.put ("Below 6", CGPAVsNumberofStudents.get(key));
				}
				else{
				        AggregatedCGPAVsContributionCount.put ("Below 6", AggregatedCGPAVsContributionCount.get("Below 6")+CGPAVsContributionCount.get(key));
					AggregatedCGPAVsNumberofStudents.put ("Below 6", AggregatedCGPAVsNumberofStudents.get("Below 6")+CGPAVsNumberofStudents.get(key));
			        }
			 }
			}


			System.out.println("printing AggregatedCGPAVsContributionCount");
		
			keySetIterator = AggregatedCGPAVsContributionCount.keySet().iterator();

			while(keySetIterator.hasNext()){
			  String key = keySetIterator.next();
			  System.out.println("key = " + key + "; value = " +  AggregatedCGPAVsContributionCount.get(key));
			}

			System.out.println("printing AggregatedCGPAVsContributionCount / AggregatedCGPAVsNumberofStudents");
			PrintWriter out = new PrintWriter("output_contributions.txt");			
			keySetIterator = AggregatedCGPAVsContributionCount.keySet().iterator();

			while(keySetIterator.hasNext()){
			  String key = keySetIterator.next();
			  System.out.println("\"" + key + "\"  " + AggregatedCGPAVsContributionCount.get(key) / AggregatedCGPAVsNumberofStudents.get(key));
			}
			out.close();
			*/


/*
			keySetIterator = userIDVsName.keySet().iterator();
			int zeroes = 0;
			int zerototen = 0;
			int eleventotwenty = 0;
			int twentyonetothirty = 0;
			int thirtyonetoforty = 0;

			while(keySetIterator.hasNext()){
			  String key = keySetIterator.next();
			  if (((String)(userIDVsName.get(key))).equalsIgnoreCase("raj kumar maity"))
				continue;
			  if (((String)(userIDVsName.get(key))).equalsIgnoreCase("sayan biswas"))
				continue;
			  if (((String)(userIDVsName.get(key))).equalsIgnoreCase("arnab sen"))
				continue;
			  if (((String)(userIDVsName.get(key))).equalsIgnoreCase("priyanka bhatt"))
				continue;
			  if (((String)(userIDVsName.get(key))).equalsIgnoreCase("rohith d vallam"))
				continue;
			  if (((String)(userIDVsName.get(key))).equalsIgnoreCase("akanksha meghlan"))
				continue;
			  if (((String)(userIDVsName.get(key))).equalsIgnoreCase("shweta jain"))
				continue;
			  if (((String)(userIDVsName.get(key))).equalsIgnoreCase("shourya roy"))
				continue;
			  if (((String)(userIDVsName.get(key))).equalsIgnoreCase("debmalya mandal"))
				continue;
			  if (((String)(userIDVsName.get(key))).equalsIgnoreCase("malay bhattacharyya"))
				continue;
			  if (((String)(userIDVsName.get(key))).equalsIgnoreCase("satyanath bhat"))
				continue;
			  if (((String)(userIDVsName.get(key))).equalsIgnoreCase("y. narahari"))
				continue;


			   System.out.println ("key = " + key + "; contribution = " + UseridVsContributionCount.get(key) + "; name = " + userIDVsName.get(key));

		          if (UseridVsContributionCount.get(key) == null)	
				zeroes++;
			  else if (UseridVsContributionCount.get(key)> 0 && UseridVsContributionCount.get(key) <= 10)
				zerototen++;
			  else if (UseridVsContributionCount.get(key)> 10 && UseridVsContributionCount.get(key) <= 20)
				eleventotwenty++;
			  else if (UseridVsContributionCount.get(key)> 20 && UseridVsContributionCount.get(key) <= 30)
				twentyonetothirty++;
			  else if (UseridVsContributionCount.get(key)> 30 && UseridVsContributionCount.get(key) <= 40)
				 thirtyonetoforty++;

			}
			
			 System.out.println("zeroes = " + zeroes);
			 System.out.println("zerototen = " + zerototen);
			 System.out.println("eleventotwenty = " + eleventotwenty);
			 System.out.println("twentyonetothirty = " + twentyonetothirty);
			 System.out.println("thirtyonetoforty = " + thirtyonetoforty);
			 
			 */
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
