document.addEventListener("DOMContentLoaded", () => {
    const logoutBtn = document.getElementById("logout-btn");

    logoutBtn.addEventListener("click", async () => {
        try {
            await fetch("/logout", {
                method: "POST",
                credentials: "same-origin"
            });
        } finally {
            window.location.href = "/";
        }
    });
});
