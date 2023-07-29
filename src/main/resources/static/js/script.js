function updateUsername() {
    fetch('/api/user/get-username') // Replace '/get-username' with the endpoint that returns the username from the server.
        .then(response => response.text())
        .then(username => {
            document.getElementById('username-display').textContent = username;
        })
        .catch(error => {
            console.error('Error fetching username:', error);
        });
}

// Call the function when the page loads to update the username if the user is already logged in.
document.addEventListener('DOMContentLoaded', updateUsername);