package com.example.webstore.controller;

import com.example.webstore.Util.MySessionContext;
import com.example.webstore.Util.PasswordEncryption;
import com.example.webstore.domain.BusinessBo;
import com.example.webstore.domain.ResultInfo;
import com.example.webstore.domain.UserBo;
import com.example.webstore.service.IBusinessService;
import com.example.webstore.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/Register")
@Slf4j
public class RegisterController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    // 生成验证码图片
    private String generateCaptchaImage() throws Exception {
        // 随机生成验证码
        String captchaCode = "";
        String captchaSource = "0123456789abcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            captchaCode += captchaSource.charAt(random.nextInt(captchaSource.length()));
        }

        // 创建验证码图片
        int width = 120;
        int height = 40;
        BufferedImage captchaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = captchaImage.getGraphics();

        // 填充背景色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 添加验证码文字
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        int textWidth = g.getFontMetrics().stringWidth(captchaCode);
        int x = (width - textWidth) / 2;
        int y = height / 2 + g.getFontMetrics().getHeight() / 2 - 5;
        g.drawString(captchaCode, x, y);

        // 添加噪点
        float noiseRatio = 0.05f;
        int noiseCount = (int) (width * height * noiseRatio);
        for (int i = 0; i < noiseCount; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int rgb = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)).getRGB();
            captchaImage.setRGB(x1, y1, rgb);
        }

        // 添加干扰线
        int lineCount = 3;
        for (int i = 0; i < lineCount; i++) {
            int x1 = random.nextInt(width);
            int y1= random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g.setColor(new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            g.drawLine(x1, y1, x2, y2);
        }
        // 将验证码保存到 Session 中
        HttpSession session = request.getSession();
        session.setAttribute("captcha", captchaCode);
//        captcha.write(response.getOutputStream());

        // 将验证码图片转换成字节数组并返回
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(captchaImage, "png", baos);
        byte[] imageBytes = baos.toByteArray();
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(imageBytes);
    }

    @GetMapping("/getCaptchaImage")
    public String[] showLoginPage() {
//        log.info("成功运行！");
        ModelAndView mav = new ModelAndView("login");
        String captchaImage="da";
        try {
            captchaImage = generateCaptchaImage();
            mav.addObject("captchaImage", captchaImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String s= (String) request.getSession().getAttribute("captcha");
//        log.info(s);
//        log.info(request.getSession().getId());
//        log.info("++++++++++++");
        String[] str=new String[2];
        str[0]=captchaImage;
        str[1]=request.getSession().getId();
        return str;
    }

    @Autowired
    private IUserService userService;

    @Autowired
    private IBusinessService iBusinessService;

    @PostMapping("/verifyTwo")
    public ResultInfo register(@RequestBody Map map, RedirectAttributes redirectAttributes) {
        MySessionContext myc= MySessionContext.getInstance();
        HttpSession session = myc.getSession((String) map.get("session"));
        String sessionCaptcha = (String) session.getAttribute("captcha");
        if (map.get("captcha").equals(sessionCaptcha)) {
            String thePassword=PasswordEncryption.getMD5Password((String) map.get("password"));
            BusinessBo businessBo=new BusinessBo( (String) map.get("name"),(String) map.get("account"),thePassword,(String) map.get("phonenumber"),(String)map.get("email"));
            businessBo.setStatus("Pending");
            businessBo.setShoptype("商家用户");
            boolean t=this.iBusinessService.getAccounts((String) map.get("account"));
            if (t){
                Integer id = this.iBusinessService.createBusiness(businessBo);
                log.info(id.toString());
                return ResultInfo.success(true);
            }else {
                return ResultInfo.success("zhanghao");
            }
        } else {
            // 验证码错误，返回错误信息
            log.info("不相同");
            return ResultInfo.success("yanzhenma");
        }
    }



    @PostMapping("/verify")
    public ResultInfo businessRegister(@RequestBody Map map, RedirectAttributes redirectAttributes) {
        MySessionContext myc= MySessionContext.getInstance();
        HttpSession session = myc.getSession((String) map.get("session"));
        String sessionCaptcha = (String) session.getAttribute("captcha");
        if (map.get("captcha").equals(sessionCaptcha)) {
            String thePassword=PasswordEncryption.getMD5Password((String) map.get("password"));
            UserBo userBo=new UserBo((String)map.get("name"),(String) map.get("account"), thePassword,
                    (String)map.get("phone"),(String)map.get("email"),(String) map.get("city"),(String) map.get("gender"),
                    (String) map.get("bankAccount"),(String) map.get("shopType"));
            userBo.setStatus("Pending");
            boolean t=this.userService.getAccounts((String) map.get("account"));
            if (t){
                Integer id = this.userService.createUser(userBo);
                log.info(id.toString());
                return ResultInfo.success(true);
            }else {
                return ResultInfo.success("zhanghao");
            }
        } else {
            // 验证码错误，返回错误信息
            log.info("不相同");
            return ResultInfo.success("yanzhenma");
        }
    }
}
