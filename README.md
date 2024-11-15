## INDIVIDUAL PROJECT: A PATIENT ASSISTANT NETWORK DATABASE SYSTEM

### Overview
This project is an individual assignment to design and implement a **Patient Assistant Network (PAN) Database System** for a non-profit organization supporting patients in need. The database will manage personnel and operational information critical to PAN's mission, including clients, volunteers, employees, and donors. This project encompasses ER design, relational schema conversion, storage structuring, SQL implementation, and a Java application to perform database operations.

#### Database Entities
- **People**: Clients, volunteers, employees, and donors with fields such as name, SSN, gender, profession, contact information, and mailing list subscription.
- **Clients**: Linked to doctors, assigned needs, insurance policies, and emergency contacts.
- **Teams**: Manage clients through volunteer work, team leadership, and report to employees.
- **Employees**: Track salary, marital status, hire dates, expenses, and team management duties.
- **Donors**: Linked to donations and can choose to remain anonymous.

#### Common Queries
1. Enter new entries (teams, clients, volunteers, employees, donors) and associate them with relevant entities.
2. Retrieve critical information (e.g., doctor contact, total expenses, volunteers per team).
3. Complex operations (e.g., salary increase, delete clients without health insurance).

### Project Tasks

#### Task 1 (75 points): ER Diagram Design
Design an **ER diagram** representing the PAN database structure based on the requirements.

#### Task 2 (30 Points): Relational Schema Conversion
Convert the ER diagram to **relational schemas**, including entity and relationship attributes.

#### Task 3 (40 Points): Storage Structure Selection
1. For each relational table, select appropriate storage structures based on query access patterns, search types, keys, and frequency.
2. Specify storage structures using **Azure SQL Database** options, justifying differences from previous selections.

#### Task 4 (25 Points): SQL Table Creation on Azure SQL Database
Write **SQL CREATE statements** to implement tables in Azure SQL Database, ensuring all constraints and selected storage structures are applied.

#### Task 5 & Task 6 (130 Points): Java Application with JDBC Integration
1. Implement SQL queries (1-15) and Java JDBC to perform operations defined in part I.
2. Implement additional options for data import/export and a quit functionality, allowing continuous operation until the quit option is selected.
3. Ensure the application is **well-documented and commented**.

### Queries for Database Operations

1. Add new teams, clients, volunteers, employees, and donors to the database.
2. Retrieve key information for PAN operations (e.g., doctor contacts, team membership, and volunteer hours).
3. Implement sorting, deletion, and conditional operations based on PAN's needs.
4. Increase salaries for employees with multiple reporting teams.
5. Delete clients without health insurance and with low priority needs.

### Implementation Guidelines
- **File Organization**: Select storage structures (indexed, heap, clustered) for each table, detailing search keys and access patterns.
- **SQL Indexing**: Include indexes where necessary, especially for frequent queries.

### Additional Resources
- **Azure SQL Database**: Find documentation relevant to storage choices and table indexing options.
- **Java JDBC**: Use JDBC for database connectivity, ensuring robust error handling and data consistency.

### Academic Integrity
This project must be completed individually with no external assistance. Violations will be treated as academic misconduct.
