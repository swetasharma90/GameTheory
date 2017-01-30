/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modularitymaximisation;

import java.io.*;
import java.util.*;

/**
 *
 * @author PEARLORCHIDS
 */
public class SocialNetwork {

    Map<Node, ArrayList<NodeEdge>> graph = new HashMap<>();
    Set<Edge> edgeList = new HashSet<>();
    List<Node> list_n = new ArrayList<>();

    public Set<Edge> getEdgeList() {
        return edgeList;
    }

    public void readFile() {
        try {
            File file = new File("paper6.txt");
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
                        Node node1 = null;
                        String vertex2 = split1[1];
                        Node node2 = null;
                        boolean found1,found2;
                        found1=found2=false;
                        System.out.println(list_n.size());
                        for (int l = 0; l < list_n.size(); l++)
                        {
			  if(list_n.get(l).getName().equals(vertex1))
                          {
                              found1 = true;
                              node1 = list_n.get(l);
                          }
                          if(list_n.get(l).getName().equals(vertex2))
                          {
                              found2 = true;
                               node2 = list_n.get(l);
                              
                          }
                        }
                        if(!found1)
                        {
                            node1  = createNode(vertex1);
                            list_n.add(node1);
                        }
                        if(!found2)
                        {
                            node2  = createNode(vertex2);
                            list_n.add(node2);
                        }
                        Edge e1 = createEdge(node1, node2);
                        Edge e2 = createEdge(node2, node1);
                        if(!edgeList.contains(e1)||!edgeList.contains(e2))
                        {
                            edgeList.add(e1);
                            edgeList.add(e2);
                        }
//                        if(!edgeList.contains(e2))
//                        {
//                            edgeList.add(e2);
//                        }
                        NodeEdge n = createNodeEdge(e1, node2);
                        if (graph.get(node1) != null && !graph.get(node1).contains(n)) {
                            graph.get(node1).add(n);
                        } else {
                            ArrayList<NodeEdge> neighbours = new ArrayList<>();
                            neighbours.add(n);
                            graph.put(node1, neighbours);
                        }
                      //  if (graph.get(node2) != null && !graph.get(node2).contains(n)) {
                       //     graph.get(node2).add(n);
                        //} else {
                          //  ArrayList<NodeEdge> neighbours = new ArrayList<>();
                            //neighbours.add(n);
                            //graph.put(node2, neighbours);
                        //}
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        //System.out.println(graph);
//        for (Map.Entry<Node, ArrayList<NodeEdge>> lockEntry : graph.entrySet()) {
//            Node threadId = lockEntry.getKey();
//            ArrayList<NodeEdge> locksOfThread = lockEntry.getValue();
//            System.out.print(threadId.getName());
//            for (int i = 0; i < locksOfThread.size(); i++) {
//                
//                System.out.print(  ":" + locksOfThread.get(i).getN().getName());
//            }
//            System.out.println("\n");
//        }
    }

    public Map<Node, ArrayList<NodeEdge>> getGraph() {
        return graph;
    }

      
    public Node createNode(String node) {
        Node n = new Node();
        n.setExtended(false);
        n.setLabel(null);
        n.setName(node);
        n.setCommunity(-1);
        n.setIsAssigned(false);
        return n;
    }

    public Edge createEdge(Node n1, Node n2) {
        Edge e = new Edge();
        e.setN1(n1);
        e.setN2(n2);
        e.setWeight(0);
        e.setWeighted(false);
        return e;
    }

    public NodeEdge createNodeEdge(Edge e, Node n) {
        NodeEdge ne = new NodeEdge();
        ne.setE(e);
        ne.setN(n);
        return ne;
    }
}
