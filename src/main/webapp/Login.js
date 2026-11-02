async function Login() {

const username = document.getElementById("userName").value;
const password = document.getElementById("password").value;

if (username === "" || password === "") {
        alert("Enter username and password");
        return;
    }

const response = await fetch("LoginServlet", {
method: "POST",
headers: {
"Content-Type": "application/x-www-form-urlencoded",
},
body: new URLSearchParams({
          username: username,
          password: password
      }),
})

if (response.ok) {
window.location.href = "index.html";
} else {
alert("Wrong username or password");
}

}