package olap.olap.project.model;

import java.util.HashSet;
import java.util.Set;

public class Dimension {

	private Set<Hierarchy> hierarchies = new HashSet<Hierarchy>();
	private Level level;
	private String name;

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

	public Integer getPropertyQty() {
		Integer qty = 0;
		qty += level.getProperyQty();
		for (Hierarchy h : getHierarchies())
			qty += h.getPropertyQty();

		return qty;
	}

	public void print() {
		System.out.println("DIM: " + name);
		level.print();
		for (Hierarchy h : hierarchies) {
			h.print();
		}
	}
}
