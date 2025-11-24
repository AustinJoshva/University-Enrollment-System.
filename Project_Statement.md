# Project Statement: University Course Enrollment System

## 1. Problem Statement

The non-validated or manual method of student course enrollment still allows for academic non-compliance, especially the very basic violations such as the exceeding of maximum credit limits or unfulfilled prerequisites. The whole process results in tuition being wasted, costs of administrative error correction, and students not performing well. The project intends to solve this issue by deploying a fully automated, rule-based system which will not only impose these academic limits but do so digitally and in real-time.

## 2. Scope of the Project

The project scope is purely the backend business logic and data modeling which are essential for enrollment validation.

Target Users: Students and Academics Advisors.

High-Level Features: Management of Data, Validation of Enrollment Rules, and Reporting of Academic Progress.

## 3. Functional Requirements (FR)

The project is broken down into three major functional modules:

FRM-1: Data Access & Persistence (CRUD operations for entities are done via the DAO layer).

FRM-2: Enrollment Rule Validation (Prerequisite and Credit Load checks are done via the Service layer).

FRM-3: Academic Status Reporting (Generation of progress reports is done via the Service layer).

## 4. Non-Functional Requirements (NFR)

Four Specified Non-Functional Requirements:

Error handling strategy: A clear exception hierarchy must be used by the system to identify fatal and non-fatal enrollment problems, thus giving feedback to the user that can be acted upon.

Maintainability: Changes to the database structure or the addition of new rules can be done independently because of the use of separate service and DAO layers.

Reliability: The system will prevent any state changes related to a student’s enrollment from being persisted unless the applicable business rules have been validated and integrity of data is maintained (e.g., a student’s enrollment cannot be updated if prerequisites are not met).
