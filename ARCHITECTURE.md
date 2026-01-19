# E-Doctorat Platform Architecture

## System Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                         FRONTEND (React)                         │
│                      http://localhost:3000                       │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐         │
│  │   Auth UI    │  │  Candidat UI │  │ Professeur UI│         │
│  │  (Login/Reg) │  │  (Dashboard) │  │  (Subjects)  │  ...    │
│  └──────┬───────┘  └──────┬───────┘  └──────┬───────┘         │
│         │                 │                  │                  │
│         └─────────────────┼──────────────────┘                  │
│                           │                                     │
│  ┌────────────────────────▼─────────────────────────┐          │
│  │              Services Layer                       │          │
│  │  ┌──────────┐ ┌──────────┐ ┌──────────┐         │          │
│  │  │  Auth    │ │ Candidat │ │Professeur│  ...    │          │
│  │  │ Service  │ │ Service  │ │ Service  │         │          │
│  │  └──────────┘ └──────────┘ └──────────┘         │          │
│  └────────────────────────┬─────────────────────────┘          │
│                           │                                     │
│  ┌────────────────────────▼─────────────────────────┐          │
│  │          Axios API Client (axios.js)             │          │
│  │  • JWT Token Interceptor                         │          │
│  │  • Error Handler                                 │          │
│  │  • Base URL: http://localhost:8085               │          │
│  └────────────────────────┬─────────────────────────┘          │
│                           │                                     │
└───────────────────────────┼─────────────────────────────────────┘
                            │
                            │ HTTP/HTTPS
                            │ JWT Bearer Token
                            │
┌───────────────────────────▼─────────────────────────────────────┐
│                    BACKEND (Spring Boot)                         │
│                    http://localhost:8085                         │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  ┌──────────────────────────────────────────────────────────┐  │
│  │               Security Layer                              │  │
│  │  ┌────────────┐ ┌──────────────┐ ┌────────────┐         │  │
│  │  │   CORS     │ │     JWT      │ │   Spring   │         │  │
│  │  │   Config   │ │   Filter     │ │  Security  │         │  │
│  │  └────────────┘ └──────────────┘ └────────────┘         │  │
│  └──────────────────────┬───────────────────────────────────┘  │
│                         │                                       │
│  ┌──────────────────────▼───────────────────────────────────┐  │
│  │               Controllers Layer                           │  │
│  │  ┌────────────┐ ┌──────────────┐ ┌────────────┐         │  │
│  │  │   Auth     │ │   Candidat   │ │ Professeur │  ...    │  │
│  │  │ Controller │ │  Controller  │ │ Controller │         │  │
│  │  └──────┬─────┘ └──────┬───────┘ └──────┬─────┘         │  │
│  └─────────┼──────────────┼────────────────┼───────────────┘  │
│            │              │                │                   │
│  ┌─────────▼──────────────▼────────────────▼───────────────┐  │
│  │               Services Layer                              │  │
│  │  • Business Logic                                         │  │
│  │  • Validation                                             │  │
│  │  • Transaction Management                                 │  │
│  └──────────────────────┬────────────────────────────────────┘  │
│                         │                                       │
│  ┌──────────────────────▼────────────────────────────────────┐  │
│  │             Repository Layer (JPA)                        │  │
│  │  • Data Access                                            │  │
│  │  • Entity Management                                      │  │
│  └──────────────────────┬────────────────────────────────────┘  │
│                         │                                       │
└─────────────────────────┼───────────────────────────────────────┘
                          │
                          │ JDBC
                          │
┌─────────────────────────▼───────────────────────────────────────┐
│                   PostgreSQL Database                            │
│                   localhost:5432/edoctorat_db                    │
├─────────────────────────────────────────────────────────────────┤
│  • auth_user                                                     │
│  • candidat                                                      │
│  • professeur                                                    │
│  • directeur_labo                                                │
│  • sujet                                                         │
│  • candidature                                                   │
│  • ... (all tables)                                              │
└─────────────────────────────────────────────────────────────────┘
```

## Authentication Flow

```
┌─────────┐           ┌─────────┐           ┌──────────┐
│  User   │           │ React   │           │  Spring  │
│ Browser │           │Frontend │           │  Backend │
└────┬────┘           └────┬────┘           └────┬─────┘
     │                     │                     │
     │  1. Enter Credentials                     │
     │ ──────────────────> │                     │
     │                     │                     │
     │                     │  2. POST /auth/login│
     │                     │ ──────────────────> │
     │                     │    {username, pwd}  │
     │                     │                     │
     │                     │                     │ 3. Validate
     │                     │                     │    Credentials
     │                     │                     │    & Generate JWT
     │                     │                     │
     │                     │  4. Return Token    │
     │                     │ <────────────────── │
     │                     │   {token, user, role}
     │                     │                     │
     │  5. Display Dashboard                     │
     │                Store Token in localStorage│
     │ <────────────────── │                     │
     │                     │                     │
     │                     │  6. Subsequent Requests
     │                     │ ──────────────────> │
     │                     │  Authorization:     │
     │                     │  Bearer <token>     │
     │                     │                     │
     │                     │  7. Validate Token  │
     │                     │     & Return Data   │
     │                     │ <────────────────── │
     │  8. Display Data    │                     │
     │ <────────────────── │                     │
     │                     │                     │
```

## API Request Flow

```
┌──────────────────────────────────────────────────────────────────┐
│                     Frontend Request                              │
└───────────────────────────┬──────────────────────────────────────┘
                            │
                ┌───────────▼────────────┐
                │  Axios Interceptor     │
                │  (Add JWT Token)       │
                └───────────┬────────────┘
                            │
                ┌───────────▼────────────┐
                │   HTTP Request         │
                │   to Backend           │
                └───────────┬────────────┘
                            │
┌───────────────────────────▼──────────────────────────────────────┐
│                     Backend Processing                            │
├──────────────────────────────────────────────────────────────────┤
│                                                                   │
│  1. CORS Filter  ────> Check Origin                              │
│                                                                   │
│  2. JWT Filter   ────> Validate Token                            │
│                        Extract User Info                          │
│                                                                   │
│  3. Security     ────> Check Permissions                         │
│                        Authorize Request                          │
│                                                                   │
│  4. Controller   ────> Route to Handler                          │
│                        Validate Input                             │
│                                                                   │
│  5. Service      ────> Business Logic                            │
│                        Process Request                            │
│                                                                   │
│  6. Repository   ────> Database Query                            │
│                        Return Data                                │
│                                                                   │
│  7. Response     ────> Format Response                           │
│                        Return to Client                           │
│                                                                   │
└───────────────────────────┬──────────────────────────────────────┘
                            │
                ┌───────────▼────────────┐
                │  HTTP Response         │
                └───────────┬────────────┘
                            │
                ┌───────────▼────────────┐
                │  Axios Interceptor     │
                │  (Handle Errors)       │
                └───────────┬────────────┘
                            │
┌───────────────────────────▼──────────────────────────────────────┐
│                   Frontend Processing                             │
│  • Update State                                                   │
│  • Re-render UI                                                   │
│  • Handle Errors                                                  │
└──────────────────────────────────────────────────────────────────┘
```

## Service Layer Organization

```
Frontend Services Structure:
├── services/
│   ├── index.js                 (Export all services)
│   ├── authService.js           (Authentication)
│   ├── candidatService.js       (Candidate operations)
│   ├── professeurService.js     (Professor operations)
│   ├── directeurLaboService.js  (Lab Director)
│   ├── directeurCEDService.js   (CED Director)
│   ├── directeurPoleService.js  (Pole Director)
│   └── scolariteService.js      (Scolarite)

Backend Controllers Structure:
├── Auth/Controllers/
│   └── AuthController.java
├── Candidat/controllers/
│   ├── CandidatController.java
│   ├── CandidatDiplomeController.java
│   ├── CandidatPostulerController.java
│   └── CandidatNotificationController.java
├── Professeur/Controllers/
│   ├── ProfesseurController.java
│   ├── ProfesseurSujetController.java
│   └── ProfesseurExaminerController.java
├── DirecteurLabo/Controllers/
├── DirecteurCED/Controllers/
├── DirecteurPole/Controllers/
└── Scolarite/Controllers/
```

## Data Flow by User Role

### Candidat Flow
```
Login → Dashboard → View Subjects → Apply → Track Applications
  │         │           │            │         │
  v         v           v            v         v
Auth   Profile API  Subjects API  Apply API  Status API
```

### Professeur Flow
```
Login → Dashboard → Propose Subject → View Candidates → Evaluate
  │         │            │                │               │
  v         v            v                v               v
Auth   Profile API   Subject API    Candidates API  Evaluation API
```

### Directeur Flow
```
Login → Dashboard → Manage → View Results → Generate Reports
  │         │         │          │              │
  v         v         v          v              v
Auth   Profile   Admin API  Results API   Reports API
```

## Technology Stack

### Frontend
- React 19.2.3
- Axios 1.13.2
- React Router 7.12.0
- Styled Components 6.3.6

### Backend
- Spring Boot 3.2.0
- Spring Security (JWT)
- PostgreSQL
- JPA/Hibernate

### Communication
- REST API
- JSON format
- JWT authentication
- CORS enabled

---

**Architecture Version:** 1.0.0  
**Date:** 2026-01-18
