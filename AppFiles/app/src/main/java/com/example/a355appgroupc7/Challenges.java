package com.example.a355appgroupc7;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Challenges implements Serializable {



    private int Id;
    private String coach;
    private String name;
    private String desc;
    private String start;
    private String activity;
    private String fail;
    private String end;

    // Constructor
    Challenges(String name, String desc, String start, String activity, String end, String fail){

        this.name = name;
        this.desc = desc;
        this.start = start;
        this.activity = activity;
        this.fail = fail;
        this.end = end;
    }


    // Getters and Setters
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
    public String getChallengeName() { return name; }

    public void setChallengeName(String name) { this.name = name; }

    public String getDesc() { return desc; }

    public void setDesc(String desc) { this.desc = desc; }

    public String getFail() {
        return fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }
}
