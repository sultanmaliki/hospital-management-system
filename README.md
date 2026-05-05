# Hospital Management System

A Java console application for managing basic hospital records: patients, doctors, and appointments. The project uses Maven, JDBC, MySQL, and a DAO (Data Access Object) layer to separate database operations from the console UI.

**Repository**: [hospital-management-system](https://github.com/sultanmaliki/hospital-management-system)  
**Version**: 0.0.1-SNAPSHOT

---

## Table of Contents

1. [Project Overview](#project-overview)
2. [Features](#features)
3. [Prerequisites](#prerequisites)
4. [Quick Start Guide](#quick-start-guide)
5. [Tech Stack](#tech-stack)
6. [Project Structure](#project-structure)
7. [Module Explanation](#module-explanation)
8. [Database Schema](#database-schema)
9. [System Architecture](#system-architecture)
10. [Security and Validation Notes](#security-and-validation-notes)
11. [Setup Instructions](#setup-instructions)
12. [Configuration](#configuration)
13. [Building and Running](#building-and-running)
14. [Testing](#testing)
15. [Sample Usage](#sample-usage)
16. [DAO Reference](#dao-reference)
17. [Troubleshooting](#troubleshooting)
18. [FAQ](#faq)
19. [Version History](#version-history)
20. [Contributing](#contributing)
21. [Future Improvements](#future-improvements)
22. [Author](#author)
23. [License](#license)

---

## Project Overview

Hospital Management System is a menu-driven Java application that stores and retrieves hospital data from a MySQL database.

The application currently supports:

- Patient records with ID, name, age, disease, and bill amount
- Doctor records with ID, name, and speciality
- Appointment records that link patients and doctors by date and time

The codebase is intentionally simple and suitable for learning JDBC, Maven, DAO interfaces, and basic console application structure.

---

## Features

### Patient Module

- Add a patient
- View all patients
- Update a patient
- Delete a patient by ID
- Validate patient input in the console flow, including positive ID, age range, non-empty name, and non-negative bill amount

### Doctor Module

- Add a doctor
- View all doctors
- Update a doctor
- Delete a doctor by ID
- Validate positive ID, non-empty name, and non-empty speciality in the console flow

### Appointment Module

- Book an appointment
- View all appointments
- View appointments by patient ID
- View appointments by doctor ID
- Update an appointment
- Cancel an appointment by ID
- Validate positive IDs and Java SQL date/time input formats

### Code Organization

- DAO interfaces for patient, doctor, and appointment operations
- DAO implementations using JDBC `PreparedStatement`
- Model classes for database entities
- Configuration loader for database connection settings
- Console entry point in `MainApp`

---

## Prerequisites

- Java Development Kit (JDK) 8 or later
- Apache Maven
- MySQL Server
- Git, if cloning from GitHub

Verify the main tools:

```bash
java -version
javac -version
mvn -version
mysql --version
```

---

## Quick Start Guide

### 1. Clone the Repository

```bash
git clone https://github.com/sultanmaliki/hospital-management-system
cd hospital-management-system
```

### 2. Create the MySQL Database

```bash
mysql -u root -p
```

```sql
CREATE DATABASE hospital;
USE hospital;

CREATE TABLE patient (
    p_id INT PRIMARY KEY,
    p_name VARCHAR(100) NOT NULL,
    p_age INT NOT NULL,
    p_disease VARCHAR(200),
    bill DOUBLE NOT NULL
);

CREATE TABLE doctor (
    d_id INT PRIMARY KEY,
    d_name VARCHAR(100) NOT NULL,
    d_speciality VARCHAR(100) NOT NULL
);

CREATE TABLE appointment (
    appointment_id INT PRIMARY KEY,
    p_id INT NOT NULL,
    d_id INT NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL,
    FOREIGN KEY (p_id) REFERENCES patient(p_id),
    FOREIGN KEY (d_id) REFERENCES doctor(d_id)
);
```

### 3. Configure Database Credentials

Windows Command Prompt:

```cmd
set DB_URL=jdbc:mysql://localhost:3306/hospital
set DB_USERNAME=root
set DB_PASSWORD=your_password
set DB_DRIVER=com.mysql.cj.jdbc.Driver
```

PowerShell:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/hospital"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_password"
$env:DB_DRIVER="com.mysql.cj.jdbc.Driver"
```

Linux/macOS:

```bash
export DB_URL="jdbc:mysql://localhost:3306/hospital"
export DB_USERNAME="root"
export DB_PASSWORD="your_password"
export DB_DRIVER="com.mysql.cj.jdbc.Driver"
```

### 4. Build and Run

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.hms.main.MainApp"
```

---

## Tech Stack

| Component | Used In This Project |
| --- | --- |
| Java | Source and target level 1.8 |
| Maven | Build and dependency management |
| MySQL | Relational database |
| MySQL Connector/J | JDBC driver, version 8.4.0 |
| JDBC | Database access |
| Java Util Logging | Application logging |

---

## Project Structure

```text
hospital-management-system/
|-- pom.xml
|-- README.md
|-- LICENSE
|-- .gitignore
|-- application-example.properties
`-- src/
    `-- main/
        `-- java/
            `-- com/
                `-- hms/
                    |-- config/
                    |   `-- ConfigLoader.java
                    |-- connection/
                    |   `-- DBConnection.java
                    |-- dao/
                    |   |-- AppointmentDAO.java
                    |   |-- DoctorDAO.java
                    |   |-- PatientDAO.java
                    |   `-- impl/
                    |       |-- AppointmentDAOImpl.java
                    |       |-- DoctorDAOImpl.java
                    |       `-- PatientDAOImpl.java
                    |-- main/
                    |   `-- MainApp.java
                    |-- model/
                    |   |-- Appointment.java
                    |   |-- Doctor.java
                    |   `-- Patient.java
                    `-- service/
                        `-- PatientService.java
```

`target/` is generated by Maven and is not part of the source structure.

---

## Module Explanation

### Configuration Module: `com.hms.config`

`ConfigLoader.java` loads database connection settings from environment variables or an `application.properties` file available on the classpath.

Configuration lookup order:

1. Environment variables
2. `application.properties`
3. Default database URL and JDBC driver values

Supported keys:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `DB_DRIVER`

### Database Connection Module: `com.hms.connection`

`DBConnection.java` manages JDBC connection creation through `DriverManager`.

Main methods:

- `connect()`: loads the JDBC driver and opens a database connection
- `disconnect(Connection)`: closes a supplied connection
- `getConnection()`: returns the current connection, creating one if needed

### Model Layer: `com.hms.model`

`Patient.java`

- Fields: `p_id`, `p_name`, `p_age`, `p_disease`, `bill`
- Includes default and parameterized constructors
- Includes getters and setters

`Doctor.java`

- Fields: `d_id`, `d_name`, `d_speciality`
- Includes default and parameterized constructors
- Includes getters, setters, and `toString()`

`Appointment.java`

- Fields: `appointment_id`, `p_id`, `d_id`, `date`, `time`
- Uses `java.sql.Date` and `java.sql.Time`
- Includes default and parameterized constructors
- Includes getters, setters, and `toString()`

### DAO Layer: `com.hms.dao` and `com.hms.dao.impl`

The DAO layer defines database operations behind interfaces and implements them with JDBC.

`PatientDAO`

- `addPatient(Patient p)`
- `getAllPatients()`
- `getPatientById(int id)`
- `updatePatient(Patient p)`
- `deletePatient(int id)`

`DoctorDAO`

- `addDoctor(Doctor d)`
- `getAllDoctors()`
- `getDoctorById(int id)`
- `updateDoctor(Doctor d)`
- `deleteDoctor(int id)`

`AppointmentDAO`

- `bookAppointment(Appointment a)`
- `getAllAppointments()`
- `getAppointmentsByPatient(int p_id)`
- `getAppointmentsByDoctor(int d_id)`
- `updateAppointment(Appointment a)`
- `cancelAppointment(int appointment_id)`

DAO implementation notes:

- SQL statements use `PreparedStatement`
- `PreparedStatement` and `ResultSet` objects are handled with try-with-resources
- DAO methods return `1` or another positive update count for successful writes, and `0` when no row is changed or validation fails
- Query methods return model objects or lists of model objects
- Invalid IDs and null model objects are rejected before SQL execution

### Service Layer: `com.hms.service`

`PatientService.java` currently exists as an empty placeholder. Business logic is handled directly by `MainApp` and the DAO layer in the current codebase.

### Main Application: `com.hms.main`

`MainApp.java` provides the console menus for patient, doctor, and appointment operations. It reads user input with `Scanner`, validates common fields, creates model objects, and calls DAO implementation methods.

---

## Database Schema

### Database: `hospital`

### Table: `patient`

```sql
CREATE TABLE patient (
    p_id INT PRIMARY KEY,
    p_name VARCHAR(100) NOT NULL,
    p_age INT NOT NULL,
    p_disease VARCHAR(200),
    bill DOUBLE NOT NULL
);
```

### Table: `doctor`

```sql
CREATE TABLE doctor (
    d_id INT PRIMARY KEY,
    d_name VARCHAR(100) NOT NULL,
    d_speciality VARCHAR(100) NOT NULL
);
```

### Table: `appointment`

```sql
CREATE TABLE appointment (
    appointment_id INT PRIMARY KEY,
    p_id INT NOT NULL,
    d_id INT NOT NULL,
    date DATE NOT NULL,
    time TIME NOT NULL,
    FOREIGN KEY (p_id) REFERENCES patient(p_id),
    FOREIGN KEY (d_id) REFERENCES doctor(d_id)
);
```

---

## System Architecture

```text
Console User
    |
    v
MainApp
    |
    v
DAO Interfaces
    |
    v
DAO Implementations
    |
    v
DBConnection + ConfigLoader
    |
    v
MySQL database: hospital
```

Flow:

1. The user selects an operation from the console menu.
2. `MainApp` reads and validates input.
3. `MainApp` creates or requests model objects.
4. DAO implementations execute SQL through JDBC.
5. Results are returned to `MainApp` and printed in the console.

---

## Security and Validation Notes

- Database credentials are not hardcoded in Java source files.
- Credentials can be provided through environment variables or a classpath `application.properties` file.
- The repository includes `application-example.properties` as a template.
- `.gitignore` excludes `application.properties` and common environment files.
- SQL operations use `PreparedStatement`.
- The console layer validates common numeric, date, time, and required text inputs.
- DAO methods validate positive IDs and null model objects before executing SQL.
- The code uses `java.util.logging` for database and application errors.

This is still a small console application. It does not include authentication, authorization, connection pooling, automated tests, or a web/API layer.

---

## Setup Instructions

### Step 1: Install Required Software

Install:

- JDK 8 or later
- Maven
- MySQL Server

### Step 2: Clone the Project

```bash
git clone https://github.com/sultanmaliki/hospital-management-system
cd hospital-management-system
```

### Step 3: Create the Database

Log in to MySQL:

```bash
mysql -u root -p
```

Run the SQL from the [Database Schema](#database-schema) section.

### Step 4: Configure the Database Connection

Use environment variables, or create a classpath `application.properties` file.

Environment variables are the simplest option for local runs:

```bash
export DB_URL="jdbc:mysql://localhost:3306/hospital"
export DB_USERNAME="root"
export DB_PASSWORD="your_password"
export DB_DRIVER="com.mysql.cj.jdbc.Driver"
```

For file-based configuration, create `src/main/resources/application.properties`:

```properties
DB_URL=jdbc:mysql://localhost:3306/hospital
DB_USERNAME=root
DB_PASSWORD=your_password
DB_DRIVER=com.mysql.cj.jdbc.Driver
```

You can use `application-example.properties` as a template. Do not commit real database credentials.

### Step 5: Build the Project

```bash
mvn clean compile
```

### Step 6: Run the Application

```bash
mvn exec:java -Dexec.mainClass="com.hms.main.MainApp"
```

---

## Configuration

`ConfigLoader` checks configuration in this order:

| Priority | Source | Notes |
| --- | --- | --- |
| 1 | Environment variables | Recommended for local terminal runs |
| 2 | `application.properties` | Must be available on the runtime classpath |
| 3 | Defaults | Default URL is `jdbc:mysql://localhost:3306/hospital`; default driver is `com.mysql.cj.jdbc.Driver` |

Environment variable names:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
DB_DRIVER
```

The application does not provide default database username or password values.

---

## Building and Running

Compile:

```bash
mvn clean compile
```

Run:

```bash
mvn exec:java -Dexec.mainClass="com.hms.main.MainApp"
```

Package:

```bash
mvn clean package
```

Main menu:

```text
===== HOSPITAL MANAGEMENT SYSTEM =====
1. Patient Module
2. Doctor Module
3. Appointment Module
4. Exit
Enter choice:
```

---

## Testing

There are no automated test classes in the current project.

Manual checks:

1. Start MySQL and create the `hospital` database.
2. Set database credentials.
3. Run `mvn clean compile`.
4. Start the application with `mvn exec:java -Dexec.mainClass="com.hms.main.MainApp"`.
5. Add a patient and doctor.
6. Book an appointment using the created patient and doctor IDs.
7. View, update, and delete records from each module.

When automated tests are added, run:

```bash
mvn test
```

---

## Sample Usage

### Add a Patient

```text
===== HOSPITAL MANAGEMENT SYSTEM =====
1. Patient Module
2. Doctor Module
3. Appointment Module
4. Exit
Enter choice: 1

--- PATIENT MODULE ---
1. Add Patient
2. View All Patients
3. Update Patient
4. Delete Patient
5. Back to Main Menu
Enter choice: 1

Patient ID: 101
Name: John Doe
Age: 35
Disease: Diabetes
Bill Amount: 5000.00
Patient added successfully
```

### Add a Doctor

```text
===== HOSPITAL MANAGEMENT SYSTEM =====
1. Patient Module
2. Doctor Module
3. Appointment Module
4. Exit
Enter choice: 2

--- DOCTOR MODULE ---
1. Add Doctor
2. View All Doctors
3. Update Doctor
4. Delete Doctor
5. Back to Main Menu
Enter choice: 1

Doctor ID: 201
Name: Dr. Sarah Johnson
Speciality: Cardiology
Doctor added successfully
```

### Book an Appointment

```text
===== HOSPITAL MANAGEMENT SYSTEM =====
1. Patient Module
2. Doctor Module
3. Appointment Module
4. Exit
Enter choice: 3

--- APPOINTMENT MODULE ---
1. Book Appointment
2. View All Appointments
3. View Patient Appointments
4. View Doctor Appointments
5. Update Appointment
6. Cancel Appointment
7. Back to Main Menu
Enter choice: 1

Appointment ID: 301
Patient ID: 101
Doctor ID: 201
Date (yyyy-mm-dd): 2026-05-15
Time (hh:mm:ss): 10:30:00
Appointment booked successfully
```

### View Appointments

```text
Enter choice: 3
Enter choice: 2

301 | Patient: 101 | Doctor: 201 | 2026-05-15 10:30:00
```

---

## DAO Reference

### `PatientDAO`

| Method | Purpose |
| --- | --- |
| `int addPatient(Patient p)` | Insert a patient |
| `List<Patient> getAllPatients()` | Fetch all patients |
| `Patient getPatientById(int id)` | Fetch one patient by ID |
| `int updatePatient(Patient p)` | Update a patient |
| `int deletePatient(int id)` | Delete a patient |

### `DoctorDAO`

| Method | Purpose |
| --- | --- |
| `int addDoctor(Doctor d)` | Insert a doctor |
| `List<Doctor> getAllDoctors()` | Fetch all doctors |
| `Doctor getDoctorById(int id)` | Fetch one doctor by ID |
| `int updateDoctor(Doctor d)` | Update a doctor |
| `int deleteDoctor(int id)` | Delete a doctor |

### `AppointmentDAO`

| Method | Purpose |
| --- | --- |
| `int bookAppointment(Appointment a)` | Insert an appointment |
| `List<Appointment> getAllAppointments()` | Fetch all appointments |
| `List<Appointment> getAppointmentsByPatient(int p_id)` | Fetch appointments for a patient |
| `List<Appointment> getAppointmentsByDoctor(int d_id)` | Fetch appointments for a doctor |
| `int updateAppointment(Appointment a)` | Update an appointment |
| `int cancelAppointment(int appointment_id)` | Delete an appointment |

---

## Troubleshooting

### `Connection refused: localhost:3306`

- Confirm MySQL is installed and running.
- Confirm the database URL is `jdbc:mysql://localhost:3306/hospital`.
- Confirm MySQL is listening on port `3306`.

### `Access denied for user`

- Check `DB_USERNAME`.
- Check `DB_PASSWORD`.
- Confirm the MySQL user has access to the `hospital` database.

### `Unknown database 'hospital'`

- Create the database with `CREATE DATABASE hospital;`.
- Run the table creation SQL from the [Database Schema](#database-schema) section.

### `ClassNotFoundException: com.mysql.cj.jdbc.Driver`

- Run `mvn clean compile` to download dependencies and compile the project.
- Confirm `mysql-connector-j` exists in `pom.xml`.

### Invalid date or time input

- Date format must be `yyyy-mm-dd`, for example `2026-05-15`.
- Time format must be `hh:mm:ss`, for example `10:30:00`.

---

## FAQ

### Is this suitable for live hospital operations?

No. It is a small console-based Java project with basic validation and JDBC database access. It does not include user accounts, roles, API endpoints, connection pooling, migrations, automated tests, or deployment configuration.

### Can I use Java 11 or newer?

The project is configured for Java 8 source and target compatibility. It should compile on newer JDKs that support Java 8 compatibility.

### Where should database credentials go?

Use environment variables for local runs, or create `src/main/resources/application.properties` for classpath-based configuration. Do not commit real credentials.

### Does the project include automated tests?

No. The current repository does not include test classes.

---

## Version History

### 0.0.1-SNAPSHOT

Current snapshot version.

Included functionality:

- Console menus for patient, doctor, and appointment management
- JDBC access to MySQL
- DAO interfaces and implementations
- Environment/property-based database configuration
- Basic input validation and logging

---

## Contributing

1. Fork or clone the repository:

   ```bash
   git clone https://github.com/sultanmaliki/hospital-management-system
   cd hospital-management-system
   ```

2. Create a branch:

   ```bash
   git checkout -b feature/your-feature-name
   ```

3. Make changes and test them locally.

4. Commit with a clear message:

   ```bash
   git commit -m "Describe your change"
   ```

5. Push your branch and open a pull request on GitHub:

   ```bash
   git push origin feature/your-feature-name
   ```

Repository URL: [https://github.com/sultanmaliki/hospital-management-system](https://github.com/sultanmaliki/hospital-management-system)

---

## Future Improvements

- Add automated unit and integration tests
- Implement the service layer for business logic
- Add connection pooling
- Add database migration scripts
- Improve console display formatting
- Add a GUI or web/API layer if the project grows beyond a console application

---

## Author

**Name**: Syed Mohammad Sultan  
**Email**: ssultanmaliki47@gmail.com

---

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

---

**Last Updated**: May 2026
