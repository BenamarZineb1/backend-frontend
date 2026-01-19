import React, { useState, useEffect } from 'react';
import { FaUsers, FaSearch, FaStar, FaClipboardCheck, FaCalendarAlt } from 'react-icons/fa';

export default function MesCandidats({ professeurId }) {
  const [candidats, setCandidats] = useState([]);
  const [evaluations, setEvaluations] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [filtreStatut, setFiltreStatut] = useState('tous');
  
  useEffect(() => {
    chargerDonnees();
  }, [professeurId]);
  
  const chargerDonnees = async () => {
    try {
      // Données de test
      const candidatsDemo = [
        {
          id: 1,
          nom: "Ben Amar",
          prenom: "Zineb",
          email: "zineb.benamar@doctorat.ma",
          cne: "R123456789",
          sujet: "Intelligence Artificielle appliquée à la médecine",
          dateInscription: "2024-01-15",
          statut: "EN_EVALUATION"
        },
        {
          id: 2,
          nom: "El Mansouri",
          prenom: "Ahmed",
          email: "ahmed.elmansouri@doctorat.ma",
          cne: "R987654321",
          sujet: "Énergies renouvelables et transition écologique",
          dateInscription: "2024-01-18",
          statut: "EVALUE"
        },
        {
          id: 3,
          nom: "Khouribga",
          prenom: "Fatima",
          email: "fatima.khouribga@doctorat.ma",
          cne: "R456789123",
          sujet: "Sécurité informatique avancée",
          dateInscription: "2024-01-20",
          statut: "EN_ATTENTE"
        }
      ];
      
      const evaluationsDemo = [
        {
          id: 1,
          candidatId: 1,
          noteDossier: 15.5,
          noteEntretien: 17.0,
          noteFinale: 16.3,
          decision: "ADMIS",
          commentaire: "Excellent dossier technique et très bonne présentation",
          dateEvaluation: "2024-01-25"
        },
        {
          id: 2,
          candidatId: 2,
          noteDossier: 14.0,
          noteEntretien: 13.5,
          noteFinale: 13.7,
          decision: "LISTE_ATTENTE",
          commentaire: "Bon dossier mais entretien moyen",
          dateEvaluation: "2024-01-26"
        }
      ];
      
      setCandidats(candidatsDemo);
      setEvaluations(evaluationsDemo);
    } catch (error) {
      console.error('Erreur chargement données:', error);
    } finally {
      setLoading(false);
    }
  };
  
  const getCandidatsFiltres = () => {
    let filtered = candidats;
    
    // Filtre recherche
    if (searchTerm) {
      filtered = filtered.filter(c => 
        c.nom.toLowerCase().includes(searchTerm.toLowerCase()) ||
        c.prenom.toLowerCase().includes(searchTerm.toLowerCase()) ||
        c.sujet.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }
    
    // Filtre statut
    if (filtreStatut !== 'tous') {
      filtered = filtered.filter(c => c.statut === filtreStatut);
    }
    
    return filtered;
  };
  
  const getEvaluationCandidat = (candidatId) => {
    return evaluations.find(e => e.candidatId === candidatId);
  };
  
  const getStatutBadge = (statut) => {
    const styles = {
      EN_ATTENTE: { backgroundColor: '#ffc107', color: 'black' },
      EN_EVALUATION: { backgroundColor: '#17a2b8', color: 'white' },
      EVALUE: { backgroundColor: '#28a745', color: 'white' },
      REFUSE: { backgroundColor: '#dc3545', color: 'white' }
    };
    
    const labels = {
      EN_ATTENTE: 'En attente',
      EN_EVALUATION: 'En évaluation',
      EVALUE: 'Évalué',
      REFUSE: 'Refusé'
    };
    
    return (
      <span style={{...styles.badge, ...styles[statut]}}>
        {labels[statut]}
      </span>
    );
  };
  
  const getDecisionBadge = (decision) => {
    const styles = {
      ADMIS: { backgroundColor: '#28a745', color: 'white' },
      LISTE_ATTENTE: { backgroundColor: '#ffc107', color: 'black' },
      REFUSE: { backgroundColor: '#dc3545', color: 'white' },
      AJOURNE: { backgroundColor: '#6c757d', color: 'white' }
    };
    
    return (
      <span style={{...styles.badge, ...styles[decision]}}>
        {decision.replace('_', ' ')}
      </span>
    );
  };
  
  if (loading) {
    return (
      <div style={styles.loading}>
        <FaUsers className="spinner" size={24} />
        <span>Chargement de vos candidats...</span>
      </div>
    );
  }
  
  const candidatsFiltres = getCandidatsFiltres();
  
  return (
    <div style={styles.container}>
      {/* En-tête */}
      <div style={styles.header}>
        <h2 style={styles.title}>
          <FaUsers style={{ marginRight: '10px' }} />
          Mes Candidats
        </h2>
        
        <div style={styles.stats}>
          <div style={styles.statCard}>
            <div style={styles.statNumber}>{candidats.length}</div>
            <div style={styles.statLabel}>Total</div>
          </div>
          <div style={styles.statCard}>
            <div style={{...styles.statNumber, color: '#17a2b8'}}>{candidats.filter(c => c.statut === 'EN_EVALUATION').length}</div>
            <div style={styles.statLabel}>En évaluation</div>
          </div>
          <div style={styles.statCard}>
            <div style={{...styles.statNumber, color: '#28a745'}}>{evaluations.filter(e => e.decision === 'ADMIS').length}</div>
            <div style={styles.statLabel}>Admis</div>
          </div>
        </div>
      </div>
      
      {/* Filtres */}
      <div style={styles.filters}>
        <div style={styles.searchBox}>
          <FaSearch style={{ marginRight: '10px', color: '#6c757d' }} />
          <input
            type="text"
            placeholder="Rechercher un candidat..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
            style={styles.searchInput}
          />
        </div>
        
        <select 
          value={filtreStatut} 
          onChange={(e) => setFiltreStatut(e.target.value)}
          style={styles.filterSelect}
        >
          <option value="tous">Tous les statuts</option>
          <option value="EN_ATTENTE">En attente</option>
          <option value="EN_EVALUATION">En évaluation</option>
          <option value="EVALUE">Évalués</option>
        </select>
      </div>
      
      {/* Liste des candidats */}
      <div style={styles.candidatsList}>
        {candidatsFiltres.length === 0 ? (
          <div style={styles.emptyState}>
            <FaUsers size={48} style={{ color: '#ccc', marginBottom: '15px' }} />
            <p>Aucun candidat trouvé</p>
          </div>
        ) : (
          candidatsFiltres.map(candidat => {
            const evaluation = getEvaluationCandidat(candidat.id);
            
            return (
              <div key={candidat.id} style={styles.candidatCard}>
                <div style={styles.candidatHeader}>
                  <div style={styles.candidatInfo}>
                    <h3 style={styles.candidatName}>
                      {candidat.prenom} {candidat.nom}
                    </h3>
                    <p style={styles.candidatEmail}>{candidat.email}</p>
                    <p style={styles.candidatCNE}>CNE: {candidat.cne}</p>
                  </div>
                  
                  <div style={styles.candidatActions}>
                    {getStatutBadge(candidat.statut)}
                  </div>
                </div>
                
                <div style={styles.sujetInfo}>
                  <h4 style={styles.sujetTitle}>Sujet:</h4>
                  <p style={styles.sujetText}>{candidat.sujet}</p>
                </div>
                
                {evaluation && (
                  <div style={styles.evaluationInfo}>
                    <h4 style={styles.evaluationTitle}>
                      <FaClipboardCheck style={{ marginRight: '8px' }} />
                      Évaluation
                    </h4>
                    
                    <div style={styles.notesGrid}>
                      <div style={styles.noteCard}>
                        <div style={styles.noteLabel}>Dossier</div>
                        <div style={styles.noteValue}>{evaluation.noteDossier}/20</div>
                      </div>
                      <div style={styles.noteCard}>
                        <div style={styles.noteLabel}>Entretien</div>
                        <div style={styles.noteValue}>{evaluation.noteEntretien}/20</div>
                      </div>
                      <div style={styles.noteCard}>
                        <div style={styles.noteLabel}>Finale</div>
                        <div style={{...styles.noteValue, fontWeight: 'bold'}}>
                          {evaluation.noteFinale}/20
                        </div>
                      </div>
                    </div>
                    
                    <div style={styles.evaluationFooter}>
                      <div>{getDecisionBadge(evaluation.decision)}</div>
                      <div style={styles.commentaire}>
                        {evaluation.commentaire}
                      </div>
                    </div>
                  </div>
                )}
                
                {!evaluation && candidat.statut === 'EN_EVALUATION' && (
                  <div style={styles.actionsSection}>
                    <button style={styles.evaluerButton}>
                      <FaStar style={{ marginRight: '8px' }} />
                      Évaluer ce candidat
                    </button>
                  </div>
                )}
              </div>
            );
          })
        )}
      </div>
    </div>
  );
}

const styles = {
  container: {
    backgroundColor: '#fff',
    borderRadius: '10px',
    boxShadow: '0 2px 10px rgba(0,0,0,0.1)',
    overflow: 'hidden'
  },
  header: {
    padding: '20px',
    borderBottom: '1px solid #eee'
  },
  title: {
    margin: '0 0 20px 0',
    color: '#003366',
    display: 'flex',
    alignItems: 'center'
  },
  stats: {
    display: 'grid',
    gridTemplateColumns: 'repeat(3, 1fr)',
    gap: '15px'
  },
  statCard: {
    textAlign: 'center',
    padding: '15px',
    backgroundColor: '#f8f9fa',
    borderRadius: '8px'
  },
  statNumber: {
    fontSize: '24px',
    fontWeight: 'bold',
    color: '#003366',
    marginBottom: '5px'
  },
  statLabel: {
    fontSize: '12px',
    color: '#666'
  },
  filters: {
    padding: '20px',
    borderBottom: '1px solid #eee',
    display: 'flex',
    gap: '15px'
  },
  searchBox: {
    display: 'flex',
    alignItems: 'center',
    flex: 1,
    border: '1px solid #ddd',
    borderRadius: '6px',
    padding: '0 12px'
  },
  searchInput: {
    flex: 1,
    border: 'none',
    padding: '12px 0',
    outline: 'none',
    fontSize: '14px'
  },
  filterSelect: {
    padding: '12px',
    border: '1px solid #ddd',
    borderRadius: '6px',
    fontSize: '14px'
  },
  candidatsList: {
    maxHeight: '500px',
    overflowY: 'auto'
  },
  candidatCard: {
    padding: '20px',
    borderBottom: '1px solid #eee'
  },
  candidatHeader: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'flex-start',
    marginBottom: '15px'
  },
  candidatInfo: {
    flex: 1
  },
  candidatName: {
    margin: '0 0 5px 0',
    fontSize: '18px',
    color: '#333'
  },
  candidatEmail: {
    margin: '0 0 3px 0',
    color: '#666',
    fontSize: '14px'
  },
  candidatCNE: {
    margin: 0,
    color: '#999',
    fontSize: '12px'
  },
  sujetInfo: {
    marginBottom: '15px'
  },
  sujetTitle: {
    margin: '0 0 5px 0',
    fontSize: '14px',
    color: '#666'
  },
  sujetText: {
    margin: 0,
    color: '#333',
    fontSize: '14px'
  },
  evaluationInfo: {
    backgroundColor: '#f8f9fa',
    padding: '15px',
    borderRadius: '8px',
    marginBottom: '15px'
  },
  evaluationTitle: {
    margin: '0 0 15px 0',
    fontSize: '16px',
    color: '#003366',
    display: 'flex',
    alignItems: 'center'
  },
  notesGrid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(3, 1fr)',
    gap: '10px',
    marginBottom: '15px'
  },
  noteCard: {
    textAlign: 'center',
    padding: '10px',
    backgroundColor: '#fff',
    borderRadius: '6px',
    border: '1px solid #eee'
  },
  noteLabel: {
    fontSize: '12px',
    color: '#666',
    marginBottom: '5px'
  },
  noteValue: {
    fontSize: '18px',
    color: '#003366'
  },
  evaluationFooter: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center'
  },
  commentaire: {
    fontSize: '13px',
    color: '#666',
    fontStyle: 'italic'
  },
  actionsSection: {
    textAlign: 'center',
    paddingTop: '15px'
  },
  evaluerButton: {
    padding: '10px 20px',
    backgroundColor: '#003366',
    color: 'white',
    border: 'none',
    borderRadius: '6px',
    fontSize: '14px',
    cursor: 'pointer',
    display: 'flex',
    alignItems: 'center',
    margin: '0 auto'
  },
  emptyState: {
    textAlign: 'center',
    padding: '40px 20px',
    color: '#999'
  },
  loading: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    padding: '40px',
    gap: '10px',
    color: '#666'
  },
  badge: {
    padding: '4px 12px',
    borderRadius: '15px',
    fontSize: '11px',
    fontWeight: 'bold',
    textTransform: 'uppercase'
  }
};