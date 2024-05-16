let nameInput = document.getElementById("nameInput");
let lastNameInput = document.getElementById("lastNameInput");
let emailInput = document.getElementById("emailInput");
let birthdayInput = document.getElementById("dateInput");
let dnieInput = document.getElementById("dnieInput");
let schoolInput = document.getElementById("schoolInput");
let courseInput = document.getElementById("courseInput");
let passInput = document.getElementById("passInput");
let pass2VerifyInput = document.getElementById("passInput2");
let errText = document.createElement('pre');
errText.classList.add("errorMsg");

/*ESTA AL REVES VALID = NO VALIDO / NONVALID = VALIDO JE JE */
function validInputProcces(inputName, errContent, containerName) {
    inputName.style.borderColor = "red";
    errText.textContent = errContent;
    errText.style.display = 'block';
    document.getElementById(containerName).appendChild(errText)
}
function nonValidInputProcces(inputName, containerName) {
    inputName.style.borderColor = "";
    errText.textContent = "";
    errText.style.display = 'none';
    let container = document.getElementById(containerName);
    if (container.contains(errText)) {
        container.removeChild(errText);
    }
}
// Validación tanto de nombre como apellido.
function validName(name) {
    let regex = /^[a-zA-Z ]+$/;
    return regex.test(name);
}

function passValidator2() {
    return passInput.value !== pass2VerifyInput.value;
}

function emailValidator(email) {
    let regex = /.+@[a-zA-Z]+\.[a-zA-z]{2,4}/;
    return regex.test(email);
}

function dniCheck(dni) {
    const dniRegex = /^[0-9]{8}[A-Z]$/;
    let res = false;
    if (dni.match(dniRegex)) {
        const digitControl = ['T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'];
        let dniArr = dni.toUpperCase().split('');
        let dniNumberString = "";
        let dniNumber;

        for(let i = 0; i < dniArr.length; i++) {
            if(!isNaN(parseInt(dniArr[i]))) {
                dniNumberString += dniArr[i]
            }
        }
        dniNumber = parseInt(dniNumberString);
        if (dniArr[dniArr.length -1] === digitControl[dniNumber % 23]) {
            res = true;
        }
    }
    return res;
}

function nieCheck(nie) {
    const nieRegex = /^[XYZ][0-9]{7}[A-Z]$/;
    let res = false;
    if (nieRegex.test(nie)) {
        const nieLettersToNumbers = {'X': 0, 'Y': 1, 'Z': 2};
        const digitControl = ['T','R','W','A','G','M','Y','F','P','D','X','B','N','J','Z','S','Q','V','H','L','C','K','E'];

        let nieArr = nie.toUpperCase().split('');
        let nieNumber = 0;

        // Convertir la letra inicial a número según la tabla
        let nieFirstNumber = nieLettersToNumbers[nieArr[0]];
        if (nieFirstNumber !== undefined) {
            nieNumber += nieFirstNumber * 10000000;
        }

        // Calcular el número NIE
        for (let i = 1; i < 8; i++) {
            nieNumber += parseInt(nieArr[i]) * Math.pow(10, 7 - i);
        }

        // Validar el dígito de control
        if (digitControl[nieNumber % 23] === nieArr[8]) {
            res = true;
        }
    }
    return res;
}

function detectIdType(dnie) {
    let dnieArr = dnie.toUpperCase().split('');
    if (dnieArr[0] >= '0' && dnieArr <= '9') {
        return dniCheck(dnie);
    } else {
        return nieCheck(dnie);
    }
}

function schoolCheck(school) {
    return school !== '0';
}

function courseCheck(course) {
    return course !== '0';
}

function passValidator(pass) {
    let containsUpper = false;
    let containsLower = false;
    let containsNumber = false;
    for(let i = 0; i < pass.length; i++) {
        let char = pass.charAt(i);
        if(char >= '0' && char <= '9') {
            containsNumber = true;
        } else if(char === char.toUpperCase()) {
            containsUpper = true;
        } else if(char === char.toLowerCase()) {
            containsLower = true;
        }
    }
    return containsUpper && containsLower && containsNumber && pass.length >= 8
}

const checkNameInput = () => {
    let res = false;
    if (!validName(nameInput.value.trim()))
        validInputProcces(nameInput, "¡Nombre incorrecto!", 'nameContainer');
    else {
        nonValidInputProcces(nameInput, 'nameContainer');
        res = true;
    }
    return res;
}
nameInput.addEventListener('input', checkNameInput);

const checkLastNameInput = () => {
    let res = false;
    if (!validName(lastNameInput.value.trim()))
        validInputProcces(lastNameInput, "¡Apellido incorrecto!", 'lastNameContainer');
    else {
        nonValidInputProcces(lastNameInput, 'lastNameContainer');
        res = true;
    }
    return res;
}
lastNameInput.addEventListener('input', checkLastNameInput);

const checkEmailInput = () => {
    let res = false;
    if(!emailValidator(emailInput.value.trim())){
        validInputProcces(emailInput, "¡Email incorrecto!", 'emailContainer');
    } else {
        nonValidInputProcces(emailInput, 'emailContainer');
        res = true;
    }
    return res;
}
emailInput.addEventListener('input', checkEmailInput);

const checkDnieInput = () => {
    let res = false;
    if(!detectIdType(dnieInput.value.trim())) {
        validInputProcces(dnieInput, "¡Dni/Nie incorrecto!", 'dnieContainer');
    } else {
        nonValidInputProcces(dnieInput, 'dnieContainer');
        res = true;
    }
    return res;
}
dnieInput.addEventListener('input', checkDnieInput);

const checkPassInput = () => {
    let res = false;
    if(!passValidator(passInput.value.trim()))
        validInputProcces(passInput, "¡Password demasiado débil!", 'passContainer');
    else {
        nonValidInputProcces(passInput, 'passContainer');
        res = true;
    }
    return res;
}
passInput.addEventListener('input', checkPassInput);

const checkPass2Input = () => {
    let res = false;
    if(passValidator2())
        validInputProcces(pass2VerifyInput, "¡Las passwords deben ser iguales!", 'pass2Container');
    else {
        nonValidInputProcces(pass2VerifyInput, 'pass2Container');
        res = true;
    }
    return res;
}
pass2VerifyInput.addEventListener('input', checkPass2Input)