package com.example.dip_project.Class;

import java.util.ArrayList;

public class UserStatistic {
    String statisticName;
    String statisticTitle;

    public UserStatistic(String statisticName, String statisticTitle) {
        this.statisticName = statisticName;
        this.statisticTitle = statisticTitle;
    }

    public String getStatisticName() {
        return statisticName;
    }

    public String getStatisticTitle() {
        return statisticTitle;
    }
}
