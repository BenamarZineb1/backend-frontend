import api from '../api/axios';

const DIRECTEUR_LABO_API = '/directeur-labo';

const DirecteurLaboService = {

  // ============== SUJETS ==============
  
  // Assign subject to professor
  affecterSujet: async (sujetId, professeurId) => {
    const response = await api.put(`${DIRECTEUR_LABO_API}/sujet/${sujetId}/affecter/${professeurId}`);
    return response.data;
  },

  // ============== CANDIDATS ==============
  
  // Get candidates of a specific lab
  getCandidatsDuLabo: async (laboId) => {
    const response = await api.get(`${DIRECTEUR_LABO_API}/labo/${laboId}/candidats`);
    return response.data;
  },

  // ============== COMMISSION ==============
  
  // Create a commission
  creerCommission: async (commissionData) => {
    const response = await api.post(`${DIRECTEUR_LABO_API}/commission`, commissionData);
    return response.data;
  },

  // Add subject to commission
  ajouterSujetACommission: async (commissionId, sujetId) => {
    const response = await api.post(`${DIRECTEUR_LABO_API}/commission/${commissionId}/sujet/${sujetId}`);
    return response.data;
  },

  // Add member to commission
  ajouterMembreCommission: async (commissionId, professeurId) => {
    const response = await api.post(`${DIRECTEUR_LABO_API}/commission/${commissionId}/membre/${professeurId}`);
    return response.data;
  }
};

export default DirecteurLaboService;
