package ru.pandaprg.wieghtdiary.Base.Presenter;


import ru.pandaprg.wieghtdiary.Base.View.BaseViewInterface;

public abstract class BasePresenter implements BasePresenterInterface {

    protected BaseViewInterface view = null;

    public  boolean isAttached (){
        if (this.view != null){
            return true;
        }
        return false;
    }

    public void attach (BaseViewInterface V){
        this.view = V;
    }

    public void detach () {
        this.view = null;
    }
}
