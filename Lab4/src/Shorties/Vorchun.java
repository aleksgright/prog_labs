package Shorties;

public class Vorchun extends Shorty {
    public Vorchun() {
        super("Vorchun");
    }

    public Vorchun(LeaveMoveTo notDefault) {

        super("Vorchun", notDefault);
    }

    @Override
    public void takeClothes(Hospital.StorageRoom storageRoom) {
        System.out.println(this + " sneaked to Storage room");
        super.takeClothes(storageRoom);
        storageRoom.takeCloth();
        System.out.println(this + " took clothes");
    }



    @Override
    protected void complain() {

    }
}
