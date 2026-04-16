# gnc_final_assesment_smartcampus# GNC SmartCampus Management System

**Author:** Vaibhav Bhatnagar (ID: 24665052)  
**Email:** vaibhavbhatnagar11506@gmail.com 
**Course:** B.Tech CSE  

## Problem Statement
The objective of this project is to build a Smart Campus Management System that allows administrators to manage student records, course catalogs, and track enrollments efficiently. The system is designed to be reliable, handling invalid inputs gracefully and processing enrollment requests asynchronously to ensure high performance.

## Features & Technical Implementation
This Java-based command-line application implements core Object-Oriented Programming (OOP) concepts alongside advanced Java features:
* **Student & Course Management:** Add and store student profiles and course details seamlessly.
* **Collections Framework:** Utilizes `HashMap` for O(1) quick lookups of students/courses, and `ArrayList` to map multiple courses to a single student.
* **Custom Exception Handling:** Implements `InvalidCampusDataException` to ensure data integrity (e.g., rejecting negative tuition fees, handling unregistered IDs).
* **Multithreading (Asynchronous Processing):** Uses the `Runnable` interface to process course registrations in the background without freezing the main application flow.
* **Unique Feature (Scholarship Fast-Track):** Implemented a specific boolean flag (`isScholarshipStudent`) for "Scholarship Students" which adjusts thread sleep times, simulating priority processing speed for specific enrollments.

## How to Run
1. Ensure you have Java installed on your system.
2. Open your terminal or VS Code.
3. Compile the program: `javac SmartCampusManager.java`
4. Run the program: `java SmartCampusManager`
