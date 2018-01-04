package han.testing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import han.testing.dao.DeviceCountDao;
import han.testing.model.HistoryEntity;

import java.util.List;

/**
 * Created by handv on 2016/5/12.
 */
@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Integer> {
    // 定义查询
    // @Param注解用于提取参数
    @Query("select de from HistoryEntity de where de.time>=:qTime order by de.time desc")
    public List<HistoryEntity> findBefore(@Param("qTime") String time);
    // 获取除手机卡外的借用者
    // @Param注解用于提取参数
    @Query("select new han.testing.dao.DeviceCountDao(da.borrower,count(da)) from HistoryEntity da where da.time>=:qTime and da.time<:qNextTime and da.type!=2 group by da.borrower order by count(da) desc")
    List<DeviceCountDao> findBorrowerNumByTime(@Param("qTime") String time, @Param("qNextTime") String nextTime);
    // 获取自定义参数
    // @Param注解用于提取参数
    @Query("select new han.testing.dao.DeviceCountDao(da.model,count(da)) from HistoryEntity da where da.time>=:qTime and da.time<:qNextTime and da.type=:qType group by da.model order by count(da) desc")
    List<DeviceCountDao> findDeviceCountByTimeAndType(@Param("qTime") String time, @Param("qNextTime") String nextTime, @Param("qType") int type);

}

