package fun.gengzi.codecopy.business.connection.service.impl;

import cn.hutool.core.net.NetUtil;
import fun.gengzi.codecopy.business.connection.dao.MysqlDTODaoExtendsJPA;
import fun.gengzi.codecopy.business.connection.entity.MysqlDTO;
import fun.gengzi.codecopy.business.connection.service.CheckMysqlConnectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class CheckMysqlConnectionServiceImpl implements CheckMysqlConnectionService {

    private Logger logger = LoggerFactory.getLogger(CheckMysqlConnectionServiceImpl.class);

    @Autowired
    @Qualifier("connectionThreadPool")
    private ThreadPoolExecutor threadPoolExecutor;

    @Autowired
    private MysqlDTODaoExtendsJPA mysqlDTODaoExtendsJPA;


    /**
     * 检测mysql链接是否有效
     * <p>
     * <p>
     * // IP 范围 排除无用的ip列表
     * // 可能的用户名 密码 匹配，常用端口匹配
     * // 多线程数据库连接 检测
     * // 先 telnet 检测，ip 端口是否可访问，再检测 用户 密码 能否 登陆，创建线程池，模拟连接。
     * // 将可以使用的mysql 库信息，存入数据库
     * // 整体迁移表，到另外的数据库
     *
     * @param mysqlDTO
     * @return
     */
    @Override
    public MysqlDTO checkMysqlConnectionIsEnable(MysqlDTO mysqlDTO) {
        for (int i = 2077100544; i < 2077103104; i++) {
            String ipv4 = NetUtil.longToIpv4(i);
            logger.info("ip ：{}", ipv4);
            mysqlDTO.setIp(ipv4);
//            int activeCount = threadPoolExecutor.getActiveCount();
//            logger.info("运行的个数 : {}", activeCount);
//            if (activeCount > 5) {
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            threadPoolExecutor.execute(new CheckConnection(mysqlDTO, this.mysqlDTODaoExtendsJPA));
            logger.info("任务个数: {} ", threadPoolExecutor.getTaskCount());
        }
        return null;
    }


    static class CheckConnection implements Runnable {
        private Logger logger = LoggerFactory.getLogger(CheckMysqlConnectionServiceImpl.class);
        private MysqlDTODaoExtendsJPA mysqlDTODaoExtendsJPA;
        private MysqlDTO mysqlDTO;
        private final static Integer PORT = 3306;
        private final static Integer TIME = 1000;

        public CheckConnection(MysqlDTO mysqlDTO, MysqlDTODaoExtendsJPA mysqlDTODaoExtendsJPA) {
            this.mysqlDTODaoExtendsJPA = mysqlDTODaoExtendsJPA;
            this.mysqlDTO = mysqlDTO;
        }

        @Override
        public void run() {
            logger.info("start");
            boolean open = NetUtil.isOpen(new InetSocketAddress(mysqlDTO.getIp(), PORT), TIME);
            if (open) {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection connection = DriverManager.getConnection("jdbc:mysql://" + mysqlDTO.getIp() + ":3306?serverTimezone=Asia/Shanghai", "root", "root");
                    logger.info("success " + mysqlDTO.getIp());
                    // 入库
                    this.mysqlDTODaoExtendsJPA.save(mysqlDTO);
                } catch (Exception e) {
                    logger.info("error " + e.getMessage());
                    // 依然存入数据库
                    this.mysqlDTODaoExtendsJPA.save(mysqlDTO);
                }
            } else {
                logger.info("无效ip（未开放 3306） " + mysqlDTO.getIp());
            }

        }
    }


}
