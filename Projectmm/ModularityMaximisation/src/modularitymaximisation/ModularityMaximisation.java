package modularitymaximisation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ModularityMaximisation {

    static int comm_count = 0;

    public static void main(String[] args) {
        int k = 0;
        int R = 1;

        SocialNetwork sc = new SocialNetwork();
        sc.readFile();
        Map<Node, ArrayList<NodeEdge>> graph = sc.getGraph();
        Set<Edge> edgeList = sc.getEdgeList();
        while (k <= R) {
            for (Map.Entry<Node, ArrayList<NodeEdge>> lockEntry : graph.entrySet()) {
                Node threadId = lockEntry.getKey();
                ArrayList<NodeEdge> locksOfThread = lockEntry.getValue();
                for (int i = 0; i < locksOfThread.size(); i++) {
                    locksOfThread.get(i).getN().setExtended(false);
                }
            }//unextend all vertices
            extension(sc, graph, edgeList);
            k++;
        }//while end

        System.out.println("Start:");

        List list = new ArrayList(edgeList);//list created
        Collections.sort(list);//sorted
        List<Community> list_comm = new ArrayList();


        Iterator iterator = list.iterator();
        Edge ed = null;
        while (iterator.hasNext()) {
            ed = (Edge) iterator.next();
            
            if ((!ed.getN1().isAssigned) && (!ed.getN2().isAssigned)) {
                comm_count++;
                Community com = new Community();
                com.setComm(comm_count);
                List<Node> list_c = new ArrayList();
                list_c.add(ed.getN1());
                ed.getN1().setIsAssigned(true);
                list_c.add(ed.getN2());
                ed.getN2().setIsAssigned(true);
                com.setList(list_c);
                list_comm.add(com);
            } else if (!ed.getN1().isAssigned) {
                comm_count++;
                Community com = new Community();
                com.setComm(comm_count);
                List<Node> list_c = new ArrayList();
                list_c.add(ed.getN1());
                ed.getN1().setIsAssigned(true);
                com.setList(list_c);
                list_comm.add(com);
            } else if (!ed.getN2().isAssigned) {
                comm_count++;
                Community com = new Community();
                com.setComm(comm_count);
                List<Node> list_c = new ArrayList();
                list_c.add(ed.getN2());
                ed.getN2().setIsAssigned(true);
                com.setList(list_c);
                list_comm.add(com);
            }
            
            //System.out.println(ed.getN1().getName()+" : "+ed.getN2().getName()+" = "+ed.getWeight());
        }
        iterator = list_comm.iterator();
        Iterator it;
        while (iterator.hasNext()) {
            Community com = (Community) iterator.next();
            it = com.getList().iterator();
            System.out.print("com " + com.getComm() + ":");
            while (it.hasNext()) {
                Node n = (Node) it.next();
                System.out.print(n.getName() + " ");
            }
            System.out.println("");
        }


    }

    public static void extension(SocialNetwork sc, Map<Node, ArrayList<NodeEdge>> graph, Set<Edge> edgeList) {
        int cFriends;
        for (Map.Entry<Node, ArrayList<NodeEdge>> lockEntry : graph.entrySet()) {
            Node threadId = lockEntry.getKey();
            ArrayList<NodeEdge> locksOfThread = lockEntry.getValue();
            System.out.print("v :" + threadId.getName());
            for (int i = 0; i < locksOfThread.size(); i++) {
                ArrayList<NodeEdge> a = graph.get(locksOfThread.get(i).getN());
                System.out.print("\nu :" + locksOfThread.get(i).getN().getName());
                ArrayList<NodeEdge> j;
                Edge edge1 = new Edge();
                edge1.setN1(locksOfThread.get(i).getN());
                edge1.setN2(threadId);
                Iterator iterator = edgeList.iterator();
                Edge ed = null;
                while (iterator.hasNext()) {
                    ed = (Edge) iterator.next();
                    if (edge1.equals(ed)) {
                        break;
                    }
                }
                if ((!ed.isWeighted()) && (!locksOfThread.get(i).getN().isExtended())) {
                    if (a != null) {
                        j = intersection(a, locksOfThread);

                        iterator = j.iterator();
                        while (iterator.hasNext()) {
                            NodeEdge d = (NodeEdge) iterator.next();
                            System.out.println(d.getN().getName());
                        }


                        if (j != null) {
                            cFriends = j.size();
                        } else {
                            cFriends = 0;
                        }
                        // check values
                        ed.setWeight(cFriends / (Math.sqrt((double) locksOfThread.size()) * Math.sqrt((double) a.size())));
                        ed.setWeighted(true);
                        locksOfThread.get(i).getN().setExtended(true);
                    }
                }

            }
        }
    }

    public static ArrayList<NodeEdge> intersection(ArrayList<NodeEdge> list1, ArrayList<NodeEdge> list2) {
        ArrayList<NodeEdge> result = new ArrayList<>(list1);

        result.retainAll(list2);

        return result;
    }
}
