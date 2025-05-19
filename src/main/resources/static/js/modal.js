function openModal(modalName) {
    fetch(`/modal/${modalName}`)
        .then(response => response.text())
        .then(html => {
            const modalContainer = document.getElementById("modalContainer");
            modalContainer.innerHTML = html;
            modalContainer.classList.remove("hidden");

            // Carregar as informações do usuário ao abrir o modal
            fetch('/api/usuarios/me')
                .then(response => {
                    if (!response.ok) throw new Error('Usuário não autenticado');
                    return response.json();
                })
                .then(data => {
                    document.getElementById('nomeUsuario').textContent = data.nome;
                    document.getElementById('emailUsuario').textContent = data.email;
                    document.getElementById('idUsuario').textContent = data.id_usuario;

                    const editarLink = document.getElementById('editarLink');
                    editarLink.href = `/usuarios/editar/${data.id_usuario}`;
                })
                .catch(error => console.error('Erro ao obter usuário:', error));
        })
        .catch(error => console.error("Erro ao carregar o modal:", error));
}

function closeModal() {
    const modalContainer = document.getElementById("modalContainer");
    modalContainer.classList.add("hidden");
    modalContainer.innerHTML = ""; // Limpa o conteúdo
}
