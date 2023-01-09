package com.example.springbootcaptcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

        private final DefaultKaptcha defaultKaptcha;

        @Autowired
        public CaptchaController(DefaultKaptcha defaultKaptcha) {
            this.defaultKaptcha = defaultKaptcha;
        }

    @GetMapping("/")
        public void getCaptcha(HttpServletResponse response,HttpServletRequest request) throws IOException {

            String text = defaultKaptcha.createText();
            BufferedImage image = defaultKaptcha.createImage(text);

             request.getSession().setAttribute("captcha", text);

            response.setContentType("image/jpeg");
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            outputStream.close();
        }


    @PostMapping("/verify-captcha")
    public String verifyCaptcha(HttpServletRequest request, @RequestParam("captcha") String captcha) {
        String captchaSession = (String) request.getSession().getAttribute("captcha");
        if (captcha.equals(captchaSession)) {
            return "captcha-success";
        } else {
            return "captcha-failure";
        }
    }

}
