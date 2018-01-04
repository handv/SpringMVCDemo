package han.testing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import han.testing.dao.DeviceCountDao;
import han.testing.dao.DeviceCountListDao;
import han.testing.model.DeviceEntity;
import han.testing.model.HistoryEntity;
import han.testing.model.ProductEntity;
import han.testing.repository.DeviceRepository;
import han.testing.repository.HistoryRepository;
import han.testing.repository.ProductRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by handv on 2016/5/12.
 */
@Controller
public class DeviceController {

    protected static final Logger logger = Logger.getLogger(DeviceController.class.getName());
    // 自动装，不需要再写原始的Connection来操作数据库
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    HistoryRepository historyRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:/devices/android";
    }

    // 显示设备信息
    @RequestMapping(value = "/devices/{product}", method = RequestMethod.GET)
    public String getDevices(@PathVariable("product") String product, ModelMap modelMap) {

        // 查询当前名称下product表中所有记录
        List<ProductEntity> productLists = productRepository.findAll();
        // 查询当前名称下product表中所有记录
        ProductEntity product_current = productRepository.findByProduct(product);
        // 根据type类型查询device表中所有记录（0为android，1为ios，2为card，3为others）
        List<DeviceEntity> deviceLists = deviceRepository.findAllByType(product_current.getId());

        // 将所有记录传递给要返回的jsp页面，放在List当中
        modelMap.addAttribute("productLists", productLists);
        // 将所有记录传递给要返回的jsp页面，放在List当中
        modelMap.addAttribute("product_current", product_current);
        // 将所有记录传递给要返回的jsp页面，放在List当中
        modelMap.addAttribute("deviceLists", deviceLists);

        //如果是手机卡，返回card.jsp
        if (product_current.getId() == 2) {
            return "/card";
        }
        // 返回pages目录下的devices.jsp页面
        else {
            return "/devices";
        }
    }

    // 更新设备拥有者信息 操作
    @RequestMapping(value = "/devices/update/{id}", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateDeviceOwner(@PathVariable("id") Integer id, @RequestParam("borrower") String borrower) {

        // 找到id所表示的设备
        DeviceEntity device = deviceRepository.findOne(id);
        String model = device.getModel();
        String owner = device.getOwner();
        int type = device.getType();
        // 格式化时间
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateformat.format(new Date());
        // 更新用户信息
        if(!owner.equals(borrower)) {
            deviceRepository.updateDevice(id, borrower, time);
            deviceRepository.flush(); // 刷新缓冲区
            // 更新借用者日志
            logger.info(time + "，设备" + model + "持有者更新：" + owner + "======>" + borrower);
            // 更新日志写入数据库
            HistoryEntity history = new HistoryEntity();
            history.setTime(time);//设置时间
            history.setModel(model);//设置设备名称
            history.setType(type);//设置设备类型
            history.setOwner(owner);//设置拥有者
            history.setBorrower(borrower);//设置借用者
            historyRepository.saveAndFlush(history);
            String data = borrower + "#|$%|*" + time;
            return data;
        }else{
            return "error";
        }
    }
    // 设备更新历史记录
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public String getHistory(ModelMap modelMap) {

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 获取当前时间
        Date today = new Date();
        // 获取十天前时间
        String day10Before = dateformat.format(new Date(today.getTime() - (long) 10 * 24 * 60 * 60 * 1000));
        // 获取十天前的更新记录
        List<HistoryEntity> historyLists = historyRepository.findBefore(day10Before);

        // 查询当前名称下product表中所有记录
        List<ProductEntity> productLists = productRepository.findAll();
        // 将所有记录传递给history页面，放在List当中
        modelMap.addAttribute("historyLists", historyLists);
        modelMap.addAttribute("productLists", productLists);
        return "history";
    }

    // 设备统计echarts表
    @RequestMapping(value = "/echarts", method = RequestMethod.GET)
    public String getDataStatistics(ModelMap modelMap) {
        //默认显示当前月的数据
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM");
        Calendar currenDate = Calendar.getInstance();
        String currentMonth = dateformat.format(currenDate.getTime());

        // 查询当前名称下product表中所有记录
        List<ProductEntity> productLists = productRepository.findAll();
        // 将所有记录传递给history页面，放在List当中
        modelMap.addAttribute("productLists", productLists);
        modelMap.addAttribute("fromDate",currentMonth);
        return "echarts";
    }

    //统计设备信息
    @RequestMapping(value = "/devices/datastat", method = RequestMethod.GET)
    public
    @ResponseBody
    List<DeviceCountListDao> getDataStat() {
        List<DeviceCountListDao> daoList = new ArrayList<DeviceCountListDao>();
        DeviceCountListDao androidDcListDao = new DeviceCountListDao();
        DeviceCountListDao iosDcListDao = new DeviceCountListDao();
        DeviceCountListDao borrowerDcListDao = new DeviceCountListDao();

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        // 获取当月第一天
        Calendar currenDate = Calendar.getInstance();
        currenDate.set(Calendar.DATE, 1);//设为当前月的1号
        String firstDayOfMonth = dateformat.format(currenDate.getTime());
        currenDate.add(Calendar.MONTH, 1);//加一个月
        String firstDayOfNextMonth = dateformat.format(currenDate.getTime());
        // 查询当月的android设备使用情况
        List<DeviceCountDao> androidCountDaos = historyRepository.findDeviceCountByTimeAndType(firstDayOfMonth, firstDayOfNextMonth, 0);
        androidDcListDao.setDeviceCount(androidCountDaos);
        androidDcListDao.setType("android");
        daoList.add(androidDcListDao);
        // 查询当月的ios设备使用情况
        List<DeviceCountDao> iosCountDaos = historyRepository.findDeviceCountByTimeAndType(firstDayOfMonth, firstDayOfNextMonth, 1);
        iosDcListDao.setDeviceCount(iosCountDaos);
        iosDcListDao.setType("ios");
        daoList.add(iosDcListDao);
        // 查询当月的借用者情况
        List<DeviceCountDao> userCountDaos = historyRepository.findBorrowerNumByTime(firstDayOfMonth, firstDayOfNextMonth);
        borrowerDcListDao.setDeviceCount(userCountDaos);
        borrowerDcListDao.setType("user");
        daoList.add(borrowerDcListDao);

        return daoList;
    }
    //按指定时间统计设备信息
    @RequestMapping(value = "/devices/datastats", method = RequestMethod.GET)
    public
    @ResponseBody
    List<DeviceCountListDao> getDataStatByDate(@RequestParam("from") String fromDate, @RequestParam("to") String toDate) {
        List<DeviceCountListDao> daoList = new ArrayList<DeviceCountListDao>();
        DeviceCountListDao androidDcListDao = new DeviceCountListDao();
        DeviceCountListDao iosDcListDao = new DeviceCountListDao();
        DeviceCountListDao borrowerDcListDao = new DeviceCountListDao();

        String toDateFormat = null;//格式化后的日期
        if ("".equals(toDate)) {
            //默认显示当前月的数据
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM");
            Calendar currenDate = Calendar.getInstance();
            toDateFormat = dateformat.format(currenDate.getTime());
        }
        // 查询时间范围内的android设备使用情况
        List<DeviceCountDao> androidCountDaos = historyRepository.findDeviceCountByTimeAndType(fromDate, toDateFormat, 0);
        androidDcListDao.setDeviceCount(androidCountDaos);
        androidDcListDao.setType("android");
        daoList.add(androidDcListDao);
        // 查询时间范围内的ios设备使用情况
        List<DeviceCountDao> iosCountDaos = historyRepository.findDeviceCountByTimeAndType(fromDate, toDateFormat, 1);
        iosDcListDao.setDeviceCount(iosCountDaos);
        iosDcListDao.setType("ios");
        daoList.add(iosDcListDao);
        // 查询时间范围内的借用者情况
        List<DeviceCountDao> userCountDaos = historyRepository.findBorrowerNumByTime(fromDate, toDateFormat);
        borrowerDcListDao.setDeviceCount(userCountDaos);
        borrowerDcListDao.setType("user");
        daoList.add(borrowerDcListDao);

        return daoList;
    }
    //查看mac地址归属
    /*@RequestMapping(value = "/devices/macId/{macId}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getMacOwner(@PathVariable("macId") String macId) {
        String uriAPI = "http://utils.youdao.ml/getMacUser/" + macId;
        StringBuilder sb = new StringBuilder();
        try {
            URL u = new URL(uriAPI);
            URLConnection uc = u.openConnection();
            uc.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.12) Gecko/20080201 Firefox/2.0.0.12");
            BufferedReader r = null;
            r = new BufferedReader(new InputStreamReader(uc.getInputStream(),
                    "UTF-8"));
            String inputLine ;
            while ((inputLine = r.readLine()) != null)
            {
                sb.append(inputLine);
            }
            r.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return sb.toString();
    }*/
}