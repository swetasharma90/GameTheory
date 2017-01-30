/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modularitymaximisation;

/**
 *
 * @author PEARLORCHIDS
 */
public class Edge implements Comparable{
    Node n1;
    Node n2;
    double weight;
    boolean weighted;

    public boolean isWeighted() {
        return weighted;
    }

    public void setWeighted(boolean weighted) {
        this.weighted = weighted;
    }
    
    public Node getN1() {
        return n1;
    }

    public void setN1(Node n1) {
        this.n1 = n1;
    }

    public Node getN2() {
        return n2;
    }

    public void setN2(Node n2) {
        this.n2 = n2;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
     @Override
     public boolean equals(Object object) {
		boolean result = false;
		if (object == null || object.getClass() != getClass()) {
			result = false;
                       // System.out.println("false");
		} else {
			Edge edge = (Edge) object;
			if (((this.n1.getName().equals(edge.n1.getName()))&&(this.n2.getName().equals(edge.n2.getName())))||
                                ((this.n2.getName().equals(edge.n1.getName()))&&(this.n1.getName().equals(edge.n2.getName()))))
					 {
				result = true;
                               //  System.out.println("true");
                                 
			}
		}
               
                //return (object instanceof Node && this.equals(((Node).this));
		return result;
	}
    @Override
	public int hashCode() {
		int hash = 3;
		hash = 7 * hash + this.n1.name.hashCode() + 7 * hash + this.n2.name.hashCode();;
		return hash;
	}

    @Override
    public int compareTo(Object o) {
        if(this.getWeight()>((Edge)o).getWeight())
            return -1;
        else
            return 1;
    }
}
