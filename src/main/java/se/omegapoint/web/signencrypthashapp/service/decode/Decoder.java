package se.omegapoint.web.signencrypthashapp.service.decode;

import com.fasterxml.jackson.annotation.JsonInclude;
import se.omegapoint.web.signencrypthashapp.common.Utils;
import se.omegapoint.web.signencrypthashapp.service.encode.EncodingTypes;
import se.omegapoint.web.signencrypthashapp.vo.EncodeVO;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Decoder {

    String decoded;
    String compare;

    public Decoder(EncodeVO encodeVO) throws UnsupportedEncodingException {
        encode(encodeVO);
    }

    public String getDecoded() {
        return decoded;
    }

    public String getCompare() {
        return compare;
    }

    private void encode(EncodeVO encodeVO) throws UnsupportedEncodingException {
        String type = encodeVO.getType();

        if (Arrays.stream(EncodingTypes.values()).anyMatch(s -> s.name().equals(type)) ) {
            System.out.println("Encoding to " + type);


            if (type.equalsIgnoreCase(EncodingTypes.HEX.toString())) {
                decodeHex(encodeVO);
            } else if (type.equalsIgnoreCase(EncodingTypes.BASE64.toString())) {
                decodeBase64(encodeVO);
            } else if (type.equalsIgnoreCase(EncodingTypes.URL.toString())) {
                decodeURL(encodeVO);
            } else if (type.equalsIgnoreCase(EncodingTypes.ASCII.toString())) {
                decodeASCII(encodeVO);
            } else if (type.equalsIgnoreCase(EncodingTypes.BINARY.toString())) {
                decodeBinary(encodeVO);
            }
        }else {
            throw new IllegalArgumentException(type+" encoding not supported");
        }

    }

    private void decodeHex(EncodeVO encodeVO) throws UnsupportedEncodingException {
        String hex = encodeVO.getEncoded();
        String plain = new String (Utils.hexToBytes(hex), "UTF-8");
        if(compare(encodeVO, plain) == null){
            System.out.println("Decoded value : " + plain);
            decoded = plain;
        }
    }

    private void decodeBase64(EncodeVO encodeVO) throws UnsupportedEncodingException {
        String plain = new String(Utils.base64toBytes(encodeVO.getEncoded()),"UTF-8");
        if(compare(encodeVO, plain) == null){
            System.out.println("Decoded value : " + plain);
            decoded = plain;
        }
    }

    private void decodeURL(EncodeVO encodeVO) throws UnsupportedEncodingException {
        String plain= URLDecoder.decode(encodeVO.getEncoded(),"UTF-8");
        if(compare(encodeVO, plain) == null){
            System.out.println("Decoded value : " + plain);
            decoded = plain;
        }

    }

    private void decodeASCII(EncodeVO encodeVO) {
        StringBuilder plain = new StringBuilder();
        for (String s:encodeVO.getEncoded().split(" ")) {
            int number = Integer.parseInt(s);
            char c = (char)number;
            plain.append(c);
        }

        if(compare(encodeVO, plain.toString()) == null){
            System.out.println("Decoded value : " + plain.toString());
            decoded = plain.toString();
        }
    }

    private void decodeBinary(EncodeVO encodeVO) {
        StringBuilder plain = new StringBuilder();
        for (String s:encodeVO.getEncoded().split(" ")) {
            int number = Integer.parseInt(s, 2);
            char c = (char)number;
            plain.append(c);
        }

        if(compare(encodeVO, plain.toString()) == null){
            System.out.println("Decoded value : " + plain.toString());
            decoded = plain.toString();
        }
    }

    private String compare(EncodeVO encodeVO, String encodedValue) {

        if(encodeVO.getCompareValue() == null || encodeVO.getCompareValue().isEmpty()){
            compare = null;
        } else {
            System.out.println(encodeVO.getType().toLowerCase()+" decoded value         : " + encodedValue);
            System.out.println(encodeVO.getType().toLowerCase()+" decoded compare value : " + encodeVO.getCompareValue());
            compare = Boolean.toString(encodedValue.equalsIgnoreCase(encodeVO.getCompareValue()));
        }

        return compare;
    }


}
