CREATE TABLE Person (
    SSN CHAR (11) NOT NULL,
    Name VARCHAR (25) NOT NULL,
    Gender CHAR(1) CHECK (Gender IN ('M', 'F')) NOT NULL,
    Profession VARCHAR (25) NOT NULL,
    Mailing_address VARCHAR (50) NOT NULL,
    Email_address VARCHAR (50) NOT NULL,
    PhoneNumber VARCHAR (15) NOT NULL,
    EmergencyContact_Name VARCHAR (25) NOT NULL,
    EmergencyContact_PhoneNumber VARCHAR (15) NOT NULL,
    Relationship VARCHAR (15) NOT NULL,
    OnMailingList  BIT  DEFAULT (0),
    CONSTRAINT CK_MailingList CHECK (OnMailingList IN (0,1)),
    CONSTRAINT [PK_Person] PRIMARY KEY CLUSTERED ([SSN] ASC)
);

CREATE TABLE InsurancePolicy (
    PolicyID CHAR (10) NOT NULL,
    Provider_name VARCHAR (25) NOT NULL,
    Provider_address VARCHAR (25) NOT NULL,
    Type VARCHAR (15) NOT NULL,
    CONSTRAINT [PK_InsurancePolicy] PRIMARY KEY CLUSTERED ([PolicyID] ASC)
);

CREATE TABLE Client(
    ClientID CHAR (10) NOT NULL,
    Doctor_name VARCHAR (15) NOT NULL,
    Doctor_PhoneNo VARCHAR (15) NOT NULL,
    PolicyID CHAR (10) NOT NULL,
    SSN CHAR (11) NOT NULL,
    Assigned_date DATE NOT NULL,
    CONSTRAINT PK_Client PRIMARY KEY CLUSTERED ([ClientID] ASC),
    CONSTRAINT FK_Client_Person FOREIGN KEY (SSN) REFERENCES Person (SSN),
    CONSTRAINT FK_Client_InsurancePolicy FOREIGN KEY (PolicyID) REFERENCES InsurancePolicy (PolicyID)  
);

CREATE TABLE Volunteer (
    VolunteerID CHAR (10) NOT NULL,
    Join_date DATE NOT NULL,
    Last_training_date DATE NOT NULL,
    Last_training_location VARCHAR (25) NOT NULL,
    SSN CHAR (11) NOT NULL,
    CONSTRAINT [PK_Volunteer] PRIMARY KEY CLUSTERED ([VolunteerID] ASC),
    CONSTRAINT FK_Volunteer_Person FOREIGN KEY (SSN) REFERENCES Person (SSN)
);

CREATE TABLE Team (
    TeamID CHAR (10)  NOT NULL,
    Team_name VARCHAR (25) NOT NULL,
    Type VARCHAR (25) NOT NULL,
    Date_formed DATE  NOT NULL,
    CONSTRAINT [PK_Team] PRIMARY KEY CLUSTERED ([TeamID] ASC)
);

CREATE TABLE VolunterTeam (
    VolunteerID CHAR (10) NOT NULL,
    TeamID CHAR (10)  NOT NULL,
    Is_active BIT DEFAULT (0),
    Hours_worked INT,
    Month char(10),
    CONSTRAINT FK_VolunteerTeam_Volunteer FOREIGN KEY (VolunteerID) REFERENCES Volunteer (VolunteerID),
    CONSTRAINT FK_VolunteerTeam_Team FOREIGN KEY (TeamID) REFERENCES Team (TeamID) 
);

CREATE TABLE ClientTeam (
    ClientID CHAR(10) NOT NULL,
    TeamID CHAR(10) NOT NULL,
    Care_date DATE NOT NULL,
    Is_active BIT DEFAULT (0),
    CONSTRAINT FK_ClientTeam_Client FOREIGN KEY (ClientID) REFERENCES Client (ClientID),
    CONSTRAINT FK_ClientTeam_Team FOREIGN KEY (TeamID) REFERENCES Team (TeamID) 
)


CREATE TABLE Donor (
    DonorID CHAR (10) NOT NULL,
    Is_anonymous BIT CONSTRAINT [DEFAULT_Donor_Is_anonymous] DEFAULT ((0)) NOT NULL,
    SSN CHAR (11) NOT NULL,
    CONSTRAINT PK_Donor PRIMARY KEY CLUSTERED ([DonorID] ASC),
    CONSTRAINT FK_Donor_Person FOREIGN KEY (SSN) REFERENCES Person (SSN)
);

CREATE TABLE Employee (
    EmployeeID CHAR(10) NOT NULL,
    SSN CHAR (11) NOT NULL,
    Salary DECIMAL(10, 2),
    Marital_status CHAR(1) CHECK (Marital_status IN ('S', 'M', 'D')),
    Hire_date DATE NOT NULL,
    CONSTRAINT PK_Employee PRIMARY KEY CLUSTERED ([EmployeeID] ASC),
    CONSTRAINT FK_Employee_Person FOREIGN KEY (SSN) REFERENCES Person (SSN)
);

CREATE TABLE Expense(
    ExpenseNo CHAR (10) NOT NULL,
    EmployeeID CHAR (10) NOT NULL,
    Description VARCHAR (25) NOT NULL,
    Expense_date DATE NOT NULL,
    Amount DECIMAL (10, 2) NOT NULL,
    CONSTRAINT PK_Expense PRIMARY KEY CLUSTERED ([ExpenseNo] ASC),
    CONSTRAINT FK_Expense_Employee FOREIGN KEY (EmployeeID) REFERENCES Employee (EmployeeID)
);

CREATE TABLE Report (
    ReportNo CHAR (10) NOT NULL,
    TeamID CHAR (10) NOT NULL,
    EmployeeID CHAR (10) NOT NULL,
    Report_date DATE NOT NULL,
    Description VARCHAR (25) NOT NULL,
    CONSTRAINT [PK_Report] PRIMARY KEY CLUSTERED ([ReportNo] ASC),
    CONSTRAINT FK_Report_Team FOREIGN KEY (TeamID) REFERENCES Team (TeamID),
    CONSTRAINT FK_Report_Employee FOREIGN KEY (EmployeeID) REFERENCES Employee (EmployeeID)
);

CREATE TABLE Need (
    NeedID CHAR (10) NOT NULL,
    Description VARCHAR (25) NOT NULL,
    CONSTRAINT [PK_Need] PRIMARY KEY CLUSTERED ([NeedID] ASC)
);

CREATE TABLE ClientNeed (
    ClientID CHAR (10) NOT NULL,
    NeedID CHAR (10) NOT NULL,
    Importance INT CHECK (Importance BETWEEN 1 AND 10),
    CONSTRAINT FK_ClientNeed_Client FOREIGN KEY (ClientID) REFERENCES Client (ClientID),
    CONSTRAINT FK_ClientNeed_Need FOREIGN KEY (NeedID) REFERENCES Need (NeedID)
);

CREATE TABLE Donation (
    DonationNo CHAR(10) NOT NULL,
    DonorID CHAR(10) NOT NULL,
    Amount DECIMAL(10, 2) NOT NULL,
    Donation_date DATE NOT NULL,
    FundRaisingCampaign VARCHAR(25),
    CONSTRAINT [PK_Donation] PRIMARY KEY CLUSTERED ([DonationNo] ASC),
    CONSTRAINT FK_Donation_Donor FOREIGN KEY (DonorID) REFERENCES Donor (DonorID)
);

CREATE TABLE CardDonation (
    DonationNo CHAR(10) NOT NULL,
    CardNo INT NOT NULL,
    Type VARCHAR (15) NOT NULL,
    Expiration_date DATE NOT NULL,
    CONSTRAINT [PK_CardDonation] PRIMARY KEY CLUSTERED ([DonationNo] ASC),
);

CREATE TABLE CheckDonation (
    DonationNo CHAR(10) NOT NULL,
    CheckNo INT NOT NULL,
    CONSTRAINT [PK_CheckDonation] PRIMARY KEY CLUSTERED ([DonationNo] ASC),
);

