/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modularitymaximisation;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PEARLORCHIDS
 */
public class Community {
    List<Node> list;
    int comm;

    public int getComm() {
        return comm;
    }

    public Community() {
        this.list = new ArrayList<>();
    }

    public void setComm(int comm) {
        this.comm = comm;
    }

    public List<Node> getList() {
        return list;
    }

    public void setList(List<Node> list) {
        this.list = list;
    }
    
}
