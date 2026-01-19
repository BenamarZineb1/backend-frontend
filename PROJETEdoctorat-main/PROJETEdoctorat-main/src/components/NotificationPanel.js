import React, { useState, useEffect } from 'react';
import { FaBell, FaCheck, FaTimes, FaExclamationTriangle, FaCalendarAlt } from 'react-icons/fa';

export default function NotificationPanel({ userId, userRole }) {
  const [notifications, setNotifications] = useState([]);
  const [stats, setStats] = useState({});
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('toutes'); // toutes, non-lues, importantes
  
  useEffect(() => {
    chargerNotifications();
    chargerStatistiques();
  }, [userId, userRole]);
  
  const chargerNotifications = async () => {
    try {
      const response = await fetch(
        `http://localhost:8085/api/notifications/non-lues?userId=${userId}&role=${userRole}`
      );
      const data = await response.json();
      setNotifications(data);
    } catch (error) {
      console.error('Erreur chargement notifications:', error);
    } finally {
      setLoading(false);
    }
  };
  
  const chargerStatistiques = async () => {
    try {
      const response = await fetch(
        `http://localhost:8085/api/notifications/stats?userId=${userId}&role=${userRole}`
      );
      const data = await response.json();
      setStats(data);
    } catch (error) {
      console.error('Erreur stats notifications:', error);
    }
  };
  
  const marquerCommeLue = async (notificationId) => {
    try {
      await fetch(`http://localhost:8085/api/notifications/${notificationId}/lire`, {
        method: 'PUT'
      });
      
      // Mettre à jour localement
      setNotifications(prev => 
        prev.map(notif => 
          notif.id === notificationId 
            ? { ...notif, statut: 'LUE' }
            : notif
        )
      );
      
      // Mettre à jour stats
      chargerStatistiques();
    } catch (error) {
      console.error('Erreur mise à jour notification:', error);
    }
  };
  
  const getIconForType = (type) => {
    switch (type?.toUpperCase()) {
      case 'ENTRETIEN': return <FaCalendarAlt style={{ color: '#007bff' }} />;
      case 'RESULTAT': return <FaCheck style={{ color: '#28a745' }} />;
      case 'COMMISSION': return <FaExclamationTriangle style={{ color: '#ffc107' }} />;
      default: return <FaBell style={{ color: '#6c757d' }} />;
    }
  };
  
  const getTypeLabel = (type) => {
    switch (type?.toUpperCase()) {
      case 'ENTRETIEN': return 'Entretien';
      case 'RESULTAT': return 'Résultat';
      case 'COMMISSION': return 'Commission';
      default: return 'Général';
    }
  };
  
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const now = new Date();
    const diffHours = Math.floor((now - date) / (1000 * 60 * 60));
    
    if (diffHours < 1) return 'À l\'instant';
    if (diffHours < 24) return `Il y a ${diffHours}h`;
    return date.toLocaleDateString('fr-FR');
  };
  
  if (loading) {
    return (
      <div style={styles.loading}>
        <FaBell className="spinner" size={24} />
        <span>Chargement des notifications...</span>
      </div>
    );
  }
  
  return (
    <div style={styles.container}>
      {/* En-tête avec statistiques */}
      <div style={styles.header}>
        <h3 style={styles.title}>
          <FaBell style={{ marginRight: '10px' }} />
          Notifications
        </h3>
        
        <div style={styles.stats}>
          <div style={styles.statItem}>
            <span style={styles.statNumber}>{stats.total || 0}</span>
            <span style={styles.statLabel}>Total</span>
          </div>
          <div style={styles.statItem}>
            <span style={{...styles.statNumber, color: '#dc3545'}}>{stats.nonLues || 0}</span>
            <span style={styles.statLabel}>Non lues</span>
          </div>
          <div style={styles.statItem}>
            <span style={{...styles.statNumber, color: '#ffc107'}}>{stats.importantes || 0}</span>
            <span style={styles.statLabel}>Importantes</span>
          </div>
        </div>
      </div>
      
      {/* Tabs de filtrage */}
      <div style={styles.tabs}>
        {['toutes', 'non-lues', 'importantes'].map(tab => (
          <button
            key={tab}
            onClick={() => setActiveTab(tab)}
            style={{
              ...styles.tabButton,
              ...(activeTab === tab ? styles.activeTab : {})
            }}
          >
            {tab.charAt(0).toUpperCase() + tab.slice(1)}
          </button>
        ))}
      </div>
      
      {/* Liste des notifications */}
      <div style={styles.notificationsList}>
        {notifications.length === 0 ? (
          <div style={styles.emptyState}>
            <FaBell size={48} style={{ color: '#ccc', marginBottom: '15px' }} />
            <p>Aucune notification</p>
          </div>
        ) : (
          notifications
            .filter(notif => {
              if (activeTab === 'non-lues') return notif.statut === 'NON_LUE';
              if (activeTab === 'importantes') return notif.importante;
              return true;
            })
            .map(notification => (
              <div 
                key={notification.id} 
                style={{
                  ...styles.notificationCard,
                  ...(notification.statut === 'NON_LUE' ? styles.unreadCard : {}),
                  ...(notification.importante ? styles.importantCard : {})
                }}
              >
                <div style={styles.notificationHeader}>
                  <div style={styles.iconContainer}>
                    {getIconForType(notification.type)}
                  </div>
                  
                  <div style={styles.notificationInfo}>
                    <h4 style={styles.notificationTitle}>{notification.titre}</h4>
                    <span style={styles.notificationType}>
                      {getTypeLabel(notification.type)}
                    </span>
                    <span style={styles.notificationDate}>
                      {formatDate(notification.dateCreation)}
                    </span>
                  </div>
                  
                  {notification.statut === 'NON_LUE' && (
                    <button
                      onClick={() => marquerCommeLue(notification.id)}
                      style={styles.readButton}
                      title="Marquer comme lu"
                    >
                      <FaCheck size={14} />
                    </button>
                  )}
                </div>
                
                <p style={styles.notificationMessage}>{notification.message}</p>
                
                {notification.lienAction && (
                  <a 
                    href={notification.lienAction} 
                    style={styles.actionLink}
                    target="_blank"
                    rel="noopener noreferrer"
                  >
                    Voir les détails
                  </a>
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
  stats: {
    display: 'flex',
    gap: '20px'
  },
  statItem: {
    textAlign: 'center'
  },
  statNumber: {
    display: 'block',
    fontSize: '20px',
    fontWeight: 'bold',
    color: '#003366'
  },
  statLabel: {
    fontSize: '12px',
    color: '#666'
  },
  tabs: {
    display: 'flex',
    borderBottom: '1px solid #eee'
  },
  tabButton: {
    flex: 1,
    padding: '15px',
    border: 'none',
    background: 'transparent',
    cursor: 'pointer',
    fontSize: '14px',
    color: '#666',
    borderBottom: '3px solid transparent'
  },
  activeTab: {
    color: '#003366',
    borderBottom: '3px solid #003366',
    fontWeight: 'bold'
  },
  notificationsList: {
    maxHeight: '400px',
    overflowY: 'auto'
  },
  notificationCard: {
    padding: '15px 20px',
    borderBottom: '1px solid #f0f0f0',
    transition: 'background-color 0.2s'
  },
  unreadCard: {
    backgroundColor: '#f8f9ff',
    borderLeft: '4px solid #003366'
  },
  importantCard: {
    backgroundColor: '#fff8f0',
    borderLeft: '4px solid #ffc107'
  },
  notificationHeader: {
    display: 'flex',
    alignItems: 'flex-start',
    marginBottom: '10px'
  },
  iconContainer: {
    marginRight: '12px',
    marginTop: '2px'
  },
  notificationInfo: {
    flex: 1
  },
  notificationTitle: {
    margin: '0 0 5px 0',
    fontSize: '16px',
    color: '#333'
  },
  notificationType: {
    display: 'inline-block',
    backgroundColor: '#e9ecef',
    color: '#495057',
    padding: '2px 8px',
    borderRadius: '12px',
    fontSize: '11px',
    marginRight: '10px'
  },
  notificationDate: {
    fontSize: '12px',
    color: '#6c757d'
  },
  readButton: {
    background: '#28a745',
    color: 'white',
    border: 'none',
    borderRadius: '50%',
    width: '24px',
    height: '24px',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    cursor: 'pointer',
    marginLeft: '10px'
  },
  notificationMessage: {
    margin: '0 0 10px 0',
    color: '#666',
    lineHeight: '1.4'
  },
  actionLink: {
    color: '#003366',
    textDecoration: 'none',
    fontSize: '13px',
    fontWeight: 'bold'
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