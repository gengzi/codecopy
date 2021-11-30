function getAesString(data, key, iv) {//加密
    var key = CryptoJS.enc.Utf8.parse(key);
    var iv = CryptoJS.enc.Utf8.parse(iv);
    var encrypted = CryptoJS.AES.encrypt(data, key,
        {
            iv: iv,
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7
        });
    return encrypted.toString();    //返回的是base64格式的密文
}

function getDAesString(encrypted, key, iv) {//解密
    var key = CryptoJS.enc.Utf8.parse(key);
    var iv = CryptoJS.enc.Utf8.parse(iv);
    var decrypted = CryptoJS.AES.decrypt(encrypted, key,
        {
            iv: iv,
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7
        });
    return decrypted.toString(CryptoJS.enc.Utf8);
}

function getAES(data) { //加密
    var key = 'AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA';  //密钥
    var iv = '1234567812345678';
    var encrypted = getAesString(data, key, iv); //密文
    var encrypted1 = CryptoJS.enc.Utf8.parse(encrypted);
    return encrypted;
}

function getDAes(data) {//解密
    var key = 'AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA';  //密钥
    var iv = '1234567812345678';
    var decryptedStr = getDAesString(data, key, iv);
    return decryptedStr;
}


function getRSAString(data, key) {//加密
    var key = CryptoJS.enc.Utf8.parse(key);
    var encrypted = CryptoJS.RSA.encrypt(data, key,
        {
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7
        });
    return encrypted.toString();    //返回的是base64格式的密文
}

function getRSAString(encrypted, key) {//解密
    var key = CryptoJS.enc.Utf8.parse(key);
    var decrypted = CryptoJS.RSA.decrypt(encrypted, key,
        {
            mode: CryptoJS.mode.CBC,
            padding: CryptoJS.pad.Pkcs7
        });
    return decrypted.toString(CryptoJS.enc.Utf8);
}


// 使用 jsencrypt 进行 rsa 前端的加密和解密
// https://github.com/travist/jsencrypt

/**
 * RSA 加密，使用公钥加密
 * @param key 公钥
 * @param str 需要加密的内容
 * @returns {CipherParams|PromiseLike<ArrayBuffer>|null|string|string|*}
 */
function encryptRSAByPublicKey(str, key) {
    Encrypt = new JSEncrypt();
    Encrypt.setPublicKey(key);
    return Encrypt.encrypt(str);
}

/**
 * RSA 解密，使用密钥解密
 * @param str 需要解密的内容
 * @param key 密钥
 * @returns {WordArray|PromiseLike<ArrayBuffer>|null|*|undefined}
 */
function decryptRSAByPrivateKey(str, key) {
    Encrypt = new JSEncrypt();
    Encrypt.setPrivateKey(key);
    return Encrypt.decrypt(str);
}

















