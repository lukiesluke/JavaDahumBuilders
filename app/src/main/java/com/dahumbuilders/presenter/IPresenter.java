package com.dahumbuilders.presenter;

import com.dahumbuilders.model.Project;

import java.util.List;

public interface IPresenter {

    void init();

    void requestFirebaseOnDataChange(List<Project> projectList);
}
