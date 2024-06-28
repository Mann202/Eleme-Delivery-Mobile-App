package com.example.fududelivery.Home.FilterBy;

import java.util.List;

public class FilterbyList {
    private String nameFilterbyList;
    private List<Filterby> filters;

    public FilterbyList(String nameFilterbyList, List<Filterby> filters) {
        this.nameFilterbyList = nameFilterbyList;
        this.filters= filters;
    }

    public String getNameFilterbyList() {
        return nameFilterbyList;
    }

    public void setNameFilterbyList(String nameFilterbyList) {
        this.nameFilterbyList = nameFilterbyList;
    }

    public List<Filterby> getFilters() {
        return filters;
    }

    public void setFilters(List<Filterby> filters) {
        this.filters = filters;
    }
}