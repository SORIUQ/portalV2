let passInput = document.getElementById("newPass");
let newpassInput = document.getElementById("oldPass");
let errorPass = document.getElementById("errorPass");
let botonHecho = document.getElementById("botonInput");
let buttonMail = document.getElementById("mailChangeButton");
let buttonPass = document.getElementById("passChangeButton");


function detColoresdp(id){
	
	var h1dp = document.getElementsByClassName("h1dp")[0];
	var tarjetaPersonal=document.getElementsByClassName("tarjetaPersonal")[0];
	var ladoIzquierdo=document.getElementsByClassName("ladoIzquierdoTarjeta")[0];
	
	switch(id){
	case 1 : 
	h1dp.classList.add('cesurColor');
    tarjetaPersonal.classList.add('cesurColor');
    ladoIzquierdo.classList.add('cesurColor');
    break;
    
    case 2 : 
	h1dp.classList.add('picassoColor');
    tarjetaPersonal.classList.add('picassoColor');
    ladoIzquierdo.classList.add('picassoColor');
    break;
    
    case 3 : 
	h1dp.classList.add('belenColor');
    tarjetaPersonal.classList.add('belenColor');
    ladoIzquierdo.classList.add('belenColor');
    break;
    
    case 4 : 
	h1dp.classList.add('turingColor');
    tarjetaPersonal.classList.add('turingColor');
    ladoIzquierdo.classList.add('turingColor');
    break;
    
    case 5 : 
	h1dp.classList.add('joseColor');
    tarjetaPersonal.classList.add('joseColor');
    ladoIzquierdo.classList.add('joseColor');
    break;
    
    default : break;
	}
  }
  
function showChangeInput (){
	let inputPassDiv = document.getElementById("inputPass");
	buttonMail.classList.toggle("hiddenPass");
	buttonPass.classList.toggle("botonSelected");
	
	if (buttonPass.innerText === "Cambiar Contraseña"){
		buttonPass.innerText = "Cancelar"
	} else buttonPass.innerText = "Cambiar Contraseña"
	
	inputPassDiv.classList.toggle("hiddenPass");
	inputPassDiv.classList.toggle("showingChange");
}

function showChangeInputMail (){
	let inputPassDiv = document.getElementById("inputMail");
	buttonPass.classList.toggle("hiddenPass");
	buttonMail.classList.toggle("botonSelected");
	
	if (buttonMail.innerText === "Cambiar Email"){
		buttonMail.innerText = "Cancelar"
	} else buttonMail.innerText = "Cambiar Email"
	
	inputPassDiv.classList.toggle("hiddenPass");
	inputPassDiv.classList.toggle("sho123wingChange");
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

const checkPassInput = () => {
    let res = false;
    if(!passValidator(passInput.value.trim())) {
		
		errorPass.classList.remove("passNotValidShow");
        validInputProcces(passInput, "¡Password demasiado débil! \nLa contraseña debe tener: \n- Una mayúscula \n- Una minúscula \n- 8 caracteres\n- Un número" , 'passContainer');

        }
    else {
		botonHecho.disabled=false;
		errorPass.classList.add("passNotValidShow");
        nonValidInputProcces(passInput, 'passContainer');
        res = true;
    }
    return res;
}


passInput.addEventListener('input', checkPassInput);



function validInputProcces(inputName, errContent, containerName) {
    inputName.style.borderColor = "red";
    errText.textContent = errContent;
    errText.style.display = 'block';
    document.getElementById(containerName).appendChild(errText)
}

function nonValidInputProcces(inputName, containerName) {
    inputName.style.borderColor = "blue";
    errText.textContent = "";
    errText.style.display = 'none';
    let container = document.getElementById(containerName);
    if (container.contains(errText)) {
        container.removeChild(errText);
    }
}




