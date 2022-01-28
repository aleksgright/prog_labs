package pokemons;

import moves.*;
import ru.ifmo.se.pokemon.*;

public class Chingling extends Pokemon{
	
	public Chingling(String name, int level){
		super(name, level);
		setStats(45, 30, 50, 65, 50, 45);
		setType(Type.PSYCHIC);
		setMove(new Thunder(), new FocusBlast(), new Swagger());
	}
}