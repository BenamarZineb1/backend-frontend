# E-Doctorat Platform - Quick Reference Card

## 🚀 Quick Start Commands

### Start Backend
```bash
# Windows
start-backend.bat

# Linux/Mac
./start-backend.sh

# Manual
cd Edoctorat-F/Edoctorat
mvn spring-boot:run
```

### Start Frontend
```bash
# Windows
start-frontend.bat

# Linux/Mac
./start-frontend.sh

# Manual
cd PROJETEdoctorat-main/PROJETEdoctorat-main
npm start
```

## 🌐 URLs

| Service | URL |
|---------|-----|
| Backend API | http://localhost:8085 |
| Frontend | http://localhost:3000 |
| Database | localhost:5432 |

## 🔑 Import Services

```javascript
// Single import
import AuthService from './services/authService';

// Multiple imports
import { 
  AuthService, 
  CandidatService, 
  ProfesseurService 
} from './services';
```

## 📝 Common Operations

### Authentication
```javascript
// Login
const response = await AuthService.login(email, password);

// Get current user
const user = AuthService.getCurrentUserInfo();

// Check if authenticated
const isAuth = AuthService.isAuthenticated();

// Logout
AuthService.logout();
```

### Candidat Operations
```javascript
// Get profile
const profile = await CandidatService.getProfile();

// Update profile
await CandidatService.updateProfile(data);

// Apply to subject
await CandidatService.postuler(sujetId);

// Get applications
const candidatures = await CandidatService.getMyCandidatures();
```

### Professeur Operations
```javascript
// Propose subject
await ProfesseurService.proposerSujet(sujetData);

// Get subjects
const sujets = await ProfesseurService.getMesSujets(profId);

// Get candidates
const candidats = await ProfesseurService.getMesCandidats(commissionId);

// Evaluate
await ProfesseurService.evaluerCandidat(evaluationData);
```

## 🧪 Test Connection

```javascript
// Run all tests
await ConnectionTest.runAllTests();

// Test backend
await ConnectionTest.testBackendConnection();

// Get connection info
ConnectionTest.getConnectionInfo();
```

## 🔧 Configuration Files

| File | Purpose |
|------|---------|
| `.env` | Frontend environment variables |
| `application.properties` | Backend configuration |
| `axios.js` | API client configuration |
| `apiConfig.js` | API endpoints mapping |

## 📡 API Endpoints Quick Map

### Auth
- `POST /auth/login` - Login
- `POST /auth/register` - Register

### Candidat
- `GET /candidat/me` - Profile
- `POST /candidat/postuler/:id` - Apply
- `GET /candidat/mes-candidatures` - Applications

### Professeur
- `POST /professeur/sujets/proposer` - Propose subject
- `GET /professeur/sujets/mes-sujets/:id` - Get subjects
- `PUT /examiner/evaluer` - Evaluate

### Directors
- `GET /directeur/ced/candidats` - CED candidates
- `GET /directeur/pole/sujets` - Pole subjects
- `GET /directeur-labo/labo/:id/candidats` - Labo candidates

### Scolarite
- `GET /scolarite/dossiers` - All files
- `PUT /scolarite/valider/:id` - Validate

## 🐛 Debug Checklist

- [ ] Backend running on port 8085?
- [ ] Frontend running on port 3000?
- [ ] PostgreSQL running?
- [ ] Database configured correctly?
- [ ] Token stored in localStorage?
- [ ] CORS errors in console?
- [ ] Check Network tab for failed requests

## 💾 localStorage Keys

| Key | Content |
|-----|---------|
| `user_token` | JWT authentication token |
| `user_role` | User role (CANDIDAT, PROFESSEUR, etc.) |
| `user_info` | Complete user information (JSON) |

## 🔐 User Roles

- `CANDIDAT` - Candidate user
- `PROFESSEUR` - Professor
- `DIRECTEUR_LABO` - Lab Director
- `DIRECTEUR_CED` - CED Director
- `DIRECTEUR_POLE` - Pole Director
- `SCOLARITE` - Scolarite staff
- `ADMIN` - Administrator

## ⚡ Quick Fixes

### Clear Authentication
```javascript
localStorage.clear();
// or
ConnectionTest.clearStoredData();
```

### Restart Backend
```bash
# Kill process on port 8085
# Windows
netstat -ano | findstr :8085
taskkill /PID <PID> /F

# Linux/Mac
lsof -ti:8085 | xargs kill -9
```

### Clear npm cache
```bash
npm cache clean --force
rm -rf node_modules
npm install
```

## 📞 Support Resources

| Resource | Location |
|----------|----------|
| Setup Guide | CONNECTION_SETUP_GUIDE.md |
| Implementation Summary | IMPLEMENTATION_SUMMARY.md |
| Backend Code | Edoctorat-F/Edoctorat/ |
| Frontend Code | PROJETEdoctorat-main/ |

## 🎨 Example Usage in Component

```javascript
import React, { useState, useEffect } from 'react';
import { CandidatService } from '../services';

function MyComponent() {
  const [profile, setProfile] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const data = await CandidatService.getProfile();
        setProfile(data);
      } catch (error) {
        console.error('Error:', error);
      } finally {
        setLoading(false);
      }
    };
    
    fetchProfile();
  }, []);

  if (loading) return <div>Loading...</div>;
  
  return <div>{profile?.name}</div>;
}
```

## 🔍 Browser Console Commands

```javascript
// Check connection
ConnectionTest.getConnectionInfo()

// Test all endpoints
ConnectionTest.runAllTests()

// View stored token
localStorage.getItem('user_token')

// View user info
JSON.parse(localStorage.getItem('user_info'))
```

---

**Version:** 1.0.0  
**Last Updated:** 2026-01-18
