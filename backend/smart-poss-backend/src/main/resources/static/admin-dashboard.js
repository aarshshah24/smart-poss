const BASE = "http://localhost:8080/api";

let outlets = [];
let verifiedOutlets = [];

function loadSummary(){
    fetch(BASE + "/admin/dashboard/summary")
        .then(r=>r.json())
        .then(d=>{
            document.getElementById("totalOutlets").innerText = d.totalOutlets;
            document.getElementById("pendingOutlets").innerText = d.pendingOutlets;
            document.getElementById("activeUsers").innerText = d.activeUsers;
        });
}

function loadPending(){
    fetch(BASE+"/admin/outlets/pending")
        .then(r=>r.json())
        .then(data=>{
            outlets=data;
            renderDashboard();
            renderRequests();
        });
}

function loadVerified(){
    fetch(BASE+"/admin/outlets/verified")
        .then(r=>r.json())
        .then(data=>{
            verifiedOutlets = data;
            renderVerified();
        });
}

function renderDashboard(){
    const dashboardTable = document.getElementById("dashboardTable");
    dashboardTable.innerHTML="";
    outlets.slice(0,5).forEach(o=>{
        dashboardTable.innerHTML+=`
        <tr>
            <td>${o.ownerName}</td>
            <td>${o.outletName}</td>
            <td>${o.city}</td>
            <td>${o.phoneNumber}</td>
            <td>
                <button class="btn btn-success btn-sm" onclick="approve('${o.id}')">Approve</button>
                <button class="btn btn-danger btn-sm" onclick="reject('${o.id}')">Reject</button>
            </td>
        </tr>`;
    });
}

function renderRequests(){
    const requestsTable = document.getElementById("requestsTable");
    requestsTable.innerHTML="";
    outlets.forEach((o,i)=>{
        requestsTable.innerHTML+=`
        <tr>
            <td>${i+1}</td>
            <td>${o.ownerName}</td>
            <td>${o.outletName}</td>
            <td>${o.city}</td>
            <td>${o.phoneNumber}</td>
            <td>
                <button class="btn btn-success btn-sm" onclick="approve('${o.id}')">Approve</button>
                <button class="btn btn-danger btn-sm" onclick="reject('${o.id}')">Reject</button>
            </td>
        </tr>`;
    });
}

function renderVerified(){
    let table = document.getElementById("verifiedTable");
    table.innerHTML = "";

    verifiedOutlets.forEach((o,i)=>{
        let formattedDate = "—";
        if (o.approvedAt) {
            const date = new Date(o.approvedAt);
            formattedDate = date.toLocaleDateString('en-GB') + " " +
                date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
        }

        table.innerHTML += `
        <tr>
            <td>${i+1}</td>
            <td>${o.ownerName}</td>
            <td>${o.outletName}</td>
            <td>${o.city}</td>
            <td class="text-success fw-bold">${formattedDate}</td>
        </tr>`;
    });
}

function approve(id){
    fetch(BASE+`/admin/outlets/${id}/approve`,{method:"PUT"})
        .then(()=>{
            loadPending();
            loadVerified();
            loadSummary();
        });
}

function reject(id){
    fetch(BASE+`/admin/outlets/${id}/reject`,{method:"PUT"})
        .then(()=>{
            loadPending();
            loadSummary();
        });
}

function showSection(id,element){
    document.querySelectorAll(".sidebar .nav-link").forEach(l=>{
        l.classList.remove("active");
    });
    element.classList.add("active");

    document.getElementById("dashboard").classList.add("d-none");
    document.getElementById("requests").classList.add("d-none");
    document.getElementById("verifiedoutlet").classList.add("d-none");

    document.getElementById(id).classList.remove("d-none");

    if(id === "verifiedoutlet"){
        loadVerified();
    }
}

loadSummary();
loadPending();
loadVerified();

setInterval(()=>{
    loadSummary();
    loadPending();
},5000);