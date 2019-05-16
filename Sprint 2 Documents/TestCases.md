| Input Value  | Method Tested | Expected Behavior | Actual Behavior |
| ------------ | ------------- | ----------------- | --------------- |
|"run","very far",4,135|createChallenge(name,descrp,min,creatorID)|Message "Challenge Created" + Posted to Challenge database|Message "Challenge Created" + Posted to Challenge database|
|(blank)|createChallenge(name,descrp,min,creatorID)|Error Message "All fields are required"|Error Message "All fields are required"|
|"test","whatever",0,135|createChallenge(name,descrp,min,creatorID)|Error Message "Mininum can not be 0"|Error Message "Mininum can not be 0"|
|"Super Cool Team",5,135,1|createTeam(name,size,OrganizerID,ChallengeID)|Message "Team Created" + Posted to Team database + Posted to LookUps table|Message "Team Created" + Posted to Team database + Posted to LookUps table|
|(blank)|createTeam(name,size,OrganizerID,ChallengeID)|Error Message "All fields are required"|Error Message "All fields are required"|
|"Daff",0,138,2|createTeam(name,size,OrganizerID,ChallengeID)|Error Message "Team must be at least size of 1|Error Message "Team must be at least size of 1|
|138,21|joinTeam(userID,teamID)|Message "Joined Team" + posted to LookUps table|Message "Joined Team" + posted to LookUps table|
|(blank)|joinTeam(userID,teamID)|Error Message "Failed to join team"|Error Message "Failed to join team"|
|138,(blank)|joinTeam(userID,teamID)|Error Message "Failed to join team"|Error Message "Failed to join team"|
|(blank),21|joinTeam(userID,teamID)|Error Message "Failed to join team"|Error Message "Failed to join team"|
|138|getTeams(userID)|Returns a list of Teams that the user is currently on|Returns a List of Teams that the user is currently on|
|1|getTeams(userID)|Returns nothing, user not on any team|Returns nothing, user not on any team|
|19|getTeamsINchallenge(challengeID)|Returns a list of Teams that is registered with the Challenge|Returns a list of Teams that is registered with the Challenge|
|225|getTeamsINchallenge(challengeID)|Returns nothing, no teams registered with challenge|Returns nothing, no teams registered with challenge|
|(blank),3,63,1|CreateTeam(teamName,teamSize,OrganizerID,ChallengeID)|Returns message "Team name required"|Returns message "Team Name Required"|
|"negative","run backwards",-2,135|createChallenge(name,descrp,min,creatorID)|User should be unable to input negative values|User is unable to enter negative values|
|"breaktheapp)(*&^%$#@!":?><","whatever",3,135|createChallenge(name,descrp,min,creatorID)|Error Message "Big oof! Challenge failure"|Error Message "Big oof! Challenge failure"|
