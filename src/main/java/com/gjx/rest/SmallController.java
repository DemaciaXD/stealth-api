package com.gjx.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gjx.dto.AnswerDto;
import com.gjx.service.IAnswerService;
import com.gjx.service.IQuestionService;
import com.gjx.service.ISmallService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rc")
public class SmallController {
    private final Logger log = LoggerFactory.getLogger(SmallController.class);
    private final int SUCCESS = 200;
    private final int FAILED = 500;

    @Autowired
    private ISmallService smallService;
    @Autowired
    private IAnswerService answerService;
    @Autowired
    private IQuestionService questionService;

    @GetMapping("/error")
    public String hellow() throws Exception {
        throw new Exception("发生错误");
    }

    /**
     * 登陆
     * @param message 报文体
     * @param response 响应对象
     * @return
     */
    @ApiOperation(value = "登陆", notes = "登陆入口")
    @ApiImplicitParam(name = "message", value = "{'js_code':'','encryptedData':'','iv':''}", required = true, dataType = "String")
    @PostMapping("/login")
    @ResponseBody
    public String login(String message, HttpServletResponse response) {
        log.info("login 入参: {}", message);
        HashMap<String, Object> resutl = new HashMap<String, Object>();
        String uuid = "";
        try {
            uuid = smallService.login(message);
            smallService.addCookie(uuid, response);
            resutl.put("code", SUCCESS);
            resutl.put("data", uuid);
            resutl.put("message", "登陆成功");
        } catch (Exception e) {
            e.printStackTrace();
            resutl.put("code", FAILED);
            resutl.put("message", "登陆异常");
        }
        String resultString = JSON.toJSONString(resutl);
        log.info(resultString);
        return resultString;
    }

    /**
     * 历史信息: 1.提过问题, 问题+答案
     * @param request 请求对象
     * @return
     */
    @ApiOperation(value = "历史问题", notes = "查询历史问题")
    @PostMapping("/hisQuestion")
    @ResponseBody
    public String hisQuestion(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String uuid = questionService.getUUID(request);
            Map<Object, Object> questionsMap = questionService.queryQuestions(uuid);
            map.put("code", SUCCESS);
            map.put("data", questionsMap);
            map.put("message", "获取问题列表成功");
        } catch (Exception e) {
            map.put("code", FAILED);
            map.put("message", "获取问题列表失败");
        }
        String result = JSON.toJSONString(map);
        log.info(result);
        return result;
    }

    /**
     * 历史信息: 2.没提过, 问题+答案
     * @param message 报文体
     * @return
     */
    @ApiOperation(value = "历史回答问题", notes = "查询历史回答问题")
    @ApiImplicitParam(name = "message", value = "{'questionId':''}", required = true, dataType = "String")
    @PostMapping("/hisAnswer")
    @ResponseBody
    public String hisAnswer(String message) {

        log.info("hisAnswer 入参: {}", message);
        JSONObject parseObject = JSON.parseObject(message);

        String questionId = parseObject.getString("questionId");
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            List<AnswerDto> answerDtoList = answerService.querAnswerDto(questionId);
            map.put("code", SUCCESS);
            map.put("data", answerDtoList);
            map.put("message", "获取问题列表成功");
        } catch (Exception e) {
            map.put("code", FAILED);
            map.put("message", "获取问题列表失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 发布问题
     * @param message 报文体
     * @param request 请求对象
     * @return
     */
    @ApiOperation(value = "发布问题", notes = "发布问题")
    @ApiImplicitParam(name = "message", value = "{'questionName':'','questionId':''}", required = true, dataType = "String")
    @PostMapping("/addQuestion")
    @ResponseBody
    public String addQuestion(String message, HttpServletRequest request) {
        log.info("addQuestion 入参: {}", message);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String qustionId = questionService.addQuestion(message, request);
            map.put("code", SUCCESS);
            map.put("data", qustionId);
            map.put("message", "提问题成功");
        } catch (Exception e) {
            map.put("code", FAILED);
            map.put("message", "提问题失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 回复问题
     * @param message 报文体
     * @param request 请求对象
     * @return
     */
    @ApiOperation(value = "回复问题", notes = "回复问题")
    @ApiImplicitParam(name = "message", value = "{'qustionId':'','content':''}", required = true, dataType = "String")
    @PostMapping("/reponseQuerstion")
    @ResponseBody
    public String reponseQuerstion(String message, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            answerService.responseQuestion(message, request);
            map.put("code", SUCCESS);
            map.put("message", "回答问题成功");
        } catch (Exception e) {
            map.put("code", FAILED);
            map.put("message", "回答问题失败");
        }
        return JSON.toJSONString(map);
    }

    @PostMapping("/rc")
    @ResponseBody
    public String rc(@RequestBody String message) {
//		String name = request.getParameter("name");
        JSONObject parseObject = JSON.parseObject(message);
        String name = parseObject.getString("name");
        System.out.println(name);
        return name;
    }
}
