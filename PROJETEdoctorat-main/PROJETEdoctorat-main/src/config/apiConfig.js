// API Endpoints Configuration
export const API_CONFIG = {
  BASE_URL: process.env.REACT_APP_API_URL || 'http://localhost:8085',
  TIMEOUT: 30000, // 30 seconds
  
  // Authentication endpoints
  AUTH: {
    REGISTER: '/auth/register',
    LOGIN: '/auth/login',
    LOGOUT: '/auth/logout',
  },
  
  // Candidat endpoints
  CANDIDAT: {
    BASE: '/candidat',
    PROFILE: '/candidat/me',
    DIPLOMES: '/candidat/diplomes',
    POSTULER: '/candidat/postuler',
    CANDIDATURES: '/candidat/mes-candidatures',
    NOTIFICATIONS: '/candidat/notifications',
  },
  
  // Professeur endpoints
  PROFESSEUR: {
    BASE: '/professeur',
    SUJETS: '/professeur/sujets',
    PROPOSER_SUJET: '/professeur/sujets/proposer',
    MES_SUJETS: '/professeur/sujets/mes-sujets',
  },
  
  // Examiner endpoints
  EXAMINER: {
    BASE: '/examiner',
    EVALUER: '/examiner/evaluer',
    COMMISSION: '/examiner/commission',
    PV: '/examiner/pv',
    ENVOYER_PV: '/examiner/envoyer-pv',
    PV_GLOBAL: '/examiner/pv-global',
  },
  
  // Directeur Labo endpoints
  DIRECTEUR_LABO: {
    BASE: '/directeur-labo',
    AFFECTER_SUJET: '/directeur-labo/sujet',
    CANDIDATS: '/directeur-labo/labo',
    COMMISSION: '/directeur-labo/commission',
  },
  
  // Directeur CED endpoints
  DIRECTEUR_CED: {
    BASE: '/directeur/ced',
    CANDIDATS: '/directeur/ced/candidats',
    SUJETS: '/directeur/ced/sujets',
    RESULTATS: '/directeur/ced/resultats',
    INSCRIPTIONS: '/directeur/ced/inscriptions',
  },
  
  // Directeur Pole endpoints
  DIRECTEUR_POLE: {
    BASE: '/directeur/pole',
    CANDIDATS: '/directeur/pole/candidats',
    SUJETS: '/directeur/pole/sujets',
    RESULTATS: '/directeur/pole/resultats',
    INSCRIPTIONS: '/directeur/pole/inscriptions',
    CALENDRIER: '/directeur/pole/calendrier',
    SUJETS_EN_ATTENTE: '/directeur/pole/sujets/en-attente',
    PUBLIER_SUJETS: '/directeur/pole/sujets/publier',
    PUBLIER_LP: '/directeur/pole/publier-lp',
    PUBLIER_LA: '/directeur/pole/publier-la',
  },
  
  // Scolarite endpoints
  SCOLARITE: {
    BASE: '/scolarite',
    DOSSIERS: '/scolarite/dossiers',
    DOSSIER: '/scolarite/dossier',
    VALIDER: '/scolarite/valider',
    COMMENTER: '/scolarite/commenter',
  },
  
  // Public endpoints
  PUBLIC: {
    SUJETS_DISPONIBLES: '/sujets/disponibles',
  }
};

// User roles
export const USER_ROLES = {
  CANDIDAT: 'CANDIDAT',
  PROFESSEUR: 'PROFESSEUR',
  DIRECTEUR_LABO: 'DIRECTEUR_LABO',
  DIRECTEUR_CED: 'DIRECTEUR_CED',
  DIRECTEUR_POLE: 'DIRECTEUR_POLE',
  SCOLARITE: 'SCOLARITE',
  ADMIN: 'ADMIN',
};

// API response status codes
export const HTTP_STATUS = {
  OK: 200,
  CREATED: 201,
  NO_CONTENT: 204,
  BAD_REQUEST: 400,
  UNAUTHORIZED: 401,
  FORBIDDEN: 403,
  NOT_FOUND: 404,
  INTERNAL_SERVER_ERROR: 500,
};

export default API_CONFIG;
