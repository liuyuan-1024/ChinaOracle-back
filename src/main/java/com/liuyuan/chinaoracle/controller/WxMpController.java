package com.liuyuan.chinaoracle.controller;

import com.liuyuan.chinaoracle.wxmp.WxMpConstant;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts.MenuButtonType;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/**
 * 微信公众号相关接口
 * todo 修改为自己的微信公众号
 **/
@RestController
@RequestMapping("/")
@Slf4j
public class WxMpController {

    @Resource
    private WxMpService wxMpService;

    @Resource
    private WxMpMessageRouter router;

    @PostMapping("/")
    public void receiveMessage(ServerWebExchange exchange)
        throws IOException {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        DataBufferFactory bufferFactory = response.bufferFactory();

//        "text/html;charset=utf-8"
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);
        // 校验消息签名，判断是否为公众平台发的消息
        String signature = request.getQueryParams().getFirst("signature");
        String nonce = request.getQueryParams().getFirst("nonce");
        String timestamp = request.getQueryParams().getFirst("timestamp");
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            DataBuffer wrap = bufferFactory.wrap("非法请求".getBytes());
            response.writeWith(Mono.fromSupplier(() -> wrap));
        }
        // 加密类型
        String encryptType = StringUtils.isBlank(request.getQueryParams().getFirst("encrypt_type"))
            ? "raw" : request.getQueryParams().getFirst("encrypt_type");
        // 明文消息
        if ("raw".equals(encryptType)) {
            return;
        }
        // aes 加密消息 todo ServerHttpRequest获取输入流
//        if ("aes".equals(encryptType)) {
//            // 解密消息
//            String msgSignature = request.getQueryParams().getFirst("msg_signature");
//            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(),
//                wxMpService.getWxMpConfigStorage(), timestamp, nonce, msgSignature);
//            log.info("message content = {}", inMessage.getContent());
//            // 路由消息并处理
//            WxMpXmlOutMessage outMessage = router.route(inMessage);
//            DataBuffer wrap;
//            if (outMessage == null) {
//                wrap = bufferFactory.wrap("".getBytes());
//            } else {
//                wrap = bufferFactory.wrap(outMessage.toEncryptedXml(wxMpService.getWxMpConfigStorage()).getBytes());
//            }
//            response.writeWith(Mono.fromSupplier(() -> wrap));
//            return;
//        }
        DataBuffer wrap = bufferFactory.wrap("不可识别的加密类型".getBytes());
        response.writeWith(Mono.fromSupplier(() -> wrap));
    }

    @GetMapping("/")
    public String check(String timestamp, String nonce, String signature, String echostr) {
        log.info("check");
        if (wxMpService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        } else {
            return "";
        }
    }

    /**
     * 设置公众号菜单
     *
     * @return
     * @throws WxErrorException
     */
    @GetMapping("/setMenu")
    public String setMenu() throws WxErrorException {
        log.info("setMenu");
        WxMenu wxMenu = new WxMenu();
        // 菜单一
        WxMenuButton wxMenuButton1 = new WxMenuButton();
        wxMenuButton1.setType(MenuButtonType.VIEW);
        wxMenuButton1.setName("主菜单一");
        // 子菜单
        WxMenuButton wxMenuButton1SubButton1 = new WxMenuButton();
        wxMenuButton1SubButton1.setType(MenuButtonType.VIEW);
        wxMenuButton1SubButton1.setName("跳转页面");
        wxMenuButton1SubButton1.setUrl(
            "https://yupi.icu");
        wxMenuButton1.setSubButtons(Collections.singletonList(wxMenuButton1SubButton1));

        // 菜单二
        WxMenuButton wxMenuButton2 = new WxMenuButton();
        wxMenuButton2.setType(MenuButtonType.CLICK);
        wxMenuButton2.setName("点击事件");
        wxMenuButton2.setKey(WxMpConstant.CLICK_MENU_KEY);

        // 菜单三
        WxMenuButton wxMenuButton3 = new WxMenuButton();
        wxMenuButton3.setType(MenuButtonType.VIEW);
        wxMenuButton3.setName("主菜单三");
        WxMenuButton wxMenuButton3SubButton1 = new WxMenuButton();
        wxMenuButton3SubButton1.setType(MenuButtonType.VIEW);
        wxMenuButton3SubButton1.setName("编程学习");
        wxMenuButton3SubButton1.setUrl("https://yupi.icu");
        wxMenuButton3.setSubButtons(Collections.singletonList(wxMenuButton3SubButton1));

        // 设置主菜单
        wxMenu.setButtons(Arrays.asList(wxMenuButton1, wxMenuButton2, wxMenuButton3));
        wxMpService.getMenuService().menuCreate(wxMenu);
        return "ok";
    }
}
