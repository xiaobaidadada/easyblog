


let essay_index=0;//文章篇数
let essay_type=0;//散文类型
let url=''
//

//对文章di添加点击标题
function addEssayToDiv( jsonessay){

    let essayfather=document.querySelector("#essay > div");

    let a=document.createElement("a");
    a.className="list-group list-group-item-action list-group-flush title";
    a.innerText=jsonessay['title'];
    a.id=jsonessay['id'];
    a.href=url+"/blog/essay/"+jsonessay['id']
        essayfather.appendChild(a)
}

//从后端获取散文
function updateessay(){

    let http = new XMLHttpRequest();

    http.open('post', url+'/index/tblog',true);//设置ajax参数
    http.onreadystatechange = function (e) {
        // console.log(http.responseText)
        if (http.readyState == 4 && http.status == 200 ) {

            // alert(http.responseText+'成功')
            let array=JSON.parse(http.responseText)['value'];
            if (array.length!=0)
            for (let t in array){
                addEssayToDiv(array[t]);

            }
            else{
                alert_me("没有更多文章了","light")
            }

            essay_index+=6;//加6次
            // alert_me(essay_index)

        }
    }
    http.setRequestHeader("Content-Type","text/plain;charset=UTF-8;")//设置这个会分两次请求发送数据，application/json;会产生自动判断的跨域，vue测试的时候和服务器端口不一样，所以不用这个
    http.timeout=2000;

    let data = {
        'essay_index': essay_index,
        'essay_type':parseInt(essay_type),
    }
    http.send(JSON.stringify(data));

}

window.onload=function (){
    load_essaytype();//加载全部文章类别
    updateessay();//加载六篇文章
    indexload();//首页访问次数增加

}

let load_essaytype=function (){
    let http = new XMLHttpRequest();

    http.open('post', url+'/index/type',true);//设置ajax参数
    http.onreadystatechange = function (e) {
        // console.log(http.responseText)
        if (http.readyState == 4 && http.status == 200 ) {

            // alert(http.responseText+'成功')
            let type= document.querySelector("#type")//文章父div
            let array=JSON.parse(http.responseText)['value'];

            for (let t in array){
                let id=array[t]['id'];
                let name=array[t]['type_name'];
                let li=document.createElement("li");
                li.innerText=name;
                li.className="dropdown-item type";
                li.id=id;
                li.addEventListener("click",essay_type_load);
                type.appendChild(li)

            }



        }
    }
    http.setRequestHeader("Content-Type","text/plain;charset=UTF-8;")//设置这个会分两次请求发送数据，application/json;会产生自动判断的跨域，vue测试的时候和服务器端口不一样，所以不用这个
    http.timeout=2000;


    http.send();
}

function alert_me(message, type) {
    var wrapper = document.createElement('div')
    wrapper.innerHTML = '<div class="alert alert-' + type + ' alert-dismissible" role="alert">' + message + '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button></div>'
    let alertPlaceholder = document.querySelector("#essay")
    alertPlaceholder.appendChild(wrapper)
}

//点击文章
function essay_type_load(event){
// console.log(event.currentTarget.id)//target只是触发的，可能不是自己的dom
     essay_type=event.currentTarget.id;


    let http = new XMLHttpRequest();

    http.open('post', url+'/index/typename',true);//设置ajax参数
    http.onreadystatechange = function (e) {
        // console.log(http.responseText)
        if (http.readyState == 4 && http.status == 200 ) {

            // alert(http.responseText+'成功')
            let array=JSON.parse(http.responseText)['typename'];

            document.querySelector("#essay > p").innerHTML=array;
           essay_index=0;//文章篇数
            document.querySelector("#essay > div").innerHTML=""
            updateessay();//根据类型加载全部文章

        }
    }
    http.setRequestHeader("Content-Type","text/plain;charset=UTF-8;")//设置这个会分两次请求发送数据，application/json;会产生自动判断的跨域，vue测试的时候和服务器端口不一样，所以不用这个
    http.timeout=2000;

    let data = {

        'id':essay_type,
    }
    http.send(JSON.stringify(data));
}

//首页访问次数增加
function indexload(){
    let http = new XMLHttpRequest();
    http.open('post', url+'/index/indexload',true);//设置ajax参数
    // http.onreadystatechange = function (e) {
    //     // console.log(http.responseText)
    // }
    http.setRequestHeader("Content-Type","text/plain;charset=UTF-8;")//设置这个会分两次请求发送数据，application/json;会产生自动判断的跨域，vue测试的时候和服务器端口不一样，所以不用这个
    http.timeout=2000;
    http.send();
}

//查询
function query(event){
    /*
    * keycode    8 = BackSpace 回格
keycode    9 = Tab
keycode   12 = Clear
keycode   13 = Enter 回车
    * */
    // console.log(event.currentTarget.value)
    if (event.key=="Enter"){
        let http = new XMLHttpRequest();
        let input=event.currentTarget.value;

        http.open('post', url+'/index/queryblog',true);//设置ajax参数
        http.onreadystatechange = function (e) {
            // console.log(http.responseText)
            if (http.readyState == 4 && http.status == 200 ) {
                document.querySelector("#essay > div").innerHTML=""
                // alert(http.responseText+'成功')
                let array=JSON.parse(http.responseText)['blog'];
                if (array.length!=0)
                    for (let t in array){
                        addEssayToDiv(array[t]);

                    }
                else{
                    alert_me("没有更多文章了","light")
                }


                document.querySelector("#essay > p").innerHTML='最新搜索结果';
                essay_index=0;//文章篇数
                essay_type=0;//散文类型
                // alert_me(essay_index)

            }
        }
        http.setRequestHeader("Content-Type","text/plain;charset=UTF-8;")//设置这个会分两次请求发送数据，application/json;会产生自动判断的跨域，vue测试的时候和服务器端口不一样，所以不用这个
        http.timeout=2000;

        let data = {
            'input': input,

        }
        http.send(JSON.stringify(data));
    }

}