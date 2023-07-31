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

function fetchPosts() {
    fetch('/api/posts/getAll')
        .then(response => response.json())
        .then(posts => {
            const postListElement = document.getElementById('postList'); //
            posts.forEach(post => {
                postListElement.innerHTML +=
                    `<li>
                        <img src="${post.imageUrl}"><br>
                        <strong>Name:</strong> ${post.catName}<br>
                        <strong>Sex:</strong> ${post.catSex}<br>
                        <strong>Age:</strong> ${post.catAge}<br>
                        <strong>Breed:</strong> ${post.catBreed}<br>
                        <strong>Description:</strong> ${post.description}<br>
                        <strong>Location:</strong> ${post.location}<br>
                        <strong>Posted by: </strong> ${post.userFirstName} ${post.userLastName}<br>
                        <strong>Mobile Number: ${post.userMobilePhone} </strong> <br>
                        <strong>Added on: ${post.createdAt} </strong> <br>
                        <!-- Add other attributes as needed -->
                    </li>`;
            });
        })
        .catch(error => console.error('Error fetching posts:', error));
}

fetchPosts();
