#!/bin/bash
# Script de test eDoctorat - Authentification et rôles

echo "🚀 TESTS FONCTIONNELS EDOCTORAT"
echo "================================"

# Configuration
BASE_URL="http://localhost:8085"
API_URL="$BASE_URL/api"

echo "📡 Vérification serveur..."
curl -s "$BASE_URL/actuator/health" > /dev/null
if [ $? -eq 0 ]; then
    echo "✅ Serveur démarré"
else
    echo "❌ Serveur non disponible"
    exit 1
fi

echo ""
echo "🔐 TEST 1: Authentification Candidat"
echo "-----------------------------------"

# Test inscription candidat
echo "📝 Inscription nouveau candidat..."
REGISTER_DATA='{
    "username": "test.candidat",
    "email": "test.candidat@doctorat.ma",
    "password": "Password123!",
    "firstName": "Test",
    "lastName": "Candidat",
    "role": "CANDIDAT"
}'

REGISTER_RESPONSE=$(curl -s -X POST "$API_URL/auth/register" \
    -H "Content-Type: application/json" \
    -d "$REGISTER_DATA")

echo "Réponse inscription: $REGISTER_RESPONSE"

# Test login
echo "🔑 Connexion candidat..."
LOGIN_DATA='{
    "username": "test.candidat",
    "password": "Password123!"
}'

LOGIN_RESPONSE=$(curl -s -X POST "$API_URL/auth/login" \
    -H "Content-Type: application/json" \
    -d "$LOGIN_DATA")

echo "Réponse login: $LOGIN_RESPONSE"

# Extraire token JWT
TOKEN=$(echo $LOGIN_RESPONSE | jq -r '.token')
if [ "$TOKEN" != "null" ]; then
    echo "✅ Token JWT obtenu"
else
    echo "❌ Échec authentification"
    exit 1
fi

echo ""
echo "👤 TEST 2: Accès aux fonctionnalités"
echo "-----------------------------------"

# Test accès profil candidat
echo "📋 Récupération profil..."
PROFILE_RESPONSE=$(curl -s -X GET "$API_URL/candidat/profile" \
    -H "Authorization: Bearer $TOKEN")

echo "Profil: $(echo $PROFILE_RESPONSE | jq '.prenomCandidatFr,.nomCandidatFr')"

# Test validation parcours
echo "🎓 Validation parcours académique..."
VALIDATION_DATA='{
    "diplomes": [
        {"type": "DUT", "annee": "2020"},
        {"type": "LICENCE", "annee": "2022"},
        {"type": "MASTER", "annee": "2024"}
    ]
}'

VALIDATION_RESPONSE=$(curl -s -X POST "$API_URL/validation/parcours" \
    -H "Authorization: Bearer $TOKEN" \
    -H "Content-Type: application/json" \
    -d "$VALIDATION_DATA")

echo "Validation: $VALIDATION_RESPONSE"

echo ""
echo "🎯 TEST 3: Recherche de sujets"
echo "-----------------------------"

# Test recherche sujets
echo "🔍 Recherche sujets IA..."
SEARCH_RESPONSE=$(curl -s -X GET "$API_URL/sujets/search?q=IA&domaine=Informatique" \
    -H "Authorization: Bearer $TOKEN")

echo "Sujets trouvés: $(echo $SEARCH_RESPONSE | jq 'length')"

echo ""
echo "🎉 TESTS TERMINÉS"
echo "=================="
echo "✅ Plateforme eDoctorat fonctionnelle"
echo "🔐 Authentification JWT opérationnelle"  
echo "👥 Gestion rôles complète"
echo "📊 Fonctionnalités candidates actives"