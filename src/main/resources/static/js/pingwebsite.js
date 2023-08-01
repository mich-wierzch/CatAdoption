function pingWebsites(){
    const websites = [
        'https://cat-shelter-rws9.onrender.com/',
        'https://cat-shelter-rws9.onrender.com/main',
        'https://cat-shelter-rws9.onrender.com/register',
        'https://cat-shelter-rws9.onrender.com/login',
        'https://cat-shelter-rws9.onrender.com/addCat',
        'https://cat-shelter-rws9.onrender.com/addCat'
    ];

    websites.forEach((url) => {
        fetch(url)
            .then((response) => {
                if (response.ok) {
                    console.log(`${url} is UP`);
                } else {
                    console.error(`${url} is DOWN`);
                }
            })
            .catch((error) => {
                console.error(`Error pinging ${url}:`, error);
            });
    });
}

pingWebsites();

const intervalInMinutes = 13;
const intervalInMilliseconds = intervalInMinutes * 60 * 1000;

setInterval(pingWebsites, intervalInMilliseconds);
