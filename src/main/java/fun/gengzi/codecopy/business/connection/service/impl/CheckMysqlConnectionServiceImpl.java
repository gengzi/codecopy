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
import java.util.Date;
import java.util.List;
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
     *
     * @return
     */
    @Override
    public MysqlDTO checkMysqlConnectionIsEnable(Long startIndex, Long endIndex) {

        for (long i = startIndex; i < endIndex; i++) {
            String ipv4 = NetUtil.longToIpv4(i);
            logger.info("ip ：{}", ipv4);
            threadPoolExecutor.execute(new CheckConnection(ipv4, this.mysqlDTODaoExtendsJPA));
            logger.info("任务个数: {} ", threadPoolExecutor.getTaskCount());
        }
        return null;
    }


    /**
     *
     *
     *
     * 出现的问题：
     * 线程池中的 任务队列，将任务添加到了 队列中，但是每个队列任务中的 对象信息，却都是最后一条的数据，
     * 现在怀疑是 都拿了最后一次的 对象。现在改为 String 类型 ，看是否可以
     *
     * 这里出现对象只有最后的一个原因是，添加到任务队列的任务，携带的都是对象的引用，而引用对象就是 任务队列的最后一个，
     * 所以在 run 方法中，创建一个新的对象，来存方对应的数据项
     *
     * 这里要注意的是，spring 是禁止在多线程下，使用注入的，也就是 如果将 dao 层对象，直接在 多线程下，是拿不到该对象的，
     * 会报 nullPointException 异常，所以，这里我将 dao 层对象，赋值。
     *
     *
     * 细节：
     *  内部类可以使用外部类所有的属性和方法
     *  静态内部类只能使用外部类静态的成员变量和成员方法
     *
     *
     */
    static class CheckConnection implements Runnable {
        private Logger logger = LoggerFactory.getLogger(CheckMysqlConnectionServiceImpl.class);
        private MysqlDTODaoExtendsJPA mysqlDTODaoExtendsJPA;
        private String ip;
        private final static Integer PORT = 3306;
        private final static Integer TIME = 1000;

        public CheckConnection(String ip, MysqlDTODaoExtendsJPA mysqlDTODaoExtendsJPA) {
            this.mysqlDTODaoExtendsJPA = mysqlDTODaoExtendsJPA;
            this.ip = ip;
        }

        @Override
        public void run() {
            logger.info("start");
            final MysqlDTO mysqlDTO = new MysqlDTO();
            mysqlDTO.setIp(ip);
            boolean open = NetUtil.isOpen(new InetSocketAddress(mysqlDTO.getIp(), PORT), TIME);
            if (open) {
                mysqlDTO.setCreatedate(new Date());
                mysqlDTO.setUpdatedate(new Date());
                mysqlDTO.setPassword("root");
                mysqlDTO.setUsername("root");
                mysqlDTO.setPort("3306");
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
                logger.info("无效ip（未开放 3306 端口） " + mysqlDTO.getIp());
            }

        }
    }


    /**
     * 根据ip查询 mysql链接信息
     * 在数据库中 password 是加密后，在查询后，会进行解密处理。响应给用户解密后的数据
     *
     * @param ip ipv4 地址
     * @return {@link MysqlDTO}
     */
    @Override
    public List<MysqlDTO> getMysqlDTOInfo(String ip) {
        return mysqlDTODaoExtendsJPA.findByIp(ip);
    }


}
