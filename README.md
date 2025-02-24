**Trip Agency Management System**

A comprehensive web-based application designed to streamline the process of managing and booking trips offered by a travel agency. This system uses a Model-View-Controller (MVC) architecture, integrates machine learning for sales prediction, and provides both administrator and user functionalities.

Introduction

The Trip Agency Management System allows users to browse, book, and manage trips in an intuitive, web-based environment. It also provides a sales prediction feature based on advertising costs, helping agencies make data-driven decisions.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**Key Features**

**1. User Dashboard:**

Search and filter trips by destination, activity type, price, or duration.
View trip details and proceed with booking.

**2. Trip Details:**

Displays price, duration, destination, availability, and more.
Allows users to finalize bookings by entering trip date, number of participants, etc.

**3. Admin Dashboard:**

Add, edit, or delete trips (name, description, price, duration, destination, activity type).
Manage existing trips via a user-friendly interface.

**4. Sales Prediction:**

Utilizes Weka-based machine learning to forecast revenue based on advertising costs.
Helps the agency plan future advertising budgets.

**5. Authentication:**

Users can register and log in to book trips.
Admins have elevated privileges to manage system data.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**Technology Stack**

Backend: Jakarta EE (Servlets, JSP, JDBC)

Frontend: HTML, CSS, Bootstrap, JavaScript

Database: SQLite

Machine Learning: Weka

Design Patterns: MVC, DAO, Singleton

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**Architecture & Design Patterns**

1. MVC (Model-View-Controller)

  Model: Business logic and data access (DAO layer).
  
  View: JSP files for user-facing interfaces.
  
  Controller: Servlets to handle HTTP requests, orchestrate data flow, and render views.

2. DAO (Data Access Object)

  Encapsulates all database operations (e.g., queries, inserts, updates).

3. Singleton

  Manages a single database connection instance for efficient resource usage.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**Database Structure**

Tables:

    Users (user credentials, roles)
    
    Trips (name, description, price, duration, destination, availability, activity type)
    
    Bookings (user info, trip references, booking date)

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**Implementation Highlights**

**1. Dashboard**

  Users can browse available trips, apply search filters, and view details.'
  
  Each trip has an "Info" or "Details" button leading to a more comprehensive description.

**2. Trip Details**

  Displays all relevant trip info.
  
  Allows immediate booking by submitting a form (date, participants, etc.).

**3. Admin Page**

  Form to add new trips (including name, price, availability, etc.).
  
  Editable table listing existing trips for updates or deletion.

**4. Screenshots & Code**

  See the attached screenshots in the documentation for a visual overview of each page and relevant code snippets.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**Machine Learning for Sales Prediction**

The application uses a regression model built in Weka to predict revenue based on advertising costs.

**Training Data:** Historical advertising spend vs. revenue.

**Algorithm:** Regression-based approach, trained and tested in Weka.

**Usage:**
    Users input monthly advertising costs.
    
    The system predicts potential revenue and displays the result on a dedicated page.

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

**Usage**

1. **Admin:**

      Log in with admin credentials
      
      Access the Admin Dashboard to add, edit, or delete trips.

2. **Users:**

      Register or log in.
      
      Browse available trips on the User Dashboard.
      
      Book a trip via the Trip Details page.

3. **Sales Prediction:**

      Navigate to the prediction page.
      
      Enter advertising cost data.
      
      Submit to view forecasted revenue.
