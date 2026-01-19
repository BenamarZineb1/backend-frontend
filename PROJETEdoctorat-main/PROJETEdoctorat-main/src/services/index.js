/**
 * Central export for all API services
 * Import services like: import { AuthService, CandidatService } from './services';
 */

export { default as AuthService } from './authService';
export { default as CandidatService } from './candidatService';
export { default as ProfesseurService } from './professeurService';
export { default as DirecteurLaboService } from './directeurLaboService';
export { default as DirecteurCEDService } from './directeurCEDService';
export { default as DirecteurPoleService } from './directeurPoleService';
export { default as ScolariteService } from './scolariteService';

// Also export API instance for custom requests
export { default as api } from '../api/axios';
