import api from '../api/axios';

const SCOLARITE_API = '/scolarite';

const ScolariteService = {

  // ============== DOSSIERS ==============
  
  // Get all student files
  getTousLesDossiers: async () => {
    const response = await api.get(`${SCOLARITE_API}/dossiers`);
    return response.data;
  },

  // Get student file by CNE
  getDossierParCne: async (cne) => {
    const response = await api.get(`${SCOLARITE_API}/dossier/${cne}`);
    return response.data;
  },

  // Validate student file
  validerDossier: async (candidatId) => {
    const response = await api.put(`${SCOLARITE_API}/valider/${candidatId}`);
    return response.data;
  },

  // Add comment to student file
  commenterDossier: async (candidatId, commentaire) => {
    const response = await api.put(`${SCOLARITE_API}/commenter/${candidatId}`, null, {
      params: { commentaire }
    });
    return response.data;
  }
};

export default ScolariteService;
