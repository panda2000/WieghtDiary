package ru.pandaprg.wieghtdiary.Main;

import java.util.HashMap;

import ru.pandaprg.wieghtdiary.Base.Presenter.BasePresenter;


public class MainPresenter extends BasePresenter {

    //private int id=0; // Todo: Перенести управление по ID в Presenter

    public void clickAddFab (){
        //Log.d ("onClickFab", "MainPresenter.clickAddFab");
        (( MainActivity)view).startMeasurement();
    }


    public void addMeasurement(HashMap<String, Double> measurementData) {
        //id = measurementData.get("_id").intValue();

        (( MainActivity)view).showMeasurement(measurementData);
    }
}
