package hydraulic;

/**
 * Represents the sink, i.e. the terminal element of a system
 *
 */
public class Sink extends Element {

	/**
	 * Constructor
	 * @param name
	 */
	public Sink(String name) {
		super(name);
		//TODO: complete
	}
	public void connect(Element elem) throws NullPointerException{
		System.out.println("impossibile connettere attivamente lo scarico.");
		throw new NullPointerException();
	}
}
