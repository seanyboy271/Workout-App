| Input Value  | Method Tested | Expected Behavior | Actual Behavior |
| ------------ | ------------- | ----------------- | --------------- |
|200|getActiveChallenges(int UserId)|Retrieves all Challenges that has started and User is registered for|Retrieves all Challenges that has started and User is registered for|
|333|getActiveChallenges(int UserId)|Empty list, no challenges User registered for has started|Empty list|
|208|getUpcomingChallenges(int UserId)|Retrieves all Challenges that has not yet started and User is registered for|Retrieves all Challenges that has not yet started and User is registered for|
|178|getUpcomingChallenges(int UserId)|Empty list, User has not registered for any future challenges|Empty list|
|200|getAthleteHistory(int UserId)|Retrieves all Challenges that User has logged result for|Retrieves all Challenges that User has logged result for|
|111|getAthleteHistory(int UserId)|Empty list, User has not logged any results for any challenges| Empty list|
|900|getAthleteHistory(intUserId)|Error: No User Exists|Error: No User Exists|
|200|getCoachHistory(int UserId)|Error: User is not a Coach | Error: User is not a Coach |
|207|getCoachHistory(int UserId)|Retrieves all Challenges the User has created|Retrives all Challenges the User has created|
|203|getCoachHistory(int UserId)|Empty list, User has not created any challenges| Empty list|
|65|getIndividualLeaderboard(int ChaId)|Retrieves 10 users with results ASC or DESC depending on Challenge Activity|Retrieves 10 users with results ASC or DESC depeneding on Challenge Activity|
|100|getIndividualLeaderboard(int ChaId)|Error: No challenge existed|Error: No challenge existed|
|66|getIndividualLeaderboard(int ChaId)|Empty list, no users has participated in challenge|Empty list|
|68|getTeamLeaderBoard(int ChaId)|Retrieves 10 teams with Average results ASC or DESC depending on Challenge Activity|Retrieves 10 teams with Average results ASC or DESC depending on Challenge Activity|
|66|getTeamLeaderBoard(int ChaId)|Empty list, no teams in challenge|Empty list|
|999|getTeamLeaderBoard(int ChaId)|Error: No challenge exists|Error: no challenge exists|
|12:00|createNotificationJoinTeam(Start time)| Should output a notification at 12:00| Notification was received slightly after 12:00|
|12:10|createNotificationCreateTeam(Start time)|Should output a notification at 12:10|Notifcation was received slightly after 12:10|
|12:00| createRepeatNotificationJoinTeam(Start time)| Should output a notification at 12:00, and another one every 30 seconds after that| Notification was received slightly after 12:00, and repeat notifications were received between 30 seconds and a minute and a half after|
|12:10| createRepeatNotificationCreateTeam(Start time)| Should output a notification at 12:10, and another one every 30 seconds after that| Notification was received slightly after 12:10, and repeat notifications were received between 30 seconds and a minute and a half after|
