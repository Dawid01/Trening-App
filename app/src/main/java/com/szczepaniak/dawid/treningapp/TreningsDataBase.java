package com.szczepaniak.dawid.treningapp;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;

public class TreningsDataBase {

    private ArrayList<TreningClass> treningClassList;
    private ArrayList<TreningClass> ownWorkouts;

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

        //BICEPS
        TreningClass BarbellCurl = new TreningClass("Biceps", "Barbell Curl", "Barbell Curl");
        treningClassList.add(BarbellCurl);
        TreningClass InclineDumbbellCurl = new TreningClass("Biceps", "Incline Dumbbell Curl", "Incline Dumbbell Curl");
        treningClassList.add(InclineDumbbellCurl);
        TreningClass ReverseGripBentOverRow = new TreningClass("Biceps", "Reverse-Grip Bent-Over Row", "Reverse-Grip Bent-Over Row");
        treningClassList.add(ReverseGripBentOverRow);
        TreningClass ConcentrationCurl = new TreningClass("Biceps", "Concentration Curl", "Concentration Curl");
        treningClassList.add(ConcentrationCurl);

        //CHEST
        TreningClass Pushups = new TreningClass("Chest", "Pushups", "Pushups");
        treningClassList.add(Pushups);
        TreningClass BarbellInclineBenchPressMediumGrip = new TreningClass("Chest", "Barbell Incline Bench Press Medium-Grip", "Barbell Incline Bench Press Medium-Grip");
        treningClassList.add(BarbellInclineBenchPressMediumGrip);
        TreningClass InclineDumbbellPress = new TreningClass("Chest", "Incline Dumbbell Press", "Incline Dumbbell Press");
        treningClassList.add(InclineDumbbellPress);
        TreningClass InclineDumbbellFlyes = new TreningClass("Chest", "Incline Dumbbell Flyes", "Incline Dumbbell Flyes");
        treningClassList.add(InclineDumbbellFlyes);
        TreningClass BarbellBenchPressMediumGrip = new TreningClass("Chest", "Barbell Bench Press - Medium Grip", "Barbell Bench Press - Medium Grip");
        treningClassList.add(BarbellBenchPressMediumGrip);
        TreningClass DipsChestVersion = new TreningClass("Chest", "Dips Chest Version", "Dips Chest Versio");
        treningClassList.add(DipsChestVersion);

        //SHOULDERS
        TreningClass SeatedBarbellMilitaryPress = new TreningClass("Shoulders", "Seated Barbell Military Press", "Seated Barbell Military Press");
        treningClassList.add(SeatedBarbellMilitaryPress);
        TreningClass SideLateralRaise = new TreningClass("Shoulders", "Side Lateral Raise", "Side Lateral Raise");
        treningClassList.add(SideLateralRaise);
        TreningClass SeatedBentOverRearDeltRaise = new TreningClass("Shoulders", "Seated Bent-Over Rear Delt Raise", "Seated Bent-Over Rear Delt Raise");
        treningClassList.add(SeatedBentOverRearDeltRaise);
        TreningClass MachineShoulderMilitaryPress = new TreningClass("Shoulders", "Machine Shoulder (Military) Press", "Machine Shoulder (Military) Press");
        treningClassList.add(MachineShoulderMilitaryPress);
        TreningClass StandingBarbellPressBehindNeck = new TreningClass("Shoulders", "Standing Barbell Press Behind Neck", "Standing Barbell Press Behind Neck");
        treningClassList.add(StandingBarbellPressBehindNeck);
        TreningClass UprightBarbellRow = new TreningClass("Shoulders", "Upright Barbell Row", "Upright Barbell Row");
        treningClassList.add(UprightBarbellRow);
        TreningClass ArnoldDumbbellPress = new TreningClass("Shoulders", "Arnold Dumbbell Press", "Arnold Dumbbell Press");
        treningClassList.add(ArnoldDumbbellPress);
    }

    public ArrayList<TreningClass> getTreningClassList() {
        return treningClassList;
    }


//    private ArrayList<TreningClass> getOwnWorkouts(Context context){
//
//        Gson gson = new Gson();
//        ArrayList<TreningClass> list = gson.fromJson(arrayString, ArrayList.class);
//    }
}
