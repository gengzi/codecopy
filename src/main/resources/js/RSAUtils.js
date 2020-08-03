// 使用 jsencrypt 进行 rsa 前端的加密和解密
// https://github.com/travist/jsencrypt

// https://github.com/travist/jsencrypt/issues/144

/**
 * RSA 加密，使用公钥加密
 * @param key 公钥
 * @param str 需要加密的内容
 * @returns {CipherParams|PromiseLike<ArrayBuffer>|null|string|string|*}
 */
function encryptRSAByPublicKey(str, key) {
    // JSEncrypt = require('jsencrypt').default;
    Encrypt = new window.JSEncrypt();
    Encrypt.setPublicKey(key);
    return Encrypt.encrypt(str);
}

/**
 * RSA 加密，使用公钥加密
 * @param key 公钥
 * @param str 需要加密的内容
 * @returns {CipherParams|PromiseLike<ArrayBuffer>|null|string|string|*}
 */
function encryptRSAByPrivateKey(str, key) {
    // JSEncrypt = require('jsencrypt').default;
    Encrypt = new window.JSEncrypt();
    Encrypt.setPrivateKey(key);
    return Encrypt.encrypt(str);
}



/**
 * RSA 解密，使用密钥解密
 * @param str 需要解密的内容
 * @param key 密钥
 * @returns {WordArray|PromiseLike<ArrayBuffer>|null|*|undefined}
 */
function decryptRSAByPrivateKey(str, key) {
    Encrypt = new window.JSEncrypt();
    Encrypt.setPrivateKey(key);
    return Encrypt.decrypt(str);
}