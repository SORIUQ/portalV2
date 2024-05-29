let originalInputValue;

const editGrade = (id) => {
    let input = document.getElementById(id);
    originalInputValue = input.value;
    input.disabled = false;
    let editBtn = document.getElementById(`edit_${id}`);
    editBtn.disabled = true;

    let confirmBtn = document.getElementById(`confirm_${id}`);
    let cancelBtn = document.getElementById(`cancel_${id}`);
    confirmBtn.classList.remove("btnEnable");
    cancelBtn.classList.remove("btnEnable");
}

const cancelEdit = (id) => {
    let input = document.getElementById(id);
    input.disabled = false;
    let editBtn = document.getElementById(`edit_${id}`);
    let confirmBtn = document.getElementById(`confirm_${id}`);
    let cancelBtn = document.getElementById(`cancel_${id}`);
    editBtn.disabled = false;
    confirmBtn.classList.add("btnEnable");
    cancelBtn.classList.add("btnEnable");
    input.value = originalInputValue;
    input.disabled = true;
}

const confirmEdit = (id) => {
    let input = document.getElementById(id);
    input.disabled = false; // Habilitar el campo antes de enviar el formulario
    let editBtn = document.getElementById(`edit_${id}`);
    let confirmBtn = document.getElementById(`confirm_${id}`);
    let cancelBtn = document.getElementById(`cancel_${id}`);
    editBtn.disabled = false;
    confirmBtn.classList.add("btnEnable");
    cancelBtn.classList.add("btnEnable");
}
