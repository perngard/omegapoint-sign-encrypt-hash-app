package se.omegapoint.web.signencrypthashapp.service.asn1;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.util.ASN1Dump;
import se.omegapoint.web.signencrypthashapp.common.TextType;
import se.omegapoint.web.signencrypthashapp.common.Utils;
import se.omegapoint.web.signencrypthashapp.vo.Asn1VO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Asn1Handler {

    String text;
    String asn1;
    String compare;

    public Asn1Handler(Asn1VO asn1VO, boolean decode) throws IOException {
        if(decode){
            decode(asn1VO);
        } else {
            encode(asn1VO);
        }
    }

    private void decode(Asn1VO asn1VO) throws IOException {
        byte[] data = Utils.convertToByte(asn1VO.getAsn1(), asn1VO.getType());
        ASN1InputStream stream = new ASN1InputStream(new ByteArrayInputStream(data));
        ASN1Primitive object = stream.readObject();
        String calculatedValue = ASN1Dump.dumpAsString(object);

        if(asn1VO.getCompareValue() == null || asn1VO.getCompareValue().isEmpty()) {
            text = calculatedValue;
        } else {
            compare = Boolean.toString(calculatedValue.equalsIgnoreCase(asn1VO.getCompareValue()));
        }

    }

    private void encode(Asn1VO asn1VO) {
       //TODO - not implemented yet
        asn1 = "not implemented yet";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCompare() {
        return compare;
    }

    public void setCompare(String compare) {
        this.compare = compare;
    }

    public String getAsn1() {
        return asn1;
    }

    public void setAsn1(String asn1) {
        this.asn1 = asn1;
    }
}
