package breaker;

public class Main {
	public static void main(String[] args){
		Game g = new Game();
		View v = new View();
		Controller c = new Controller(v, g);
		v.setController(c);
	}
}
