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
            document.getElementById(target+"-body").innerHTML = data;
            setTimeout(function () {
                $('#'+target).modal('show');
            }, 500);
        }
    });
}