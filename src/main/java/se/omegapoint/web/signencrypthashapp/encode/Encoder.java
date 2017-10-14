package se.omegapoint.web.signencrypthashapp.encode;

import com.fasterxml.jackson.annotation.JsonInclude;
import se.omegapoint.web.signencrypthashapp.Utils;
import se.omegapoint.web.signencrypthashapp.encrypt.Cryptos;
import se.omegapoint.web.signencrypthashapp.encrypt.crypto.AES;
import se.omegapoint.web.signencrypthashapp.encrypt.crypto.Blowfish;
import se.omegapoint.web.signencrypthashapp.encrypt.crypto.DES;
import se.omegapoint.web.signencrypthashapp.encrypt.crypto.DESede;
import se.omegapoint.web.signencrypthashapp.vo.EncoderVO;
import se.omegapoint.web.signencrypthashapp.vo.ResponseVO;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Encoder {

    String encoded;
    String compare;

    public Encoder(EncoderVO encoderVO) throws UnsupportedEncodingException {
        encode(encoderVO);
    }

    public String getEncoded() {
        return encoded;
    }

    public String getCompare() {
        return compare;
    }

    private void encode(EncoderVO encoderVO) throws UnsupportedEncodingException {
        String type = encoderVO.getType();

        if (Arrays.stream(Encoders.values()).anyMatch(s -> s.name().equals(type)) ) {
            System.out.println("Encoding to " + type);


            if (type.equalsIgnoreCase(Encoders.HEX.toString())) {
                makeHexEncode(encoderVO);
            } else if (type.equalsIgnoreCase(Encoders.BASE64.toString())) {
                makeBase64Encode(encoderVO);
            } else if (type.equalsIgnoreCase(Encoders.URL.toString())) {
                makeURLEncode(encoderVO);
            } else if (type.equalsIgnoreCase(Encoders.ASCII.toString())) {
                makeASCIIEncode(encoderVO);
            }
        }else {
            throw new IllegalArgumentException(type+" encoding not supported");
        }

    }

    private void makeHexEncode(EncoderVO encoderVO) throws UnsupportedEncodingException {
        String hex = Utils.bytesToHex(encoderVO.getText().getBytes("UTF-8"));
        if(compare(encoderVO, hex) == null){
            System.out.println("Encoded value : " + hex);
            encoded = hex;
        }
    }

    private void makeBase64Encode(EncoderVO encoderVO) throws UnsupportedEncodingException {
        String base64String = Utils.toBase64String(encoderVO.getText().getBytes("UTF-8"));
        if(compare(encoderVO, base64String) == null){
            System.out.println("Encoded value : " + base64String);
            encoded = base64String;
        }
    }

    private void makeURLEncode(EncoderVO encoderVO) throws UnsupportedEncodingException {
        String urlEncoded= URLEncoder.encode(encoderVO.getText(),"UTF-8");
        if(compare(encoderVO, urlEncoded) == null){
            System.out.println("Encoded value : " + urlEncoded);
            encoded = urlEncoded;
        }

    }

    private void makeASCIIEncode(EncoderVO encoderVO) {
        StringBuilder ascii = new StringBuilder();
        for (char c : encoderVO.getText().toCharArray()) {
            ascii.append((int) c);
            ascii.append(" ");
        }

        if(compare(encoderVO, ascii.toString().trim()) == null){
            System.out.println("Encoded value : " + ascii.toString().trim());
            encoded = ascii.toString().trim();
        }
    }

    private String compare(EncoderVO encoderVO, String encodedValue) {

        if(encoderVO.getCompareValue() == null || encoderVO.getCompareValue().isEmpty()){
            compare = null;
        } else {
            System.out.println(encoderVO.getType().toLowerCase()+" encoded value         : " + encodedValue);
            System.out.println(encoderVO.getType().toLowerCase()+" encoded compare value : " + encoderVO.getCompareValue());
            compare = Boolean.toString(encodedValue.equalsIgnoreCase(encoderVO.getCompareValue()));
        }

        return compare;
    }


}
