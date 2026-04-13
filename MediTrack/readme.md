


<h1>Project Overview</h1>
Medication/adherence tracker intended to help users follow a daily medication schedule and maintain a record of doses taken. Addresses the problem of missing medication doses, taking more medication by accident, or forgetting which medications have been taken already if there are multiple kinds. 
The application should be usable locally as a single-user system. Reminders will be in-application and on a visible dashboard. As a stretch goal, system notifications/emails could be implemented as well. Data will be stored locally initially, with a stretch goal of optional website/cloud storage? Include safety disclosure that the app does not provide medical advice, only for informational use to aid users track a plan from their doctor.
The success of the project will be judged on: can the application be set up easily by the user, do they receive timely reminders of when to take their medication, are the reminders easy to read and clear for the user, are medication doses logged with minimal interruption.

<h1>This application is intended for: </h1>
People who take prescription medication daily
People who take multiple medications and want to avoid confusion
People who take vitamins or supplements regularly
Older adults or busy users who often forget doses

## How to Run

### Prerequisites
- Java 25 installed (or compatible Java version configured for this project)
- No separate Maven install required (uses Maven Wrapper)

### Start the app (Windows / PowerShell

go to your working directory (example: cd "c:\Users\yourname\Desktop\MediTrack")

>.\mvnw spring-boot:run

Navigate to your browser and enter in the URL:
>(http://localhost:8080/)

The medication tracker program will load into the browser and function as intended.


## Server Directories
> Dashboard: http://localhost:8080/

> Add medication: http://localhost:8080/medications/new

> H2 console (optional): http://localhost:8080/h2-console


## Viewing Data in H2 Console

Open: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:file:./data/meditrack


## Reset / Clear Database
1. Stop the app (Ctrl+C)

### 2. Delete the DB files:
>>Remove-Item .\data\meditrack* -Force  
>>Restart the app

### 3. Clear only dose logs (keep medications + schedule times)   
>In the H2 console, run:
>>DELETE FROM DOSE_INSTANCE;   
>>Reload the dashboard to regenerate today’s schedule.

## Current Features (Layer 1)

### Medication setup
- Add a medication with:
  - Name (required)
  - Dosage (optional)
  - Instructions (optional)
  - One or more scheduled times (comma-separated `HH:MM`, e.g. `08:00,20:00`)
- Stored in a local database.

### Automatic daily schedule generation
- When the dashboard loads (`GET /`), the app generates **DoseInstance** rows for today from saved medication times.
- Prevents duplicates using a unique constraint on (medication, date, time).

### Dose logging (adherence)
- Each scheduled dose can be marked as:
  - `TAKEN`
  - `SKIPPED`
- Changes persist after refresh and restart.

### Local persistence
- Uses **H2 in file mode**, so data is saved locally between runs.
- Database runtime files are ignored by Git (see `.gitignore`).

### Basic UI
- Dashboard table: time, medication, status, log buttons
- Add medication form page
- Simple CSS styling served as a static resource

---

## Tech Stack
- Java 25
- Spring Boot 4.0.3
- Spring Web MVC
- Spring Data JPA (Hibernate)
- Thymeleaf
- H2 Database (file mode)
- Maven Wrapper (`mvnw.cmd`)

---

