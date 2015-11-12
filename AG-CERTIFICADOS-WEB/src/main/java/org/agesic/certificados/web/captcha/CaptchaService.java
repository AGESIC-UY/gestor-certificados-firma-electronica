/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.agesic.certificados.web.captcha;

import com.octo.captcha.component.image.backgroundgenerator.GradientBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;
import java.awt.Color;
import java.awt.Font;

public class CaptchaService {

    private static ImageCaptchaService instance = null;

    public static ImageCaptchaService getInstance() {
        if (instance == null) {
            CustomImageEngine engine = new CustomImageEngine();
            instance = new DefaultManageableImageCaptchaService(new FastHashMapCaptchaStore(),
                    engine, 180, 0x186a0, 0x124f8);
        }
        return instance;
    }

    static class CustomImageEngine extends ListImageCaptchaEngine {

        CustomImageEngine() {
        }

        @Override
        protected void buildInitialFactories() {
            // Range of characters to generate
            //
            WordGenerator wgen =new RandomWordGenerator("abcdefghijklmsonqrstuvwxyz123456789");
            // Range of text colors
            RandomRangeColorGenerator cgen =  new RandomRangeColorGenerator(new int[]{20, 25}, new int[]{50, 120}, new int[]{50, 255});
            // Number of challenge characters  
            TextPaster textPaster = new RandomTextPaster(new Integer(7), new Integer(7),cgen, Boolean.valueOf(true));

            // Background and size of the image.
            GradientBackgroundGenerator background = new GradientBackgroundGenerator(250,
                    50,
                    Color.white,
                    Color.white);

            // Alternate background options.
            // FunkyBackgroundGenerator background =
            //    new FunkyBackgroundGenerator (new Integer(250), new Integer(50));
            // UniColorBackgroundGenerator
            //    background =
            //  new UniColorBackgroundGenerator (250,50);
            //

            // Text fonts  
            Font fontsList[] = {
                new Font("Arial", 0, 10),
                new Font("Tahoma", 0, 10),
                new Font("Verdana", 0, 10)
            };

            FontGenerator fontGenerator = new RandomFontGenerator(new Integer(20), new Integer(35), fontsList);
            WordToImage wordToImage = new ComposedWordToImage(fontGenerator, background, textPaster);
            addFactory(new GimpyFactory(wgen, wordToImage));
        }
    }
}