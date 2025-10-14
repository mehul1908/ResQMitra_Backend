Here’s a clean and professional `README.md` for your **ResQMitra Backend**, written in a natural and developer-friendly tone:

---

# ResQMitra Backend

ResQMitra is a Spring Boot–based backend service that powers an emergency response platform. It handles user authentication, incident reporting, volunteer coordination, and administrative analytics.

---

## 📘 Overview

This backend provides REST APIs for three main user roles:

* **Citizen** – Can raise incident alerts.
* **Volunteer** – Can register for and resolve incidents.
* **Admin** – Can monitor incidents and manage users.

The APIs use **JWT-based authentication** for secure access control.
Unauthorized (401) and Forbidden (403) errors are returned when tokens are missing or roles are invalid.

---

## 🚀 Tech Stack

* **Java 17 / Spring Boot**
* **Spring Security (JWT Authentication)**
* **JPA / Hibernate**
* **PostgreSQL**
* **Lombok**
* **Maven**

---

## ⚙️ Authentication

All secured endpoints require a valid JWT token in the `Authorization` header.

```
Authorization: Bearer <token>
```

### Roles Supported

* `Volunteer`
* `Citizen`
* `Admin`

---

## 📡 API Endpoints

### 1. **User Login**

**POST** `/auth/login`
**No Token Required**

#### Request

```json
{
  "emailId": "mehul.vv19@gmail.com",
  "password": "@Mehul1908"
}
```

#### Successful Response (200 OK)

```json
{
  "status": true,
  "message": "User login successfully",
  "data": {
    "name": "Mehul",
    "email": "mehul.vv19@gmail.com",
    "role": "Volunteer",
    "token": "eyJhbGciOiJIUzM4NCJ9...",
    "expiryDate": "2025-10-06T09:44:52"
  }
}
```

#### Error Responses

* **404 Not Found** – Email doesn’t exist
* **400 Bad Request** – Invalid email format
* **403 Forbidden** – Wrong password

---

### 2. **Register Incident**

**POST** `/incident/register`
**No Token Required**

#### Request

```json
{
  "longitude": 76.0318426,
  "latitude": 22.2679748
}
```

#### Response

```json
{
  "status": true,
  "message": "Incident is created",
  "data": null
}
```

---

### 3. **Assign Volunteer to Incident**

**POST** `/incident/register/volunteer`
**Token Required** (Role: Volunteer)

#### Request

```json
{
  "incidentId": 2,
  "volunteerId": "vijayvargiyamehul@gmail.com"
}
```

#### Response (201 Created)

```json
{
  "status": true,
  "message": "Incident is assigned to Volunteer",
  "data": null
}
```

#### Error Responses

* **404 Not Found** – Volunteer or Incident not found

---

### 4. **Get All Incidents**

**GET** `/incident/get`
**Token Required** (Role: Any)

#### Response

```json
{
  "status": true,
  "message": "List of Incidents",
  "data": [
    {
      "incidentId": 2,
      "latitude": 22.2679748,
      "longitude": 76.0318426,
      "status": "ACTIVE",
      "createdAt": "2025-10-03T17:46:31.330017",
      "resolvedAt": null
    }
  ]
}
```

---

### 5. **Get Incidents by Volunteer**

**GET** `/incident/get/byvolunteer`
**Token Required** (Role: Volunteer)

* Volunteer ID is extracted automatically from the JWT.

#### Response

```json
{
  "status": true,
  "message": "List of Incidents",
  "data": [...]
}
```

---

### 6. **Get Incidents by Date**

**GET** `/incident/get/bydate`
**Token Required** (Role: Admin)

#### Request

```json
{
  "startDate": "2025-10-01",
  "endDate": "2025-10-05"
}
```

#### Response

```json
{
  "status": true,
  "message": "List of Incidents",
  "data": [
    {
      "incidentId": 2,
      "raisedBy": "mehul.vv19@gmail.com",
      "latitude": 22.2679748,
      "longitude": 76.0318426,
      "status": "ACTIVE",
      "createdAt": "2025-10-03T17:46:31.330017",
      "resolvedAt": null
    }
  ]
}
```

---

### 7. **User Registration**

**POST** `/user/register`
**No Token Required**

#### Request

```json
{
  "email": "pragyaa20@gmail.com",
  "password": "@Mehul1908",
  "name": "Pragya Jain",
  "phoneNum": "9179962222",
  "role": "Volunteer"
}
```

#### Response

```json
{
  "status": true,
  "message": "User is created",
  "data": null
}
```

#### Error

* **409 Conflict** – User already exists

---

### 8. **Delete User**

**DELETE** `/user/delete`
**Token Required**

#### Response

```json
{
  "status": true,
  "message": "User deactivated successfully",
  "data": null
}
```

---

### 9. **Update User Details**

**PUT** `/user/update`
**Token Required**

#### Request

```json
{
  "name": "Mehul",
  "phone": "9179962222"
}
```

* Only include fields you want to update.

#### Response

```json
{
  "status": true,
  "message": "User is updated successfully",
  "data": null
}
```

---

### 10. **Resolve Incident**

**PUT** `/incident/resolve/{incidentId}`
**Token Required** (Role: Volunteer)

#### Response

**204 No Content**

---

### 11. **Update Volunteer Location**

**PUT** `/user/update/location`
**Token Required** (Role: Volunteer)

#### Request

```json
{
  "latitude": 22.2679748,
  "longitude": 76.0318426
}
```

#### Response

```json
{
  "status": true,
  "message": "User Location updated",
  "data": null
}
```

---

## 🔐 Common Error Codes

| Status  | Meaning                                 |
| ------- | --------------------------------------- |
| **400** | Validation failed                       |
| **401** | Unauthorized – Token missing or invalid |
| **403** | Forbidden – Role not permitted          |
| **404** | Resource not found                      |
| **409** | Conflict – Duplicate resource           |
| **500** | Internal server error                   |

---

## 🧪 Testing

You can test the API using tools like **Postman** or **cURL**.
Make sure your backend is running and PostgreSQL is connected.

Example:

```
POST http://localhost:8080/auth/login
```

---

## 🧩 Future Enhancements

* Email or SMS alerts for nearby volunteers
* Real-time incident tracking using WebSockets
* Dashboard analytics for Admins

---

Would you like me to add a **“Setup and Run Locally”** section with instructions for cloning, configuring PostgreSQL, and running the Spring Boot server?
