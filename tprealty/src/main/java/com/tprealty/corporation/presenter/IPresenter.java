package com.tprealty.corporation.presenter;

import java.util.List;

import com.tprealty.corporation.model.Project;

public interface IPresenter {

    void init();

    void requestFirebaseOnDataChange(List<Project> projectList);
}
