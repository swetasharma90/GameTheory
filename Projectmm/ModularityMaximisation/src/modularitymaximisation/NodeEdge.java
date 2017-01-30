/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modularitymaximisation;

/**
 *
 * @author PEARLORCHIDS
 */
public class NodeEdge {
    Node n;
    Edge e;
    public Edge getE() {
        return e;
    }

    public void setE(Edge e) {
        this.e = e;
    }

    public Node getN() {
        return n;
    }

    public void setN(Node n) {
        this.n = n;
    }
    
    @Override
     public boolean equals(Object object) {
		boolean result = false;
		if (object == null || object.getClass() != getClass()) {
			result = false;
                       // System.out.println("false");
		} else {
			NodeEdge node = (NodeEdge) object;
			if (this.n.name.equals(node.getN().getName()))
					 {
				result = true;
                               //  System.out.println("true");
                                 
			}
		}
               
                //return (object instanceof Node && this.equals(((Node).this));
		return result;
	}
}
