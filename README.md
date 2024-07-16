# PharmaConnect

PharmaConnect provides:
* Unified Database System for both Medical Stores and Customers. 
* Location based price comparison.​
* Real time updates on stock availability.​
* 'Lock-in' System for Booking Medicines.​
* Customer Analytics (most popular drugs, recommended drugs for customers, etc.)​
* Store Analytics (Popular drug companies, customer trends)​.

# Overview

[Understand the project requirements.](https://dalu.sharepoint.com/:p:/r/teams/CSCI5308_Fall2023/_layouts/15/Doc.aspx?sourcedoc=%7B8AC43AD8-3A29-4AAD-95FC-1708B3F14F19%7D&file=Group_12_PharmaConnect.pptx&action=edit&mobileredirect=true "Project description")

## LIVE DEPOLYED APPLICATION

Please ensure you are connected to [Dal VPN](https://dalu.sharepoint.com/sites/its/SitePages/vpn.aspx?xsdata=MDV8MDF8fDExMTQ0MjBhYjNkOTRlZGJkOGNmMDhkYmJmOTRmZGM3fDYwYjgxOTk5MGI3ZjQxMmQ5MmEzZTE3ZDhhZTllM2UwfDB8MHw2MzgzMTQ0MTg4MzY4NTQ5OTF8VW5rbm93bnxWR1ZoYlhOVFpXTjFjbWwwZVZObGNuWnBZMlY4ZXlKV0lqb2lNQzR3TGpBd01EQWlMQ0pRSWpvaVYybHVNeklpTENKQlRpSTZJazkwYUdWeUlpd2lWMVFpT2pFeGZRPT18MXxMM1JsWVcxekx6RTVPbVpKTUc5R1NEWk5RelZUUnpVdGRVeHVabE42UTJWbll6UjRVelZNUWpOSWJXTXRPSG94VWs5aWRYTXhRSFJvY21WaFpDNTBZV04yTWk5amFHRnVibVZzY3k4eE9UbzBZV1UwTkRGak0yVTFZVEUwWW1FNE9HRTVNMlZtTkROa016VmtOVFZqTkVCMGFISmxZV1F1ZEdGamRqSXZiV1Z6YzJGblpYTXZNVFk1TlRnME5UQTRNalF5TXc9PXwxMzZlZTlmMmI0OTc0NzgxNzY2OTA4ZGJiZjk0ZmRjNXxkMmZjMDcxM2M3YTg0ZGEyYmIyMDZkMDUyYTA4ZjZhMg%3D%3D&sdata=OFdmNHhPL1NXcE9zSGM3c28wWUV5Q1pnVnJ1eWJSZmp5em9lT2NVYWdycz0%3D):

URL: http://172.17.3.127:8074

## BACKEND - DEPENDENCIES

1. [Install Java 17 or higher](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
2. [Install Maven](https://maven.apache.org/download.cgi) - version: 3.8.8
3. [Setup MySQL Locally](https://www.mysql.com/downloads/)
   - [Run setup SQL script](https://git.cs.dal.ca/courses/2023-fall/csci-5308/Group09/-/blob/main/PharmaConnect-Database/)
4. [Docker](https://www.docker.com/products/docker-desktop/)

## BACKEND - BUILD/DEPLOYMENT INSTRUCTIONS

cd PharmaConnect-Backend/pharma

1. Navigate to PharmaConnect-Backend directory

    `cd PharmaConnect-Backend/pharma`

2. Run tests:

   `mvn clean test`

3. Package Jar:

   `mvn clean package -Dspring.profiles.active=prod -DskipTests`

4. Build docker image

   `docker build -t backend-app .`

5. Run docker container

   `docker run -d -p 8073:8080 --name pharmaconnect-be backend-app`

Backend API should be accessible at http://localhost:8073

## INITIAL CODE SMELLS

- [TODO]

## CODE SMELLS POST REFACTORING

- [TODO]

## Frontend

PharmaConnect requires [Node.js](https://nodejs.org/) v18+ to run.

Check your node & npm version using:

`node -v`

`npm -v`

## Run Locally

1. Navigate to PharmaConnect-Frontend directory

   `cd PharmaConnect-Frontend`

2. Install Node Dependencies

   `npm install`

4. Run React App

   `npm run start:dev`

Frontend should be accessible at http://localhost:4200

## Run Using Docker

1. Build docker image

   `docker build -t frontend-app PharmaConnect-Frontend/`

2. Run docker container

   `docker run -d -p 8074:4200 --name pharmaconnect-fe frontend-app`

Frontend should be accessible at http://localhost:8074

# Use Cases

### Use Case–1: Customer/Store Signup and Login
* Customer/Store owner is asked to fill in the signup form providing the required information.
* Customer/Store owner is asked to set up the password as per the specified message.
* Customer/Store owner is asked to provide a valid email address.
* Customer/Store owner should be older than 18.
* Customer/Store owner is signed up by clicking “Sign Up” button.
* Customer/Store owner is directed to the login page.
* Customer/Store owner is asked to fill email and password for logging in.
* Invalid credentials will display an error dialog box.
* Valid credentials will log the customer/store owner in with a success message and display the “Find Medicine” page for customer and “Dashboard” by default.

### Use Case–2: Store inventory management
* The store can login and head to the “Products” page to manage inventory.
* The store can add medicines one-by-one using the “Add new” button.
* The store can select a drug from the dropdown in the dialog box to add and specify its quantity available and its unit price.
* Clicking “Save” will add the drug to the inventory and display on the “Products” page.
* This medicine will now be available to the customer with its available quantity and price.
* The store can update data regarding any of its drug using the update symbol in front of the drug.
* The dialog box allows the store personnel to update its quantity or price and reflect it in “Products” page.
* The store can also delete a drug from its inventory by clicking the delete button.
* The dialog box will ask for confirmation and delete it when clicked “Yes”.
* Any problem in performing these 3 operations will display an appropriate error message.

### Use Case–3: Medicine reservation by customer.
* The customer can reserve medicine from the “Find Medicine” page.
* The customer can search for required medicine from the list of medicines or search from the input field based on the medicine name or company name.
* The customer is displayed the searched medicine with its company, average price, a “Learn More” button and “Find Store” button.
* The “Learn More” button will display more information related to the drug.
* The “Find Store” button will show a list of medical stores which have the drug available.
* Each card will display the quantity available, its unit price, a “Know More” button, input fields to mention the quantity required and the time for reservation, and a “Reserve” button.
* The “Know More” button will display a dialog box with detailed information regarding the store, contact person and the user ratings and reviews.
* The customer can give reviews and ratings in this dialog box.
* Clicking “Reserve” button will reserve the medicine from that store with the quantity and time specified.
* On successful reservation, the reservation is displayed in the “Reservations” page in both the customer and store side.
* If the quantity to be booked is greater than the quantity available or if the time for reservation exceeds 180 minutes, the reservation will be denied.
* Once the reservation is successful, it’s status is changed to “Pending for Pickup”.
* The customer gets a confirmation mail on the registered email address for the reservation.
* It is updated to “Complete” when the store clicks the “Complete” button.
* It is updated to “Cancelled” if the customer does not pick up the medicine within the reserved time.
* Once the reservation is completed or cancelled, it is moved to the” Orders” page on both the customer’s and store’s side.

### Use Case–4: Blood Donation by customers
* The customer can view ads for blood donation from the “Donate Blood” page.
* The customer can choose one of the ads and click the link to register for blood donation.
* A dialog opens containing a form for customer information necessary for blood donation.
* The customer fills in the form which requires a minimum age of 18 to donate blood.
* The customer will get a confirmation mail on the registered email address after form submission.
* A dialog will show an error message when the form submission fails.

### Use Case–5: Updating profile
* The customer/store can update its information from the “Profile” page.
* The customer/store can change its password from here which requires the current password.
* The store can provide its working hours here in a week which will be displayed in the “Know More” dialog box about store information on the customer side.
* The customer/store can logout by clicking the “Logout” button.
