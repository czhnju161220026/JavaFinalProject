package njuczh.Attributes;

import njuczh.MyAnnotation.Author;

@Author(name = "崔子寒")
public enum BulletAttribute {
    HERO("bullet.png"),EVIL("bullet2.png"),FIRE("fire.png"),WATER("water.png"),STINGER("stinger.png");
    final private String imagePath;
    BulletAttribute(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getImagePath() {
        return imagePath;
    }
}
