const BASE_URL = "http://localhost:8080/api";

function postData(endpoint, data) {
    return fetch(BASE_URL + endpoint, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Request failed");
            }
            return response.json();
        });
}
