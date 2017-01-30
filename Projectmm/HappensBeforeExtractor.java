/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package racedetection;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import static racedetection.AppConstants.*;

/**
 *
 * @author LAKSHMI BANSAL
 */
public class HappensBeforeExtractor {

    public static Map<Integer, ArrayList<Integer>> happens_before_extraction() {
        Map<Integer, ArrayList<Integer>> happensBefore = new ConcurrentHashMap<>();
        Map<String, Integer> trackOfLastOperation = new ConcurrentHashMap<>();
        for (int key = 1; key <= RaceDetection.trace.values().size(); key++) {
            happensBefore.put(key, new ArrayList<Integer>());
            if (!trackOfLastOperation.keySet().contains(RaceDetection.trace.get(key).getArg1())) {
                trackOfLastOperation.put(RaceDetection.trace.get(key).getArg1(), 0);
            }
        }
        trackOfLastOperation.remove(RaceDetection.trace.get(1).getArg1());
        trackOfLastOperation.put(RaceDetection.trace.get(1).getArg1(), 1);
        for (int key = 2; key <= RaceDetection.trace.values().size(); key++) {
            Operation operation = RaceDetection.trace.get(key);
            Integer previousOperation = trackOfLastOperation.get(operation.getArg1());
            if (previousOperation != 0) {
                createHappensBefore(happensBefore, previousOperation, key);
            }
            trackOfLastOperation.remove(operation.getArg1());
            trackOfLastOperation.put(operation.getArg1(), key);

            switch (operation.getName()) {
                case THREADINIT:
                    for (int traceLineNo = key; traceLineNo > 1; traceLineNo--) {
                        Operation findOperation = RaceDetection.trace.get(traceLineNo);
                        if (findOperation.getName().equals(FORK) && findOperation.getArg2().equals(operation.getArg1())) {
                            createHappensBefore(happensBefore, traceLineNo, key);
                            break;
                        }
                    }
                    break;
                case JOIN:
                    for (int traceLineNo = key; traceLineNo > 1; traceLineNo--) {
                        Operation findOperation = RaceDetection.trace.get(traceLineNo);
                        if (findOperation.getName().equals(THREADEXIT) && findOperation.getArg1().equals(operation.getArg2())) {
                            createHappensBefore(happensBefore, traceLineNo, key);
                            break;
                        }
                    }
                    break;
                case LOCK:
                    for (int traceLineNo = key; traceLineNo > 1; traceLineNo--) {
                        Operation findOperation = RaceDetection.trace.get(traceLineNo);
                        if (findOperation.getName().equals(UNLOCK) && !findOperation.getArg1().equals(operation.getArg1()) && findOperation.getArg2().equals(operation.getArg2())) {
                            createHappensBefore(happensBefore, traceLineNo, key);
                            break;
                        }
                    }
                    break;
                case WAIT:
//                    for (int traceLineNo = key; traceLineNo > 1; traceLineNo--) {
//                        Operation findOperation = RaceDetection.trace.get(traceLineNo);
//                        if (findOperation.getName().equals(NOTIFY) && findOperation.getArg1().equals(operation.getArg2())) {
//                            createHappensBefore(happensBefore, traceLineNo, key);
//                            break;
//                        }
//                    }
//                    break;
//                case WAIT:
                    for (int traceLineNo = 1; traceLineNo < key; traceLineNo++) {
                        Operation findOperation = RaceDetection.trace.get(traceLineNo);
                        if (findOperation.getName().equals(NOTIFY) && findOperation.getArg1().equals(operation.getArg2())) {
                            ArrayList<Operation> notify_list = RaceDetection.notify_wait_map.get(findOperation.getSrNo());
                            if(notify_list.contains(operation)){
                                createHappensBefore(happensBefore, traceLineNo, key);
                                break;
                            }
                        }
                    }
                    break;

            }
        }
//        for (Entry<Integer, ArrayList<Integer>> entry : happensBefore.entrySet()) {
//            System.out.print(entry.getKey() + ": ");
//            for (Integer listValues : entry.getValue()) {
//                System.out.print(listValues + " ");
//            }
//            System.out.print("\n");
//        }
        return happensBefore;
    }

    private static void createHappensBefore(Map<Integer, ArrayList<Integer>> happensBefore, Integer previousOperation, Integer key) {
        ArrayList<Integer> happenbeforeList = happensBefore.get(previousOperation);
        happenbeforeList.add(key);
        happensBefore.remove(previousOperation);
        happensBefore.put(previousOperation, happenbeforeList);
    }
}
