package fun.gengzi.codecopy.business.luckdraw.init;

import fun.gengzi.codecopy.business.luckdraw.constant.LuckdrawContants;
import fun.gengzi.codecopy.business.luckdraw.dao.PrizeDao;
import fun.gengzi.codecopy.business.luckdraw.entity.TbPrize;
import fun.gengzi.codecopy.dao.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *  <h1>奖品数目缓存到redis中</h1>
 */
@Service
public class LuckdrawInitCache implements ApplicationRunner {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PrizeDao prizeDao;


    /**
     *
     * 初始化现在生效的活动的奖品库存（redis）
     *
     * @param applicationArguments
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        // 查询现在生效的活动，对应的奖品信息
        List<TbPrize> tbPrizes = prizeDao.qryTbPrizeByEffectActivityId(new Date());

        tbPrizes.forEach(tbPrize -> {
            Integer id = tbPrize.getId();
            String activityid = tbPrize.getActivityid();
            Integer prizeNum = tbPrize.getPrizeNum();
            String onleyprizekey = String.format(LuckdrawContants.ONLEYPRIZEKEY, activityid, id);
            redisUtil.incr(onleyprizekey, prizeNum);
        });
    }
}
