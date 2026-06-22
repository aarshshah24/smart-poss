const BASE_URL = "http://localhost:8080/api";

/*
 Generic POST request
*/
function postData(endpoint, data) {
    return fetch(BASE_URL + endpoint, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
        .then(async response => {
            const result = await response.json();

            if (!response.ok) {
                throw result;
            }

            return result;
        });
}

/*
 Generic GET request (for admin dashboard later)
*/
function getData(endpoint) {
    return fetch(BASE_URL + endpoint)
        .then(async response => {
            const result = await response.json();

            if (!response.ok) {
                throw result;
            }

            return result;
        });
}

/*
 Generic PUT request (approve / reject)
*/
function putData(endpoint) {
    return fetch(BASE_URL + endpoint, {
        method: "PUT"
    }).then(response => {
        if (!response.ok) {
            throw new Error("Request failed");
        }
    });
}
