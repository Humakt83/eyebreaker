package fi.ukkosnetti.breaker.editor;

public class Main {
	public static void main(String[] args){
		EditorView v = new EditorView();
		Editor e = new Editor();
		EditorController c = new EditorController(e);
		v.setController(c);
	}
}
