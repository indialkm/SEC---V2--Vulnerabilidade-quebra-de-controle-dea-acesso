import { login } from './api.js';

const loginForm = document.getElementById('loginForm');

if (loginForm) {
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const email = document.getElementById('email').value;
        const senha = document.getElementById('senha').value;

        try {
            const userData = await login(email, senha);
            localStorage.setItem('id', userData.id);
            localStorage.setItem('nome', userData.nome);
            localStorage.setItem('role', JSON.stringify(userData.role));
            localStorage.setItem('nomeCurso', userData.curso?.nomeCurso || '');
            localStorage.setItem('cursoId', userData.curso?.id || '');
            window.location.href = 'dashboard.html';
        } catch (error) {
            alert('Erro: Verifique suas credenciais.');
        }
    });
}

export function verificarAutenticacao() {
    if (!localStorage.getItem("id")) {
        window.location.href = "index.html";
    }
}