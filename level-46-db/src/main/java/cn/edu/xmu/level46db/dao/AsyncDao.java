package cn.edu.xmu.level46db.dao;

import cn.edu.xmu.level46db.mapper.CETOrderInfoPoMapper;
import cn.edu.xmu.level46db.mapper.ExtendCETPoMapper;
import cn.edu.xmu.level46db.model.po.CETOrderInfoPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import java.time.LocalDateTime;

/**
 * @author xiuchen lang 22920192204222
 * @date 2022/05/12 15:38
 */
@Repository
public class AsyncDao {
    @Autowired
    ExtendCETPoMapper extendCETPoMapper;
    @Autowired
    CETOrderInfoPoMapper cetOrderInfoPoMapper;

    /**
     * 扣除数据库中的库存
     */
    @Async("asyncExecutorConsumerRushCET")
    public synchronized void decrCETtableById(Long id) throws Exception {
        int rush = extendCETPoMapper.rush(id);
        if (rush == 0) {
            System.out.println("修改失败");
            throw new Exception("修改失败");
        }
    }

    @Async("asyncExecutorConsumerInsertOrder")
    public void insertCETOrderTable(Long cetId) {
        SecureRandom secureRandom = new SecureRandom();
        CETOrderInfoPo cetOrderInfoPo = new CETOrderInfoPo();
        cetOrderInfoPo.setCetId(cetId);
        cetOrderInfoPo.setUserId(secureRandom.nextLong());
        cetOrderInfoPo.setCreateTime(LocalDateTime.now());
        int insert = cetOrderInfoPoMapper.insert(cetOrderInfoPo);
    }
}
