package utility;

public enum Location {
    KITCHEN("Kitchen"), ROOM("Room"), STAFFROOM("StaffRoom"), OUTOFHOSPITAL("OutOfHospital");
    private String name;

    Location(String locationName) {
        this.name = locationName;
    }

    public String locationName() {
        return name;
    }
}
