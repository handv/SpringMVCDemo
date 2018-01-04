package han.testing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import han.testing.model.DeviceEntity;

import java.util.List;

/**
 * Created by handv on 2016/5/12.
 */
@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Integer> {
    @Modifying      // 说明该方法是修改操作
    @Transactional  // 说明该方法是事务性操作
    // 定义查询
    // @Param注解用于提取参数
    @Query("update DeviceEntity de set de.owner=:qOwner, de.time=:qTime where de.id=:qId")
    public void updateDevice(@Param("qId")Integer id, @Param("qOwner")String owner, @Param("qTime")String time);
    @Modifying      // 说明该方法是修改操作
    @Transactional  // 说明该方法是事务性操作

    // 定义查询
    // @Param注解用于提取参数
    @Query("select de from DeviceEntity de where de.type=:qType order by share desc,id")
    public List<DeviceEntity> findAllByType(@Param("qType")Integer type);
}

