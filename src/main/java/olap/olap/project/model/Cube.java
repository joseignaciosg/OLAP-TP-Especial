package olap.olap.project.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
	
	

	public boolean changeDimensionName(String oldName, String newName, int columnCount ){
		boolean ret = true;
		System.out.println("-----------"+dimensions.values());
		System.out.println("-----------"+dimensions.values().toArray());
		Collection<Dimension> vals = dimensions.values();
		Iterator<Dimension> it = vals.iterator();
		Dimension d;
		int propertyCount;
		while(it.hasNext()){
			d = it.next();
			if(d.getName().equals(oldName)){
				propertyCount = d.getPropertyQty();
				/*si la cantidad de propiedades de la dimensión en el
				 * cubo es diferente a la cantidad de campos de la tabla
				 * en la base de datos, entonces hubo un error
				 * */
				System.out.println("DIM:" +d.getName() +": props:" +propertyCount);
				if (columnCount != propertyCount){
					ret = false;
					break;
				}
				d.setName(newName);
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
