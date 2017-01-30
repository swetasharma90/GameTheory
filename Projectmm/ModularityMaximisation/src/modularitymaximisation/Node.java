/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modularitymaximisation;

/**
 *
 * @author PEARLORCHIDS
 */
public class Node {
    String label;
    boolean extended;
    String name;
  boolean isAssigned;
  int community;
  int degree;

    public int getCommunity() {
        return community;
    }

    public void setCommunity(int community) {
        this.community = community;
    }
    public boolean isIsAssigned() {
        return isAssigned;
    }

    public void setIsAssigned(boolean isAssigned) {
        this.isAssigned = isAssigned;
    }
  
    public boolean isExtended() {
        return extended;
    }

  
    public void setExtended(boolean extended) {
        this.extended = extended;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
   
    @Override
     public boolean equals(Object object) {
		boolean result = false;
		if (object == null || object.getClass() != getClass()) {
			result = false;
                       // System.out.println("false");
		} else {
			Node node = (Node) object;
			if (this.name.equals(node.getName()))
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
		hash = 7 * hash + this.name.hashCode();
		return hash;
	}
}
