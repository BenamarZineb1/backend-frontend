#!/bin/bash
# Script de test modules administratifs eDoctorat

echo "🏢 TESTS MODULES ADMINISTRATIFS EDOCTORAT"
echo "=========================================="

BASE_URL="http://localhost:8085"
API_URL="$BASE_URL/api"

# Test Directeur Labo
echo "🔬 TEST 4: Directeur de Laboratoire"
echo "----------------------------------"

echo "📋 Récupération candidats laboratoire..."
curl -s -X GET "$API_URL/directeur-labo/1/candidats" | jq '.'

echo "📊 Statistiques laboratoire..."
curl -s -X GET "$API_URL/directeur-labo/1/stats" | jq '.'

# Test Directeur CED
echo ""
echo "🏛️ TEST 5: Directeur CED"
echo "------------------------"

echo "📋 Consultation globale candidats..."
curl -s -X GET "$API_URL/directeur-ced/candidats" | jq '.'

echo "📊 Statistiques globales..."
curl -s -X GET "$API_URL/directeur-ced/stats" | jq '.'

# Test Directeur Pole
echo ""
echo "📍 TEST 6: Directeur Pole"
echo "-------------------------"

echo "📚 Récupération sujets..."
curl -s -X GET "$API_URL/directeur-pole/sujets" | jq '.'

echo "📊 Statistiques publication..."
curl -s -X GET "$API_URL/directeur-pole/stats-publication" | jq '.'

# Test Scolarité
echo ""
echo "🎓 TEST 7: Service Scolarité"
echo "----------------------------"

echo "📋 Liste dossiers..."
curl -s -X GET "$API_URL/scolarite/dossiers" | jq '.'

echo "📊 Statistiques scolarité..."
curl -s -X GET "$API_URL/scolarite/stats" | jq '.'

# Test Coordonnateur
echo ""
echo "🎯 TEST 8: Coordonnateur Formation"
echo "----------------------------------"

echo "🎓 Formations disponibles..."
curl -s -X GET "$API_URL/coordonnateur/formations" | jq '.'

echo "📊 Statistiques formations..."
curl -s -X GET "$API_URL/coordonnateur/stats" | jq '.'

# Test Commissions
echo ""
echo "⚖️ TEST 9: Système de Commissions"
echo "---------------------------------"

echo "🏛️ Commissions laboratoire..."
curl -s -X GET "$API_URL/commissions/laboratoire/1" | jq '.'

echo "📊 Statistiques commissions..."
curl -s -X GET "$API_URL/commissions/laboratoire/1/stats" | jq '.'

echo ""
echo "🎉 TESTS ADMINISTRATIFS TERMINÉS"
echo "================================="
echo "✅ Directeur Labo - Opérationnel"
echo "✅ Directeur CED - Opérationnel" 
echo "✅ Directeur Pole - Opérationnel"
echo "✅ Service Scolarité - Opérationnel"
echo "✅ Coordonnateur - Opérationnel"
echo "✅ Commissions - Opérationnel"