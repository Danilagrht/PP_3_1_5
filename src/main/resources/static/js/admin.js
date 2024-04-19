//Автозаполнение окна изменения пользователя
$(document).ready(function() {
    $('#editUserModal').on('show.bs.modal', function (event) {
        console.log(event.relatedTarget.id);

        let modalBody = $(this).find('.modal-body');

        fetch('/admin/users')
            .then(response => response.json())
            .then(data => {
                modalBody.find('#editId').val(data[event.relatedTarget.id.slice(16)].id);
                modalBody.find('#editUsername').val(data[event.relatedTarget.id.slice(16)].username);
                modalBody.find('#editFirstName').val(data[event.relatedTarget.id.slice(16)].firstName);
                modalBody.find('#editLastName').val(data[event.relatedTarget.id.slice(16)].lastName);
                modalBody.find('#editAge').val(data[event.relatedTarget.id.slice(16)].age);
                modalBody.find('#editEmail').val(data[event.relatedTarget.id.slice(16)].email);
            })
            .catch(error => console.error('Error:', error));

        /*let id = $(event.relatedTarget).data('id');
        let username = $(event.relatedTarget).data('username');
        let firstName = $(event.relatedTarget).data('firstname');
        let lastName = $(event.relatedTarget).data('lastname');
        let age = $(event.relatedTarget).data('age');
        let email = $(event.relatedTarget).data('email');*/
    });
});

//POST запрос на изменение пользователя
$(document).ready(function () {
    $('#editUserModalSubmit').click(async function () {
        let formData = {
            id: $('#editId').val(),
            username: $('#editUsername').val(),
            firstName: $('#editFirstName').val(),
            lastName: $('#editLastName').val(),
            age: $('#editAge').val(),
            email: $('#editEmail').val(),
            password: $('#editPassword').val(),
            role: $('#editRole').val().join(', ')
        }

        try {
            const response = await $.ajax({
                type: 'POST', url: '/admin/updateUser', data: formData
            });
            window.location.href = '/admin';
        } catch (error) {
            console.error('Error:', error);
        }
    })
})

//Автозаполнение окна удаления пользователя
$(document).ready(function() {
    $('#deleteUserModal').on('show.bs.modal', function (event) {
        let modalBody = $(this).find('.modal-body');

        fetch('/admin/users')
            .then(response => response.json())
            .then(data => {
                modalBody.find('#deleteId').val(data[event.relatedTarget.id.slice(18)].id);
                modalBody.find('#deleteUsername').val(data[event.relatedTarget.id.slice(18)].username);
                modalBody.find('#deleteFirstName').val(data[event.relatedTarget.id.slice(18)].firstName);
                modalBody.find('#deleteLastName').val(data[event.relatedTarget.id.slice(18)].lastName);
                modalBody.find('#deleteAge').val(data[event.relatedTarget.id.slice(18)].age);
                modalBody.find('#deleteEmail').val(data[event.relatedTarget.id.slice(18)].email);
            })
            .catch(error => console.error('Error:', error));
    });
});

//POST запрос на удаление пользователя
$(document).ready(function () {
    $('#deleteUserModalSubmit').click(async function () {
        let formData = {
            id: $('#deleteId').val()
        }

        try {
            const response = await $.ajax({
                type: 'POST', url: '/admin/deleteUser', data: formData
            });
            window.location.href = '/admin';
        } catch (error) {
            console.error('Error:', error);
        }
    })
})

//POST запрос на добавление пользователя
$(document).ready(function () {
    $('#addNewUserSubmit').click(async function () {
        let formData = {
            username: $('#newUsername').val(),
            firstName: $('#newFirstName').val(),
            lastName: $('#newLastName').val(),
            age: $('#newAge').val(),
            email: $('#newEmail').val(),
            password: $('#newPassword').val(),
            role: $('#newRole').val().join(', ')
        }

        try {
            const response = await $.ajax({
                type: 'POST', url: '/admin/addUser', data: formData
            });
            window.location.href = '/admin';
        } catch (error) {
            console.error('Error:', error);
        }
    })
})

//Заполнение таблицы пользователя
/*$(document).ready(function() {
    fetch('/admin/currentUser')
        .then(response => response.json())
        .then(data => {
            $('#userTableBody').empty();

            console.log(data);

            data.forEach(user => {
                $('#userTableBody').append(
                    `<tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${user.role}</td>
                    </tr>`
                );
            });
        })
        .catch(error => console.error('Error:', error));
});*/

//GET запрос данных авторизированного пользователя
$(document).ready(function() {
    fetch('/admin/currentUser')
        .then(response => response.json())
        .then(data => {
            console.log(data[0].roles);
            console.log(data);

            let usernameHeader = document.getElementById("usernameHeader");
            usernameHeader.textContent = data[0].username;

            let rolesHeader = document.getElementById("rolesHeader");
            rolesHeader.textContent = data[0].rolesString;

            let sidebarAdminButton = document.getElementById("sidebarAdminButton");
            let adminPanel = document.getElementById("adminPanel");
            let sidebarUserButton = document.getElementById("sidebarUserButton");
            let userPanel = document.getElementById("userPanel");

            if (hasRole(data[0].roles, "ROLE_ADMIN")) {
                sidebarAdminButton.style.display = "block";
                sidebarAdminButton.classList.add("active");
                //adminPanel.style.display = "block";
                adminPanel.classList.add("active");
            } else if (hasRole(data[0].roles, "ROLE_USER")) {
                //sidebarUserButton.style.display = "block";
                //userPanel.style.display = "block";
                sidebarUserButton.classList.add("active");
                userPanel.classList.add("active");
            }

            if (hasRole(data[0].roles, "ROLE_USER")) {
                sidebarUserButton.style.display = "block";
                //userPanel.style.display = "block";
            }

            let currentUserTableData = document.getElementById("currentUserTableData");
            let newRow = document.createElement("tr");

            let idCeil = document.createElement("td");
            let firstNameCeil = document.createElement("td");
            let lastNameCeil = document.createElement("td");
            let ageCeil = document.createElement("td");
            let emailCeil = document.createElement("td");
            let roleCeil = document.createElement("td");

            let idText = document.createTextNode(data[0].id);
            let firstNameText = document.createTextNode(data[0].firstName);
            let lastNameText = document.createTextNode(data[0].lastName);
            let ageText = document.createTextNode(data[0].age);
            let emailText = document.createTextNode(data[0].email);
            let roleText = document.createTextNode(data[0].rolesString);

            idCeil.appendChild(idText);
            firstNameCeil.appendChild(firstNameText);
            lastNameCeil.appendChild(lastNameText);
            ageCeil.appendChild(ageText);
            emailCeil.appendChild(emailText);
            roleCeil.appendChild(roleText);

            newRow.appendChild(idCeil);
            newRow.appendChild(firstNameCeil);
            newRow.appendChild(lastNameCeil);
            newRow.appendChild(ageCeil);
            newRow.appendChild(emailCeil);
            newRow.appendChild(roleCeil);

            currentUserTableData.appendChild(newRow);
        })
        .catch(error => console.error('Error:', error));
});

//GET запрос данных о всех пользователях
$(document).ready(function() {
    fetch('/admin/users')
        .then(response => response.json())
        .then(data => {
            console.log(data);

            let userTableData = document.getElementById("userTableData");
            for (let i = 0; i < data.length; i++) {
                let newRow = document.createElement("tr");

                let idCeil = document.createElement("td");
                let firstNameCeil = document.createElement("td");
                let lastNameCeil = document.createElement("td");
                let ageCeil = document.createElement("td");
                let emailCeil = document.createElement("td");
                let roleCeil = document.createElement("td");
                let editButtonCeil = document.createElement("td");
                let deleteButtonCeil = document.createElement("td");

                let idText = document.createTextNode(data[i].id);
                let firstNameText = document.createTextNode(data[i].firstName);
                let lastNameText = document.createTextNode(data[i].lastName);
                let ageText = document.createTextNode(data[i].age);
                let emailText = document.createTextNode(data[i].email);
                let roleText = document.createTextNode(data[i].rolesString);

                let editButton = document.createElement("button");
                editButton.textContent = "Edit";
                editButton.classList.add("btn", "btn-sm", "btn-outline-primary");
                editButton.setAttribute("data-bs-toggle", "modal");
                editButton.setAttribute("data-bs-target", "#editUserModal");
                editButton.id = "editButtonNumber" + i;

                let deleteButton = document.createElement("button");
                deleteButton.textContent = "Delete";
                deleteButton.classList.add("btn", "btn-sm", "btn-outline-danger");
                deleteButton.setAttribute("data-bs-toggle", "modal");
                deleteButton.setAttribute("data-bs-target", "#deleteUserModal");
                deleteButton.id = "deleteButtonNumber" + i;

                idCeil.appendChild(idText);
                firstNameCeil.appendChild(firstNameText);
                lastNameCeil.appendChild(lastNameText);
                ageCeil.appendChild(ageText);
                emailCeil.appendChild(emailText);
                roleCeil.appendChild(roleText);
                editButtonCeil.appendChild(editButton);
                deleteButtonCeil.appendChild(deleteButton);

                newRow.appendChild(idCeil);
                newRow.appendChild(firstNameCeil);
                newRow.appendChild(lastNameCeil);
                newRow.appendChild(ageCeil);
                newRow.appendChild(emailCeil);
                newRow.appendChild(roleCeil);
                newRow.appendChild(editButtonCeil);
                newRow.appendChild(deleteButtonCeil);

                userTableData.appendChild(newRow);
            }
        })
        .catch(error => console.error('Error:', error));
});

function hasRole(rolesArray, roleToCheck) {
    for (let i = 0; i < rolesArray.length; i++) {
        if (roleToCheck === rolesArray[i].name) {
            return true;
        }
    }
    return false;
};