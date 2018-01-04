package han.testing.dao;

import java.util.List;

/**
 * Created by handv on 2016/6/24.
 */
public class DeviceCountListDao {
    private String type;//设备类型，ios/android

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private List<DeviceCountDao> deviceCount;

    public List<DeviceCountDao> getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(List<DeviceCountDao> deviceCount) {
        this.deviceCount = deviceCount;
    }
}
