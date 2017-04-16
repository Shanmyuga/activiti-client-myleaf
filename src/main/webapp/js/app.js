$(document).ready(function(){
    $("a").click(function(event){
        event.preventDefault();
    });
});

function refreshProcess() {
	var url = '/refreshProcessList';
	$("#data-sidenav").load(url);
}

function startTask(arg) {
	var url = '/startProcess?processDefinitionId=' + arg;
	$("#formContent").load(url);
}