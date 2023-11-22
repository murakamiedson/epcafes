 async function pesquisacep(cep) {
		    cep = cep.replace(/[^0-9]/g, "");
			console.log(cep);
		    var url = "https://viacep.com.br/ws/" + cep + "/json/";
			console.log(url);
			const response = await fetch(url)
			const data = await response.json()
			console.log(data.bairro);
			document.getElementById("logradouro").value = data.logradouro;
		    document.getElementById("bairro").value = data.bairro;
		    document.getElementById("localidade").value = data.localidade;
		    document.getElementById("uf").value = data.uf;
}