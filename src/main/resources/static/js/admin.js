//Автозаполнение окна изменения пользователя
let editUserModal = document.getElementById('editUserModal')
editUserModal.addEventListener('show.bs.modal', function (event) {
    let modalBodyID = document.getElementById('editId')
    let modalBodyUsername = document.getElementById('editUsername')
    let modalBodyFirstName = document.getElementById('editFirstName')
    let modalBodyLastName = document.getElementById('editLastName')
    let modalBodyAge = document.getElementById('editAge')
    let modalBodyEmail = document.getElementById('editEmail')

    let id = event.relatedTarget.getAttribute('data-id')
    let username = event.relatedTarget.getAttribute('data-username')
    let firstName = event.relatedTarget.getAttribute('data-firstname')
    let lastName = event.relatedTarget.getAttribute('data-lastname')
    let age = event.relatedTarget.getAttribute('data-age')
    let email = event.relatedTarget.getAttribute('data-email')

    modalBodyID.value = id
    modalBodyUsername.value = username
    modalBodyFirstName.value = firstName
    modalBodyLastName.value = lastName
    modalBodyAge.value = age
    modalBodyEmail.value = email
})

//POST запрос на изменение пользователя
$(document).ready(function(){
    $('#editUserModalSubmit').click(function(){
        let formData = {
            id : $('#editId').val(),
            username : $('#editUsername').val(),
            firstName : $('#editFirstName').val(),
            lastName : $('#editLastName').val(),
            age : $('#editAge').val(),
            email: $('#editEmail').val(),
            password: $('#editPassword').val(),
            role: $('#editRole').val()
        }

        $.ajax({
            type: 'POST',
            url: '/admin/updateUser',
            data: formData,
            success: function(response) {
                window.location.href = '/admin';
            }
        })
    })
})

//Автозаполнение окна удаления пользователя
let deleteUserModal = document.getElementById('deleteUserModal')
deleteUserModal.addEventListener('show.bs.modal', function (event) {
    let modalBodyID = document.getElementById('deleteId')
    let modalBodyUsername = document.getElementById('deleteUsername')
    let modalBodyFirstName = document.getElementById('deleteFirstName')
    let modalBodyLastName = document.getElementById('deleteLastName')
    let modalBodyAge = document.getElementById('deleteAge')
    let modalBodyEmail = document.getElementById('deleteEmail')

    let id = event.relatedTarget.getAttribute('data-id')
    let username = event.relatedTarget.getAttribute('data-username')
    let firstName = event.relatedTarget.getAttribute('data-firstname')
    let lastName = event.relatedTarget.getAttribute('data-lastname')
    let age = event.relatedTarget.getAttribute('data-age')
    let email = event.relatedTarget.getAttribute('data-email')

    modalBodyID.value = id
    modalBodyUsername.value = username
    modalBodyFirstName.value = firstName
    modalBodyLastName.value = lastName
    modalBodyAge.value = age
    modalBodyEmail.value = email
})

//POST запрос на удаление пользователя
$(document).ready(function(){
    $('#deleteUserModalSubmit').click(function(){
        let formData = {
            id : $('#deleteId').val()
        }

        $.ajax({
            type: 'POST',
            url: '/admin/deleteUser',
            data: formData,
            success: function(response) {
                window.location.href = '/admin';
            }
        })
    })
})

//POST запрос на добавление пользователя
$(document).ready(function(){
    $('#addNewUserSubmit').click(function(){
        let formData = {
            username : $('#newUsername').val(),
            firstName : $('#newFirstName').val(),
            lastName : $('#newLastName').val(),
            age : $('#newAge').val(),
            email: $('#newEmail').val(),
            password: $('#newPassword').val(),
            role: $('#newRole').val()
        }

        $.ajax({
            type: 'POST',
            url: '/admin/addUser',
            data: formData,
            success: function(response) {
                window.location.href = '/admin';
            }
        })
    })
})