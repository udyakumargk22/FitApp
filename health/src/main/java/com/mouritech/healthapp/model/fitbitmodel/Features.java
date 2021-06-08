/*
 *
 *  * Purpose  Features.java
 *
 *  Copyright  2021  MouriTech
 *
 *  @author  udayv
 *
 *  Created on Mar 31, 2021
 *
 *  Modified on Mar 31, 2021
 *
 */

package com.mouritech.healthapp.model.fitbitmodel;

public class Features
{
    private String exerciseGoal;

    public String getExerciseGoal ()
    {
        return exerciseGoal;
    }

    public void setExerciseGoal (String exerciseGoal)
    {
        this.exerciseGoal = exerciseGoal;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [exerciseGoal = "+exerciseGoal+"]";
    }
}
