function traduzir(texto1,texto2,id1,id3,id2){
    var lingua = "pt";
    $.get("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20191213T001309Z.e25bcf08c8d9a645.75e8c9f95200ffd1d0268c7653acdc230f838a71&text="+
        texto1+"&lang="+lingua, function(result){
        document.getElementById(id1).innerHTML=result.text[0];
    })
    $.get("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20191213T001309Z.e25bcf08c8d9a645.75e8c9f95200ffd1d0268c7653acdc230f838a71&text="+
        texto2+"&lang="+lingua, function(result){
        document.getElementById(id2).innerHTML=result.text[0];
        reconhece(result.text[0],id3);
    })


}

function reconhece(texto,id) {
    $.get("https://translate.yandex.net/api/v1.5/tr.json/detect?key=trnsl.1.1.20191213T001309Z.e25bcf08c8d9a645.75e8c9f95200ffd1d0268c7653acdc230f838a71&text="+
        texto,function (result) {
        document.getElementById(id).innerHTML=result.lang;
    })
}

function load() {
    var text = document.getElementById("text").value;
    var res = text.split(/\r?\n/);
    var filtered = res.filter(Boolean);
    var index;
    for (index = 0; index < filtered.length; index=index+4) {
        $("#tbNames tr:last").after("<tr>" +
            "<td><a href=\"" + filtered[index]+ "\">"+filtered[index]+"</a></td> "
            + "<td>" + filtered[index+1] + "</td> "
            + "<td"+" id = var"+(index+2)+">"+ filtered[index+2] + "</td> "
            + "<td"+" id = var"+(index+3)+">" + filtered[index+3] +"</td> "
            + "<td"+" id = var"+index+"></td>"
            + "<td><button onclick = \"traduzir(\'"+filtered[index+2]+ "\',\'"+filtered[index+3]+ "\',\'"+"var" + (index+2) + "\',\'"+"var" + (index) + "\',\'"
                + "var" + (index+3) + "\')\">Traduzir</button> </td>  </tr>");
    }

}

function load2() {
    var text = document.getElementById("text").value;
    var res = text.split(/\r?\n/);
    var filtered = res.filter(Boolean);
    var index;
    for (index = 0; index < filtered.length; index=index+4) {
        reconhece(filtered[index+3],"var"+index);
    }
}








