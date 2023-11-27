 async function pesquisacep(cep) {
	 try{
		
		 	cep = cep.replace(/[^0-9]/g, "");
			console.log(cep);
		    var url = "https://viacep.com.br/ws/" + cep + "/json/";
			console.log(url);
			const response = await fetch(url);
			const data = await response.json();
			
			if(data.logradouro == undefined){
				return alert("CEP não encontrado!");
			}
			document.getElementById("logradouro").value = data.logradouro;
		    document.getElementById("bairro").value = data.bairro;
		    document.getElementById("localidade").value = data.localidade;
		    document.getElementById("uf").value = data.uf;
	 } catch{
		 alert("Erro ao buscar CEP\nVerifique se o CEP é válido");
	 }
		    
}