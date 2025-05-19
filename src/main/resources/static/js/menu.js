document.addEventListener("DOMContentLoaded", function () {
    const menuContainer = document.getElementById("menuContainer");

    if (menuContainer) {
        fetch("/fragments/menu")
            .then(response => {
                if (!response.ok) {
                    throw new Error("Erro ao carregar o menu.");
                }
                return response.text();
            })
            .then(html => {
                menuContainer.innerHTML = html;
            })
            .catch(error => {
                console.error("Erro ao carregar o menu:", error);
            });
    }
});
