

const radios = document.querySelectorAll('input[type="radio"]');

radios.forEach(radio => {
    if (radio.disabled) {
		radio.style.appearance = "none";
	} else {
		 radio.closest('td').classList.add('ocupado');
	}
});


