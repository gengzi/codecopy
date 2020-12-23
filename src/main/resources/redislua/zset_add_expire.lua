-- local 修饰为局部变量
-- KEYS[1]  KEY1
-- ARGV[1] value[1]
-- local current = redis.call('GET', KEYS[1])
-- if 判断语句 if  then end
-- if current == ARGV[1]
-- then redis.call('SET', KEYS[1], ARGV[2])
-- return true
-- end
-- return false


-- zset 设置值并设置过期时间
-- eval "return redis.call('ZADD',KEYS[1],1,'ddd')" 1 ss:dd
-- print(ARGV[1])

-- redis.log(redis.LOG_DEBUG,ARGV[1])
--命令参考： http://www.redis.cn/commands/eval.html   http://www.redis.cn/topics/ldb.html

-- 获取key
local key = KEYS[1]
-- 解析json数据
local content = cjson.decode(ARGV[1])
-- 获取数组长度
local length = content["size"]
local ttl = content["ttl"]
for i = 1, length , 1 do
    local current = redis.call('ZADD', key, content["info"][i]["score"] , content["info"][i]["value"])
end
local num = redis.call('Expire', KEYS[1], ttl)
return "ok"
