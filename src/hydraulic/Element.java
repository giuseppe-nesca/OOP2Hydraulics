package hydraulic;

/**
 * Represents the generic abstract element of an hydraulics system.
 * It is the base class for all elements.
 *
 * Any element can be connect to a downstream element
 * using the method {@link #connect(Element) connect()}.
 */
public abstract class Element {
	
	protected String name;
	protected Element in,out;
	protected double flow;  // in == out / out/2 for Split : the T element
	
	public double getFlow(){
		return flow;
	}
	
	public void setFlow(double flow){
		this.flow=flow;
	}
	/**
	 * Constructor
	 * @param name the name of the element
	 */
	public Element(String name){
		this.name = name;
	}

	/**
	 * getter method
	 * @return the name of the element
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Connects this element to a given element.
	 * The given element will be connected downstream of this element
	 * @param elem the element that will be placed downstream
	 */
	public void connect(Element elem){
		
		out=elem; elem.inConnection(this);
		
	}
	/**
	 * Set input of the connected element 
	 **/
	protected void inConnection(Element elem){
		in=elem;
	}
	
	/**
	 * Retrieves the element connected downstream of this
	 * @return downstream element
	 */
	public Element getOutput(){
		return out;
	}
	
}
