package com.example.a355appgroupc7;

import java.io.Serializable;

public class LogObject implements Serializable {
    int LogId;
    String UserId;
    String Username;
    String Result;
    String TeamName;
    String LogDate;
    String ChallengeName;
    String ChallengeId;
    String ChallengeStart;
    String ChallengeEnd;
    String Activity;
    String Descrip;
    String Coach;

    public String getCoach() {
        return Coach;
    }

    public void setCoach(String coach) {
        Coach = coach;
    }

    public String getDescrip() {
        return Descrip;
    }

    public void setDescrip(String descrip) {
        Descrip = descrip;
    }


    public String getChallengeStart() {
        return ChallengeStart;
    }

    public void setChallengeStart(String challengeStart) {
        ChallengeStart = challengeStart;
    }

    public String getChallengeEnd() {
        return ChallengeEnd;
    }

    public void setChallengeEnd(String challengeEnd) {
        ChallengeEnd = challengeEnd;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }

    public LogObject(String UserId, String ChallengeId, String Result){
        this.UserId=UserId;
        this.ChallengeId=ChallengeId;
        this.Result=Result;
    }

    public int getLogId() {
        return LogId;
    }

    public void setLogId(int logId) {
        LogId = logId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getChallengeId() {
        return ChallengeId;
    }

    public void setChallengeId(String challengeId) {
        ChallengeId = challengeId;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public String getLogDate() {
        return LogDate;
    }

    public void setLogDate(String logDate) {
        LogDate = logDate;
    }

    public String getChallengeName() {
        return ChallengeName;
    }

    public void setChallengeName(String challengeName) {
        ChallengeName = challengeName;
    }
}



