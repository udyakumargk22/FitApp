/*
 *
 *  * Purpose  FitbitProfileModel.java
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

public class FitbitProfileModel
{
    private User user;

    public User getUser ()
    {
        return user;
    }

    public void setUser (User user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [user = "+user+"]";
    }
}
