# Kinective ATM Monitoring System - Design Documentation

## 1. Introduction

This document outlines the design, implementation, and key features of the ATM Monitoring System. The goal of this application is to securely monitor the status and behavior of ATMs in real-time and provide various APIs for monitoring transactions, failures, and video logs. This system runs on the ATM device and is callable by other applications within the bank’s network.

## 2. System Design

### 2.1 Overview

The ATM Monitoring System is designed to work within the bank's network, interacting with transaction logs, customer information, and video logs. It provides APIs for monitoring ATM activity in real-time. Below is a high-level view of the system:

- **ATM Device**: Sends and receives transaction information, video logs, and failures.
- **Authentication Service**: Verifies the tokens provided by the bank.
- **Transaction Processing**: Returns Transaction Information based on transactionType deposit, withdrawal etc
- **Failure Monitoring**: Tracks ATM and device failures.
- **Video Log Service**: Provides access to video logs based on time ranges.

### 2.2 Architecture Diagram

An architecture diagram should be included to show the components and their interactions, including the ATM device, API gateway, transaction service, video log service, etc.

## 3. Component Design

### 3.1 Authentication Service

- **Description**: This service ensures that all API requests are authorized by validating the authentication tokens provided by the bank's system. A JWT (JSON Web Token) filter is added to intercept every request and validate the token before any other API is accessed. The assumption is that the user has already been authenticated, and the token issued is then sent to the ATM system. Here, we verify the token's validity by checking the signature and ensuring that the user has the proper roles to access the respective API. Role-based authorization is enforced using the @PreAuthorize annotation on each secured API.
- **Public Endpoint for Testing**: A public endpoint is provided to generate tokens for testing purposes.
     - **Endpoint**: `/atm-monitoring/v1/generate-token` (Open access to generate tokens)
- **Technology**: Uses JWT (JSON Web Tokens) for authorization.

### 3.2 Transaction Service

- **Description**: Monitors transactions at the ATM and provides breakdowns by type (Deposit, Withdrawal, Balance Inquiry).
- **Endpoints**:
    - `/atm-monitoring/v1/transactions/customers/count/24-hours`: Returns the total number of customers in the last 24 hours.
    - `/atm-monitoring/v1/transactions?transactionType=`: Returns a breakdown of transactions by type.
- **Data**:
    - Transaction model with fields like transactionId, customerId, transactionType, amount, timestamp, status.

### 3.3 Failure Monitoring Service

- **Description**: Tracks system and attached device failures like downtimes or transaction-related issues.
- **Endpoint**:
    - `/atm-monitoring/v1/failures`: Returns a list of ATM failures with incident context.
- **Data**:
    - FailureLog model with fields like atmId, transactionId, failureType, timestamp, description.

### 3.4 Video Log Service

- **Description**: This service provides the ability to download video urls from ATMs for specific time ranges. The video logs can be retrieved by filtering based on ATM ID, transaction ID, customer ID, and a specified time range.

- **Endpoints**:
    - `/v1/video-logs/atm/{atmId}/transaction/{transactionId}`: Allows downloading of video logs for a specific ATM and transaction.
        - **Authorization**: Requires `ROLE_OWNER`.
        - **Parameters**:
            - `atmId`: Unique identifier of the ATM.
            - `transactionId`: Unique identifier of the transaction.
    - `/v1/video-logs/atm/{atmId}/customer/{customerId}`: Retrieves video logs based on ATM ID, customer ID, and a time range.
        - **Authorization**: Requires `ROLE_OWNER`.
        - **Parameters**:
            - `atmId`: Unique identifier of the ATM.
            - `customerId`: Unique identifier of the customer.
            - `startTime`: Start time of the video log retrieval period.
            - `endTime`: End time of the video log retrieval period.
    - `/v1/video-logs`: Allows downloading of video logs for a specific time range.
        - **Authorization**: Requires `ROLE_OWNER`.
        - **Parameters**:
            - `startTime`: Start time of the time range.
            - `endTime`: End time of the time range.

- **Data**:
    - The `VideoLog` model contains fields such as:
        - `atmId`: Unique identifier of the ATM.
        - `transactionId`: Unique identifier of the transaction.
        - `customerId`: Unique identifier of the customer.
        - `videoUrl`: URL of the video log.
        - `timestamp`: Time when the log was recorded.


### 3.5 Error Handling

All APIs follow a standard error format, returning meaningful error messages and HTTP status codes (e.g., 400 Bad Request, 403 Forbidden, 500 Internal Server Error).

## 4. Data Models and Relationships

### 4.1 Transaction

- **Description**: The `Transaction` model represents a transaction that occurred at an ATM. It includes details such as the transaction type, the customer involved, the amount, and the status of the transaction.

- **Fields**:
    - `transactionId` (String): Unique identifier for the transaction.
    - `customerId` (String): Unique identifier for the customer involved in the transaction.
    - `transactionType` (TransactionType): Type of transaction (e.g., "WITHDRAWAL", "DEPOSIT").
    - `amount` (double): The amount of money involved in the transaction.
    - `timestamp` (Instant): The time when the transaction occurred.
    - `status` (String): The status of the transaction (e.g., "SUCCESS", "FAILED").

- **Relationships**:
    - One `Transaction` can have a corresponding `VideoLog` entry if the ATM system captures video logs for the transaction.
    - A `Transaction` can also be related to `FailureLog` if there was a failure during the transaction process.

---

### 4.2 VideoLog

- **Description**: The `VideoLog` model represents a video log associated with an ATM transaction. It stores details about the video captured for a transaction, including the ATM ID, customer ID, and the URL where the video can be accessed.

- **Fields**:
    - `atmId` (String): Unique identifier of the ATM (ignored in API responses).
    - `transactionId` (String): Unique identifier of the transaction associated with the video log.
    - `customerId` (String): Unique identifier of the customer involved in the transaction.
    - `videoUrl` (String): URL where the video log can be accessed.
    - `timestamp` (Instant): The time when the video log was recorded.

- **Relationships**:
    - A `VideoLog` is directly associated with a `Transaction` via the `transactionId`. This creates a one-to-one relationship between a `Transaction` and its corresponding `VideoLog`.

---

### 4.3 FailureLog

- **Description**: The `FailureLog` model represents a log of failures that occurred during transactions at ATMs. It includes the failure type, error code, and reference to the associated transaction.

- **Fields**:
    - `atmId` (String): Unique identifier of the ATM where the failure occurred.
    - `timestamp` (Instant): The time when the failure occurred.
    - `failureType` (FailureType): The type of failure that occurred (e.g., hardware, network).
    - `errorCode` (String): Error code associated with the failure.
    - `transactionId` (String): Reference to the related transaction ID (if applicable).

- **Relationships**:
    - The `FailureLog` references a `Transaction` via the `transactionId`, indicating that the failure occurred during that specific transaction. This creates a one-to-one or one-to-many relationship (depending on how multiple failures during one transaction are logged).

---

### 4.4 Schema Considerations

- **Database Schema Transfer**: While the `Transaction`, `VideoLog`, and `FailureLog` models maintain clear relationships in the current application layer (through references like `transactionId`).

- **Data Transformation in Schema-Based DB**: In schema-based databases, such as traditional SQL databases, these models are often normalized into separate tables (`Transaction`, `VideoLog`, `FailureLog`) with foreign key relationships. This makes querying the relationships more efficient. 

### 4.5 API Documentation
The API documentation for the ATM monitoring system is available in the `api-docs.json` file in resources/docs folder. This file provides a complete overview of all the endpoints, request parameters, response schemas, and status codes.

To generate the documentation, Swagger/OpenAPI was used, and it can be visualized by loading the file into any compatible Swagger UI.


## 5. Current Implementation Status (NO database)

As of now, we have implemented repository classes that allow for the addition of dummy data for testing purposes. These include:

- **Dummy Transactions**: Repository class to create and store sample transaction records.
- **Dummy Failure Logs**: Repository class to log various failure scenarios associated with ATM transactions.
- **Dummy Video Logs**: Repository class to generate and store dummy video logs related to specific transactions.
- **Dummy Banking Users**: Repository class to manage and create dummy banking users records for testing and validation.

These classes facilitate the testing of the ATM Monitoring System's functionality and ensure that the core features can be validated before integrating with real data sources.


## 6. Configuring Postman to Run

A Postman collection and an environment json file has been added to src/resources/postman for testing the API endpoints.


### Steps to Configure Postman

### Step 1: Import Postman Collection and Environment

1. **Open Postman** and click on the **Import** button in the top-left corner.
2. **Select Files** and import both the following JSON files:
    - **Postman Collection**: Contains all the API endpoints to test.
    - **Environment File (`atm-monitoring.json`)**: Contains environment variables including `jwtToken`.
3. Once imported, you will see the collection and environment available in Postman.

### Step 2: Set Up the Environment

1. Click on the **Environment Quick Look** (eye icon) at the top-right corner of Postman.
2. Select the **`atm-monitoring` environment** from the dropdown. This ensures that your API requests have access to the relevant variables.

### Step 3: Generate Access Token

1. Locate the **`/v1/generate-token`** request inside the imported collection.
2. Run the request by entering the following:
    - **Parameter**: `empCode = 123456` (required for generating a JWT token). emp code=123456 has necessary roles to perform.
3. The response will include the **JWT token**, and it will be **automatically saved** in the environment variable named `jwtToken`.

### Step 4: Running Other API Requests

1. Once the token has been generated and saved, it will be used automatically for authentication.
2. Now you can run any other request within the collection, and they will use the saved token from the `jwtToken` environment variable.


