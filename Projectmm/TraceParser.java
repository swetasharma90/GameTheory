/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package racedetection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static racedetection.AppConstants.*;

public class TraceParser {

    public void parsing(String filename) {
        //public void parsing() {
        try {
            File file = new File(filename);
            //File file = new File("trace1.txt");
            String trace_operation;

            Scanner scanner = new Scanner(file, "Cp1252");
            int i = 1;
            while (scanner.hasNextLine()) {
                trace_operation = scanner.nextLine();
                Operation operation = new Operation();
                trace_operation = trace_operation.replaceAll("\u00A0", " ").trim();
                if (trace_operation.length() > 0) {
                    String[] split1 = trace_operation.split(":", 2);
                    operation.setSrNo(split1[0]);
                    if (split1.length > 1) {
                        operation.setName((split1[1].substring(0, split1[1].indexOf("("))).trim());
                    }
                    String argString = trace_operation.substring(trace_operation.indexOf("(") + 1, trace_operation.indexOf(")"));
                    if (!argString.isEmpty()) {
                        split1 = argString.split(",", 2);
                        operation.setArg1(split1[0].trim());
                        if (split1.length > 1) {
                            operation.setArg2(split1[1].trim());
                        }
                    }
                    RaceDetection.trace.put(i, operation);
                    // System.out.println(i + operation.getName() + operation.getArg1() + operation.getArg2());
                    i = i + 1;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("No such file exist!!");
        }
    }

    public static void malformedTraceChecker() {
        Set<String> created_but_not_runningThreadIds = new HashSet<>();
        Map<String, String> fork_Join_map = new HashMap<>();
        Set<String> runningThreadIds = new HashSet<>();
        Set<String> finishedThreadIds = new HashSet<>();
        Set<String> finished_and_Not_joinedThreadIds = new HashSet<>();
        Map<String, ArrayList<String>> lockMap = new ConcurrentHashMap<>();
        try {
            if (!RaceDetection.trace.get(1).getName().equals(THREADINIT)) {
                throw new MalFormedTraceException("First Operation should be THREADINIT\n");
            } else {
                runningThreadIds.add(RaceDetection.trace.get(1).getArg1());
            }
            for (int key = 1; key <= RaceDetection.trace.values().size(); key++) {
                Operation operation = RaceDetection.trace.get(key);
                if (operation.getName().contains(THREADINIT) && key > 1) {
                    //threadinit(t1) followed by threadinit(t1) before Threadexit(t1)
                    if (runningThreadIds.contains(operation.getArg1())) {
                        throw new MalFormedTraceException("At step " + key + ",More Than one attempt to initialize thread " + operation.getArg1() + "\n");
                    } else {
                        if (created_but_not_runningThreadIds.contains(operation.getArg1())) {
                            runningThreadIds.add(operation.getArg1());
                            created_but_not_runningThreadIds.remove(operation.getArg1());
                        } else {
                            throw new MalFormedTraceException("At step " + key + ", THREADINIT of " + operation.getArg1() + " without FORK.\n");
                        }
                    }
                } else if (!runningThreadIds.contains(operation.getArg1())) {
                    throw new MalFormedTraceException("Thread " + operation.getArg1() + " has not been initialized before use at step " + key + "\n");
                }

                switch (operation.getName()) {
                    case THREADEXIT:
                        if (lockMap.get(operation.getArg1()) == null || (lockMap.get(operation.getArg1()) != null && lockMap.get(operation.getArg1()).isEmpty())) {
                            if (runningThreadIds.contains(operation.getArg1())) {
                                runningThreadIds.remove(operation.getArg1());
                                finishedThreadIds.add(operation.getArg1());
                            }
                        } else {
                            throw new MalFormedTraceException("Thread " + operation.getArg1() + " can not exit while holding lock\n");
                        }
                        break;
                    case FORK:
                        //two same threadid forks
                        if (created_but_not_runningThreadIds.contains(operation.getArg2()) || runningThreadIds.contains(operation.getArg2())) {
                            throw new MalFormedTraceException("More Than one Fork for " + operation.getArg2() + "\n");
                        }
                        created_but_not_runningThreadIds.add(operation.getArg2());
                        finished_and_Not_joinedThreadIds.add(operation.getArg2());
                        // thread is forked by which thread
                        fork_Join_map.put(operation.getArg2(), operation.getArg1());
                        break;
                    case JOIN:
                        if (!finishedThreadIds.contains(operation.getArg2()) && !runningThreadIds.contains(operation.getArg2())) {
                            throw new MalFormedTraceException("At step " + key + ", Thread " + operation.getArg2() + " has not been initialied yet.\n");
                        }
                        if (!finishedThreadIds.contains(operation.getArg2())) {
                            throw new MalFormedTraceException("At step " + key + ", Thread " + operation.getArg2() + " has not been exited yet.\n");
                        }
                        if (!fork_Join_map.get(operation.getArg2()).equals(operation.getArg1())) {
                            throw new MalFormedTraceException("At step " + key + ", Thread " + operation.getArg2() + " is not forked by " + operation.getArg1() + " .\n");
                        }
                        if (finished_and_Not_joinedThreadIds.contains(operation.getArg2())) {
                            finished_and_Not_joinedThreadIds.remove(operation.getArg2());
                        } else {
                            throw new MalFormedTraceException("At step " + key + ", More Than one attempt to join " + operation.getArg2() + ".\n");
                        }
                        break;
                    case LOCK:
                        for (Entry<String, ArrayList<String>> lockEntry : lockMap.entrySet()) {
                            String threadId = lockEntry.getKey();
                            ArrayList<String> locksOfThread = lockEntry.getValue();
                            if (locksOfThread != null && locksOfThread.contains(operation.getArg2())) {
                                throw new MalFormedTraceException("At step " + key + ", Lock is already held by " + threadId + ".\n");
                            }
                        }
                        ArrayList<String> lock_of_current_thread = lockMap.get(operation.getArg1());
                        if (lock_of_current_thread == null) {
                            lock_of_current_thread = new ArrayList<>();
                        }
                        lock_of_current_thread.add(operation.getArg2());
                        lockMap.put(operation.getArg1(), lock_of_current_thread);
                        break;
                    case UNLOCK:
                        //                    boolean flag = false;
                        //                  for (Entry<String, ArrayList<String>> lockEntry : lockMap.entrySet()) {
                        //                    String threadId = lockEntry.getKey();
                        //                  ArrayList<String> locksOfThread = lockEntry.getValue();
                        ArrayList<String> locksOfThread = lockMap.get(operation.getArg1());
                        if (locksOfThread != null && locksOfThread.contains(operation.getArg2())) {
//                            if (!operation.getArg1().equals(threadId)) {
//                                throw new MalFormedTraceException("At step " + key + ", Wrong UNLOCK!! Lock is not held by " + operation.getArg1() + ".\n");
//                            }
//                            flag = true;
                            locksOfThread.remove(operation.getArg2());
                            lockMap.remove(operation.getArg1());
                            lockMap.put(operation.getArg1(), locksOfThread);
                        } //}
                        // if (!flag) {
                        else {
                            throw new MalFormedTraceException("At step " + key + ", Wrong UNLOCK!! Lock is not held by " + operation.getArg1() + ".\n");
                        }
                        break;
                    case NOTIFY:
//                        ArrayList<Integer> noti_waitArrayList = notify_wait_map.get(operation.getArg1());
//                        if(noti_waitArrayList==null){
//                            noti_waitArrayList = new ArrayList<>();
//                        }
//                        notify_wait_map.put(operation.getArg1(),noti_waitArrayList);
//                        break;
                        ArrayList<Operation> noti_waitArrayList = new ArrayList<>();
                        RaceDetection.notify_wait_map.put(operation.getSrNo(), noti_waitArrayList);
                        break;
                    case WAIT:
                        boolean wtn = true;
                        for (int traceLineNo = 1; traceLineNo < key; traceLineNo++) {
                            Operation findOperation = RaceDetection.trace.get(traceLineNo);
                            if (findOperation.getName().equals(NOTIFY) && findOperation.getArg1().equals(operation.getArg2())) {
                                ArrayList<Operation> notify_list = RaceDetection.notify_wait_map.get(findOperation.getSrNo());
                                if (notify_list == null) {
                                    throw new MalFormedTraceException("No corresponding Notify exist for step " + key);
                                }
                                boolean flag = false;
                                for (Operation op : notify_list) {
                                    if (op.getArg1().equals(operation.getArg1())) {
                                        flag = true;
                                        break;
                                    }
                                }
                                if (flag == true) {
                                    continue;
                                } else {
                                    wtn = false;
                                    notify_list.add(operation);
                                    RaceDetection.notify_wait_map.put(findOperation.getSrNo(), notify_list);
                                    break;
                                }
                            }
                        }
                        if (wtn) {
                            throw new MalFormedTraceException("No notify corresponding to wait");
                        }
                        break;
//                        ArrayList<String> notify_waitArrayList = notify_wait_map.get(operation.getArg2());
//                        if (notify_waitArrayList == null) {
//                            throw new MalFormedTraceException("No corresponding Notify exist for step " + key);
//                        } else {
//                            notify_waitArrayList.add(operation.getArg1());
//                            notify_wait_map.put(operation.getArg2(), notify_waitArrayList);
//                        }

                }
            }
            for (String threadId : created_but_not_runningThreadIds) {
                throw new MalFormedTraceException("Thread " + threadId + " has been created but not intialized.\n");
            }
            for (String threadId : runningThreadIds) {
                throw new MalFormedTraceException("Thread " + threadId + " is yet to be exited.\n");
            }
//            for (String threadId : finished_and_Not_joinedThreadIds) {
//                throw new MalFormedTraceException("Thread " + threadId + " is yet to be joined.\n");
//            }
            for (Entry<String, ArrayList<String>> lockEntry : lockMap.entrySet()) {
                String threadId = lockEntry.getKey();
                ArrayList<String> locksOfThread = lockEntry.getValue();
                if (locksOfThread != null && locksOfThread.size() > 0) {
                    throw new MalFormedTraceException("Thread " + threadId + " has not released all the locks.\n");
                }
            }
//            for (Entry<String, ArrayList<Operation>> notifyentry : RaceDetection.notify_wait_map.entrySet()) {
//                String threadNo = notifyentry.getKey();
//                ArrayList<Operation> locksOfThread = notifyentry.getValue();
//                if (locksOfThread == null || locksOfThread.size() == 0) {
//                    throw new MalFormedTraceException("No corresponidng wait for notify\n");
//                }
//
//            }
        } catch (MalFormedTraceException ex) {
            System.out.println("Malformed Trace: " + ex.toString());
            System.exit(0);
        }
    }
}
