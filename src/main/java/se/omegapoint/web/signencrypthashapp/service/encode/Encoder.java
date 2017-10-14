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

        if (Arrays.stream(Encoders.values()).anyMatch(s -> s.name().equals(type)) ) {
            System.out.println("Encoding to " + type);


            if (type.equalsIgnoreCase(Encoders.HEX.toString())) {
                makeHexEncode(encodeVO);
            } else if (type.equalsIgnoreCase(Encoders.BASE64.toString())) {
                makeBase64Encode(encodeVO);
            } else if (type.equalsIgnoreCase(Encoders.URL.toString())) {
                makeURLEncode(encodeVO);
            } else if (type.equalsIgnoreCase(Encoders.ASCII.toString())) {
                makeASCIIEncode(encodeVO);
            }
        }else {
            throw new IllegalArgumentException(type+" encoding not supported");
        }

    }

    private void makeHexEncode(EncodeVO encodeVO) throws UnsupportedEncodingException {
        String hex = Utils.bytesToHex(encodeVO.getText().getBytes("UTF-8"));
        if(compare(encodeVO, hex) == null){
            System.out.println("Encoded value : " + hex);
            encoded = hex;
        }
    }

    private void makeBase64Encode(EncodeVO encodeVO) throws UnsupportedEncodingException {
        String base64String = Utils.toBase64String(encodeVO.getText().getBytes("UTF-8"));
        if(compare(encodeVO, base64String) == null){
            System.out.println("Encoded value : " + base64String);
            encoded = base64String;
        }
    }

    private void makeURLEncode(EncodeVO encodeVO) throws UnsupportedEncodingException {
        String urlEncoded= URLEncoder.encode(encodeVO.getText(),"UTF-8");
        if(compare(encodeVO, urlEncoded) == null){
            System.out.println("Encoded value : " + urlEncoded);
            encoded = urlEncoded;
        }

    }

    private void makeASCIIEncode(EncodeVO encodeVO) {
        StringBuilder ascii = new StringBuilder();
        for (char c : encodeVO.getText().toCharArray()) {
            ascii.append((int) c);
            ascii.append(" ");
        }

        if(compare(encodeVO, ascii.toString().trim()) == null){
            System.out.println("Encoded value : " + ascii.toString().trim());
            encoded = ascii.toString().trim();
        }
    }

    private String compare(EncodeVO encodeVO, String encodedValue) {

        if(encodeVO.getCompareValue() == null || encodeVO.getCompareValue().isEmpty()){
            compare = null;
        } else {
            System.out.println(encodeVO.getType().toLowerCase()+" encoded value         : " + encodedValue);
            System.out.println(encodeVO.getType().toLowerCase()+" encoded compare value : " + encodeVO.getCompareValue());
            compare = Boolean.toString(encodedValue.equalsIgnoreCase(encodeVO.getCompareValue()));
        }

        return compare;
    }


}
