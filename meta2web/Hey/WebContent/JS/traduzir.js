function traduzir(){
    var lingua = "pt";
    var text = document.getElementById("text").value;

    $.get("https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20191213T001309Z.e25bcf08c8d9a645.75e8c9f95200ffd1d0268c7653acdc230f838a71&text="+
        text+"&lang="+lingua, function(result){
        document.getElementById("text").innerHTML=result.text;
    })

}

function reconhece() {
    var text= document.getElementById("text").value;

    $.get("https://translate.yandex.net/api/v1.5/tr.json/detect?key=trnsl.1.1.20191213T001309Z.e25bcf08c8d9a645.75e8c9f95200ffd1d0268c7653acdc230f838a71&text="+
        text,function (result) {
        document.getElementById("text").innerHTML=result.lang;
    })
}

function myFunction() {
    var str = "How are you doing today?";
    var res = str.split("o");
    document.getElementById("demo").innerHTML = res;
}