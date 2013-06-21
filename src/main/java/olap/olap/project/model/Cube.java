package olap.olap.project.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Cube {

	
	//String inicio , fin... 
	//Dimension.getName(temportal)
	private Map<String, Dimension> dimensions = new HashMap<String, Dimension>();
	private Set<Measure> measures = new HashSet<Measure>();
	private String name;
	
	
	public Cube(String name) {
		this.name = name;
	}
	
	public boolean changeDimensionName(String oldName, String newName){
		boolean ret = false;
		Dimension[] vals = (Dimension[]) dimensions.values().toArray();
		for(int i = 0; i< vals.length; i++){
			if (vals[i].getName().equals(oldName)){
				vals[i].setName(newName);
				ret = true;
			}
		}
		return ret;
	}
	
	public void addDimension(String dimName, Dimension dim) {
		dimensions.put(dimName, dim);
	}
	
	public Map<String, Dimension> getDimensions() {
		return dimensions;
	}

	public void addMeasure(Measure m) {
		measures.add(m);
	}
	
	public Set<Measure> getMeasures() {
		return measures;
	}
	
	public String getName() {
		return name;
	}
	
	public void print() {
		System.out.println("CUBE: " +name);
		for(Measure m: measures) {
			m.print();
		}
		for(Entry<String, Dimension> e : dimensions.entrySet()) {
			System.out.println("DIM: " +e.getKey() + ": " + e.getValue().getName());
		}
	}
}
