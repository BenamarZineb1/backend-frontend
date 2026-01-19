import api from '../api/axios';

const DIRECTEUR_CED_API = '/directeur/ced';

const DirecteurCEDService = {

  // ============== CANDIDATS ==============
  
  // Get all candidates
  getAllCandidats: async () => {
    const response = await api.get(`${DIRECTEUR_CED_API}/candidats`);
    return response.data;
  },

  // ============== SUJETS ==============
  
  // Get all subjects
  getAllSujets: async () => {
    const response = await api.get(`${DIRECTEUR_CED_API}/sujets`);
    return response.data;
  },

  // ============== RESULTATS ==============
  
  // Get all results
  getResultats: async () => {
    const response = await api.get(`${DIRECTEUR_CED_API}/resultats`);
    return response.data;
  },

  // ============== INSCRIPTIONS ==============
  
  // Get all inscriptions
  getInscriptions: async () => {
    const response = await api.get(`${DIRECTEUR_CED_API}/inscriptions`);
    return response.data;
  }
};

export default DirecteurCEDService;
