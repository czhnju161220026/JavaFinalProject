package njuczh.Attributes;

public enum BulletAttribute {
    HERO("bullet.png"),EVIL("bullet2.png"),FIRE("fire.png"),WATER("water.png");
    final private String imagePath;
    BulletAttribute(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getImagePath() {
        return imagePath;
    }
}
