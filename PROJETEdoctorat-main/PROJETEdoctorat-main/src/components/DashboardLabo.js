import React, { useState, useEffect } from 'react';
import { FaUsers, FaClipboardList, FaChartBar, FaFilePdf, FaCalendarAlt } from 'react-icons/fa';

export default function DashboardLabo({ laboratoireId }) {
  const [candidats, setCandidats] = useState([]);
  const [stats, setStats] = useState({});
  const [loading, setLoading] = useState(true);
  
  useEffect(() => {
    chargerDonnees();
  }, [laboratoireId]);
  
  const chargerDonnees = async () => {
    try {
      // Charger candidats
      const candidatsResponse = await fetch(`http://localhost:8085/api/directeur-labo/${laboratoireId}/candidats`);
      const candidatsData = await candidatsResponse.json();
      setCandidats(candidatsData);
      
      // Charger statistiques
      const statsResponse = await fetch(`http://localhost:8085/api/directeur-labo/${laboratoireId}/stats`);
      const statsData = await statsResponse.json();
      setStats(statsData);
      
    } catch (error) {
      console.error('Erreur chargement dashboard:', error);
    } finally {
      setLoading(false);
    }
  };
  
  const genererPV = async () => {
    try {
      const response = await fetch(`http://localhost:8085/api/directeur-labo/${laboratoireId}/generer-pv`, {
        method: 'POST'
      });
      const pv = await response.json();
      alert('PV global généré avec succès!');
      console.log('PV généré:', pv);
    } catch (error) {
      console.error('Erreur génération PV:', error);
    }
  };
  
  if (loading) {
    return (
      <div style={styles.loading}>
        <FaUsers className="spinner" size={24} />
        <span>Chargement du dashboard laboratoire...</span>
      </div>
    );
  }
  
  return (
    <div style={styles.container}>
      {/* En-tête */}
      <div style={styles.header}>
        <h2 style={styles.title}>
          <FaUsers style={{ marginRight: '10px' }} />
          Dashboard Directeur de Laboratoire
        </h2>
        
        <button onClick={genererPV} style={styles.pvButton}>
          <FaFilePdf style={{ marginRight: '8px' }} />
          Générer PV Global
        </button>
      </div>
      
      {/* Statistiques */}
      <div style={styles.statsGrid}>
        <StatCard 
          title="Total Commissions" 
          value={stats.totalCommissions || 0} 
          icon={<FaClipboardList />}
          color="#003366"
        />
        <StatCard 
          title="Commissions Terminées" 
          value={stats.commissionsTerminees || 0} 
          icon={<FaCalendarAlt />}
          color="#28a745"
        />
        <StatCard 
          title="Total Candidats" 
          value={stats.totalCandidats || 0} 
          icon={<FaUsers />}
          color="#8B1E3F"
        />
        <StatCard 
          title="Candidats Admis" 
          value={stats.candidatsAdmis || 0} 
          icon="✅"
          color="#28a745"
        />
      </div>
      
      {/* Liste des candidats */}
      <div style={styles.section}>
        <h3 style={styles.sectionTitle}>Candidats du Laboratoire</h3>
        <div style={styles.candidatsList}>
          {candidats.map(candidat => (
            <div key={candidat.id} style={styles.candidatCard}>
              <div style={styles.candidatHeader}>
                <h4 style={styles.candidatName}>
                  {candidat.prenom} {candidat.nom}
                </h4>
                <span style={{...styles.badge, backgroundColor: getStatusColor(candidat.statut)}}>
                  {candidat.statut}
                </span>
              </div>
              
              <div style={styles.candidatDetails}>
                <p><strong>Email:</strong> {candidat.email}</p>
                <p><strong>CNE:</strong> {candidat.cne}</p>
                <p><strong>Sujet:</strong> {candidat.sujet}</p>
                <p><strong>Professeur:</strong> {candidat.professeur}</p>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

// Composant carte statistique
const StatCard = ({ title, value, icon, color }) => (
  <div style={{ ...styles.statCard, borderLeft: `4px solid ${color}` }}>
    <div style={styles.statIcon}>{icon}</div>
    <div style={styles.statContent}>
      <div style={{ ...styles.statValue, color }}>{value}</div>
      <div style={styles.statTitle}>{title}</div>
    </div>
  </div>
);

// Fonction utilitaire pour couleur statut
const getStatusColor = (statut) => {
  switch (statut) {
    case 'EN_EVALUATION': return '#17a2b8';
    case 'EVALUE': return '#28a745';
    case 'EN_ATTENTE': return '#ffc107';
    case 'REFUSE': return '#dc3545';
    default: return '#6c757d';
  }
};

const styles = {
  container: {
    padding: '20px',
    backgroundColor: '#f5f5f5',
    minHeight: '100vh'
  },
  header: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '30px'
  },
  title: {
    color: '#003366',
    margin: 0,
    display: 'flex',
    alignItems: 'center'
  },
  pvButton: {
    padding: '12px 20px',
    backgroundColor: '#8B1E3F',
    color: 'white',
    border: 'none',
    borderRadius: '8px',
    fontSize: '14px',
    fontWeight: 'bold',
    cursor: 'pointer',
    display: 'flex',
    alignItems: 'center'
  },
  statsGrid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))',
    gap: '20px',
    marginBottom: '30px'
  },
  statCard: {
    backgroundColor: '#fff',
    padding: '20px',
    borderRadius: '10px',
    boxShadow: '0 2px 10px rgba(0,0,0,0.1)',
    display: 'flex',
    alignItems: 'center'
  },
  statIcon: {
    fontSize: '28px',
    marginRight: '15px'
  },
  statContent: {
    flex: 1
  },
  statValue: {
    fontSize: '24px',
    fontWeight: 'bold',
    marginBottom: '5px'
  },
  statTitle: {
    color: '#666',
    fontSize: '14px'
  },
  section: {
    backgroundColor: '#fff',
    padding: '25px',
    borderRadius: '10px',
    boxShadow: '0 2px 10px rgba(0,0,0,0.1)'
  },
  sectionTitle: {
    color: '#003366',
    marginBottom: '20px',
    fontSize: '20px'
  },
  candidatsList: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(350px, 1fr))',
    gap: '20px'
  },
  candidatCard: {
    border: '1px solid #eee',
    borderRadius: '8px',
    padding: '20px'
  },
  candidatHeader: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: '15px'
  },
  candidatName: {
    margin: 0,
    color: '#333',
    fontSize: '18px'
  },
  badge: {
    padding: '4px 12px',
    borderRadius: '15px',
    fontSize: '11px',
    fontWeight: 'bold',
    color: 'white'
  },
  candidatDetails: {
    fontSize: '14px',
    color: '#666'
  },
  loading: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    padding: '40px',
    gap: '10px',
    color: '#666'
  }
};