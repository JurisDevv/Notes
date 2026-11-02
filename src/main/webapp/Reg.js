async function reg() {
console.log("REG START")
const username = document.getElementById("userName").value;
const password = document.getElementById("password").value;
const repeatPassword = document.getElementById("repeatPassword").value;

if(password === repeatPassword) {

const response = await fetch("regServlet", {
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
window.location.href = "Login.html";
}

} else {
alert("Passwords don't match");
}

}

function signIn() {
window.location.href = "Login.html";
}