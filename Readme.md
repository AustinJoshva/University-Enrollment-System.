### Rule-Based Academic Enrollment System ###

Project Overview

With this project, a backend system is executed to control student enrollment in university courses. It employs a strict approach to academic policies by validating prerequisites and maintaining maximum credit loads of 18 credits, using a modular, object-oriented design (Service, DAO, Model architecture) in Java.

Presently, the version in use incorporates an in-memory data storage (InMemoryEnrollmentDAO) that facilitates fast testing and illustration of the business rules.

## Features

Rule Enforcement: Enrollment is instantly blocked if the student has not taken the prerequisite courses or if he/she goes above the maximum credit load (18 credits).

Modular Architecture: Incorporates the Service Layer for business logic and the DAO Layer for data abstraction, thus, ensuring Maintainability (NFR-2).

Status Reporting: Produces an extensive academic progress report that lists completed courses, current enrollment, and total credits (FRM-3).

Custom Error Handling: Makes use of an exception hierarchy (PrerequisiteNotMetException, EnrollmentException) for precise feedback on enrollment failures, thus, reinforcing a strong Error Handling Strategy (NFR-1).

Data Structures: Adopts Set collections for the $O(1)$ efficiency in the checking of completed courses and enrollment status.

## Technologies & Tools Used

Language: Java (JDK 8 or higher)

Architecture: Three-Tier (App, Service, DAO)

Data Storage: In-Memory (using Java HashMap and HashSet)

Version Control: Git

##Steps to Install & Run the Project

Prerequisites: Make sure the Java Development Kit (JDK) is installed and configured in your system environment.

Clone Repository:

git clone [YOUR_REPOSITORY_URL]
cd [repository-name]


Compile Source Files: Taking the standard package structure (dao, model, service, app) into consideration, compile all Java files.

javac -d out dao/
