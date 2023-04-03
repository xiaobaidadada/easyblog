
let config= {
    host:'/' //设置为空白不用切换host了
}



/**
 * get 请求
 * @param path
 * @param query
 * @param handle
 */
function asy_get(path, query, handle,navigate) {

    let result = {
        code: "未知",
        data: "无"
    }



    // console.log(Token)

    fetch(config.host + path + (query!=null?"?"+query:"") , {
        method: "GET",
        headers:{

        },

    }).then(response => response.json())
        .then(json_data => {
            //保证肯定是json数据
            if (json_data['code'] == 1) {
                result.data = json_data['value'];
                result.code = "成功"

            }
            else if(json_data['code'] == 3){
                //非法登录

            }
            else {

                console.log(result.code )
            }
            handle(result)

        });

}

/**
 * post 请求
 * @param path
 * @param query
 * @param object_data
 * @param handle
 */
function asy_post_by_json(path, query, dict_data,handle,navigate) {


    let result = {
        code: "未知",
        data: "无"
    }



    fetch(config.host + path + (query!=null?"?"+query:""), {
        method: "POST",
        headers:{
            'Content-Type': 'application/json',

        },
        body: JSON.stringify(dict_data)

    }).then(response => response.json())
        .then(json_data => {
            //保证肯定是json数据
            if (json_data['code'] == 1) {
                result.data = json_data['value'];
                result.code = "成功"

            }
            else if(json_data['code'] == 3){
                //非法登录
                //跳转到登录

            }
            else {

                console.log(result.code )
            }

            handle(result)

        });

}

/**
 * post 请求
 * @param path
 * @param query
 * @param object_data
 * @param handle
 */
function asy_post_by_formData(path, query, formData,handle,navigate) {


    let result = {
        code: "未知",
        data: "无"
    }



    fetch(config.host + path + (query!=null?"?"+query:""), {
        method: "POST",
        headers:{
            'Content-Type': 'multipart/form-data',

        },
        body: formData

    }).then(response => response.json())
        .then(json_data => {
            //保证肯定是json数据
            if (json_data['code'] == 1) {
                result.data = json_data['value'];
                result.code = "成功"

            }
            else if(json_data['code'] == 3){

            }
            else {

                console.log(result.code )
            }

            handle(result)

        });

}



export default asy_get;

export {asy_post_by_json,asy_post_by_formData};