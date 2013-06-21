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
			ret.add(this.getName()+"_"+p.getName());
		}
		return ret;
	}
	
	public boolean changePropertyName(String oldName, String newName, String fieldType){
		Iterator<Property> it = properties.iterator();
		while(it.hasNext()){
			Property p = it.next();
			String realName = this.getName()+"_"+p.getName();
			if (realName.equals(oldName)){
				String ptype = SQLAttribute.valueOf(p.getType().toUpperCase()).toString();
				System.out.println("######## Property Type: "+ ptype );
				System.out.println("######## Field    Type: "+ fieldType );
				if ( ptype.equals(fieldType) ){
					p.setName(newName);
					return true;
				}else{
					return false;
				}
			}
		}
		return true;
	}
	

	public Integer getProperyQty() {
		return properties.size();
	}

}
