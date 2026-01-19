import api from '../api/axios';

const DIRECTEUR_POLE_API = '/directeur/pole';

const DirecteurPoleService = {

  // ============== CONSULTATION ==============
  
  // Get all candidates
  getAllCandidats: async () => {
    const response = await api.get(`${DIRECTEUR_POLE_API}/candidats`);
    return response.data;
  },

  // Get all subjects
  getAllSujets: async () => {
    const response = await api.get(`${DIRECTEUR_POLE_API}/sujets`);
    return response.data;
  },

  // Get all results
  getResultats: async () => {
    const response = await api.get(`${DIRECTEUR_POLE_API}/resultats`);
    return response.data;
  },

  // Get all inscriptions
  getInscriptions: async () => {
    const response = await api.get(`${DIRECTEUR_POLE_API}/inscriptions`);
    return response.data;
  },

  // ============== CALENDRIER ==============
  
  // Add calendar entry
  ajouterCalendrier: async (calendrierData) => {
    const response = await api.post(`${DIRECTEUR_POLE_API}/calendrier`, calendrierData);
    return response.data;
  },

  // Get calendar
  getCalendrier: async () => {
    const response = await api.get(`${DIRECTEUR_POLE_API}/calendrier`);
    return response.data;
  },

  // Check if subject submission is open
  isDepotSujetOuvert: async () => {
    const response = await api.get(`${DIRECTEUR_POLE_API}/calendrier/depot-sujet-ouvert`);
    return response.data;
  },

  // ============== GESTION SUJETS ==============
  
  // Get pending subjects
  getSujetsEnAttente: async () => {
    const response = await api.get(`${DIRECTEUR_POLE_API}/sujets/en-attente`);
    return response.data;
  },

  // Publish selected subjects
  publierSujets: async (idsSujets) => {
    const response = await api.put(`${DIRECTEUR_POLE_API}/sujets/publier`, idsSujets);
    return response.data;
  },

  // ============== PUBLICATION ==============
  
  // Publish LP (Liste Principale)
  publierLP: async () => {
    const response = await api.post(`${DIRECTEUR_POLE_API}/publier-lp`);
    return response.data;
  },

  // Publish LA (Liste d'Attente)
  publierLA: async () => {
    const response = await api.post(`${DIRECTEUR_POLE_API}/publier-la`);
    return response.data;
  }
};

export default DirecteurPoleService;
