const API_URL = 'http://localhost:8080/api';

export const login = async (email, senha) => {
    const response = await fetch(`${API_URL}/auth/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, senha }),
    });

    if (!response.ok) {
        throw new Error('Falha na autenticação');
    }

    return response.json();
};

export const lancarNota = async (dados) => {
    const response = await fetch(`${API_URL}/avaliacoes`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dados)
    });
    if (!response.ok) throw new Error("Falha no lançamento");
    return response.json();
};

export const listarAlunosPorCurso = async (cursoId) => {
    const response = await fetch(`${API_URL}/usuarios/curso/${cursoId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    if (!response.ok) {
        throw new Error("Erro ao buscar lista de alunos");
    }

    return response.json();
};

export const buscarNotasPorAluno = async (alunoId) => {
    const response = await fetch(`${API_URL}/avaliacoes/aluno/${alunoId}`);
    if (!response.ok) throw new Error("Erro ao buscar notas");
    return response.json();
};

// NOVO: Método para atualizar a nota (o PUT)
export const atualizarNota = async (id, novoValor) => {
    const response = await fetch(`${API_URL}/avaliacoes/${id}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ valor: novoValor })
    });

    if (!response.ok) {
        throw new Error("Erro ao atualizar a nota");
    }
    return response.json();
};