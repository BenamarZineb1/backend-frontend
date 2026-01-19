import React, { useState, useEffect } from 'react';
import { FaCalendarAlt, FaClock, FaMapMarkerAlt, FaUsers, FaPlus } from 'react-icons/fa';

export default function CalendrierDynamique({ role }) {
  const [evenements, setEvenements] = useState([]);
  const [stats, setStats] = useState({});
  const [periode, setPeriode] = useState('semaine'); // semaine, mois
  const [loading, setLoading] = useState(true);
  
  useEffect(() => {
    chargerEvenements();
    chargerStatistiques();
  }, [role, periode]);
  
  const chargerEvenements = async () => {
    try {
      // Pour démo - utiliser données locales
      const evenementsDemo = [
        {
          id: 1,
          titre: "Entretien candidature - Intelligence Artificielle",
          description: "Entretien avec le candidat pour le sujet IA médicale",
          type: "ENTRETIEN",
          dateDebut: new Date(Date.now() + 3 * 24 * 60 * 60 * 1000),
          dateFin: new Date(Date.now() + 3 * 24 * 60 * 60 * 1000 + 90 * 60 * 1000),
          lieu: "Salle B101 - Bâtiment principal",
          statut: "PLANIFIE"
        },
        {
          id: 2,
          titre: "Deadline dépôt dossiers",
          description: "Fin de la période de dépôt des dossiers de candidature",
          type: "DEADLINE",
          dateDebut: new Date(Date.now() + 10 * 24 * 60 * 60 * 1000),
          dateFin: new Date(Date.now() + 10 * 24 * 60 * 60 * 1000),
          lieu: "En ligne",
          statut: "PLANIFIE"
        },
        {
          id: 3,
          titre: "Commission d'examen - Physique",
          description: "Commission d'évaluation des candidatures en physique",
          type: "COMMISSION",
          dateDebut: new Date(Date.now() - 60 * 60 * 1000),
          dateFin: new Date(Date.now() + 2 * 60 * 60 * 1000),
          lieu: "Amphi A - Campus principal",
          statut: "EN_COURS"
        }
      ];
      
      setEvenements(evenementsDemo);
    } catch (error) {
      console.error('Erreur chargement événements:', error);
    } finally {
      setLoading(false);
    }
  };
  
  const chargerStatistiques = async () => {
    const statsDemo = {
      totalEvenements: 15,
      evenementsFuturs: 8,
      evenementsEnCours: 1,
      types: 4
    };
    setStats(statsDemo);
  };
  
  const getTypeColor = (type) => {
    switch (type) {
      case 'ENTRETIEN': return '#007bff';
      case 'DEADLINE': return '#dc3545';
      case 'COMMISSION': return '#ffc107';
      case 'DEPOT': return '#28a745';
      default: return '#6c757d';
    }
  };
  
  const getTypeIcon = (type) => {
    switch (type) {
      case 'ENTRETIEN': return '👥';
      case 'DEADLINE': return '⏰';
      case 'COMMISSION': return '🏛️';
      case 'DEPOT': return '📄';
      default: return '📅';
    }
  };
  
  const getStatusBadge = (statut) => {
    const styles = {
      PLANIFIE: { backgroundColor: '#17a2b8', color: 'white' },
      EN_COURS: { backgroundColor: '#ffc107', color: 'black' },
      TERMINE: { backgroundColor: '#28a745', color: 'white' },
      ANNULE: { backgroundColor: '#dc3545', color: 'white' }
    };
    
    return (
      <span style={{...styles.badge, ...styles[statut]}}>
        {statut.replace('_', ' ')}
      </span>
    );
  };
  
  const formatDate = (date) => {
    return new Date(date).toLocaleDateString('fr-FR', {
      weekday: 'long',
      day: 'numeric',
      month: 'long',
      year: 'numeric'
    });
  };
  
  const formatTime = (date) => {
    return new Date(date).toLocaleTimeString('fr-FR', {
      hour: '2-digit',
      minute: '2-digit'
    });
  };
  
  if (loading) {
    return (
      <div style={styles.loading}>
        <FaCalendarAlt className="spinner" size={24} />
        <span>Chargement du calendrier...</span>
      </div>
    );
  }
  
  return (
    <div style={styles.container}>
      {/* En-tête avec statistiques */}
      <div style={styles.header}>
        <h3 style={styles.title}>
          <FaCalendarAlt style={{ marginRight: '10px' }} />
          Calendrier
        </h3>
        
        <div style={styles.controls}>
          <select 
            value={periode} 
            onChange={(e) => setPeriode(e.target.value)}
            style={styles.periodSelect}
          >
            <option value="semaine">Cette semaine</option>
            <option value="mois">Ce mois</option>
            <option value="tous">Tous les événements</option>
          </select>
          
          <button style={styles.addButton}>
            <FaPlus style={{ marginRight: '5px' }} />
            Nouvel événement
          </button>
        </div>
      </div>
      
      {/* Statistiques */}
      <div style={styles.stats}>
        <div style={styles.statCard}>
          <div style={styles.statNumber}>{stats.evenementsFuturs}</div>
          <div style={styles.statLabel}>Événements à venir</div>
        </div>
        <div style={styles.statCard}>
          <div style={{...styles.statNumber, color: '#ffc107'}}>{stats.evenementsEnCours}</div>
          <div style={styles.statLabel}>En cours</div>
        </div>
        <div style={styles.statCard}>
          <div style={{...styles.statNumber, color: '#6c757d'}}>{stats.totalEvenements}</div>
          <div style={styles.statLabel}>Total</div>
        </div>
      </div>
      
      {/* Liste des événements */}
      <div style={styles.eventsList}>
        {evenements.length === 0 ? (
          <div style={styles.emptyState}>
            <FaCalendarAlt size={48} style={{ color: '#ccc', marginBottom: '15px' }} />
            <p>Aucun événement prévu</p>
          </div>
        ) : (
          evenements
            .sort((a, b) => new Date(a.dateDebut) - new Date(b.dateDebut))
            .map(event => (
              <div key={event.id} style={styles.eventCard}>
                <div style={styles.eventHeader}>
                  <div style={{...styles.eventType, backgroundColor: getTypeColor(event.type)}}>
                    {getTypeIcon(event.type)}
                  </div>
                  
                  <div style={styles.eventInfo}>
                    <h4 style={styles.eventTitle}>{event.titre}</h4>
                    <p style={styles.eventDescription}>{event.description}</p>
                    
                    <div style={styles.eventMeta}>
                      <div style={styles.metaItem}>
                        <FaClock style={{ marginRight: '5px', color: '#6c757d' }} />
                        <span>
                          {formatDate(event.dateDebut)} à {formatTime(event.dateDebut)}
                        </span>
                      </div>
                      
                      {event.lieu && (
                        <div style={styles.metaItem}>
                          <FaMapMarkerAlt style={{ marginRight: '5px', color: '#6c757d' }} />
                          <span>{event.lieu}</span>
                        </div>
                      )}
                    </div>
                  </div>
                  
                  <div style={styles.eventActions}>
                    {getStatusBadge(event.statut)}
                  </div>
                </div>
                
                {new Date(event.dateDebut) > new Date() && (
                  <div style={styles.reminder}>
                    <FaClock style={{ marginRight: '8px' }} />
                    Dans {Math.ceil((new Date(event.dateDebut) - new Date()) / (1000 * 60 * 60))} heures
                  </div>
                )}
              </div>
            ))
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
    borderBottom: '1px solid #eee',
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center'
  },
  title: {
    margin: 0,
    color: '#003366',
    display: 'flex',
    alignItems: 'center'
  },
  controls: {
    display: 'flex',
    gap: '15px',
    alignItems: 'center'
  },
  periodSelect: {
    padding: '8px 12px',
    border: '1px solid #ddd',
    borderRadius: '6px',
    fontSize: '14px'
  },
  addButton: {
    padding: '8px 15px',
    backgroundColor: '#003366',
    color: 'white',
    border: 'none',
    borderRadius: '6px',
    fontSize: '14px',
    cursor: 'pointer',
    display: 'flex',
    alignItems: 'center'
  },
  stats: {
    display: 'grid',
    gridTemplateColumns: 'repeat(3, 1fr)',
    gap: '15px',
    padding: '20px'
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
  eventsList: {
    maxHeight: '500px',
    overflowY: 'auto'
  },
  eventCard: {
    padding: '20px',
    borderBottom: '1px solid #eee',
    transition: 'background-color 0.2s'
  },
  eventHeader: {
    display: 'flex',
    alignItems: 'flex-start',
    marginBottom: '15px'
  },
  eventType: {
    width: '40px',
    height: '40px',
    borderRadius: '50%',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    fontSize: '18px',
    marginRight: '15px',
    flexShrink: 0
  },
  eventInfo: {
    flex: 1
  },
  eventTitle: {
    margin: '0 0 8px 0',
    fontSize: '16px',
    color: '#333'
  },
  eventDescription: {
    margin: '0 0 12px 0',
    color: '#666',
    fontSize: '14px',
    lineHeight: '1.4'
  },
  eventMeta: {
    display: 'flex',
    gap: '20px',
    flexWrap: 'wrap'
  },
  metaItem: {
    display: 'flex',
    alignItems: 'center',
    fontSize: '13px',
    color: '#666'
  },
  eventActions: {
    display: 'flex',
    alignItems: 'center'
  },
  badge: {
    padding: '4px 12px',
    borderRadius: '15px',
    fontSize: '11px',
    fontWeight: 'bold',
    textTransform: 'uppercase'
  },
  reminder: {
    backgroundColor: '#e7f3ff',
    color: '#004085',
    padding: '10px 15px',
    borderRadius: '6px',
    fontSize: '13px',
    display: 'flex',
    alignItems: 'center'
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
  }
};