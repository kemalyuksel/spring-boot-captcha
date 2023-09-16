# Spring Boot Captcha Generator and Verify with DefaultKaptcha 

Creating a captcha with Spring Boot is a method used for verifying users in a web application. This process compares the accuracy of the numbers and letters entered by the user with the numbers and letters displayed in an image. This prevents automated bot entries in the application and ensures the security of real users.

To perform captcha generation, there are several libraries available in the Java language. In this article, we will use a library called kaptcha. This library generates captcha images and performs the verification process.

```
<dependency>
    <groupId>com.github.penggle</groupId>
    <artifactId>kaptcha</artifactId>
    <version>2.3.2</version>
</dependency>
```

Next, create a CaptchaController class and add a getCaptcha() method as follows:

```
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

private final DefaultKaptcha defaultKaptcha;

publicCaptchaController() {

this.defaultKaptcha = new DefaultKaptcha();

// A configuration object was created for Captcha settings.
Config config =newConfig(newProperties());

// Captcha img width
config.getProperties().setProperty("kaptcha.image.width", "400");

// Captcha img height
config.getProperties().setProperty("kaptcha.image.height", "100");

// Captcha font color
config.getProperties().setProperty("kaptcha.textproducer.font.color", "blue");

// Captcha font size
config.getProperties().setProperty("kaptcha.textproducer.font.size", "60");

// Captcha char length
config.getProperties().setProperty("kaptcha.textproducer.char.length", "4");

// Captcha font style
config.getProperties().setProperty("kaptcha.textproducer.font.names", "Arial, Courier");

/* Captcha noise color (The line intended to reduce the readability of text in the middle of the image.) */
config.getProperties().setProperty("kaptcha.noise.color", "black");

// Captcha char string 
config.getProperties().setProperty("kaptcha.textproducer.char.string", "0123456789");

defaultKaptcha.setConfig(config);
}

@GetMapping("/")
public void getCaptcha(HttpServletResponseresponse)throwsIOException {

      String text = defaultKaptcha.createText();
      BufferedImage image = defaultKaptcha.createImage(text);

      response.setContentType("image/jpeg");
      OutputStream outputStream = response.getOutputStream();
      ImageIO.write(image, "jpg", outputStream);
      outputStream.close();    

	 }
}
```

The Config object here is configured to set up the configuration settings for our defaultKaptcha object that comes from the kaptcha dependency. I've provided comments to explain what some of these settings do.

Afterward, the created config is passed as a parameter to the setConfig method of our defaultKaptcha object.

The getCaptcha() method of our CaptchaController class receives an HTTP request and responds with a randomly generated captcha image. To display this image, you can use an <img> tag in your HTML page:


```
<img src="/captcha" alt="captcha">
```

After showing the captcha image, you can create a form to validate the numbers and letters entered by the user. Upon submitting the form, you can create a verifyCaptcha() method in your CaptchaController class as follows:

```
@PostMapping("/verify-captcha")
public String verifyCaptcha(@RequestParam("captcha") String captcha) {
    // Within this method, validate the entered captcha value and redirect the result to a page.
    if (defaultKaptcha.verify(captcha)) {
        return "captcha-success";
    } else {
        return "captcha-failure";
    }
}
```

This code validates the captcha value entered by the user and redirects to the "captcha-success" page if verification is successful or the "captcha-failure" page if it fails. However, in your case, you will make an API request directly.

When you send a request to http://localhost:8080/captcha/, you will receive a result like this.


![Captcha Image](src/main/resources/static/captcha.png)




Requirements
------------

Java 17

İntellij Idea

Dependency:
------------

```
<dependency>
    <groupId>com.github.penggle</groupId>
    <artifactId>kaptcha</artifactId>
    <version>2.3.2</version>
</dependency>
```
