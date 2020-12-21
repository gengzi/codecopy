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

local current = redis.call('ZADD', 'test:zset:add',' 2 "world" 3 "bar"')
return true