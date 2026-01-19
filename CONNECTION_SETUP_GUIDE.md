# Backend-Frontend Connection Setup Guide

## Overview
This guide explains the complete connection between the Spring Boot backend and React frontend for the E-Doctorat platform.

## Architecture

### Backend (Spring Boot)
- **Port**: 8085
- **Base URL**: http://localhost:8085
- **Security**: JWT-based authentication
- **CORS**: Configured for React frontend (ports 3000, 3001)

### Frontend (React)
- **Port**: 3000 (default)
- **API Base URL**: http://localhost:8085
- **Proxy**: Configured in package.json
- **Authentication**: Token stored in localStorage

## Setup Instructions

### 1. Backend Setup

#### Prerequisites
- Java 17+
- PostgreSQL database
- Maven

#### Configuration
Edit `application.properties`:
```properties
server.port=8085
spring.datasource.url=jdbc:postgresql://localhost:5432/edoctorat_db
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
jwt.secret=MonSuperSecretTresTresLongPourEdoctorat2024SecuriteMaximale123456789
```

#### Start Backend
```bash
cd Edoctorat-F/Edoctorat
mvn clean install
mvn spring-boot:run
```

Backend will be available at: http://localhost:8085

### 2. Frontend Setup

#### Prerequisites
- Node.js 14+
- npm or yarn

#### Configuration
The frontend is pre-configured with:
- `.env` file with API URL
- Axios interceptors for authentication
- Proxy configuration in package.json

#### Install Dependencies
```bash
cd PROJETEdoctorat-main/PROJETEdoctorat-main
npm install
```

#### Start Frontend
```bash
npm start
```

Frontend will be available at: http://localhost:3000

## API Connection Details

### Authentication Flow

1. **Login Request** (Frontend → Backend)
   ```javascript
   POST /auth/login
   Body: { username: "user@example.com", password: "password" }
   ```

2. **Login Response** (Backend → Frontend)
   ```json
   {
     "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
     "role": "CANDIDAT",
     "message": "Connecte avec succès",
     "user": {
       "id": 1,
       "username": "user@example.com",
       "email": "user@example.com",
       "firstName": "John",
       "lastName": "Doe",
       "role": "CANDIDAT"
     }
   }
   ```

3. **Token Storage** (Frontend)
   ```javascript
   localStorage.setItem('user_token', response.data.token);
   localStorage.setItem('user_role', response.data.role);
   localStorage.setItem('user_info', JSON.stringify(response.data.user));
   ```

4. **Authenticated Requests** (Frontend → Backend)
   ```javascript
   // Axios automatically adds header via interceptor
   Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   ```

### Available Services

#### 1. Auth Service (`authService.js`)
- `register(userData)` - Register new user
- `login(email, password)` - Login user
- `logout()` - Logout user
- `getCurrentUserToken()` - Get stored token
- `getCurrentUserRole()` - Get user role
- `getCurrentUserInfo()` - Get user info
- `isAuthenticated()` - Check authentication status

#### 2. Candidat Service (`candidatService.js`)
- `getProfile()` - Get candidate profile
- `updateProfile(data)` - Update profile
- `getDiplomes()` - Get diplomas
- `addDiplome(data, file)` - Add diploma
- `deleteDiplome(id)` - Delete diploma
- `getAllSujets()` - Get available subjects
- `postuler(sujetId)` - Apply to subject
- `getMyCandidatures()` - Get applications
- `getNotifications()` - Get notifications

#### 3. Professeur Service (`professeurService.js`)
- `proposerSujet(sujetData)` - Propose subject
- `getMesSujets(professeurId)` - Get subjects
- `getMesCandidats(commissionId)` - Get candidates
- `evaluerCandidat(evaluationData)` - Evaluate candidate
- `genererPv(commissionId)` - Generate PV
- `envoyerPv(commissionId)` - Send PV

#### 4. Directeur Labo Service (`directeurLaboService.js`)
- `affecterSujet(sujetId, professeurId)` - Assign subject
- `getCandidatsDuLabo(laboId)` - Get lab candidates
- `creerCommission(data)` - Create commission
- `ajouterSujetACommission(commissionId, sujetId)` - Add subject to commission
- `ajouterMembreCommission(commissionId, professeurId)` - Add member

#### 5. Directeur CED Service (`directeurCEDService.js`)
- `getAllCandidats()` - Get all candidates
- `getAllSujets()` - Get all subjects
- `getResultats()` - Get results
- `getInscriptions()` - Get inscriptions

#### 6. Directeur Pole Service (`directeurPoleService.js`)
- `getAllCandidats()` - Get candidates
- `getAllSujets()` - Get subjects
- `ajouterCalendrier(data)` - Add calendar
- `getCalendrier()` - Get calendar
- `getSujetsEnAttente()` - Get pending subjects
- `publierSujets(ids)` - Publish subjects
- `publierLP()` - Publish main list
- `publierLA()` - Publish waiting list

#### 7. Scolarite Service (`scolariteService.js`)
- `getTousLesDossiers()` - Get all files
- `getDossierParCne(cne)` - Get file by CNE
- `validerDossier(candidatId)` - Validate file
- `commenterDossier(candidatId, comment)` - Add comment

## Usage Examples

### Example 1: Login
```javascript
import AuthService from './services/authService';

const handleLogin = async () => {
  try {
    const response = await AuthService.login('user@example.com', 'password');
    console.log('Login successful:', response);
    console.log('User role:', response.role);
    // Redirect based on role
  } catch (error) {
    console.error('Login failed:', error);
  }
};
```

### Example 2: Get Candidate Profile
```javascript
import CandidatService from './services/candidatService';

const fetchProfile = async () => {
  try {
    const profile = await CandidatService.getProfile();
    console.log('Profile:', profile);
  } catch (error) {
    console.error('Error fetching profile:', error);
  }
};
```

### Example 3: Propose Subject (Professor)
```javascript
import ProfesseurService from './services/professeurService';

const proposeSubject = async () => {
  try {
    const sujetData = {
      professeurId: 1,
      titre: 'AI in Healthcare',
      description: 'Research on AI applications...'
    };
    const result = await ProfesseurService.proposerSujet(sujetData);
    console.log('Subject proposed:', result);
  } catch (error) {
    console.error('Error proposing subject:', error);
  }
};
```

## Error Handling

The frontend axios configuration includes automatic error handling:

- **401 Unauthorized**: Clears tokens and redirects to login
- **403 Forbidden**: Logs access forbidden error
- **404 Not Found**: Logs resource not found
- **500 Server Error**: Logs server error
- **Network Error**: Logs network connection issues

## Security Features

### Backend
- JWT token authentication
- BCrypt password hashing
- CORS configuration
- Role-based access control
- Stateless sessions

### Frontend
- Token stored in localStorage
- Automatic token injection in requests
- Token expiration handling
- Protected routes
- Role-based UI rendering

## Troubleshooting

### Issue: CORS Errors
**Solution**: Ensure backend CORS configuration includes your frontend URL:
```java
configuration.setAllowedOrigins(Arrays.asList(
    "http://localhost:3000",
    "http://localhost:3001"
));
```

### Issue: 401 Unauthorized
**Solution**: 
1. Check if token is stored: `localStorage.getItem('user_token')`
2. Verify token hasn't expired
3. Check backend authentication configuration

### Issue: Connection Refused
**Solution**:
1. Ensure backend is running on port 8085
2. Check PostgreSQL database is running
3. Verify network connectivity

### Issue: Token Not Sent
**Solution**: Check axios interceptor configuration in `axios.js`

## Testing the Connection

### 1. Test Backend Health
```bash
curl http://localhost:8085/auth/login
```

### 2. Test Frontend Connection
Open browser console and check network tab for API calls

### 3. Test Authentication Flow
1. Open frontend: http://localhost:3000
2. Go to login page
3. Enter credentials
4. Check browser console for API responses
5. Verify token in localStorage

## Environment Variables

### Frontend `.env`
```
REACT_APP_API_URL=http://localhost:8085
REACT_APP_NAME=E-Doctorat Platform
REACT_APP_VERSION=1.0.0
REACT_APP_MAX_FILE_SIZE=10485760
```

### Backend `application.properties`
```properties
server.port=8085
jwt.secret=your_secret_key
jwt.expiration=86400000
spring.datasource.url=jdbc:postgresql://localhost:5432/edoctorat_db
```

## Production Deployment

### Backend
1. Update `application.properties` with production database
2. Build: `mvn clean package`
3. Deploy JAR file to server
4. Update CORS to include production frontend URL

### Frontend
1. Update `.env` with production API URL
2. Build: `npm run build`
3. Deploy `build` folder to web server
4. Configure reverse proxy (nginx/Apache)

## Support

For issues or questions:
1. Check console errors (F12 in browser)
2. Verify both backend and frontend are running
3. Check network tab for failed requests
4. Review backend logs for errors
