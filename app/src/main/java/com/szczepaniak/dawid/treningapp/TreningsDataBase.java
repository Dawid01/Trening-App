package com.szczepaniak.dawid.treningapp;

import java.util.ArrayList;

public class TreningsDataBase {

    private ArrayList<TreningClass> treningClassList;

    public TreningsDataBase() {

        treningClassList =  new ArrayList<>();
        createTreningsList();
    }

    private void createTreningsList(){

        //ABDOMEN
        TreningClass Crunch =  new TreningClass("Abdomen", "Crunch", "The crunch is performed while lying face up on the floor with knees bent, by curling the shoulders up towards the pelvis. This is an isolation exercise for the abdominals.");
        treningClassList.add(Crunch);
        TreningClass LegRaise =  new TreningClass("Abdomen", "Leg Raise", "The leg raise is performed while sitting on a bench or flat on the floor by raising the knees towards the shoulders, or legs to a vertical upright position. This is a compound exercise that also involves the hip flexors.");
        treningClassList.add(LegRaise);


        //BACK
        TreningClass BentOverBarbellDeadlift = new TreningClass("Back", "Bent-Over Barbell Deadlift", "Bent-Over Barbell Deadlift");
        treningClassList.add(BentOverBarbellDeadlift);
        TreningClass WideGripPullUp = new TreningClass("Back", "Wide-Grip Pull-Up", "Wide-Grip Pull-Up");
        treningClassList.add(WideGripPullUp);
        TreningClass SingleArmDumbbellRow = new TreningClass("Back", "Single-Arm Dumbbell Row", "Single-Arm Dumbbell Row");
        treningClassList.add(SingleArmDumbbellRow);
    }

    public ArrayList<TreningClass> getTreningClassList() {
        return treningClassList;
    }
}
