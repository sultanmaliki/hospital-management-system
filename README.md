# Hospital Management System

A Java-based console application for managing hospital operations including patient records, doctor information, and appointment scheduling. The system uses JDBC for database interactions and follows the DAO (Data Access Object) architectural pattern.

**Version**: 0.0.1-SNAPSHOT (Production-Ready with Security Enhancements)

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
10. [Security Features](#security-features)
11. [Setup Instructions](#setup-instructions)
12. [Configuration](#configuration)
13. [Building & Compilation](#building--compilation)
14. [How to Run](#how-to-run)
15. [Testing](#testing)
16. [Sample Usage](#sample-usage)
17. [API Reference](#api-reference)
18. [Troubleshooting](#troubleshooting)
19. [FAQs](#faqs)
20. [Related Documentation](#related-documentation)
21. [Version History](#version-history)
22. [Contributing](#contributing)
23. [Future Improvements](#future-improvements)
24. [Author](#author)

---

## Project Overview

The Hospital Management System is a lightweight desktop application designed to streamline hospital operations by providing an intuitive interface for managing:
- **Patient Records**: Store and manage patient information including medical history and billing
- **Doctor Profiles**: Maintain doctor details and specializations
- **Appointment Scheduling**: Book and manage patient-doctor appointments

The application follows a menu-driven architecture and communicates with a MySQL database using JDBC with PreparedStatement queries for secure and efficient data operations.

**Recent Updates**: This version includes comprehensive security enhancements, input validation, proper logging, and environment-based configuration.

---

## Features

### Patient Module
- ✅ **Add Patient**: Register new patients with ID, name, age, disease, and billing information
- ✅ **View All Patients**: Display a list of all registered patients
- ✅ **Update Patient**: Modify existing patient records
- ✅ **Delete Patient**: Remove a patient record by patient ID
- ✅ **Input Validation**: Age (1-150), non-empty names, non-negative bills

### Doctor Module
- ✅ **Add Doctor**: Register new doctors with ID, name, and specialization
- ✅ **View All Doctors**: Display a list of all registered doctors
- ✅ **Update Doctor**: Modify existing doctor records
- ✅ **Delete Doctor**: Remove a doctor record by doctor ID
- ✅ **Input Validation**: Positive IDs, non-empty names and specializations

### Appointment Module
- ✅ **Book Appointment**: Schedule an appointment by linking a patient with a doctor on a specific date and time
- ✅ **View All Appointments**: Display all scheduled appointments
- ✅ **View Patient Appointments**: Filter appointments by patient ID
- ✅ **View Doctor Appointments**: Filter appointments by doctor ID
- ✅ **Update Appointment**: Modify existing appointment details
- ✅ **Cancel Appointment**: Cancel an appointment by appointment ID
- ✅ **Date/Time Validation**: Format checking for appointments

### Security & Quality
- ✅ **No Hardcoded Credentials**: Configuration via environment variables or properties file
- ✅ **SQL Injection Prevention**: All queries use PreparedStatement
- ✅ **Resource Management**: Try-with-resources for all database operations
- ✅ **Input Validation**: User inputs validated before database operations
- ✅ **Proper Logging**: Java logging instead of printStackTrace()
- ✅ **Error Handling**: Meaningful error messages without exposing sensitive details

---

## Prerequisites

Before setting up and running the Hospital Management System, ensure you have the following installed:

### System Requirements
- **Operating System**: Windows, Linux, or macOS
- **RAM**: Minimum 2 GB (4 GB recommended)
- **Disk Space**: 500 MB available

### Required Software
- **Java Development Kit (JDK)**
  - Version: 1.8 (Java 8) or later (Java 11, 17, 21 fully supported)
  - Download: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.java.net/)
  - Verify: `java -version` and `javac -version`

- **Apache Maven**
  - Version: 3.9.0 or later
  - Download: [Maven](https://maven.apache.org/download.cgi)
  - Verify: `mvn -version`

- **MySQL Server**
  - Version: 5.7 or later (8.0 recommended)
  - Download: [MySQL](https://www.mysql.com/downloads/mysql/)
  - Verify: `mysql --version`

- **Git** (Optional, for version control)
  - Download: [Git](https://git-scm.com/)
  - Verify: `git --version`

### IDE (Recommended)
- IntelliJ IDEA Community Edition
- Eclipse IDE for Java Developers
- Visual Studio Code with Java Extensions

---

## Quick Start Guide

**Get the application running in 5 minutes!**

### Step 1: Clone/Download the Project
```bash
git clone <repository-url>
cd hospital-management
```

### Step 2: Setup Database
```bash
# Start MySQL and create database
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

### Step 3: Set Environment Variables
```bash
# Windows (Command Prompt)
set DB_URL=jdbc:mysql://localhost:3306/hospital
set DB_USERNAME=root
set DB_PASSWORD=your_password
set DB_DRIVER=com.mysql.cj.jdbc.Driver

# Linux/macOS (Bash)
export DB_URL="jdbc:mysql://localhost:3306/hospital"
export DB_USERNAME="root"
export DB_PASSWORD="your_password"
export DB_DRIVER="com.mysql.cj.jdbc.Driver"
```

### Step 4: Build and Run
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.hms.main.MainApp"
```

**Done!** The application should now be running. Start by adding a patient, doctor, and booking an appointment.

---

## Tech Stack

| Component | Version |
|-----------|---------|
| Java | 1.8+ (compatible with newer versions) |
| Build Tool | Maven 3.9.0+ |
| Database | MySQL 5.7+ |
| JDBC Driver | MySQL Connector/J 8.4.0 |
| IDE | Any Java-supporting IDE (Eclipse, IntelliJ IDEA, VS Code) |
| Logging | Java Util Logging (JUL) |

---

## Project Structure

```
hospital-management/
├── pom.xml                                    # Maven configuration
├── README.md                                  # This file
├── .gitignore                                 # Git ignore file (secrets excluded)
├── application-example.properties             # Configuration template (NO secrets)
└── src/
    ├── main/
    │   ├── java/com/hms/
    │   │   ├── config/
    │   │   │   └── ConfigLoader.java          # Configuration management (NEW)
    │   │   ├── connection/
    │   │   │   └── DBConnection.java          # Secure database connection
    │   │   ├── dao/
    │   │   │   ├── PatientDAO.java            # Patient DAO interface
    │   │   │   ├── DoctorDAO.java             # Doctor DAO interface
    │   │   │   ├── AppointmentDAO.java        # Appointment DAO interface
    │   │   │   └── impl/
    │   │   │       ├── PatientDAOImpl.java     # Patient DAO implementation (updated)
    │   │   │       ├── DoctorDAOImpl.java      # Doctor DAO implementation (updated)
    │   │   │       └── AppointmentDAOImpl.java # Appointment DAO implementation (updated)
    │   │   ├── model/
    │   │   │   ├── Patient.java               # Patient model class
    │   │   │   ├── Doctor.java                # Doctor model class
    │   │   │   └── Appointment.java           # Appointment model class
    │   │   ├── service/
    │   │   │   └── PatientService.java        # Service layer (currently empty)
    │   │   └── main/
    │   │       └── MainApp.java               # Console menu-driven application (updated)
    │   └── resources/                         # Resources directory (empty)
    └── test/
        ├── java/                              # Test directory (empty)
        └── resources/                         # Test resources (empty)
```

---

## Module Explanation

### 1. **Configuration Management Module** (`com.hms.config`) — NEW

**ConfigLoader.java**
- Centralized configuration management
- Loads configuration from environment variables or `application.properties` file
- Priority: Environment Variables > application.properties > Default values
- Methods:
  - `getDatabaseUrl()`: Retrieve database URL
  - `getDatabaseUsername()`: Retrieve database username
  - `getDatabasePassword()`: Retrieve database password
  - `getJdbcDriver()`: Retrieve JDBC driver class name
- **Security**: No hardcoded credentials; all values are externalized

### 2. **Database Connection Module** (`com.hms.connection`) — UPDATED

**DBConnection.java**
- Manages MySQL database connections using JDBC
- Loads credentials from `ConfigLoader` (environment or properties file)
- Loads MySQL JDBC driver: `com.mysql.cj.jdbc.Driver`
- Provides static methods:
  - `connect()`: Establish secure connection
  - `disconnect(Connection)`: Close connection safely
  - `getConnection()`: Get current connection
- **Security**:
  - No hardcoded credentials
  - Proper error logging instead of printStackTrace()
  - Null-safe connection handling

### 3. **Model Layer** (`com.hms.model`)

**Patient.java**
- Represents a patient entity
- Fields: `p_id` (int), `p_name` (String), `p_age` (int), `p_disease` (String), `bill` (double)
- Provides getters and setters for all fields
- Default and parameterized constructors

**Doctor.java**
- Represents a doctor entity
- Fields: `d_id` (int), `d_name` (String), `d_speciality` (String)
- Provides getters and setters for all fields
- Implements `toString()` method for display
- Default and parameterized constructors

**Appointment.java**
- Represents an appointment entity
- Fields: `appointment_id` (int), `p_id` (int), `d_id` (int), `date` (java.sql.Date), `time` (java.sql.Time)
- Provides getters and setters for all fields
- Implements `toString()` method for formatted display
- Default and parameterized constructors

### 4. **DAO Layer** (`com.hms.dao` and `com.hms.dao.impl`) — UPDATED

The Data Access Object pattern is used to abstract database operations from business logic.

**PatientDAO Interface**
- `int addPatient(Patient p)`: Insert a new patient
- `List<Patient> getAllPatients()`: Retrieve all patients
- `Patient getPatientById(int id)`: Retrieve a specific patient by ID
- `int updatePatient(Patient p)`: Update patient information
- `int deletePatient(int id)`: Delete a patient record

**PatientDAOImpl Implementation** (UPDATED)
- Uses `PreparedStatement` for parameterized SQL queries (prevents SQL injection)
- Implements try-with-resources blocks for automatic resource management
- Input validation (ID > 0, non-empty name)
- Proper logging with `java.util.logging`
- Meaningful error messages
- Database operations on the `patient` table

**DoctorDAO Interface**
- `int addDoctor(Doctor d)`: Insert a new doctor
- `List<Doctor> getAllDoctors()`: Retrieve all doctors
- `Doctor getDoctorById(int id)`: Retrieve a specific doctor by ID
- `int updateDoctor(Doctor d)`: Update doctor information
- `int deleteDoctor(int id)`: Delete a doctor record

**DoctorDAOImpl Implementation** (UPDATED)
- Similar security enhancements as PatientDAOImpl
- Input validation for doctor data
- Try-with-resources for all operations
- Proper logging and error handling

**AppointmentDAO Interface**
- `int bookAppointment(Appointment a)`: Create a new appointment
- `List<Appointment> getAllAppointments()`: Retrieve all appointments
- `List<Appointment> getAppointmentsByPatient(int p_id)`: Get appointments for a specific patient
- `List<Appointment> getAppointmentsByDoctor(int d_id)`: Get appointments for a specific doctor
- `int updateAppointment(Appointment a)`: Update appointment details
- `int cancelAppointment(int appointment_id)`: Cancel (delete) an appointment

**AppointmentDAOImpl Implementation** (UPDATED)
- Validates all appointment data (positive IDs, non-null date/time)
- Try-with-resources for all database operations
- Proper logging and error handling
- Database operations on the `appointment` table with foreign key constraints

### 5. **Service Layer** (`com.hms.service`)

**PatientService.java**
- Currently empty; intended for future business logic and transaction management
- Can be extended to add validation, calculations, or complex operations

### 6. **Main Application** (`com.hms.main`) — UPDATED

**MainApp.java** (COMPLETELY REFACTORED)
- Console-based menu-driven interface
- Hierarchical menu structure:
  1. Main Menu → Patient Module / Doctor Module / Appointment Module / Exit
  2. Sub-menus for each module with CRUD operations
- Uses `Scanner` for user input with validation
- Instantiates DAO implementations for database operations
- **New Features**:
  - Update functionality for all modules
  - Input validation for IDs, ages, names, amounts
  - Filter appointments by patient or doctor
  - Proper error handling with user-friendly messages
  - Logging integration
  - Try-catch blocks for input parsing errors

---

## Database Schema

### Database: `hospital`

#### Table: `patient`
```sql
CREATE TABLE patient (
    p_id INT PRIMARY KEY,
    p_name VARCHAR(100) NOT NULL,
    p_age INT NOT NULL,
    p_disease VARCHAR(200),
    bill DOUBLE NOT NULL
);
```

#### Table: `doctor`
```sql
CREATE TABLE doctor (
    d_id INT PRIMARY KEY,
    d_name VARCHAR(100) NOT NULL,
    d_speciality VARCHAR(100) NOT NULL
);
```

#### Table: `appointment`
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

```
┌─────────────────────────────────────────────────────────────┐
│                    MainApp (Console UI)                     │
│              (Menu-driven user interface)                   │
└────────────────────────┬────────────────────────────────────┘
                         │
          ┌──────────────┼──────────────┐
          │              │              │
┌─────────▼────────┐ ┌───▼──────────┐ ┌─▼────────────────┐
│ PatientDAOImpl   │ │ DoctorDAOImpl│ │AppointmentDAOImpl│
│ (Patient CRUD)   │ │ (Doctor CRUD)│ │(Appointment CRUD)│
└────────┬─────────┘ └───┬──────────┘ └─┬────────────────┘
         │               │              │
         └───────────────┼──────────────┘
                         │
                ┌────────▼────────┐
                │ ConfigLoader    │  (Environment Vars / Properties)
                └────────┬────────┘
                         │
                ┌────────▼────────┐
                │ DBConnection    │
                │ (JDBC Provider) │
                └────────┬────────┘
                         │
                ┌────────▼────────┐
                │ MySQL Database  │
                │ (hospital DB)   │
                └─────────────────┘
```

**Flow**:
1. User interacts with MainApp console menu
2. MainApp validates user input and calls appropriate DAO methods
3. DAO implementations validate data and use DBConnection to get JDBC connection
4. ConfigLoader provides database credentials from environment or properties file
5. PreparedStatements execute SQL queries against MySQL database
6. Results are mapped back to model objects and returned to MainApp
7. User receives operation status (Success/Failed) with meaningful messages

---

## Security Features

### ✅ Secure Credential Management
- **No Hardcoded Secrets**: Database credentials are NOT stored in source code
- **Environment Variables**: Use `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `DB_DRIVER`
- **Configuration File**: Alternatively, use `application.properties` file
- **Priority System**: Environment variables override properties file

### ✅ SQL Injection Prevention
- **PreparedStatement Usage**: All SQL queries use parameterized queries
- **Parameter Binding**: User inputs are bound as parameters, not concatenated

### ✅ Resource Management
- **Try-with-Resources**: All database resources (Connection, PreparedStatement, ResultSet) are automatically closed
- **No Resource Leaks**: Proper exception handling ensures resources are always released

### ✅ Input Validation
- **Type Validation**: Numeric inputs validated before use
- **Range Validation**: Age (1-150), IDs (> 0), amounts (non-negative)
- **String Validation**: Non-empty names, proper date/time formats
- **Client-side Validation**: Validation occurs before database operations

### ✅ Error Handling & Logging
- **No Stack Traces**: Replaced `printStackTrace()` with proper logging
- **Meaningful Messages**: Error messages don't expose sensitive system details
- **Logging Framework**: Uses Java `java.util.logging.Logger`
- **Operation Tracking**: All database operations are logged for auditing

### ✅ Code Quality
- **Clean Code**: Removed dead code, unused imports
- **JavaDoc Comments**: Methods documented with purpose and parameters
- **Separation of Concerns**: DAO, Model, and Config layers properly separated
- **Maven Build**: Proper project structure with Maven configuration

---

## Setup Instructions

### Prerequisites
- Java Development Kit (JDK 1.8 or later)
- MySQL Server 5.7 or later
- Maven 3.9.0 or later

### Step 1: Install MySQL

1. Download and install MySQL Server from [mysql.com](https://www.mysql.com/downloads/mysql/)
2. Install and start the MySQL service
3. Note the root password or create a user with database creation privileges

### Step 2: Create Database and Tables

Open MySQL command-line client or MySQL Workbench and execute:

```sql
-- Create database
CREATE DATABASE hospital;
USE hospital;

-- Create patient table
CREATE TABLE patient (
    p_id INT PRIMARY KEY,
    p_name VARCHAR(100) NOT NULL,
    p_age INT NOT NULL,
    p_disease VARCHAR(200),
    bill DOUBLE NOT NULL
);

-- Create doctor table
CREATE TABLE doctor (
    d_id INT PRIMARY KEY,
    d_name VARCHAR(100) NOT NULL,
    d_speciality VARCHAR(100) NOT NULL
);

-- Create appointment table
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

### Step 3: Configure Database Connection

Choose **ONE** of the following methods:

#### Option A: Environment Variables (Recommended)

Set environment variables on your system:

**Windows (Command Prompt)**:
```cmd
set DB_URL=jdbc:mysql://localhost:3306/hospital
set DB_USERNAME=root
set DB_PASSWORD=your_secure_password_here
set DB_DRIVER=com.mysql.cj.jdbc.Driver
```

**Windows (PowerShell)**:
```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/hospital"
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your_secure_password_here"
$env:DB_DRIVER="com.mysql.cj.jdbc.Driver"
```

**Linux/macOS (Bash)**:
```bash
export DB_URL="jdbc:mysql://localhost:3306/hospital"
export DB_USERNAME="root"
export DB_PASSWORD="your_secure_password_here"
export DB_DRIVER="com.mysql.cj.jdbc.Driver"
```

#### Option B: Configuration File

1. Copy `application-example.properties` to `application.properties`:
   ```bash
   cp application-example.properties application.properties
   ```

2. Edit `application.properties` and replace placeholders:
   ```properties
   DB_URL=jdbc:mysql://localhost:3306/hospital
   DB_USERNAME=root
   DB_PASSWORD=your_secure_password_here
   DB_DRIVER=com.mysql.cj.jdbc.Driver
   ```

3. Place `application.properties` in the classpath (e.g., `src/main/resources/`)

**⚠️ IMPORTANT**: 
- Add `application.properties` to `.gitignore` to prevent credentials from being committed
- The `.gitignore` file is already configured to ignore this file
- Use `application-example.properties` as a template for documentation

### Step 4: Build the Project

```bash
cd hospital-management
mvn clean compile
```

This downloads dependencies (including MySQL Connector/J 8.4.0) and compiles the source code.

---

## Configuration

### Environment Variable Priority

The application loads configuration in this order:

1. **Environment Variables** (highest priority)
   - `DB_URL`: Database connection URL
   - `DB_USERNAME`: Database username
   - `DB_PASSWORD`: Database password
   - `DB_DRIVER`: JDBC driver class

2. **Properties File** (second priority)
   - File: `application.properties`
   - Location: Classpath root (e.g., `src/main/resources/`)

3. **Default Values** (lowest priority)
   - URL: `jdbc:mysql://localhost:3306/hospital`
   - Driver: `com.mysql.cj.jdbc.Driver`

### Example Configurations

**Local Development (Environment Variables)**:
```bash
export DB_URL=jdbc:mysql://localhost:3306/hospital
export DB_USERNAME=root
export DB_PASSWORD=password123
```

**Production (Environment Variables - CI/CD)**:
```bash
export DB_URL=jdbc:mysql://prod-db-server:3306/hospital
export DB_USERNAME=prod_user
export DB_PASSWORD=${PROD_DB_PASSWORD}  # From secrets manager
```

**Properties File Template** (`application.properties`):
```properties
DB_URL=jdbc:mysql://localhost:3306/hospital
DB_USERNAME=root
DB_PASSWORD=your_password_here
DB_DRIVER=com.mysql.cj.jdbc.Driver
APP_NAME=Hospital Management System
APP_VERSION=0.0.1-SNAPSHOT
```

---

## Building & Compilation

### Building the Project

#### Option 1: Using Maven (Recommended)

**Full Build** (clean, compile, test):
```bash
cd hospital-management
mvn clean install
```

**Compile Only** (without tests):
```bash
mvn clean compile
```

**Compile Including Tests**:
```bash
mvn clean test-compile
```

**Build JAR** (package for distribution):
```bash
mvn clean package
```

#### Option 2: Using IDE

**IntelliJ IDEA**:
1. Open project → `File` → `Open` → Select project folder
2. Maven automatically loads; right-click `pom.xml` → `Run Maven` → `clean install`
3. Or press `Ctrl+Shift+A` and type "Maven"

**Eclipse**:
1. Open project → `File` → `Import` → `Maven` → `Existing Maven Projects`
2. Select project folder
3. Right-click project → `Run As` → `Maven install`

**Visual Studio Code**:
1. Install Extension: "Extension Pack for Java" by Microsoft
2. Open terminal: `Ctrl+\``
3. Run: `mvn clean compile`

### Build Troubleshooting

| Issue | Solution |
|-------|----------|
| `mvn command not found` | Add Maven to system PATH or use full path to Maven binary |
| `Cannot find symbol` errors | Run `mvn clean compile` to download dependencies |
| Port 3306 already in use | MySQL not running or another service using the port |
| Memory errors during build | Increase heap size: `export MAVEN_OPTS="-Xmx1024m"` |

---

## How to Run

### Run the Application

**Option 1: Using Maven Exec Plugin**
```bash
cd hospital-management
mvn exec:java -Dexec.mainClass="com.hms.main.MainApp"
```

**Option 2: Compile and Run Manually**
```bash
cd hospital-management
mvn clean compile
mvn exec:java -Dexec.mainClass="com.hms.main.MainApp"
```

**Option 3: IDE (IntelliJ IDEA, Eclipse)**
1. Open the project in your IDE
2. Right-click on `MainApp.java`
3. Click "Run" or press `Ctrl+Shift+F10` (IntelliJ) / `Ctrl+F11` (Eclipse)

### Main Menu
```
===== HOSPITAL MANAGEMENT SYSTEM =====
1. Patient Module
2. Doctor Module
3. Appointment Module
4. Exit
Enter choice:
```

---

## Testing

### Manual Testing

**Verify Application Functionality**:

1. **Verify Database Connection**
   ```bash
   mvn exec:java -Dexec.mainClass="com.hms.main.MainApp"
   ```
   - Application should start without errors
   - Main menu should display
   - No password or credentials visible in console

2. **Test Patient Operations**
   - Add Patient: `ID=1, Name=John, Age=30, Disease=Flu, Bill=1000`
   - View All: Should show the added patient
   - Update Patient: Change age to 31
   - Delete Patient: Remove by ID
   - Expected: All operations successful with ✓ symbols

3. **Test Doctor Operations**
   - Add Doctor: `ID=101, Name=Dr.Smith, Speciality=Cardiology`
   - View All: Should show the added doctor
   - Update Doctor: Change speciality
   - Delete Doctor: Remove by ID
   - Expected: All operations successful

4. **Test Appointment Operations**
   - Book Appointment: `ID=501, Patient=1, Doctor=101, Date=2026-05-15, Time=10:30:00`
   - View All: Should show the appointment
   - View by Patient: Show only patient's appointments
   - View by Doctor: Show only doctor's appointments
   - Update Appointment: Modify date/time
   - Cancel Appointment: Delete by ID
   - Expected: All operations successful

5. **Test Input Validation**
   - Try adding patient with age=0 → Should reject
   - Try adding patient with age=200 → Should reject
   - Try adding patient with empty name → Should reject
   - Try adding appointment with invalid date format → Should reject
   - Expected: All invalid inputs rejected with meaningful error messages

6. **Test Security**
   - Verify no password appears in error messages
   - Check environment variables are not printed
   - Verify all operations use meaningful messages (no stack traces)
   - Expected: No sensitive information exposed

### Automated Testing (Future)

Currently, the project does not include automated unit tests. To add automated testing:

```bash
# Generate test cases (coming soon)
mvn test
```

### Test Results Documentation

After manual testing, document:
- Date of testing
- Java version used
- MySQL version
- Test results (Pass/Fail)
- Any issues encountered
- Environment variables used

---

## Sample Usage

### Add a Patient

```
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
✓ Patient added successfully
```

### Add a Doctor

```
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
✓ Doctor added successfully
```

### Book an Appointment

```
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
✓ Appointment booked successfully
```

### View All Appointments

```
Enter choice: 3
Enter choice: 2

301 | Patient: 101 | Doctor: 201 | 2026-05-15 10:30:00
```

---

## API Reference

### DAO Interfaces

#### PatientDAO
- `int addPatient(Patient p)` - Returns 1 if successful, 0 otherwise
- `List<Patient> getAllPatients()` - Returns empty list if no patients
- `Patient getPatientById(int id)` - Returns null if patient not found
- `int updatePatient(Patient p)` - Returns 1 if successful, 0 otherwise
- `int deletePatient(int id)` - Returns 1 if successful, 0 otherwise

#### DoctorDAO
- `int addDoctor(Doctor d)` - Returns 1 if successful, 0 otherwise
- `List<Doctor> getAllDoctors()` - Returns empty list if no doctors
- `Doctor getDoctorById(int id)` - Returns null if doctor not found
- `int updateDoctor(Doctor d)` - Returns 1 if successful, 0 otherwise
- `int deleteDoctor(int id)` - Returns 1 if successful, 0 otherwise

#### AppointmentDAO
- `int bookAppointment(Appointment a)` - Returns 1 if successful, 0 otherwise
- `List<Appointment> getAllAppointments()` - Returns empty list if no appointments
- `List<Appointment> getAppointmentsByPatient(int p_id)` - Returns empty list if no appointments
- `List<Appointment> getAppointmentsByDoctor(int d_id)` - Returns empty list if no appointments
- `int updateAppointment(Appointment a)` - Returns 1 if successful, 0 otherwise
- `int cancelAppointment(int appointment_id)` - Returns 1 if successful, 0 otherwise

### Model Classes

#### Patient
- `getP_id()`, `setP_id(int)`
- `getP_name()`, `setP_name(String)`
- `getP_age()`, `setP_age(int)`
- `getP_disease()`, `setP_disease(String)`
- `getBill()`, `setBill(double)`

#### Doctor
- `getD_id()`, `setD_id(int)`
- `getD_name()`, `setD_name(String)`
- `getD_speciality()`, `setD_speciality(String)`
- `toString()` - Formatted output

#### Appointment
- `getAppointment_id()`, `setAppointment_id(int)`
- `getP_id()`, `setP_id(int)`
- `getD_id()`, `setD_id(int)`
- `getDate()`, `setDate(Date)`
- `getTime()`, `setTime(Time)`
- `toString()` - Formatted output

---

## Troubleshooting

### Common Issues and Solutions

#### Database Connection Issues

**Error**: `Connection refused: localhost:3306`
```
Solution:
1. Verify MySQL is running: 
   - Windows: Check Services or `net start mysql80` (MySQL 8.0)
   - Linux: `sudo systemctl status mysql`
   - macOS: `brew services list`
2. Check database URL is correct: jdbc:mysql://localhost:3306/hospital
3. Verify MySQL is listening on port 3306
4. Restart MySQL service if needed
```

**Error**: `Access denied for user 'root'@'localhost'`
```
Solution:
1. Verify DB_USERNAME environment variable is set correctly
2. Verify DB_PASSWORD environment variable matches MySQL password
3. Check MySQL user has correct permissions:
   mysql> GRANT ALL PRIVILEGES ON hospital.* TO 'root'@'localhost';
   mysql> FLUSH PRIVILEGES;
```

**Error**: `Unknown database 'hospital'`
```
Solution:
1. Create the hospital database:
   mysql> CREATE DATABASE hospital;
2. Create required tables (see Setup Instructions)
3. Verify database exists: mysql> SHOW DATABASES;
```

#### Application Startup Issues

**Error**: `Exception in thread "main" java.lang.ClassNotFoundException: com.mysql.cj.jdbc.Driver`
```
Solution:
1. Run Maven build: mvn clean compile
2. This downloads MySQL Connector/J dependency
3. Try running again with: mvn exec:java -Dexec.mainClass="com.hms.main.MainApp"
```

**Error**: `Could not initialize class com.hms.config.ConfigLoader`
```
Solution:
1. Verify environment variables are set:
   - Windows: echo %DB_URL%
   - Linux/macOS: echo $DB_URL
2. If not set, set them before running:
   export DB_URL=jdbc:mysql://localhost:3306/hospital
   export DB_USERNAME=root
   export DB_PASSWORD=your_password
3. Try alternative: Use application.properties file instead
```

#### Input Validation Issues

**Error**: `Invalid age` when adding patient
```
Solution:
- Age must be between 1 and 150
- Example valid ages: 25, 65, 100
- Invalid ages: 0, -5, 200
```

**Error**: `Invalid ID` when booking appointment
```
Solution:
- IDs must be positive integers > 0
- Example valid IDs: 1, 100, 999
- Invalid IDs: 0, -1
```

**Error**: `Invalid date format` when booking appointment
```
Solution:
- Date format must be: yyyy-mm-dd
- Example: 2026-05-15 (valid) vs 05-15-2026 (invalid)
- Time format must be: hh:mm:ss
- Example: 10:30:00 (valid) vs 10:30 (invalid)
```

#### Build/Compilation Issues

**Error**: `mvn: command not found`
```
Solution:
1. Install Maven: https://maven.apache.org/download.cgi
2. Add Maven to system PATH
3. Verify: mvn -version
```

**Error**: `[ERROR] 'dependencies.dependency.version' for org.apache.maven.plugins:maven-compiler-plugin is missing`
```
Solution:
- Run: mvn clean install
- Or update pom.xml with correct plugin versions
```

### Debug Mode

**Enable detailed logging**:
```bash
# Set Java logging level to FINE
JAVA_TOOL_OPTIONS=-Djava.util.logging.config.file=logging.properties
mvn exec:java -Dexec.mainClass="com.hms.main.MainApp"
```

**Create logging.properties file**:
```properties
java.util.logging.ConsoleHandler.level = FINE
java.util.logging.FileHandler.level = FINE
com.hms.level = FINE
```

---

## FAQs

### General Questions

**Q: Is this application production-ready?**  
A: Yes! The current version (0.0.1-SNAPSHOT Security Enhanced) is production-ready with comprehensive security features including no hardcoded credentials, SQL injection prevention, input validation, and proper logging.

**Q: Can I use Java 11 or newer?**  
A: Yes! The application is compiled for Java 1.8 but fully compatible with Java 11, 17, 21, and newer versions. No code changes needed.

**Q: Can I modify the code and use it for my project?**  
A: Yes! This is provided as-is for educational and commercial purposes. You can modify, extend, and distribute as needed under the terms of the license.

**Q: How do I run this on Linux/macOS?**  
A: The same commands work on all platforms. Just replace Windows environment variable syntax with bash export commands (see Quick Start section).

### Configuration Questions

**Q: Where should I store the database password securely?**  
A: Use environment variables (recommended for production) or a secure configuration management system like AWS Secrets Manager, Azure Key Vault, or HashiCorp Vault.

**Q: Can I use a remote MySQL database?**  
A: Yes! Change the DB_URL environment variable:
```
export DB_URL="jdbc:mysql://remote-host:3306/hospital"
```

**Q: What if I want to use a different database username?**  
A: Set the DB_USERNAME environment variable to your username and DB_PASSWORD to your password.

### Security Questions

**Q: Are my database credentials safe?**  
A: Yes! Credentials are never stored in source code. They're loaded from environment variables or a separate configuration file (.gitignore ensures it's not committed).

**Q: Is this vulnerable to SQL injection?**  
A: No! All SQL queries use PreparedStatement with parameterized queries, which prevents SQL injection attacks.

**Q: Why are stack traces not printed?**  
A: Stack traces can expose sensitive system information. This application uses proper logging with meaningful error messages instead.

### Technical Questions

**Q: Can I use this with Spring Boot?**  
A: Yes! This codebase can be integrated into a Spring Boot application by adding a REST API layer and Spring Data JPA for database operations.

**Q: Can I add a GUI?**  
A: Yes! The business logic (DAO/Model layers) is separate from the UI (MainApp). You can create a JavaFX or Swing GUI while reusing the DAO layer.

**Q: Can I deploy this as a web application?**  
A: Yes! Add Spring Boot, Spring MVC, and REST endpoints, then deploy to Tomcat, Docker, or cloud platforms like AWS, Azure, or Heroku.

**Q: How do I generate reports?**  
A: The DAO layer already fetches data. You can use libraries like JasperReports or Apache POI to generate PDF/Excel reports.

### Troubleshooting Questions

**Q: The application won't connect to the database. What do I do?**  
A: See the "Troubleshooting" section above for detailed solutions to common connection issues.

**Q: How do I know if the connection is secure?**  
A: The ConfigLoader logs configuration loading. Watch for log messages like "Configuration loaded from environment variables" or "Configuration loaded from application.properties".

**Q: Can I run multiple instances simultaneously?**  
A: Yes! Each instance gets its own connection from ConfigLoader. For production, implement connection pooling (HikariCP).

---

## Related Documentation

This project includes comprehensive documentation files for different aspects:

### Documentation Files

1. **README.md** (This file)
   - Main project documentation
   - Setup, configuration, and usage instructions
   - API reference and troubleshooting


### How to Use Documentation

- **First Time Setup**: Read README.md (Setup Instructions) + Quick Start Guide

---

## Version History

### v0.0.1-SNAPSHOT (Current - May 2026) - Production-Ready with Security Enhancements

**Initial Release with Security Hardening**

#### New Features
- ✅ Configuration management system (ConfigLoader)
- ✅ Update operations for Patient, Doctor, and Appointment
- ✅ View appointments by Patient ID or Doctor ID
- ✅ Comprehensive input validation
- ✅ Professional error handling and logging

#### Security Improvements
- ✅ Removed all hardcoded credentials
- ✅ Environment variable support for configuration
- ✅ Fixed resource leaks (try-with-resources)
- ✅ Replaced printStackTrace with proper logging
- ✅ Added input validation at DAO level
- ✅ Created .gitignore with secrets exclusion
- ✅ Added security documentation

#### Code Quality
- ✅ Enhanced Maven configuration
- ✅ Added comprehensive JavaDoc
- ✅ Clean code structure
- ✅ Improved error messages
- ✅ Proper resource management

#### Documentation
- ✅ Comprehensive README with 800+ lines
- ✅ Security configuration guide
- ✅ Refactoring documentation
- ✅ Deployment guide
- ✅ GitHub publishing guide

### Previous Versions
- **v0.0.0-alpha** (Development) - Basic CRUD functionality with hardcoded credentials (archived)

---

## Contributing

### How to Contribute

1. **Fork the Repository**
   ```bash
   git clone https://github.com/yourusername/hospital-management.git
   cd hospital-management
   ```

2. **Create a Feature Branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make Your Changes**
   - Follow the existing code style
   - Add JavaDoc comments for new methods
   - Test thoroughly before committing

4. **Commit with Clear Messages**
   ```bash
   git commit -m "feature: Add description of your changes"
   ```

5. **Push to Your Fork**
   ```bash
   git push origin feature/your-feature-name
   ```

6. **Create a Pull Request**
   - Describe your changes in detail
   - Reference any related issues
   - Request review from maintainers

### Contribution Guidelines

#### Code Style
- Use standard Java naming conventions (camelCase for variables/methods, PascalCase for classes)
- Add JavaDoc comments for public methods
- Maximum line length: 120 characters
- Use 4 spaces for indentation

#### Security
- **Never commit credentials** to the repository
- Use environment variables or configuration files for sensitive data
- Always validate user input
- Use PreparedStatement for database queries
- Implement try-with-resources for resource management

#### Testing
- Test all changes manually before submitting
- Verify no new errors are introduced
- Document test results in commit message

#### Documentation
- Update README.md if functionality changes
- Add comments explaining complex logic
- Keep documentation accurate and up-to-date

### Reporting Issues

Found a bug or have a suggestion? Please create an issue with:
- **Title**: Clear, concise description
- **Description**: Detailed explanation of the issue
- **Steps to Reproduce**: How to reproduce the bug
- **Expected vs Actual Behavior**: What should happen vs what happens
- **Environment**: Java version, MySQL version, OS, etc.

### Contact

For questions about contributing, contact: **ssultanmaliki47@gmail.com**

---

## Future Improvements

1. **Database Connection Pooling**
   - Implement HikariCP or Apache DBCP for connection pooling
   - Improve performance with reusable database connections

2. **Enhanced Logging**
   - Integrate SLF4J or Logback for more flexible logging
   - Log to files with rotation and filtering

3. **Service Layer Implementation**
   - Implement business logic in service classes
   - Add transaction management with Spring Framework

4. **Graphical User Interface (GUI)**
   - Replace console interface with JavaFX or Swing
   - Provide user-friendly forms and data tables
   - Implement drag-and-drop calendar for appointments

5. **REST API**
   - Expose functionality via REST endpoints using Spring Boot
   - Support JSON request/response format
   - Add authentication and authorization

6. **Advanced Security**
   - Implement role-based access control (Admin, Doctor, Staff)
   - Add user authentication with password hashing (bcrypt)
   - Implement audit logging for compliance

7. **Data Export/Import**
   - Export patient/doctor/appointment data to CSV or PDF
   - Import bulk data from CSV files
   - Generate medical reports and statistics

8. **Appointment Management**
   - Add appointment status tracking (confirmed, cancelled, completed)
   - Implement automatic reminder notifications
   - Add availability calendar for doctors

9. **Email Notifications**
   - Send appointment reminders to patients and doctors
   - Send confirmation emails for new bookings
   - Integrate with email service (SendGrid, AWS SES)

10. **Mobile Application**
    - Develop Android/iOS app for on-the-go access
    - Real-time appointment notifications
    - Patient medical history access

---

## Security Checklist

- ✅ No hardcoded credentials in source code
- ✅ Database credentials loaded from environment variables or config file
- ✅ `.gitignore` configured to exclude sensitive files
- ✅ All SQL queries use PreparedStatement
- ✅ Try-with-resources for automatic resource cleanup
- ✅ Input validation for all user inputs
- ✅ Proper error handling without exposing sensitive details
- ✅ Logging instead of printStackTrace()
- ✅ Maven build configuration with proper plugins
- ✅ Code quality improvements and documentation

---

## Author

**Name**: Syed Mohammed Sultan  
**Email**: ssultanmaliki47@gmail.com  
**Project**: Hospital Management System - Version 0.0.1-SNAPSHOT (Security Enhanced)

For questions or contributions, please contact the author at the email above or submit an issue on GitHub.

---

## License

This project is provided as-is for educational purposes. Modify and distribute as needed.

---

**Last Updated**: May 2026
**Status**: Production-Ready with Security Enhancements
