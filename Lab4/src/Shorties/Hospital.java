package Shorties;

import java.util.Objects;

public class Hospital {
    private final String name;

    public class Kitchen extends LeaveMoveTo {
        public Kitchen() {
            setName("kitchen");
        }

        public Hospital getHospital() {
            return Hospital.this;
        }
    }


    public class Room extends LeaveMoveTo {
        public Room() {
            setName("room");
        }
        public static class Bed{
            @Override
            public String toString() {
                return "bed";
            }
        }

        public Hospital getHospital() {
            return Hospital.this;
        }
    }


    public class StaffRoom extends LeaveMoveTo {
        public StaffRoom() {
            setName("staffRoom");
        }

        public Hospital getHospital() {
            return Hospital.this;
        }
    }


    public class StorageRoom extends LeaveMoveTo {
        private int clothesCount = 3;

        public StorageRoom() {
            setName("storageRoom");
        }

        public void addCloth() {
            clothesCount++;
        }

        public void takeCloth() {
            clothesCount--;
        }

        public int getClothesCount() {
            return clothesCount;
        }

        public Hospital getHospital() {
            return Hospital.this;
        }

    }


    public Hospital(String hospName) {
        name = hospName;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hospital)) return false;
        Hospital hospital = (Hospital) o;
        return Objects.equals(name, hospital.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
