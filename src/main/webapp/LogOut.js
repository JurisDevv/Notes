async function LogoutUser() {
    try {
        const response = await fetch("LogoutServlet", {
            method: "POST"
        });

        if (response.ok) {
            window.location.href = "Login.html";
        }
    } catch (error) {
        console.error("Logout failed:", error);
    }
}