package han.testing.dao;

/**
 * Created by handv on 2016/6/24.
 */
public class DeviceCountDao {
    private String device;
    private Long count;

    public DeviceCountDao(String device, Long count) {
        this.device = device;
        this.count = count;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
