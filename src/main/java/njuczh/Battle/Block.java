package njuczh.Battle;
import javafx.scene.image.Image;
import njuczh.MyAnnotation.Author;
import njuczh.Things.*;

@Author(name = "崔子寒")
public class Block{
    private Creature creature = null;
    private boolean isEmpty = true;

    public boolean creatureEnter(Creature creature) throws NullPointerException{
        isEmpty = false;
        this.creature = creature;
        if(this.creature==null) {
            throw  new NullPointerException() ;
        }
        return true;
    }

    public void creatureLeave() {
        isEmpty = true;
        creature = null;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public Creature getCreature() {
        return creature;
    }

    public Image getImage() {
        if(creature == null) {
            return null;
        }
        else {
            return creature.getImage();
        }
    }
}
