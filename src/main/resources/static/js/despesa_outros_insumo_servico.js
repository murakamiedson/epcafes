function submitDespesa(e) {

	var sourceElement = e.currentTarget.parentElement.parentElement;

	var despesaData = {
		id: sourceElement.querySelector("input[name='id']").value,
		descricao: sourceElement.querySelector("input[name='descricao']").value,
		notaFiscal: sourceElement.querySelector("input[name='notaFiscal']").value,
		valor: sourceElement.querySelector("input[name='valor']").value,
		porcUtilizacao: sourceElement.querySelector("input[name='porcUtilizacao']").value,
	}
	
	fetch('/despesa_insumo_servico/update', {
		method: "post",
		 body: JSON.stringify(despesaData),
		 headers: {
    		'Accept': 'application/json',
    		'Content-Type': 'application/json'
  		}})
  		.then((stuff)=>{
			console.log(stuff);
        	location.reload();
		}).catch((error)=>{
			console.log(error);
		})

}


function deleteDespesa(e){
	var sourceElement = e.currentTarget.parentElement.parentElement;
	var id = sourceElement.querySelector("input[name='id']").value;
	fetch(`/despesa_insumo_servico/delete/${id}`, {method: 'post'})
	.then((stuff)=>{
		location.reload();
	}).catch((err)=>{
		console.log(err);
	})
}

function createDespesa(e){
	
	var sourceElement = e.currentTarget.parentElement.parentElement;
	
	var despesaData = {
		descricao: sourceElement.querySelector("input[name='descricao']").value,
		notaFiscal: sourceElement.querySelector("input[name='notaFiscal']").value,
		valor: sourceElement.querySelector("input[name='valor']").value,
		porcUtilizacao: sourceElement.querySelector("input[name='porcUtilizacao']").value,
	}
	
	fetch('/despesa_insumo_servico/create', {
		method: "post",
		 body: JSON.stringify(despesaData),
		 headers: {
    		'Accept': 'application/json',
    		'Content-Type': 'application/json'
  		}})
  		.then((stuff)=>{
			console.log(stuff);
        	location.reload();
		}).catch((error)=>{
			console.log(error);
		})

}