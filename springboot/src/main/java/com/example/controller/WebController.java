package com.example.controller;


import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import com.example.common.enums.LogType;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.*;
import com.example.exception.CustomException;
import com.example.service.*;
import com.example.utils.ValidateCodeCache;
import com.example.utils.ValidateCodeProperties;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 基础前端接口
 */
@RestController
public class WebController {

    @Resource
    private AdminService adminService;
    @Resource
    private StaffService staffService;

    @Resource
    private SaleService saleService;

    @Resource
    private ClueService clueService;

    @Resource
    private ContractService contractService ;

    @Resource
    private GoodsService goodsService;
    @Resource
    private CustomerService customerService;

    @Resource
    private PublicCustomerService publiccustomerService;

    @Resource
    private FollowUpService followUpService;


    @Resource
    private PlanService planService;

    @Resource
    private TripartiteService tripartiteService;

    @GetMapping("/")
    public Result hello() {
        return Result.success("访问成功");
    }

    @GetMapping("/validateCode")
    public void getValidateCode(HttpServletRequest request, HttpServletResponse response) {
        // 生成验证码
        ValidateCodeProperties code = new ValidateCodeProperties();
        setHeader(response, code.getType());
        Captcha captcha = createCaptcha(code);
        // 存储验证码到缓存
        ValidateCodeCache.setCache(request.getParameter("key"), captcha.text().toLowerCase());
        try {
            captcha.out(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(ResultCodeEnum.VALIDATE_CODE_ERROR);
        }
    }

    /**
     * 创建验证码
     */
    private Captcha createCaptcha(ValidateCodeProperties code) {
        Captcha captcha = null;
        if ("gif".equalsIgnoreCase(code.getType())) {
            captcha = new GifCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        } else {
            captcha = new SpecCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        }
        captcha.setCharType(code.getCharType());

        return captcha;
    }

    /**
     * 设置验证码返回头
     */
    private void setHeader(HttpServletResponse response, String type) {
        if ("gif".equalsIgnoreCase(type)) {
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        } else {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    }


    /**
     * 登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        if (ObjectUtil.isEmpty(account.getUsername()) || ObjectUtil.isEmpty(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole()) || ObjectUtil.isEmpty(account.getCode())
                || ObjectUtil.isEmpty(account.getKey())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        boolean validate = ValidateCodeCache.validateCode(account.getKey(), account.getCode());
        if (!validate) {
            return Result.error(ResultCodeEnum.VALIDATE_CODE_ERROR);
        }
        ValidateCodeCache.removeCache(account.getKey(), account.getCode());
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            account = adminService.login(account);
        } else if (RoleEnum.STAFF.name().equals(account.getRole())) {
            account = staffService.login(account);
        }
        LogsService.recordLog(account.getUsername() + "登录系统", LogType.LOGIN.getValue(), account.getUsername());
        return Result.success(account);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.STAFF.name().equals(account.getRole())) {
            staffService.register(account);
        }
        LogsService.recordLog(account.getUsername() + "注册账号", LogType.REGISTER.getValue(), account.getUsername());
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getNewPassword())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            adminService.updatePassword(account);
        } else if (RoleEnum.STAFF.name().equals(account.getRole())) {
            staffService.updatePassword(account);
        }
        return Result.success();
    }

    @GetMapping("/countData")
    public Result getCountData() {
        List<Customer> customerList = customerService.selectAll(null);
        List<PublicCustomer> publicCustomerList = publiccustomerService.selectAll(null);
        List<Sale> saleList = saleService.selectAll(null);
        List<Clue> clueList = clueService.selectAll(null);
        List<Contract> contractList = contractService.selectAll(null);
        List<FollowUp> followUpList = followUpService.selectAll(null);
        List<Tripartite> suppliersList = tripartiteService.selectAll(null);
        List<Plan> planList = planService.selectAll(null);  
        long user = customerList.size();
        long potential = followUpList.size();
        long publicuser = publicCustomerList.size();
        long saledate = saleList.size();
        long plan = planList.size();
        int tripartite = suppliersList.size();
        long clue = clueList.size();
        long contract = contractList.size();

        double totalSalesAmount = saleList.stream() .mapToDouble(sale -> sale.getNum() * sale.getPrice()) .sum();

        Dict dict = Dict.create();
        dict.set("user", user)
                .set("potential", potential)
                .set("contract", contract)
                .set("clue", clue)
                .set("tripartite", tripartite)
                .set("publicuser", publicuser)
                .set("saledate", saledate)
                .set("totalSalesAmount", totalSalesAmount)
                .set("plan",plan);

        return Result.success(dict);
    }

    /**
     * 获取过去6个月内每个月的金额数据统计信息
     * @return 包含每月金额数据的结果对象
     */
    //折线数据统计
    @GetMapping("/saleData")

    public Result getSaleData() {
        // 获取当前日期
        LocalDate today = LocalDate.now();

        // 生成过去6个月的月份列表
        List<String> monthList = IntStream.rangeClosed(0, 5)
                .mapToObj(i -> today.minusMonths(i).format(DateTimeFormatter.ofPattern("yyyy-MM")))
                .collect(Collectors.toList());

        // 遍历每个月份，查询销售数据并计算总销售额
        List<Dict> resList = monthList.stream()
                .map(month -> {
                    Sale sale = new Sale();
                    sale.setTime(month);
                    List<Sale> sales = saleService.selectAll(sale);
                    Double total = sales.stream()
                            .mapToDouble(Sale::getTotal)
                            .sum();
                    return Dict.create().set("name", month).set("value", total);
                })
                .collect(Collectors.toList());

        // 返回包含销售数据的结果对象
        return Result.success(resList);
    }
    //用户地区
    @GetMapping("/userData")
    public Result getUserData() {
        List<Customer> customerList = customerService.selectAll(null);
        Set<String> addressSet = customerList.stream().map(Customer::getAddress).collect(Collectors.toSet());
        List<Dict> resList = new ArrayList<>();
        for (String address : addressSet) {
            Integer count = (int) customerList.stream().filter(customer -> customer.getAddress().equals(address)).count();
            Dict dict = Dict.create(); dict.set("address", address).set("count", count); resList.add(dict);
        }
        return Result.success(resList);
    }
    //饼图统计 公海用户状态
    @GetMapping("/StoreData")
    public Result StoreData() {
        List<PublicCustomer> publiccustomerList = publiccustomerService.selectAll(null);

        Map<String, Long> countMap = publiccustomerList.stream()
                .collect(Collectors.groupingBy(PublicCustomer::getStatus, Collectors.counting()));

        List<Dict> resList = countMap.entrySet().stream()
                .map(entry -> {
                    Dict dict = Dict.create();
                    dict.set("name", entry.getKey()).set("value", entry.getValue().intValue());
                    return dict;
                })
                .collect(Collectors.toList());

        return Result.success(resList);
    }
    //行业分布
    @GetMapping("/IndustryData")
    public Result IndustryData() {
        List<PublicCustomer> publicCustomerList = publiccustomerService.selectAll(null);

        Map<String, Long> countMap = publicCustomerList.stream()
                .collect(Collectors.groupingBy(PublicCustomer::getIndustry, Collectors.counting()));

        List<Dict> resList = countMap.entrySet().stream()
                .map(entry -> {
                    Dict dict = Dict.create();
                    dict.set("name", entry.getKey()).set("value", entry.getValue().intValue());
                    return dict;
                })
                .collect(Collectors.toList());

        return Result.success(resList);
    }
    //用户状态
    @GetMapping("/StatusData")
    public Result StatusData() {
        List<Customer> customerList = customerService.selectAll(null);

        Map<String, Long> countMap = customerList.stream()
                .collect(Collectors.groupingBy(Customer::getStatus, Collectors.counting()));

        List<Dict> cuList = countMap.entrySet().stream()
                .map(entry -> {
                    Dict dict = Dict.create();
                    dict.set("name", entry.getKey()).set("value", entry.getValue().intValue());
                    return dict;
                })
                .collect(Collectors.toList());

        return Result.success(cuList);
    }
    //用户级别
    @GetMapping("/LevelData")
    public Result LevelData() {
        List<Customer> customerList = customerService.selectAll(null);

        Map<String, Long> countMap = customerList.stream()
                .collect(Collectors.groupingBy(Customer::getLevel, Collectors.counting()));

        List<Dict> cuList = countMap.entrySet().stream()
                .map(entry -> {
                    Dict dict = Dict.create();
                    dict.set("name", entry.getKey()).set("value", entry.getValue().intValue());
                    return dict;
                })
                .collect(Collectors.toList());

        return Result.success(cuList);
    }
    //跟进方式
    @GetMapping("/MethodData")
    public Result MethodData() {
        List<FollowUp> followUpList = followUpService.selectAll(null);

        Map<String, Long> countMap = followUpList.stream()
                .collect(Collectors.groupingBy(FollowUp::getWay, Collectors.counting()));

        List<Dict> foList = countMap.entrySet().stream()
                .map(entry -> {
                    Dict dict = Dict.create();
                    dict.set("name", entry.getKey()).set("value", entry.getValue().intValue());
                    return dict;
                })
                .collect(Collectors.toList());

        return Result.success(foList);
    }
    //跟进内容
    @GetMapping("/ContentData")
    public Result ContentData() {
        List<FollowUp> followUpList = followUpService.selectAll(null);

        Map<String, Long> countMap = followUpList.stream()
                .collect(Collectors.groupingBy(FollowUp::getContent, Collectors.counting()));

        List<Dict> foList = countMap.entrySet().stream()
                .map(entry -> {
                    Dict dict = Dict.create();
                    dict.set("name", entry.getKey()).set("value", entry.getValue().intValue());
                    return dict;
                })
                .collect(Collectors.toList());

        return Result.success(foList);
    }
    //线索转换率
    @GetMapping("/FallbackData")
    public Result FallbackData() {
        List<Clue> clueList = clueService.selectAll(null);

        Map<String, Long> countMap = clueList.stream()
                .collect(Collectors.groupingBy(Clue::getStatus, Collectors.counting()));

        List<Dict> cuList = countMap.entrySet().stream()
                .map(entry -> {
                    Dict dict = Dict.create();
                    dict.set("name", entry.getKey()).set("value", entry.getValue().intValue());
                    return dict;
                })
                .collect(Collectors.toList());

        return Result.success(cuList);
    }
}
