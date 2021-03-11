package model;

import java.util.ArrayList;
import java.util.List;

public class Project {
    public String projName;
    public String address;
    public List<ProjectList> projectList = new ArrayList<>();

    public String getProjName() {
        return projName;
    }

    public String getAddress() {
        return address;
    }

    public List<ProjectList> getProjectList() {
        return projectList;
    }
}
