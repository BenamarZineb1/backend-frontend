# API Testing Examples

This document provides ready-to-use examples for testing the E-Doctorat API endpoints.

## Prerequisites

- Backend running on http://localhost:8085
- PostgreSQL database running and configured
- Valid user accounts in database (for protected endpoints)

## Tools

You can use any of these tools to test:
- **Postman** (recommended)
- **cURL** (command line)
- **Browser Console** (using fetch or axios)
- **Thunder Client** (VS Code extension)

---

## 1. Authentication Endpoints

### Register New User

**Request:**
```http
POST http://localhost:8085/auth/register
Content-Type: application/json

{
  "username": "john.doe@example.com",
  "password": "SecurePassword123!",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "CANDIDAT"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8085/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john.doe@example.com",
    "password": "SecurePassword123!",
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "CANDIDAT"
  }'
```

**Expected Response:**
```json
{
  "id": 1,
  "username": "john.doe@example.com",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "role": "CANDIDAT"
}
```

### Login

**Request:**
```http
POST http://localhost:8085/auth/login
Content-Type: application/json

{
  "username": "john.doe@example.com",
  "password": "SecurePassword123!"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8085/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john.doe@example.com",
    "password": "SecurePassword123!"
  }'
```

**Expected Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJqb2huLmRvZUBleGFtcGxlLmNvbSIsImlhdCI6MTY0MDk5NTIwMCwiZXhwIjoxNjQxMDgxNjAwfQ.abcdefghijklmnopqrstuvwxyz",
  "role": "CANDIDAT",
  "message": "Connecte avec succès",
  "user": {
    "id": 1,
    "username": "john.doe@example.com",
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "CANDIDAT"
  }
}
```

**Save the token** - You'll need it for authenticated requests!

---

## 2. Candidat Endpoints

### Get Candidate Profile

**Request:**
```http
GET http://localhost:8085/candidat/me
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

**cURL:**
```bash
curl -X GET http://localhost:8085/candidat/me \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
```

### Update Profile

**Request:**
```http
PUT http://localhost:8085/candidat/me
Authorization: Bearer YOUR_JWT_TOKEN_HERE
Content-Type: application/json

{
  "telephone": "+212612345678",
  "adresse": "123 Rue de l'Université, Casablanca",
  "dateNaissance": "1995-05-15"
}
```

### Get Available Subjects

**Request:**
```http
GET http://localhost:8085/sujets/disponibles
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

### Apply to Subject

**Request:**
```http
POST http://localhost:8085/candidat/postuler/1
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

### Get My Applications

**Request:**
```http
GET http://localhost:8085/candidat/mes-candidatures
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

### Add Diploma

**Request:**
```http
POST http://localhost:8085/candidat/diplomes
Authorization: Bearer YOUR_JWT_TOKEN_HERE
Content-Type: multipart/form-data

Form Data:
- titre: "Master en Informatique"
- etablissement: "Université Hassan II"
- anneeObtention: 2023
- moyenne: 16.5
- file: [Upload PDF file]
```

**cURL:**
```bash
curl -X POST http://localhost:8085/candidat/diplomes \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE" \
  -F "titre=Master en Informatique" \
  -F "etablissement=Université Hassan II" \
  -F "anneeObtention=2023" \
  -F "moyenne=16.5" \
  -F "file=@/path/to/diploma.pdf"
```

---

## 3. Professeur Endpoints

### Propose a Subject

**Request:**
```http
POST http://localhost:8085/professeur/sujets/proposer
Authorization: Bearer YOUR_JWT_TOKEN_HERE
Content-Type: application/json

{
  "professeurId": 1,
  "titre": "Intelligence Artificielle en Santé",
  "description": "Recherche sur l'application de l'IA dans le diagnostic médical"
}
```

### Get My Subjects

**Request:**
```http
GET http://localhost:8085/professeur/sujets/mes-sujets/1
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

### Get Candidates for Evaluation

**Request:**
```http
GET http://localhost:8085/examiner/commission/1
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

### Evaluate Candidate

**Request:**
```http
PUT http://localhost:8085/examiner/evaluer
Authorization: Bearer YOUR_JWT_TOKEN_HERE
Content-Type: application/json

{
  "candidatId": 1,
  "commissionId": 1,
  "noteEcrit": 85,
  "noteOral": 90,
  "decision": "ADMIS",
  "commentaire": "Excellent candidat avec une solide formation"
}
```

---

## 4. Directeur Labo Endpoints

### Get Lab Candidates

**Request:**
```http
GET http://localhost:8085/directeur-labo/labo/1/candidats
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

### Create Commission

**Request:**
```http
POST http://localhost:8085/directeur-labo/commission
Authorization: Bearer YOUR_JWT_TOKEN_HERE
Content-Type: application/json

{
  "nom": "Commission Informatique 2024",
  "dateReunion": "2024-06-15T10:00:00",
  "lieu": "Salle A, Bâtiment Sciences"
}
```

### Assign Subject to Professor

**Request:**
```http
PUT http://localhost:8085/directeur-labo/sujet/1/affecter/2
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

### Add Subject to Commission

**Request:**
```http
POST http://localhost:8085/directeur-labo/commission/1/sujet/1
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

---

## 5. Directeur CED Endpoints

### Get All Candidates

**Request:**
```http
GET http://localhost:8085/directeur/ced/candidats
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

### Get All Subjects

**Request:**
```http
GET http://localhost:8085/directeur/ced/sujets
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

### Get Results

**Request:**
```http
GET http://localhost:8085/directeur/ced/resultats
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

### Get Inscriptions

**Request:**
```http
GET http://localhost:8085/directeur/ced/inscriptions
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

---

## 6. Directeur Pole Endpoints

### Get All Candidates

**Request:**
```http
GET http://localhost:8085/directeur/pole/candidats
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

### Add Calendar Entry

**Request:**
```http
POST http://localhost:8085/directeur/pole/calendrier
Authorization: Bearer YOUR_JWT_TOKEN_HERE
Content-Type: application/json

{
  "evenement": "Ouverture des candidatures",
  "dateDebut": "2024-01-15",
  "dateFin": "2024-03-15",
  "type": "CANDIDATURE"
}
```

### Get Calendar

**Request:**
```http
GET http://localhost:8085/directeur/pole/calendrier
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

### Get Pending Subjects

**Request:**
```http
GET http://localhost:8085/directeur/pole/sujets/en-attente
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

### Publish Subjects

**Request:**
```http
PUT http://localhost:8085/directeur/pole/sujets/publier
Authorization: Bearer YOUR_JWT_TOKEN_HERE
Content-Type: application/json

[1, 2, 3, 5, 8]
```

### Publish Main List (LP)

**Request:**
```http
POST http://localhost:8085/directeur/pole/publier-lp
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

---

## 7. Scolarite Endpoints

### Get All Student Files

**Request:**
```http
GET http://localhost:8085/scolarite/dossiers
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

### Get File by CNE

**Request:**
```http
GET http://localhost:8085/scolarite/dossier/R123456789
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

### Validate Student File

**Request:**
```http
PUT http://localhost:8085/scolarite/valider/1
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

### Add Comment to File

**Request:**
```http
PUT http://localhost:8085/scolarite/commenter/1?commentaire=Documents conformes, dossier validé
Authorization: Bearer YOUR_JWT_TOKEN_HERE
```

---

## Testing in Browser Console

### Set up API client
```javascript
const API_URL = 'http://localhost:8085';
const TOKEN = 'YOUR_JWT_TOKEN_HERE'; // Get this from login response

const api = (endpoint, options = {}) => {
  return fetch(`${API_URL}${endpoint}`, {
    ...options,
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${TOKEN}`,
      ...options.headers
    }
  }).then(res => res.json());
};
```

### Test Login
```javascript
fetch('http://localhost:8085/auth/login', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({
    username: 'john.doe@example.com',
    password: 'SecurePassword123!'
  })
})
.then(res => res.json())
.then(data => {
  console.log('Login successful:', data);
  localStorage.setItem('token', data.token);
});
```

### Test Protected Endpoint
```javascript
const token = localStorage.getItem('token');

fetch('http://localhost:8085/candidat/me', {
  headers: {
    'Authorization': `Bearer ${token}`
  }
})
.then(res => res.json())
.then(data => console.log('Profile:', data));
```

---

## Postman Collection

### Import this JSON into Postman:

```json
{
  "info": {
    "name": "E-Doctorat API",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "variable": [
    {
      "key": "baseUrl",
      "value": "http://localhost:8085"
    },
    {
      "key": "token",
      "value": ""
    }
  ],
  "item": [
    {
      "name": "Auth",
      "item": [
        {
          "name": "Login",
          "request": {
            "method": "POST",
            "header": [],
            "body": {
              "mode": "raw",
              "raw": "{\n  \"username\": \"user@example.com\",\n  \"password\": \"password123\"\n}",
              "options": { "raw": { "language": "json" } }
            },
            "url": { "raw": "{{baseUrl}}/auth/login" }
          }
        }
      ]
    }
  ]
}
```

---

## Common HTTP Status Codes

| Code | Meaning | When You'll See It |
|------|---------|-------------------|
| 200 | OK | Successful GET/PUT request |
| 201 | Created | Successful POST that creates resource |
| 400 | Bad Request | Invalid data sent |
| 401 | Unauthorized | Missing or invalid token |
| 403 | Forbidden | Don't have permission |
| 404 | Not Found | Resource doesn't exist |
| 500 | Server Error | Backend error |

---

## Troubleshooting

### CORS Error
**Problem:** `Access to fetch at 'http://localhost:8085/...' from origin 'http://localhost:3000' has been blocked by CORS policy`

**Solution:** Ensure CorsConfig.java is properly configured with your frontend URL.

### 401 Unauthorized
**Problem:** All requests return 401

**Solutions:**
1. Check token is included: `Authorization: Bearer YOUR_TOKEN`
2. Verify token hasn't expired
3. Ensure you're logged in with correct credentials

### Connection Refused
**Problem:** `Failed to fetch` or `ERR_CONNECTION_REFUSED`

**Solution:** 
1. Ensure backend is running: `http://localhost:8085`
2. Check PostgreSQL is running
3. Verify port 8085 is not blocked by firewall

### 500 Internal Server Error
**Problem:** Backend returns 500

**Solution:** Check backend console logs for detailed error message

---

## Next Steps

1. **Create Test Data**: Register users with different roles
2. **Test Each Endpoint**: Go through each endpoint systematically
3. **Save Collection**: Export your Postman collection for reuse
4. **Document Issues**: Note any errors or unexpected behaviors
5. **Integration Test**: Test complete workflows (login → action → logout)

---

**Version:** 1.0.0  
**Last Updated:** 2026-01-18
