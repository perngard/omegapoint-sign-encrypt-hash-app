package se.omegapoint.web.signencrypthashapp.service.encode;

import com.fasterxml.jackson.annotation.JsonInclude;
import se.omegapoint.web.signencrypthashapp.common.Utils;
import se.omegapoint.web.signencrypthashapp.vo.EncodeVO;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Encoder {

    String encoded;
    String compare;

    public Encoder(EncodeVO encodeVO) throws UnsupportedEncodingException {
        encode(encodeVO);
    }

    public String getEncoded() {
        return encoded;
    }

    public String getCompare() {
        return compare;
    }

    private void encode(EncodeVO encodeVO) throws UnsupportedEncodingException {
        String type = encodeVO.getType();

        if (Arrays.stream(EncodingTypes.values()).anyMatch(s -> s.name().equals(type)) ) {
            System.out.println("Encoding to " + type);


            if (type.equalsIgnoreCase(EncodingTypes.HEX.toString())) {
                encodeHex(encodeVO);
            } else if (type.equalsIgnoreCase(EncodingTypes.BASE64.toString())) {
                encodeBase64(encodeVO);
            } else if (type.equalsIgnoreCase(EncodingTypes.URL.toString())) {
                encodeURL(encodeVO);
            } else if (type.equalsIgnoreCase(EncodingTypes.ASCII.toString())) {
                encodeASCII(encodeVO);
            }
        }else {
            throw new IllegalArgumentException(type+" encoding not supported");
        }

    }

    private void encodeHex(EncodeVO encodeVO) throws UnsupportedEncodingException {
        String hex = Utils.bytesToHex(encodeVO.getText().getBytes("UTF-8"));
        if(encodeVO.getCompareValue() == null){
            System.out.println("Encoded value : " + hex);
            encoded = hex;
        } else {
            compare(encodeVO.getCompareValue().replace(" ",""), hex, encodeVO.getType());
        }
    }

    private void encodeBase64(EncodeVO encodeVO) throws UnsupportedEncodingException {
        String base64String = Utils.bytesToBase64(encodeVO.getText().getBytes("UTF-8"));
        if(compare(encodeVO.getCompareValue(), base64String, encodeVO.getType()) == null){
            System.out.println("Encoded value : " + base64String);
            encoded = base64String;
        }
    }

    private void encodeURL(EncodeVO encodeVO) throws UnsupportedEncodingException {
        String urlEncoded= URLEncoder.encode(encodeVO.getText(),"UTF-8");
        if(compare(encodeVO.getCompareValue(), urlEncoded, encodeVO.getType()) == null){
            System.out.println("Encoded value : " + urlEncoded);
            encoded = urlEncoded;
        }

    }

    private void encodeASCII(EncodeVO encodeVO) {
        StringBuilder ascii = new StringBuilder();
        for (char c : encodeVO.getText().toCharArray()) {
            ascii.append((int) c);
            ascii.append(" ");
        }

        if(compare(encodeVO.getCompareValue(), ascii.toString().trim(), encodeVO.getType()) == null){
            System.out.println("Encoded value : " + ascii.toString().trim());
            encoded = ascii.toString().trim();
        }
    }

    private String compare(String compareValue, String encodedValue, String type) {

        if(compareValue == null || compareValue.isEmpty()){
            compare = null;
        } else {
            System.out.println(type.toLowerCase()+" encoded value         : " + encodedValue);
            System.out.println(type.toLowerCase()+" encoded compare value : " + compareValue);
            compare = Boolean.toString(encodedValue.equalsIgnoreCase(compareValue));
        }

        return compare;
    }


}
