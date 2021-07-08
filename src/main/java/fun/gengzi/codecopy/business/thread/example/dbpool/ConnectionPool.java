package fun.gengzi.codecopy.business.thread.example.dbpool;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {
    // 双向链表
    private LinkedList<Connection> pool = new LinkedList<Connection>();

    public ConnectionPool(int size) {
        // 初始化数据库连接池大小
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                pool.add(ConnectionDriver.createConnection());
            }
        }
    }

    /**
     * 获取一个链接
     *
     * @return
     */
    public Connection fethchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            // 超时等待
            long timeout = System.currentTimeMillis() + mills;
            long remaining = mills;
            while (pool.isEmpty() && remaining > 0) {
                // 等待超时
                pool.wait(remaining);
                remaining = timeout - System.currentTimeMillis();
            }
            if (!pool.isEmpty()) {
                return pool.removeFirst();
            } else {
                return null;
            }
        }
    }


    /**
     * 释放一个链接
     * @param connection
     */
    public void reseleConnection(Connection connection){
        if(connection != null){
            synchronized (pool){
                pool.add(connection);
                // 唤醒所有
                pool.notifyAll();
            }
        }
    }






}
