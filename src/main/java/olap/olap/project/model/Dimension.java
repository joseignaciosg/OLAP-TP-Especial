package olap.olap.project.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Dimension {

	private Set<Hierarchy> hierarchies = new HashSet<Hierarchy>();
	private Level level;
	private String name; //temporal

	public Dimension(String name) {
		this(name, null);
	}

	public Dimension(String name, Level level) {
		this.name = name;
		setLevel(level);
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void addHierarchy(Hierarchy h) {
		hierarchies.add(h);
	}

	public Set<Hierarchy> getHierarchies() {
		return hierarchies;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}

	public Integer getPropertyQty() {
		Integer qty = 0;
		qty += level.getProperyQty();
		for (Hierarchy h : getHierarchies())
			qty += h.getPropertyQty();

		return qty;
	}
	
	public List<String> getPropertyNames(){
		List<String> ret = new ArrayList<String>();
		for(Property p: level.getProperties()){
			ret.add(level.getName()+"_"+p.getName());
		}
		for(Hierarchy h: hierarchies){
			ret.addAll(h.getPropertyNames());
		}
		return ret;
	}

	public boolean changePropertyName(String oldName, String newName, String fieldType){
		Iterator<Property> iter = this.level.getProperties().iterator();
		boolean valid = true;
		while (iter.hasNext()) {
			Property p = iter.next();
			String realName = level.getName()+"_"+p.getName();
			if(realName.equals(oldName)){
				String ptype = SQLAttribute.valueOf(p.getType().toUpperCase()).toString();
				System.out.println("######## Property Type: "+ ptype );
				System.out.println("######## Field    Type: "+ fieldType );
				if(fieldType.matches("int*.")){
					fieldType = "integer";
				}
				if ( ptype.toLowerCase().equals(fieldType) ){
					p.setName(newName);
					return true;
				}else{
					return false;
				}
				
			}
		}
		Iterator<Hierarchy> it = this.hierarchies.iterator();
		while (it.hasNext()) {
			Hierarchy h = it.next();
			valid &= h.changePropertyName( oldName,  newName, fieldType);
		}
		return valid;
	}
	
	public void print() {
		System.out.println("DIM: " + name);
		level.print();
		for (Hierarchy h : hierarchies) {
			h.print();
		}
	}

	
}
