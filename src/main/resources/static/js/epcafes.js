/*
* Função para atualizar o body do modal com o conteúdo retornado da requisição
* @param url - url da requisição
* @param target - id do modal
* @param data - dados a serem enviados na requisição
*
* Body do modal deve ter o id: target-body
*/
function atualizaModal(url, target, data){
    $.ajax({
        url: url,
        type: 'GET',
        data: data,
        success: function (data) {
            $('#'+target+"-body").empty();
            $('#'+target+"-body").append(data);
            setTimeout(function () {
                $('#'+target).modal('show');
            }, 500);
        }
    });
}

function deleteItem(id){
    if(!confirm("Deseja realmente excluir o item?")) return false;
    $.ajax({
        url: location.href + "/delete/" + id,
        type: 'GET',
        success: function (data) {
            alert(data);
            location.reload();
        },
        error: function (data) {
            alert(data);
        }
    });
}