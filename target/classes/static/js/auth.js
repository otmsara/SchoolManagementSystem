async function login() {
    const res = await fetch("/auth/login", {
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
    await fetch("/auth/register", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            username: username.value,
            password: password.value
        })
    });
    window.location = "/login";
}

function authFetch(url, options = {}) {
    const token = localStorage.getItem("token");
    return fetch(url, {
        ...options,
        headers: {
            ...options.headers,
            "Authorization": "Bearer " + token
        }
    });
}
