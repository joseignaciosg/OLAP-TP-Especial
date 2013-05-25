package olap.olap.project.model;


public class Level extends PropertyHolder {

	private String name;
	private int pos;
	
	public Level(String name, int pos) {
		super();
		this.name = name;
		this.pos = pos;
	}

	public String getName() {
		return name;
	}
	
	public int getPos() {
		return pos;
	}
	
	public void print() {
		System.out.println("LEVEL: "+name+" pos: "+pos);
		printProps();
	}
	
}
