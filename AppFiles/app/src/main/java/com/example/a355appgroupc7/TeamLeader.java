package com.example.a355appgroupc7;

public class TeamLeader {
    String teamName;
    int teamSize;
    double teamAvg;
    double teamTotal;
    int teamId;

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public double getTeamTotal() {
        return teamTotal;
    }

    public void setTeamTotal(double teamTotal) {
        this.teamTotal = teamTotal;
    }

    TeamLeader(String teamName, int teamId){
        this.teamName=teamName;
        this.teamId=teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public double getTeamSize() {
        return teamSize;
    }

    public void setTeamSize(int teamSize) {
        this.teamSize = teamSize;
    }

    public double getTeamAvg() {
        return teamAvg;
    }

    public void setTeamAvg(double teamAvg) {
        this.teamAvg = teamAvg;
    }
}
