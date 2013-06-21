package olap.olap.project.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Level {

	private String name;
	private int pos;
	private Set<Property> properties = new HashSet<Property>();

	public Level(String name, int pos) {
		super();
		this.name = name;
		this.pos = pos;
	}

	public void addProperty(Property p) {
		properties.add(p);
	}

	public Set<Property> getProperties() {
		return properties;
	}

	public String getName() {
		return name;
	}

	public int getPos() {
		return pos;
	}

	public void print() {
		System.out.println("LEVEL: " + name + " pos: " + pos);
		for (Property p : properties) {
			p.print();
		}
	}
	
	public List<String> getPropertyNames(){
		List<String> ret = new ArrayList<String>();
		for(Property p: properties){
			ret.add(p.getName());
		}
		return ret;
	}
	
	public boolean changePropertyName(String oldName, String newName){
		Iterator<Property> it = properties.iterator();
		while(it.hasNext()){
			Property p = it.next();
			if (p.getName().equals(oldName)){
				p.setName(newName);
				return true;
			}
		}
		return true;
	}
	

	public Integer getProperyQty() {
		return properties.size();
	}

}
