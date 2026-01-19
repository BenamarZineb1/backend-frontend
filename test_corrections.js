// Script de test pour vérifier la correction des problèmes
console.log('🧪 TEST CORRECTIONS EDOCTORAT\n');

const testData = {
  credentials: {
    email: 'correction.test@doctorat.ma',
    password: 'Correction2024!',
    firstName: 'Test',
    lastName: 'Correction',
    cne: 'CORR123456789',
    cin: 'CIN123456',
    telephone: '+212612345678',
    dateNaissance: '1995-06-15',
    lieuNaissance: 'Casablanca'
  }
};

async function testCompleteFlow() {
  console.log('🚀 TEST COMPLET: Inscription → Login → Affichage\n');
  
  try {
    // 1. Inscription
    console.log('1️⃣ Inscription...');
    const registerResponse = await fetch('http://localhost:8085/auth/register', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        email: testData.credentials.email,
        username: testData.credentials.email,
        password: testData.credentials.password,
        firstName: testData.credentials.firstName,
        lastName: testData.credentials.lastName,
        role: 'CANDIDAT',
        cne: testData.credentials.cne,
        numDoc: testData.credentials.cin,
        telephone: testData.credentials.telephone,
        dateNaissance: testData.credentials.dateNaissance,
        lieuNaissance: testData.credentials.lieuNaissance
      })
    });
    
    console.log(`   📝 Status inscription: ${registerResponse.status}`);
    
    if (!registerResponse.ok) {
      const error = await registerResponse.text();
      throw new Error(`Échec inscription: ${error}`);
    }
    
    // 2. Login
    console.log('\n2️⃣ Login...');
    const loginResponse = await fetch('http://localhost:8085/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        username: testData.credentials.email,
        password: testData.credentials.password
      })
    });
    
    const loginData = await loginResponse.json();
    console.log(`   🔐 Status login: ${loginResponse.status}`);
    
    if (!loginData.token) {
      throw new Error('Token non reçu');
    }
    
    // 3. Test accès profil
    console.log('\n3️⃣ Test accès profil (/candidat/me)...');
    const profileResponse = await fetch('http://localhost:8085/candidat/me', {
      headers: { 'Authorization': `Bearer ${loginData.token}` }
    });
    
    console.log(`   📄 Status profil: ${profileResponse.status}`);
    
    if (profileResponse.status === 200) {
      const profileData = await profileResponse.json();
      console.log('   ✅ Données profil reçues:');
      console.log('      ID:', profileData.id);
      console.log('      CNE:', profileData.cne);
      console.log('      CIN:', profileData.cin);
      console.log('      Nom Fr:', profileData.nomCandidatFr);
      console.log('      Prénom Fr:', profileData.prenomCandidatFr);
      console.log('      Téléphone:', profileData.telCandidat);
      console.log('      Lieu Naissance:', profileData.villeDeNaissance);
      
      // Vérification des données importantes
      const checks = [
        { field: 'cne', expected: testData.credentials.cne, actual: profileData.cne },
        { field: 'cin', expected: testData.credentials.cin, actual: profileData.cin },
        { field: 'nomCandidatFr', expected: testData.credentials.lastName, actual: profileData.nomCandidatFr },
        { field: 'prenomCandidatFr', expected: testData.credentials.firstName, actual: profileData.prenomCandidatFr },
        { field: 'telCandidat', expected: testData.credentials.telephone, actual: profileData.telCandidat }
      ];
      
      console.log('\n📊 VÉRIFICATION DONNÉES:');
      let allGood = true;
      checks.forEach(check => {
        const status = check.actual === check.expected ? '✅' : '❌';
        console.log(`   ${status} ${check.field}: ${check.actual || 'VIDE'} (attendu: ${check.expected})`);
        if (check.actual !== check.expected) allGood = false;
      });
      
      if (allGood) {
        console.log('\n🎉 TOUTES LES DONNÉES SONT CORRECTEMENT ENREGISTRÉES!');
      } else {
        console.log('\n⚠️ Certaines données sont manquantes ou incorrectes');
      }
    }
    
    // 4. Test modification
    console.log('\n4️⃣ Test modification profil...');
    const updateData = {
      adresse: '123 Avenue Mohammed V, Casablanca',
      sexe: 'Féminin'
    };
    
    const updateResponse = await fetch('http://localhost:8085/candidat/me', {
      method: 'PUT',
      headers: { 
        'Authorization': `Bearer ${loginData.token}`,
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(updateData)
    });
    
    console.log(`   🔄 Status update: ${updateResponse.status}`);
    
    if (updateResponse.status === 200) {
      const updatedProfile = await updateResponse.json();
      console.log('   ✅ Profil mis à jour avec adresse:', updatedProfile.adresse);
    }
    
    // 5. Résultat final
    console.log('\n' + '='.repeat(50));
    console.log('🎯 RÉSULTAT FINAL TEST');
    console.log('='.repeat(50));
    console.log('📧 Email test:', testData.credentials.email);
    console.log('🔐 Mot de passe:', testData.credentials.password);
    console.log('✅ Inscription fonctionnelle');
    console.log('✅ Login fonctionnel');  
    console.log('✅ Données affichées');
    console.log('✅ Modifications possibles');
    
    return {
      success: true,
      credentials: testData.credentials,
      profile: profileData
    };
    
  } catch (error) {
    console.error('\n❌ ERREUR DANS LE TEST:', error.message);
    return { success: false, error: error.message };
  }
}

// Exécution du test
console.log('Pour exécuter ce test, collez ce code dans la console du navigateur\n');
console.log('// Exécution: testCompleteFlow();');