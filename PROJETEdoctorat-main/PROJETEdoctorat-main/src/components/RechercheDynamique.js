import React, { useState, useEffect } from 'react';
import { FaSearch, FaFilter, FaTags, FaClock, FaChartBar } from 'react-icons/fa';

export default function RechercheDynamique() {
  const [motsCles, setMotsCles] = useState('');
  const [domaine, setDomaine] = useState('');
  const [suggestions, setSuggestions] = useState([]);
  const [resultats, setResultats] = useState([]);
  const [loading, setLoading] = useState(false);
  const [stats, setStats] = useState({});
  
  // Debounce pour recherche dynamique
  useEffect(() => {
    const delayDebounceFn = setTimeout(() => {
      if (motsCles.length > 1) {
        rechercherSuggestions();
      }
    }, 300);
    
    return () => clearTimeout(delayDebounceFn);
  }, [motsCles]);
  
  // Chargement initial des statistiques
  useEffect(() => {
    chargerStatistiques();
  }, []);
  
  const rechercherSuggestions = async () => {
    try {
      const response = await fetch(`http://localhost:8085/api/sujets/suggestions/mots-cles?prefix=${motsCles}`);
      const data = await response.json();
      setSuggestions(data.slice(0, 5)); // Limiter à 5 suggestions
    } catch (error) {
      console.error('Erreur suggestions:', error);
    }
  };
  
  const chargerStatistiques = async () => {
    try {
      const response = await fetch('http://localhost:8085/api/sujets/stats');
      const data = await response.json();
      setStats(data);
    } catch (error) {
      console.error('Erreur stats:', error);
    }
  };
  
  const rechercherSujets = async (e) => {
    e.preventDefault();
    setLoading(true);
    
    try {
      const params = new URLSearchParams();
      if (motsCles) params.append('motsCles', motsCles);
      if (domaine) params.append('domaine', domaine);
      
      const response = await fetch(`http://localhost:8085/api/sujets/recherche?${params}`);
      const data = await response.json();
      setResultats(data);
    } catch (error) {
      console.error('Erreur recherche:', error);
    } finally {
      setLoading(false);
    }
  };
  
  const selectionnerSuggestion = (suggestion) => {
    setMotsCles(suggestion);
    setSuggestions([]);
  };
  
  return (
    <div style={styles.container}>
      {/* Barre de recherche dynamique */}
      <div style={styles.searchSection}>
        <h2 style={styles.title}>
          <FaSearch style={{ marginRight: '10px' }} />
          Recherche Dynamique de Sujets
        </h2>
        
        <form onSubmit={rechercherSujets} style={styles.searchForm}>
          <div style={styles.inputGroup}>
            <input
              type="text"
              value={motsCles}
              onChange={(e) => setMotsCles(e.target.value)}
              placeholder="Rechercher par mots-clés, titre, description..."
              style={styles.searchInput}
            />
            {suggestions.length > 0 && (
              <div style={styles.suggestions}>
                {suggestions.map((suggestion, index) => (
                  <div
                    key={index}
                    onClick={() => selectionnerSuggestion(suggestion)}
                    style={styles.suggestionItem}
                  >
                    <FaTags style={{ marginRight: '8px', color: '#8B1E3F' }} />
                    {suggestion}
                  </div>
                ))}
              </div>
            )}
          </div>
          
          <div style={styles.filters}>
            <select
              value={domaine}
              onChange={(e) => setDomaine(e.target.value)}
              style={styles.filterSelect}
            >
              <option value="">Tous les domaines</option>
              <option value="Informatique">Informatique</option>
              <option value="Physique">Physique</option>
              <option value="Mathématiques">Mathématiques</option>
              <option value="Biologie">Biologie</option>
            </select>
            
            <button type="submit" style={styles.searchButton} disabled={loading}>
              {loading ? 'Recherche...' : 'Rechercher'}
            </button>
          </div>
        </form>
      </div>
      
      {/* Statistiques en temps réel */}
      <div style={styles.statsSection}>
        <h3 style={styles.statsTitle}>
          <FaChartBar style={{ marginRight: '10px' }} />
          Statistiques en Temps Réel
        </h3>
        <div style={styles.statsGrid}>
          <StatCard 
            title="Total Sujets" 
            value={stats.totalSujets || 0} 
            icon={<FaTags />}
            color="#003366"
          />
          <StatCard 
            title="Sujets Ouverts" 
            value={stats.sujetsOuverts || 0} 
            icon={<FaClock />}
            color="#28a745"
          />
          <StatCard 
            title="Domaines" 
            value={stats.domaines || 0} 
            icon="📚"
            color="#8B1E3F"
          />
        </div>
      </div>
      
      {/* Résultats */}
      <div style={styles.resultsSection}>
        {loading ? (
          <div style={styles.loading}>Recherche en cours...</div>
        ) : resultats.length > 0 ? (
          <div>
            <h3 style={styles.resultsTitle}>
              {resultats.length} résultat{resultats.length > 1 ? 's' : ''} trouvé{resultats.length > 1 ? 's' : ''}
            </h3>
            <div style={styles.resultsGrid}>
              {resultats.map((sujet) => (
                <SujetCard key={sujet.id} sujet={sujet} />
              ))}
            </div>
          </div>
        ) : motsCles || domaine ? (
          <div style={styles.noResults}>
            Aucun sujet trouvé pour ces critères
          </div>
        ) : null}
      </div>
    </div>
  );
}

// Composant carte sujet
const SujetCard = ({ sujet }) => (
  <div style={styles.sujetCard}>
    <h4 style={styles.sujetTitle}>{sujet.titre}</h4>
    <p style={styles.sujetDescription}>{sujet.description}</p>
    <div style={styles.sujetMeta}>
      <span style={styles.domaineTag}>{sujet.domaineRecherche}</span>
      <span style={styles.specialiteTag}>{sujet.specialite}</span>
    </div>
    <div style={styles.motsCles}>
      {sujet.getMotsClesList && sujet.getMotsClesList().slice(0, 3).map((mot, index) => (
        <span key={index} style={styles.motCle}>{mot}</span>
      ))}
    </div>
  </div>
);

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

// Styles
const styles = {
  container: {
    padding: '20px',
    maxWidth: '1200px',
    margin: '0 auto'
  },
  title: {
    color: '#003366',
    marginBottom: '20px',
    display: 'flex',
    alignItems: 'center'
  },
  searchSection: {
    backgroundColor: '#fff',
    padding: '25px',
    borderRadius: '10px',
    boxShadow: '0 2px 10px rgba(0,0,0,0.1)',
    marginBottom: '30px'
  },
  searchForm: {
    display: 'flex',
    flexDirection: 'column',
    gap: '15px'
  },
  inputGroup: {
    position: 'relative'
  },
  searchInput: {
    width: '100%',
    padding: '15px',
    fontSize: '16px',
    border: '2px solid #ddd',
    borderRadius: '8px',
    outline: 'none',
    transition: 'border-color 0.3s'
  },
  suggestions: {
    position: 'absolute',
    top: '100%',
    left: 0,
    right: 0,
    backgroundColor: '#fff',
    border: '1px solid #ddd',
    borderRadius: '0 0 8px 8px',
    zIndex: 1000,
    maxHeight: '200px',
    overflowY: 'auto'
  },
  suggestionItem: {
    padding: '10px 15px',
    cursor: 'pointer',
    display: 'flex',
    alignItems: 'center',
    borderBottom: '1px solid #eee'
  },
  filters: {
    display: 'flex',
    gap: '15px',
    alignItems: 'center'
  },
  filterSelect: {
    padding: '12px',
    border: '2px solid #ddd',
    borderRadius: '8px',
    fontSize: '14px',
    flex: 1
  },
  searchButton: {
    padding: '12px 25px',
    backgroundColor: '#003366',
    color: '#fff',
    border: 'none',
    borderRadius: '8px',
    fontSize: '16px',
    fontWeight: 'bold',
    cursor: 'pointer',
    transition: 'background-color 0.3s'
  },
  statsSection: {
    marginBottom: '30px'
  },
  statsTitle: {
    color: '#003366',
    marginBottom: '20px',
    display: 'flex',
    alignItems: 'center'
  },
  statsGrid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(250px, 1fr))',
    gap: '20px'
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
  resultsSection: {
    backgroundColor: '#fff',
    padding: '25px',
    borderRadius: '10px',
    boxShadow: '0 2px 10px rgba(0,0,0,0.1)'
  },
  resultsTitle: {
    color: '#003366',
    marginBottom: '20px'
  },
  resultsGrid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(auto-fit, minmax(350px, 1fr))',
    gap: '20px'
  },
  sujetCard: {
    border: '1px solid #eee',
    borderRadius: '8px',
    padding: '20px',
    transition: 'box-shadow 0.3s'
  },
  sujetTitle: {
    color: '#003366',
    marginBottom: '10px'
  },
  sujetDescription: {
    color: '#666',
    marginBottom: '15px',
    lineHeight: '1.5'
  },
  sujetMeta: {
    display: 'flex',
    gap: '10px',
    marginBottom: '15px'
  },
  domaineTag: {
    backgroundColor: '#003366',
    color: '#fff',
    padding: '4px 12px',
    borderRadius: '20px',
    fontSize: '12px'
  },
  specialiteTag: {
    backgroundColor: '#8B1E3F',
    color: '#fff',
    padding: '4px 12px',
    borderRadius: '20px',
    fontSize: '12px'
  },
  motsCles: {
    display: 'flex',
    gap: '8px',
    flexWrap: 'wrap'
  },
  motCle: {
    backgroundColor: '#f0f0f0',
    color: '#666',
    padding: '3px 10px',
    borderRadius: '15px',
    fontSize: '11px'
  },
  loading: {
    textAlign: 'center',
    padding: '40px',
    color: '#666'
  },
  noResults: {
    textAlign: 'center',
    padding: '40px',
    color: '#999',
    fontStyle: 'italic'
  }
};