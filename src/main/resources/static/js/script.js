document.addEventListener("DOMContentLoaded", function () {
    fetch("/api/cats/getAllCats")
        .then(response => response.json())
        .then(data => displayCats(data))
        .catch(error => console.error("Error fetchin cats: ", error))
});

function displayCats(cats) {
    const catListContainer = document.getElementById("catListContainer");
    catListContainer.innerHTML = "";

    cats.forEach(cat => {
        const catElement = document.createElement("div");

        const imageElement = document.createElement("img");
        imageElement.src = cat.imageUrl;
        imageElement.alt = cat.name;
        imageElement.style.width="100px";
        catElement.appendChild(imageElement);

        const textElement = document.createElement("p");
        textElement.textContent = `Name: ${cat.name}, Age: ${cat.age}, Breed: ${cat.breed}`;
        catElement.appendChild(textElement);

        catListContainer.appendChild(catElement);
    });
}

const addCatButton = document.querySelector('.add_cat');
const addCatModal = document.getElementById('addCatModal');
const closeModalButton = document.getElementById('closeModal');

addCatButton.addEventListener('click', () => {
    addCatModal.style.display = 'block';
});

closeModalButton.addEventListener('click', () => {
    addCatModal.style.display = 'none';
});


