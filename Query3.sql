-- Enter a new volunteer into the Volunteers table
INSERT INTO Volunteer (VolunteerID, SSN, Join_date, Last_training_date, Last_training_location)
VALUES ('Alice Johnson', '123-42-9034', '2024-01-15', 'Ohio');

-- Associate the volunteer with one or more teams
INSERT INTO VolunteerTeam (VolunteerID, TeamID)
VALUES (V001, T001), (V001, T002);  -- Assuming VolunteerID 1 is 'Alice Johnson', and teams 1 and 2

