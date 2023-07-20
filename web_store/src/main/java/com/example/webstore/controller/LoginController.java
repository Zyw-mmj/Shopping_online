package com.example.webstore.controller;

import com.example.webstore.Util.MySessionContext;
import com.example.webstore.Util.PasswordEncryption;
import com.example.webstore.dao.model.User;
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
import java.util.*;
import java.util.List;

@RestController
@RequestMapping("/Login")
@Slf4j
public class LoginController {
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
        log.info(s);
        log.info(request.getSession().getId());
        log.info("++++++++++++");
        String[] str=new String[2];
        str[0]=captchaImage;
        str[1]=request.getSession().getId();
        return str;
    }

    @Autowired
    private IUserService userService;


    @PostMapping("/verify")
    public ResultInfo login(@RequestBody Map map, RedirectAttributes redirectAttributes) {
        MySessionContext myc= MySessionContext.getInstance();
        HttpSession session = myc.getSession((String) map.get("session"));
        String sessionCaptcha = (String) session.getAttribute("captcha");
        if (map.get("captcha").equals(sessionCaptcha)) {
            // 验证码正确，继续验证用户名和密码
            log.info("相同12345");
            log.info((String) map.get("username"));
            List<UserBo> userBoList=this.userService.getTheUser((String) map.get("username"));
            if (userBoList.size()==0){
                return ResultInfo.success("notExist");
            }else {
                UserBo user = userBoList.get(0);
                String real=user.getPassword();
                String salt=real.substring(Math.max(real.length() - 32, 0));
                String thePassword= PasswordEncryption.getMD5Password((String) map.get("password"),salt);
                if (real.equals(thePassword)){
                    if (user.getStatus().equals("Approved")){
                        String[] s=new String[2];
                        s[0]="Approved";
                        s[1]=user.getId().toString();
                        return ResultInfo.success(s);
                    }else {

                        return ResultInfo.success("Rejected");
                    }
                }else {
                    return ResultInfo.success("密码有误");
                }
            }
        } else {
            // 验证码错误，返回错误信息
            log.info("不相同");
            return ResultInfo.success("yanzhenma");
        }
    }

    @Autowired
    private IBusinessService  iBusinessService;

    @PostMapping("/verifyTwo")
    public ResultInfo BusinessLogin(@RequestBody Map map, RedirectAttributes redirectAttributes) {
        MySessionContext myc= MySessionContext.getInstance();
        HttpSession session = myc.getSession((String) map.get("session"));
        String sessionCaptcha = (String) session.getAttribute("captcha");
        if (map.get("captcha").equals(sessionCaptcha)) {
            log.info("相同");
            List<BusinessBo> businessBoList=this.iBusinessService.getTheBusiness((String) map.get("username"));
            if (businessBoList.size()==0){
                return ResultInfo.error("notExist");
            }else {
                BusinessBo businessBo = businessBoList.get(0);
                String real=businessBo.getPassword();
                String salt=real.substring(Math.max(real.length() - 32, 0));
                String thePassword= PasswordEncryption.getMD5Password((String) map.get("password"),salt);
                if (real.equals(thePassword)){
                    if (businessBo.getStatus().equals("Approved")){
                        String[] s=new String[2];
                        s[0]="Approved";
                        s[1]=businessBo.getId().toString();
                        return ResultInfo.success(s);
                    }else {
                        return ResultInfo.success("Rejected");
                    }
                }else {
                    return ResultInfo.success("密码有误");
                }

            }
        } else {
            // 验证码错误，返回错误信息
            log.info("不相同");
            return ResultInfo.success("yanzhenma");
        }
    }
}
