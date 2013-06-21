package olap.olap.project.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Hierarchy {

	private String name;
	private Set<Level> levels = new HashSet<Level>();

	public Hierarchy(String name) {
		this.name = name;
	}

	public void addLevel(Level l) {
		levels.add(l);
	}

	public String getName() {
		return name;
	}

	public Set<Level> getLevels() {
		return levels;
	}

	public void print() {
		System.out.println("HIER: " + name);
		for (Level l : levels) {
			l.print();
		}
	}

	public Integer getPropertyQty() {
		Integer qty = 0;
		for (Level l : getLevels())
			qty += l.getProperyQty();
		return qty;
	}
	
	public List<String> getPropertyNames(){
		List<String> ret = new ArrayList<String>();
		for(Level l: levels){
			ret.addAll(l.getPropertyNames());
		}
		return ret;
	}
	
	public boolean changePropertyName(String oldName, String newName, String fieldType){
		Iterator<Level> it = this.levels.iterator();
		boolean valid = true;
		while (it.hasNext()) {
			Level l = it.next();
			valid &= l.changePropertyName(oldName, newName, fieldType);
		}
		return valid;
	}
	
}
