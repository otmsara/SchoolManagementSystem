async function login() {
    const res = await fetch("/api/auth/login", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            username: username.value,
            password: password.value
        })
    });

    if (res.ok) {
        const data = await res.json();
        localStorage.setItem("token", data.token);
        window.location = "/dashboard";
    } else {
        error.innerText = "Invalid credentials";
    }
}

async function register() {
    await fetch("/api/auth/register", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            username: username.value,
            password: password.value
        })
    });

    window.location = "/login";
}
