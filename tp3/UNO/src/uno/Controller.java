package uno;

public abstract class Controller {

    public abstract Player whoIsNext(Uno game);
    public abstract Controller changeController();


}

class RightController extends Controller {

    public Player whoIsNext(Uno game) {
        return game.getCurrentPlayer().getNext();
    }

    public Controller changeController() {
        return new LeftController();
    }

}

class LeftController extends Controller {
    public Player whoIsNext(Uno game) {
        return game.getCurrentPlayer().getPrev();
    }

    public Controller changeController() {
        return new RightController();
    }
}