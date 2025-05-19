function openModal(modalName) {
    fetch(`/modal/${modalName}`)
        .then(response => response.text())
        .then(html => {
            const modalContainer = document.getElementById("modalContainer");
            modalContainer.innerHTML = html;
            modalContainer.classList.remove("hidden");
        })
        .catch(error => console.error("Erro ao carregar o modal:", error));
}

function closeModal() {
    const modalContainer = document.getElementById("modalContainer");
    modalContainer.classList.add("hidden");
    modalContainer.innerHTML = ""; // Limpa o conteúdo
}
