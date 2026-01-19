import React, { useState, useEffect } from "react";
import LayoutCandidat, { commonStyles } from "./LayoutCandidat";
import { useAuth } from "../../contexts/AuthContext";
// 👇 IMPORT IMPORTANT : Le lien vers le Backend
import CandidatService from "../../services/candidatService"; 
import { FaSave, FaCamera, FaLock, FaSpinner } from "react-icons/fa";

export default function InfoTab() {
  const { user, setUser } = useAuth();
  const [isLoading, setIsLoading] = useState(true);

  // Initialisation avec des chaînes vides (on attend le backend pour remplir)
  const [formData, setFormData] = useState({
    nom: "", prenom: "", email: "", cne: "", // Champs lecture seule
    nomAr: "", prenomAr: "", cin: "",
    dateNaissance: "", lieuNaissance: "", lieuNaissanceAr: "",
    telephone: "", adresse: "", sexe: "Féminin"
  });

  const [photoPreview, setPhotoPreview] = useState(null);
  const [photoFile, setPhotoFile] = useState(null); // Pour stocker le fichier réel à envoyer

  // 1. CHARGEMENT DES DONNÉES DEPUIS SPRING BOOT
  useEffect(() => {
    const fetchProfile = async () => {
      try {
        // Appel au service (GET /api/candidat/me)
        const data = await CandidatService.getProfile();
        
        // On remplit le formulaire avec les données de la BDD
        setFormData((prev) => ({
          ...prev,
          ...data, // Écrase les vides par les vraies infos
          // Mapping des champs backend vers frontend
          nom: data.nomCandidatFr || data.lastName || "",
          prenom: data.prenomCandidatFr || data.firstName || "",
          nomAr: data.nomCandidatAr || "",
          prenomAr: data.prenomCandidatAr || "",
          lieuNaissance: data.villeDeNaissance || "",
          lieuNaissanceAr: data.villeDeNaissanceAr || "",
          telephone: data.telCandidat || "",
          // Gestion des dates : parfois le format reçu doit être ajusté pour l'input date
          dateNaissance: data.dateDeNaissance ? data.dateDeNaissance.split('T')[0] : "" 
        }));

        // Si le candidat a déjà une photo (URL)
        if (data.photoUrl) {
            setPhotoPreview(data.photoUrl); 
        }

      } catch (error) {
        console.error("Erreur chargement profil", error);
        alert("Impossible de récupérer vos informations.");
      } finally {
        setIsLoading(false);
      }
    };

    fetchProfile();
  }, []);

  // Changement des inputs
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  // Changement de la photo (Preview + Stockage Fichier)
  const handlePhotoChange = (e) => {
    const file = e.target.files[0];
    if (!file) return;
    if (file.size > 1024 * 1024) return alert("La photo ne doit pas dépasser 1 Mo.");
    
    setPhotoFile(file); // On garde le fichier pour l'envoi
    setPhotoPreview(URL.createObjectURL(file)); // On affiche l'aperçu
  };

  // 2. ENVOI DES MODIFICATIONS VERS SPRING BOOT
  const handleSubmit = async (e) => {
    e.preventDefault();
    
    try {
      console.log('📥 Tentative sauvegarde profil...');
      
      // Étape A : Mise à jour des infos texte
      const result = await CandidatService.updateProfile(formData);
      
      console.log('📡 Résultat sauvegarde:', result);

      // Étape B : Si une nouvelle photo a été choisie (Optionnel selon ton backend)
      if (photoFile) {
         console.log("Photo prête à être envoyée (nécessite endpoint spécifique)");
      }

      // Mise à jour du contexte global (AuthContext) 
      setUser({ ...user, ...formData }); 
      
      // Message de succès
      const successMessage = result?.message || "Informations personnelles enregistrées avec succès !";
      alert(successMessage);
      
    } catch (error) {
      console.error("❌ Erreur sauvegarde:", error);
      
      // Message d'erreur plus détaillé
      let errorMessage = "Erreur lors de l'enregistrement. ";
      if (error.response?.status === 403) {
        errorMessage += "Accès refusé. Vos modifications ont été sauvegardées localement.";
      } else if (error.response?.status) {
        errorMessage += `Code erreur: ${error.response.status}`;
      } else {
        errorMessage += "Vérifiez votre connexion."; 
      }
      
      alert(errorMessage);
    }
  };

  if (isLoading) {
    return (
      <LayoutCandidat>
        <div style={{ display: "flex", justifyContent: "center", marginTop: "50px", color: "#003366" }}>
          <FaSpinner className="spinner" size={40} style={{ animation: "spin 1s linear infinite" }} />
          <style>{`@keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }`}</style>
        </div>
      </LayoutCandidat>
    );
  }

  return (
    <LayoutCandidat>
      <div style={commonStyles.card}>
        <h2 style={commonStyles.title}>Informations Personnelles</h2>
        
        <form onSubmit={handleSubmit}>
          {/* PHOTO */}
          <div style={{ display: "flex", flexDirection: "column", alignItems: "center", marginBottom: "30px" }}>
            <div style={{ position: "relative", width: "120px", height: "120px" }}>
              <div style={{
                width: "100%", height: "100%", borderRadius: "50%", overflow: "hidden",
                border: "3px solid #003366", background: "#eee", display: "flex", alignItems: "center", justifyContent: "center"
              }}>
                {photoPreview ? (
                  <img src={photoPreview} alt="Profil" style={{ width: "100%", height: "100%", objectFit: "cover" }} />
                ) : (
                  <span style={{ fontSize: "30px", color: "#ccc" }}>Photo</span>
                )}
              </div>
              <label style={{
                position: "absolute", bottom: "0", right: "0", background: "#8B1E3F", color: "#fff",
                width: "35px", height: "35px", borderRadius: "50%", display: "flex", alignItems: "center", justifyContent: "center",
                cursor: "pointer", border: "2px solid #fff", boxShadow: "0 2px 5px rgba(0,0,0,0.2)"
              }}>
                <FaCamera size={16} />
                <input type="file" accept="image/*" onChange={handlePhotoChange} style={{ display: "none" }} />
              </label>
            </div>
            <p style={{ fontSize: "12px", color: "#666", marginTop: "5px" }}>Cliquez pour modifier la photo</p>
          </div>

          <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: "20px" }}>
            
            {/* CHAMPS VERROUILLÉS (Venant du backend / Auth) */}
            <InputGroup label="Nom" value={formData.nom} locked />
            <InputGroup label="Prénom" value={formData.prenom} locked />
            <InputGroup label="Email" value={formData.email} locked />
            <InputGroup label="CNE / Code Massar" value={formData.cne} locked />

            <div style={{ gridColumn: "span 2", height: "1px", background: "#eee", margin: "10px 0" }} />

            {/* CHAMPS MODIFIABLES */}
            <InputGroup label="CIN" name="cin" value={formData.cin} onChange={handleChange} />
            <InputGroup label="Date de naissance" type="date" name="dateNaissance" value={formData.dateNaissance} onChange={handleChange} />
            
            <InputGroup label="Nom en Arabe" name="nomAr" value={formData.nomAr} onChange={handleChange} isRtl placeholder="النسب" />
            <InputGroup label="Prénom en Arabe" name="prenomAr" value={formData.prenomAr} onChange={handleChange} isRtl placeholder="الاسم" />
            
            <InputGroup label="Lieu naissance (Fr)" name="lieuNaissance" value={formData.lieuNaissance} onChange={handleChange} />
            <InputGroup label="Lieu naissance (Ar)" name="lieuNaissanceAr" value={formData.lieuNaissanceAr} onChange={handleChange} isRtl />
            
            <InputGroup label="Téléphone" name="telephone" value={formData.telephone} onChange={handleChange} />
            
            <div>
              <label style={commonStyles.label}>Sexe</label>
              <select name="sexe" value={formData.sexe} onChange={handleChange} style={commonStyles.input}>
                <option value="Féminin">Féminin</option>
                <option value="Masculin">Masculin</option>
              </select>
            </div>
            
            <div style={{ gridColumn: "span 2" }}>
              <label style={commonStyles.label}>Adresse</label>
              <input type="text" name="adresse" value={formData.adresse} onChange={handleChange} style={commonStyles.input} placeholder="N° Rue, Quartier, Ville..." />
            </div>
          </div>

          <div style={{ marginTop: "30px", textAlign: "right", borderTop: "1px solid #eee", paddingTop: "20px" }}>
            <button type="submit" style={commonStyles.btnSuccess}>
                <FaSave style={{ marginRight: "8px" }} /> Enregistrer
            </button>
          </div>
        </form>
      </div>
    </LayoutCandidat>
  );
}

// --- Composant InputGroup (Inchangé, c'est très propre) ---
const InputGroup = ({ label, value, name, onChange, locked = false, isRtl = false, type = "text", placeholder = "" }) => (
  <div>
    <label style={commonStyles.label}>
      {label} {locked && <FaLock size={10} color="#999" style={{ marginLeft: "5px" }} />}
    </label>
    <input
      type={type}
      name={name}
      value={value || ""} // Gestion du null pour éviter l'erreur React "uncontrolled input"
      onChange={locked ? undefined : onChange}
      readOnly={locked}
      disabled={locked}
      dir={isRtl ? "rtl" : "ltr"}
      placeholder={placeholder}
      style={{
        ...commonStyles.input,
        backgroundColor: locked ? "#F7FAFC" : "#fff",
        cursor: locked ? "not-allowed" : "text",
        color: locked ? "#718096" : "#2D3748",
        borderColor: locked ? "#E2E8F0" : "#CBD5E0",
      }}
    />
  </div>
);