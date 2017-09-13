package com.example.kaeuc.smartufpa.asynctasks.interfaces;

import com.example.kaeuc.smartufpa.models.Place;
import com.example.kaeuc.smartufpa.utils.enums.ServerResponse;

import java.util.ArrayList;

/**
 * Created by kaeuc on 29/08/2017.
 */

public interface OnSearchQueryListener {
    void onSearchQueryResponse(ArrayList<Place> places, ServerResponse taskStatus);
}