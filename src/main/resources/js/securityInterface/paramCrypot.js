// 编写加密js 的function 方法
// java 调用这些js 方法，执行加密操作，发送ajax 请求
// 前端js混淆，引入额外参数，替换页面的原属性值
// 后台接受 ajax 请求，获取参数，将参数解密处理，执行具体业务


// var username = "16636663456"
// var password = "gengzi666"

function requestParamCrypot(username, password) {
    // 调用加密js 进行加密   -- 这里使用 微博登录中提取的 js 方法进行加密
    // 获取加密后的用户名
    username = getusername(username)
    // 当前时间
    var servicetime = new Date().getTime()
    // nonce 和 rsapubkey 是首次访问微博网站，就能返回的信息，这里我们之间写死
    var nonce = "5XP86"
    var rsaPubkey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALbcdLKOtTKOjalffv/LLLOqfyh8Ep4XHjvOivMU3Nb1N0puG4+NTrXBS8GDczgsZ+7J6D7FTcH8JInMKpz85LMCAwEAAQ=="
    // 获取加密后的密码
    password = getpassword(password, servicetime, nonce, rsaPubkey)

    // 加密完毕，调用后台接口
    var data = JSON.stringify({
        "nonce": nonce,
        "serviceTime": servicetime,
        "sp": password,
        "su": username
    });
    return data;
}


/**
 * 公钥加密
 * @param pwd 密码
 * @param servicetime 时间戳
 * @param nonce nonce
 * @param rsaPubkey 公钥
 * @returns {string} 加密后的参数
 */
function getpasswordToJsencrypt(pwd, servicetime, nonce, rsaPubkey) {
    Encrypt = new window.JSEncrypt();
    Encrypt.setPublicKey(rsaPubkey);
    return Encrypt.encrypt([servicetime, nonce].join('\t') + '\n' + pwd);
}


function requestParamCrypotByJsencrypt(username, password) {
    // 调用加密js 进行加密   -- 这里使用 微博登录中提取的 js 方法进行加密
    // 获取加密后的用户名
    username = getusername(username)
    // 当前时间
    var servicetime = new Date().getTime()
    // nonce 和 rsapubkey 是首次访问微博网站，就能返回的信息，这里我们之间写死
    var nonce = "5XP86"
    var rsaPubkey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALbcdLKOtTKOjalffv/LLLOqfyh8Ep4XHjvOivMU3Nb1N0puG4+NTrXBS8GDczgsZ+7J6D7FTcH8JInMKpz85LMCAwEAAQ=="
    // 获取加密后的密码
    password = getpasswordToJsencrypt(password, servicetime, nonce, rsaPubkey)

    // 加密完毕，调用后台接口
    var data = JSON.stringify({
        "nonce": nonce,
        "serviceTime": servicetime,
        "sp": password,
        "su": username
    });
    return data;
}


// d/8TgJb/+G9VwBKNJVphRH4MnlCnPRX0Iqz7f7MysxCaFMvTnOn8wOWmzWfuCfEeRmkSIexBbzHcTS30UbRB4g==


