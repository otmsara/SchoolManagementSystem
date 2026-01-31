// ============================================
// 🔐 SYSTÈME D'AUTHENTIFICATION COMPLET
// ============================================

// ✅ VÉRIFICATION D'AUTHENTIFICATION
function checkAuth() {
    const token = localStorage.getItem('token');
    if (!token) {
        window.location.href = '/login';
        return false;
    }
    return true;
}

// ✅ VÉRIFICATION DU RÔLE
function checkRole(requiredRole) {
    const role = localStorage.getItem('role');
    if (role !== requiredRole) {
        alert('Accès non autorisé pour votre rôle');
        window.location.href = '/login';
        return false;
    }
    return true;
}

// ✅ DÉCONNEXION
function logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('username');
    window.location.href = '/login';
}

// ✅ OBTENIR LE TOKEN POUR LES REQUÊTES API
function getAuthHeaders() {
    const token = localStorage.getItem('token');
    return {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
    };
}

// ✅ OBTENIR LE RÔLE ACTUEL
function getCurrentRole() {
    return localStorage.getItem('role');
}

// ✅ OBTENIR LE USERNAME ACTUEL
function getCurrentUsername() {
    return localStorage.getItem('username');
}

// ✅ FETCH AVEC AUTHENTIFICATION AUTOMATIQUE
async function authenticatedFetch(url, options = {}) {
    const token = localStorage.getItem('token');

    if (!token) {
        window.location.href = '/login';
        throw new Error('No authentication token');
    }

    // Ajouter le header Authorization automatiquement
    const headers = {
        ...options.headers,
        'Authorization': `Bearer ${token}`
    };

    const response = await fetch(url, {
        ...options,
        headers
    });

    // Si 401/403, rediriger vers login
    if (response.status === 401 || response.status === 403) {
        localStorage.clear();
        window.location.href = '/login';
        throw new Error('Authentication failed');
    }

    return response;
}

// ✅ PROTECTION AUTOMATIQUE AU CHARGEMENT DE LA PAGE
document.addEventListener('DOMContentLoaded', function() {
    // Vérifier si on est sur une page protégée (pas login ou landing)
    const path = window.location.pathname;

    if (path === '/login' || path === '/') {
        return; // Pages publiques
    }

    // Vérifier l'authentification
    if (!checkAuth()) {
        return;
    }

    // Vérifier le rôle selon la page
    if (path.startsWith('/student/')) {
        checkRole('STUDENT');
    } else if (path.startsWith('/teacher/')) {
        checkRole('TEACHER');
    } else if (path.startsWith('/admin/')) {
        checkRole('ADMIN');
    }
});