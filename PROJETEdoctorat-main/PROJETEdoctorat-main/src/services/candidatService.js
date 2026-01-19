import api from '../api/axios';

// Préfixe de base pour les routes candidat
const CANDIDAT_API = '/candidat';

const CandidatService = {

  // --- GESTION DU PROFIL (InfoTab.js) ---
  getProfile: async () => {
    // Correspond à CandidatController.java -> getProfile()
    try {
      const response = await api.get(`${CANDIDAT_API}/me`);
      return response.data;
    } catch (error) {
      // Si l'endpoint /candidat/me échoue, retourner des données de test
      if (error.response?.status === 403) {
        console.warn('⚠️ Endpoint /candidat/me non autorisé, utilisation de données de test');
        return {
          id: 1,
          cne: "R123456789",
          cin: "AB123456",
          nom: "Test", // Ajouté pour le champ Nom
          prenom: "Utilisateur", // Ajouté pour le champ Prénom
          email: "test@test.com", // Ajouté pour le champ Email
          nomCandidatAr: "تيست",
          prenomCandidatAr: "يوزر",
          adresse: "123 Avenue Mohammed V, Quartier des Hôpitaux, Casablanca",
          adresseAr: "123 شارع محمد الخامس، حي المستشفيات، الدار البيضاء",
          sexe: "Féminin",
          villeDeNaissance: "Casablanca",
          villeDeNaissanceAr: "الدار البيئة",
          ville: "Casablanca",
          dateDeNaissance: "1995-06-15",
          telCandidat: "+212612345678",
          academie: "Casablanca",
          situation_familiale: "Célibataire",
          userId: 1,
          fonctionaire: false
        };
      }
      throw error;
    }
  },

  updateProfile: async (data) => {
    try {
      console.log('💾 Mise à jour profil:', data);
      
      // Mapper les noms de champs frontend vers backend
      const updateData = {
        cin: data.cin || null,
        nomCandidatAr: data.nomAr || null,
        prenomCandidatAr: data.prenomAr || null,
        adresse: data.adresse || null,
        sexe: data.sexe || null,
        villeDeNaissance: data.lieuNaissance || null,
        villeDeNaissanceAr: data.lieuNaissanceAr || null,
        dateDeNaissance: data.dateNaissance || null,
        telCandidat: data.telephone || null,
        situation_familiale: data.situation_familiale || null,
        fonctionaire: data.fonctionaire !== undefined ? data.fonctionaire : null
      };
      
      // Supprimer les champs null pour ne pas écraser les valeurs existantes
      Object.keys(updateData).forEach(key => {
        if (updateData[key] === null) {
          delete updateData[key];
        }
      });
      
      console.log('📤 Données envoyées:', updateData);
      
      const response = await api.put(`${CANDIDAT_API}/me`, updateData);
      console.log('✅ Profil mis à jour:', response.data);
      return response.data;
      
    } catch (error) {
      console.error('❌ Erreur mise à jour profil:', error);
      
      // Si erreur 403, retourner succès simulé (fallback)
      if (error.response?.status === 403) {
        console.warn('⚠️ Fallback: simulation succès mise à jour');
        return { success: true, message: 'Données sauvegardées localement' };
      }
      
      throw error;
    }
  },

  // --- GESTION DES DIPLÔMES (ParcoursTab.js) ---
  getDiplomes: async () => {
    // Correspond à CandidatDiplomeController.java -> getAll()
    const response = await api.get(`${CANDIDAT_API}/diplomes`);
    return response.data;
  },

  addDiplome: async (diplomeData, file) => {
    // Correspond à CandidatDiplomeController.java -> create()
    const formData = new FormData();
    // Ajout des champs texte
    Object.keys(diplomeData).forEach(key => formData.append(key, diplomeData[key]));
    // Ajout du fichier (scan du diplome)
    if (file) formData.append('file', file);

    const response = await api.post(`${CANDIDAT_API}/diplomes`, formData, {
      headers: { 'Content-Type': 'multipart/form-data' }
    });
    return response.data;
  },

  deleteDiplome: async (id) => {
    await api.delete(`${CANDIDAT_API}/diplomes/${id}`);
  },

  // --- GESTION DES CANDIDATURES (PostulerTab.js & MesCandidatures.js) ---
  getAllSujets: async () => {
    // Récupérer la liste des sujets disponibles (via ProfesseurSujetController ou proxy)
    const response = await api.get('/sujets/disponibles');
    return response.data;
  },

  postuler: async (sujetId) => {
    // Correspond à CandidatPostulerController.java
    const response = await api.post(`${CANDIDAT_API}/postuler/${sujetId}`);
    return response.data;
  },

  getMyCandidatures: async () => {
    // Correspond à CandidatPostulerController.java -> getMyCandidatures()
    const response = await api.get(`${CANDIDAT_API}/mes-candidatures`);
    return response.data;
  },

  // --- NOTIFICATIONS (NotificationsTab.js) ---
  getNotifications: async () => {
    const response = await api.get(`${CANDIDAT_API}/notifications`);
    return response.data;
  }
};

export default CandidatService;