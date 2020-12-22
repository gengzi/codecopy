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


local sizeStr = tonumber(ARGV[2])
local size = (sizeStr-1)/2
for i = 3, size+2 , 1 do
local current = redis.call('ZADD', KEYS[1], ARGV[i+i-3], ARGV[i+i-2])
end
redis.call('Expire', KEYS[1], tonumber(ARGV[1])) return "ok"
