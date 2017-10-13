package se.omegapoint.web.signencrypthashapp.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.omegapoint.web.signencrypthashapp.encrypt.EncryptCalculator;
import se.omegapoint.web.signencrypthashapp.hash.HashCalculator;

@RestController
public class WebController {

    @RequestMapping(value = "/hash", method = RequestMethod.GET)
    public HashCalculator calculate(@RequestParam(value="text", defaultValue="This is a test") String text,
                             @RequestParam(value="algorithm", defaultValue="SHA-256") String algorithm,
                             @RequestParam(value="compare", defaultValue="c7be1ed902fb8dd4d48997c6452f5d7e509fbcdbe2808b16bcf4edce4c07d14e") String compare) {

        return new HashCalculator(text, algorithm, compare);
    }

    @RequestMapping(value = "/encrypt", method = RequestMethod.GET)
    public EncryptCalculator calculate(@RequestParam(value="clearText", defaultValue="This is a test") String clearText,
                                       @RequestParam(value="algorithm", defaultValue="AES") String algorithm,
                                       @RequestParam(value="keyLength", defaultValue="128") String keyLength,
                                       @RequestParam(value="type", defaultValue="ECB") String type,
                                       @RequestParam(value="padding", defaultValue="PKCS5Padding") String padding,
                                       @RequestParam(value="secret", defaultValue="OPerationHack") String secret,
                                       @RequestParam(value="encryptedText", defaultValue="70939779d718f2a19bee2082fd436d55") String encryptedText) {

        return new EncryptCalculator(clearText, algorithm, keyLength, type, padding, secret, encryptedText);
    }

}
