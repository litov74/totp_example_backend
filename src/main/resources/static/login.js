async function login(event) {
    if (event) event.preventDefault();

    const login = document.getElementById("login").value.trim();
    const totp = document.getElementById("totp").value.trim();
    const errorDiv = document.getElementById("error");

    if (!login || !totp) {
        errorDiv.innerText = "Пожалуйста, заполните оба поля";
        return;
    }

    const res = await fetch("/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ login, totp }),
        credentials: "same-origin" // важно, чтобы cookie сохранялась
    });

    if (!res.ok) {
        errorDiv.innerText = "Ошибка авторизации";
        return;
    }

    // Перенаправление на страницу grades
    window.location.href = "/grades";
}
