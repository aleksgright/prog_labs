import pokemons.*;
import ru.ifmo.se.pokemon.*;

public class Battleground {
    public static void main(String[] args){
	Battle b = new Battle();
	Pokemon p1 = new Giratina("Avdol", 2);
	Pokemon p2 = new Chimeco("Kujo", 10);
	Pokemon p3 = new Chingling("Joseph", 10);
	Pokemon p4 = new Goomy("Polnareff", 1);
	Pokemon p5 = new Sliggoo("Kakyoin", 7);
	Pokemon p6 = new Goodra("Dio", 4);

	b.addAlly(p1);
	b.addAlly(p3);
	b.addAlly(p4);

	b.addFoe(p2);
	b.addFoe(p5);
	b.addFoe(p6);

	b.go();
    }
}
