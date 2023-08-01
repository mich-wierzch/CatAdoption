function fetchPosts() {
    fetch('/api/posts/getByUser')
        .then(response => response.json())
        .then(posts => {
            const postListElement = document.getElementById('userPostList'); //
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
                        <strong>Mobile Number:</strong> ${post.userMobilePhone}  <br>
                        <strong>Added on:</strong> ${post.createdAt}  <br>
                        <button onclick="deletePost(${post.postId})">Delete Post</button>
                      
                        <!-- Add other attributes as needed -->
                    </li>`;
            });
        })
        .catch(error => console.error('Error fetching posts:', error));
}

fetchPosts();

function deletePost(postId) {
    // Display the confirmation modal to the user
    if (confirm('Are you sure you want to delete this post?')) {
        // If the user confirms, perform the DELETE request to delete the post with the given postId
        fetch(`/api/posts/delete/${postId}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    // If the deletion was successful, remove the post from the UI
                    const postElement = document.getElementById(`post_${postId}`);
                    postElement.remove();
                } else {
                    console.error('Failed to delete post:', response.status);
                }
            })
            .catch(error => console.error('Error deleting post:', error));
    }
}

function updateUsername() {
    fetch('/api/user/get-username') // Replace '/get-username' with the endpoint that returns the username from the server.
        .then(response => response.text())
        .then(username => {
            document.getElementById('username-display').textContent = username;
            if (username !== 'anonymousUser'){
                document.getElementById('logout-link').style.display = 'inline-block';
            }
        })
        .catch(error => {
            console.error('Error fetching username:', error);
        });
}

// Call the function when the page loads to update the username if the user is already logged in.
document.addEventListener('DOMContentLoaded', updateUsername);
