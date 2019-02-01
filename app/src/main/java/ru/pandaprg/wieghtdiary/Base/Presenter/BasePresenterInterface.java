package ru.pandaprg.wieghtdiary.Base.Presenter;


import ru.pandaprg.wieghtdiary.Base.View.BaseViewInterface;

public interface BasePresenterInterface {
    boolean isAttached ();
    void attach (BaseViewInterface V);
    void detach ();
}
