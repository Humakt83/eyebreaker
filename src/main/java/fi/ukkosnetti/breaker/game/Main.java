package fi.ukkosnetti.breaker.game;

import fi.ukkosnetti.breaker.game.controller.Controller;
import fi.ukkosnetti.breaker.game.ui.View;

public class Main {
	public static void main(String[] args){
		Game g = new Game();
		View v = new View();
		Controller c = new Controller(v, g);
		v.setController(c);
	}
}
