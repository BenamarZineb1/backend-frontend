import api from '../api/axios';

const PROFESSEUR_API = '/professeur';
const EXAMINER_API = '/examiner';

const ProfesseurService = {

  // ============== SUJETS ==============
  
  // Propose a new research topic
  proposerSujet: async (sujetData) => {
    const response = await api.post(`${PROFESSEUR_API}/sujets/proposer`, sujetData);
    return response.data;
  },

  // Get all subjects of a professor
  getMesSujets: async (professeurId) => {
    const response = await api.get(`${PROFESSEUR_API}/sujets/mes-sujets/${professeurId}`);
    return response.data;
  },

  // ============== CANDIDATS ==============
  
  // Get candidates for a specific commission
  getMesCandidats: async (commissionId) => {
    const response = await api.get(`${EXAMINER_API}/commission/${commissionId}`);
    return response.data;
  },

  // Evaluate a candidate
  evaluerCandidat: async (evaluationData) => {
    const response = await api.put(`${EXAMINER_API}/evaluer`, evaluationData);
    return response.data;
  },

  // ============== PV (PROCES-VERBAL) ==============
  
  // Generate PV for commission
  genererPv: async (commissionId) => {
    const response = await api.get(`${EXAMINER_API}/pv/${commissionId}`, {
      responseType: 'blob'
    });
    return response.data;
  },

  // Send PV to lab director
  envoyerPv: async (commissionId) => {
    const response = await api.post(`${EXAMINER_API}/envoyer-pv/${commissionId}`);
    return response.data;
  },

  // Get global PV
  getPvGlobal: async () => {
    const response = await api.get(`${EXAMINER_API}/pv-global`);
    return response.data;
  }
};

export default ProfesseurService;
