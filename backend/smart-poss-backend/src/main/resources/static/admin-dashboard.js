// ===============================
// DASHBOARD SUMMARY
// ===============================

fetch("http://localhost:8080/api/admin/dashboard/summary")
    .then(response => response.json())
    .then(data => {

        document.getElementById("totalOutlets").innerText =
            data.totalOutlets;

        document.getElementById("pendingOutlets").innerText =
            data.pendingOutlets;

        document.getElementById("approvedOutlets").innerText =
            data.approvedOutlets;

        // these two stay for future use
        document.getElementById("revenue").innerText = "₹0";
        document.getElementById("activeUsers").innerText = 0;
    });


// ===============================
// PENDING OUTLET TABLE
// ===============================

fetch("http://localhost:8080/api/admin/outlets/pending")
    .then(response => response.json())
    .then(outlets => {

        const tableBody =
            document.getElementById("pendingOutletTable");

        tableBody.innerHTML = "";

        outlets.forEach(outlet => {

            tableBody.innerHTML += `
        <tr>
          <td>${outlet.ownerName}</td>
          <td>${outlet.outletName}</td>
          <td>${outlet.city}</td>
          <td>${outlet.phoneNumber}</td>

          <td>
            <button class="btn btn-success btn-sm"
              onclick="approveOutlet('${outlet.id}')">
              Approve
            </button>

            <button class="btn btn-danger btn-sm"
              onclick="rejectOutlet('${outlet.id}')">
              Reject
            </button>
          </td>
        </tr>
      `;
        });
    });


// ===============================
// APPROVE
// ===============================

function approveOutlet(id) {

    fetch(`/api/admin/outlets/${id}/approve`, {
        method: "PUT"
    })
        .then(() => location.reload());
}


// ===============================
// REJECT
// ===============================

function rejectOutlet(id) {

    fetch(`/api/admin/outlets/${id}/reject`, {
        method: "PUT"
    })
        .then(() => location.reload());
}
