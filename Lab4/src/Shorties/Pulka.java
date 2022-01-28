package Shorties;

import Meals.Kvas;
import Meals.Meal;
import utility.Ingridient;

public class Pulka extends Shorty {
    private Meal meal;
    private int mealCount = 0;

    public class PulkaLeg {
        private boolean isBroken;
        private boolean hasTumour = false;
        private boolean pain = false;

        public class Tumour {
            private double size;

            public Tumour(double s) {
                size = s;
                PulkaLeg.this.hasTumour = true;
                PulkaLeg.this.pain = true;
                System.out.println("Tumour has appeared");
            }

            public void increase() {
                size++;
                System.out.println(Pulka.this + "'s tumour increased");
            }

            public void decrease() {
                size--;
                System.out.println(Pulka.this + "'s tumour decreased");
            }

            public PulkaLeg getPulkaLeg() {
                return PulkaLeg.this;
            }
        }

        public PulkaLeg() {
            isBroken = true;
            Pulka.this.setBrokenLeg(true);
        }

        public void setBroken(boolean broken) {
            isBroken = broken;
        }

        public boolean isBroken() {
            return isBroken;
        }

        public Pulka getPulka() {
            return Pulka.this;
        }

        public boolean isPain() {
            return pain;
        }

        public void setPain(boolean pain) {
            this.pain = pain;
        }
    }

    public Pulka() {
        super("Pulka");
        setBrokenLeg(true);
    }

    public Pulka(LeaveMoveTo notDefault) {
        super("Pulka", notDefault);
        setBrokenLeg(true);
    }

    public void callNurse(Nurse nurse) {
        nurse.move(this.getLocation());
    }

    public void order(Nurse nurse, Meal meal, Hospital.Kitchen kitchen) {
        callNurse(nurse);
        System.out.println("Pulka: Cook me " + meal);
        receiveMeal(nurse.perform(meal, kitchen));
        nurse.move(getLocation());
        if (mealCount == 5) {
            nurse.complain();
            nurse.tire();
        }
        if (mealCount == 4) {
            order(nurse, new Kvas(Ingridient.PEAR), kitchen);
            nurse.move(getLocation());
        }
        //complain(this.meal);
    }

    // Лучше не называть методы стандартными именами, если в них другая логика. Геттер - это геттер, а у тебя тут еще какой-то бизнес-процесс
    public void receiveMeal(Meal meal) {
        System.out.println("Pulka gets " + meal);
        this.meal = meal;
        mealCount++;
        if (mealCount == 4 || mealCount == 5) {
            complain(meal);
        }
    }

    public void orderToFindDog(Nurse nurse, LeaveMoveTo where) {
        class Dog {
            final String name;

            Dog(String n) {
                name = n;
            }
        }
        Dog bulka = new Dog("Bul'ka");
        System.out.println(this + " find my dog Bul'ka");
        nurse.lookForSmth(bulka, where);
    }

    public void askForBandage(MedicalStaff medic) {
        medic.move(this.getLocation());
        medic.putBandage();
    }

    public void askForCast(MedicalStaff medic) {
        medic.move(this.getLocation());
        medic.putCast();
    }

    @Override
    protected void complain() {
        System.out.println("This hospital is awful.");
    }

    protected void complain(Meal meal) {
        if (mealCount == 5) {
            System.out.println("This " + meal.getName() + " stinks of onion");
        }
        if (mealCount == 4) {
            System.out.println("I asked for Pear Kvas");
        }
    }
}
