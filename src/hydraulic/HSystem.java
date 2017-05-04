package hydraulic;

import java.util.ArrayList;

import org.omg.CORBA.Current;

import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.linker.LinkerServices.Implementation;

/**
 * Main class that act as a container of the elements for
 * the simulation of an hydraulics system 
 * 
 */
public class HSystem {
	
	ArrayList<Element> elements = new ArrayList<>();
	
	/**
	 * Adds a new element to the system
	 * @param elem
	 */
	public void addElement(Element elem){
		elements.add(elem);		
	}
	
	/**
	 * returns the element added so far to the system
	 * @return an array of elements whose length is equal to the number of added elements
	 */
	public Element[] getElements(){
		return  (Element[]) elements.toArray();
	}
	
	/**
	 * Prints the layout of the system starting at each Source
	 */
	public String layout(){
		// TODO: to be implemented
		return null;
	}
	
	/**
	 * starts the simulation of the system
	 */
	public void simulate(){
		java.util.ListIterator<Element> i = elements.listIterator();
		while(!((Element)i.next() instanceof Source )){
			};			
			
		//assert che sia ben formato
		System.out.println("Source found. Starting simulation...");
		
		Element element = ((Element)i.previous());
		element.getOutput().setFlow(element.getFlow());
		
		if(simulation(element)){
			System.out.println("well done!");
		}else { 
			System.out.println("something goes wrong");
			}
		
		//print results
		for(Element x : elements){
			System.out.println(x.getName()+" : "+x.flow);
		}
	}
	
	private boolean simulation(Element milestone){
		
		Element currentElement=milestone;
		
		while(!(currentElement.getOutput() instanceof Sink))
		{
			if (currentElement.getOutput() instanceof Split){
				
				currentElement.getOutput().setFlow(currentElement.getFlow());
					
				Split split=(Split) currentElement.getOutput();
				
				//gestione Split errata per causa dell impostazione tramite next
				
				if(
//						simulation(((Split) currentElement).getOutputs()[0]) || 
//						simulation(((Split) currentElement).getOutputs()[1])
						simulationSplit(split) || simulationSplit(split)
						)
				{
					System.out.println("source joined");
					return true; //fine di tutto
				}
				else{
					System.out.println("Split:Error:no can't find a good path.");
					return false;
				}
			}
			if (currentElement.getOutput() instanceof Tap){ if(((Tap)currentElement.getOutput()).getStatus()==false) return false;}
			
			currentElement.getOutput().setFlow(currentElement.getFlow());
			
			currentElement=currentElement.getOutput();
		}
		
		return true;
	}
	
	private boolean simulationSplit(Split currentElement){
		final int N=2;
		int i=0;
		while(!(currentElement.getOutputs()[i] instanceof Sink) && i<N)
		{
			if (currentElement.getOutputs()[i] instanceof Split){
				
				currentElement.getOutput().setFlow(currentElement.getFlow());
					
				currentElement=(Split) currentElement.getOutputs()[i];
				
				//gestione Split errata per causa dell impostazione tramite next
				
				if(
						simulation(((Split) currentElement).getOutputs()[0]) || 
						simulation(((Split) currentElement).getOutputs()[1])
						)
				{
					System.out.println("source joined");
					return true; //fine di tutto
				}
				else{
					System.out.println("Split:Error:no can't find a good path.");
					return false;
				}
			}
			if (currentElement.getOutputs()[i] instanceof Tap){ if(((Tap)currentElement.getOutputs()[i]).getStatus()==false) return false;}
			
			currentElement.getOutputs()[i].setFlow(currentElement.getFlow());
			
			//currentElement=currentElement.getOutput();
			if(simulation(currentElement.getOutputs()[i])) return true;
		}
		
		return true;
	}
}
