const BASE = "http://localhost:8080/api";

let outlets = [];

// DASHBOARD SUMMARY
fetch(BASE + "/admin/dashboard/summary")
    .then(r=>r.json())
    .then(d=>{
        totalOutlets.innerText=d.totalOutlets;
        pendingOutlets.innerText=d.pendingOutlets;
    });

function loadPending(){

    fetch(BASE+"/admin/outlets/pending")
        .then(r=>r.json())
        .then(data=>{
            outlets=data;
            renderDashboard();
            renderRequests();
        });
}

function renderDashboard(){

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

function approve(id){
    fetch(BASE+`/admin/outlets/${id}/approve`,{method:"PUT"})
        .then(()=>loadPending());
}

function reject(id){
    fetch(BASE+`/admin/outlets/${id}/reject`,{method:"PUT"})
        .then(()=>loadPending());
}

function showSection(id,element){

    document.querySelectorAll(".sidebar .nav-link").forEach(l=>{
        l.classList.remove("active");
    });

    element.classList.add("active");

    dashboard.classList.add("d-none");
    requests.classList.add("d-none");

    document.getElementById(id).classList.remove("d-none");
}

loadPending();