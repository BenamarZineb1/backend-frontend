#!/bin/bash
# Script de démarrage et test frontend eDoctorat

echo "🌐 DEMO FRONTEND EDOCTORAT"
echo "=========================="

# Démarrer frontend React
echo "🚀 Démarrage interface React..."
cd "c:\Users\PH\Desktop\edoctorat\PROJETEdoctorat-main\PROJETEdoctorat-main"

# Vérifier si node_modules existe
if [ ! -d "node_modules" ]; then
    echo "📦 Installation dépendances..."
    npm install
fi

echo "🔌 Démarrage serveur développement..."
npm start &

# Attendre démarrage
sleep 10

echo "📡 Vérification frontend..."
curl -s "http://localhost:3000" > /dev/null
if [ $? -eq 0 ]; then
    echo "✅ Frontend démarré sur http://localhost:3000"
else
    echo "❌ Problème démarrage frontend"
fi

echo ""
echo "🎯 SCÉNARIOS DE DÉMONSTRATION"
echo "============================="

echo "1. 📝 Inscription candidat"
echo "   - Accéder à http://localhost:3000/register"
echo "   - Remplir formulaire avec:"
echo "     * Nom: Test"
echo "     * Prénom: Candidat" 
echo "     * Email: test@candidat.ma"
echo "     * Mot de passe: Password123!"

echo ""
echo "2. 🔐 Connexion"
echo "   - Accéder à http://localhost:3000/login"
echo "   - Utiliser credentials ci-dessus"

echo ""
echo "3. 📋 Tableau de bord candidat"
echo "   - Vue profil personnel"
echo "   - Validation parcours académique"
echo "   - Recherche sujets dynamique"
echo "   - Notifications en temps réel"

echo ""
echo "4. 👨‍🏫 Interface professeur"
echo "   - Gestion sujets de recherche"
echo "   - Évaluations commissions"
echo "   - Suivi candidats"

echo ""
echo "5. 🏛️ Interface administrateurs"
echo "   - Directeur Labo: gestion commissions"
echo "   - Directeur CED: vue globale"
echo "   - Directeur Pole: publications"
echo "   - Scolarité: validation dossiers"

echo ""
echo "🎉 DÉMONSTRATION PRÊTE!"
echo "======================="
echo "Frontend: http://localhost:3000"
echo "Backend: http://localhost:8085"
echo "Documentation API: http://localhost:8085/swagger-ui.html"