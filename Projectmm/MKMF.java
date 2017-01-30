/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mkmf;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author LAKSHMI BANSAL
 */
public class MKMF {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Map<String,ArrayList<String>> graph = new HashMap<>();
        try{
            File file = new File("abc");
            //File file = new File("trace1.txt");
            String edge;

            Scanner scanner = new Scanner(file, "Cp1252");
            int i = 1;
            while (scanner.hasNextLine()) {
                edge = scanner.nextLine();
                edge = edge.replaceAll("\u00A0", " ").trim();
                if (edge.length() > 0) {
                    String[] split1 = edge.split(" ", 2);
                    if (split1.length > 1) {
                        String vertex1 = split1[0];
                        String vertex2 = split1[2];
                        if(graph.get(vertex1)!=null && !graph.get(vertex1).contains(vertex2)){
                            graph.get(vertex1).add(vertex2);
                        }else{
                           ArrayList<String> neighbours = new ArrayList<>();
                           neighbours.add(vertex2);
                           graph.put(vertex1, neighbours);
                        }
                        if(graph.get(vertex2)!=null && !graph.get(vertex2).contains(vertex1)){
                            graph.get(vertex2).add(vertex1);
                        }else{
                           ArrayList<String> neighbours = new ArrayList<>();
                           neighbours.add(vertex1);
                           graph.put(vertex2, neighbours);
                        }
                    }
                }
            }
        }catch(Exception e){
            
        }
    }
}
