package se.omegapoint.web.signencrypthashapp.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.omegapoint.web.signencrypthashapp.service.asn1.Asn1Handler;
import se.omegapoint.web.signencrypthashapp.service.decode.Decoder;
import se.omegapoint.web.signencrypthashapp.service.encode.Encoder;
import se.omegapoint.web.signencrypthashapp.service.crypto.CryptoHandler;
import se.omegapoint.web.signencrypthashapp.error.ErrorResponse;
import se.omegapoint.web.signencrypthashapp.exception.GenericControlException;
import se.omegapoint.web.signencrypthashapp.service.hash.HashCalculator;
import se.omegapoint.web.signencrypthashapp.service.hmac.HMacCalculator;
import se.omegapoint.web.signencrypthashapp.service.jwt.JWTHandler;
import se.omegapoint.web.signencrypthashapp.vo.*;

@RestController
public class WebController {

    @RequestMapping(value = "/encode", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public ResponseEntity<Encoder> encode(@RequestBody EncodeVO encodeVO){

        Encoder encoder =null;
        try{
            encoder = new Encoder(encodeVO);
        } catch (Exception e){
            throw new GenericControlException(e.getMessage());
        }
        return new ResponseEntity<>(encoder, HttpStatus.OK);
    }

    @RequestMapping(value = "/decode", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public ResponseEntity<Decoder> decode(@RequestBody EncodeVO encodeVO){

        Decoder decoder =null;
        try{
            decoder = new Decoder(encodeVO);
        } catch (Exception e){
            throw new GenericControlException(e.getMessage());
        }
        return new ResponseEntity<>(decoder, HttpStatus.OK);
    }

    @RequestMapping(value = "/hash", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public ResponseEntity<HashCalculator> calculate(@RequestBody HashVO hashVO){

        HashCalculator hashCalculator =null;
        try{
            hashCalculator = new HashCalculator(hashVO);
        } catch (Exception e){
            throw new GenericControlException(e.getMessage());
        }
         return new ResponseEntity<>(hashCalculator, HttpStatus.OK);
    }

    @RequestMapping(value = "/hmac", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public ResponseEntity<HMacCalculator> calculate(@RequestBody HMacVO hmacVO){

        HMacCalculator hmacCalculator =null;
        try{
            hmacCalculator = new HMacCalculator(hmacVO);
        } catch (Exception e){
            throw new GenericControlException(e.getMessage());
        }
        return new ResponseEntity<>(hmacCalculator, HttpStatus.OK);
    }

    @RequestMapping(value = "/encrypt", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public ResponseEntity<CryptoHandler> encrypt(@RequestBody CryptoVO cryptoVO){

        CryptoHandler CryptoHandler =null;
        try{
            CryptoHandler = new CryptoHandler(cryptoVO, true);
        } catch (Exception e){
            throw new GenericControlException(e.getMessage());
        }
        return new ResponseEntity<>(CryptoHandler, HttpStatus.OK);
    }

    @RequestMapping(value = "/decrypt", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public ResponseEntity<CryptoHandler> decrypt(@RequestBody CryptoVO cryptoVO){

        CryptoHandler CryptoHandler =null;
        try{
            CryptoHandler = new CryptoHandler(cryptoVO, false);
        } catch (Exception e){
            throw new GenericControlException(e.getMessage());
        }
        return new ResponseEntity<>(CryptoHandler, HttpStatus.OK);
    }

    @RequestMapping(value = "/jwt/create", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public ResponseEntity<JWTHandler> create(@RequestBody JWTTokenVO tokenVO){

        JWTHandler handler =null;
        try{
            handler = new JWTHandler(tokenVO,true);
        } catch (Exception e){
            throw new GenericControlException(e.getMessage());
        }
        return new ResponseEntity<>(handler, HttpStatus.OK);
    }

    @RequestMapping(value = "/jwt/verify", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public ResponseEntity<JWTHandler> verify(@RequestBody JWTTokenVO tokenVO){

        JWTHandler handler =null;
        try{
            handler = new JWTHandler(tokenVO,false);
        } catch (Exception e){
            throw new GenericControlException(e.getMessage());
        }
        return new ResponseEntity<>(handler, HttpStatus.OK);
    }

//    @RequestMapping(value = "/asn1/encode", method = RequestMethod.POST, headers = "Content-Type=application/json")
//    public ResponseEntity<Asn1Handler> encode(@RequestBody Asn1VO asn1VO){
//
//        Asn1Handler handler =null;
//        try{
//            handler = new Asn1Handler(asn1VO,false);
//        } catch (Exception e){
//            throw new GenericControlException(e.getMessage());
//        }
//        return new ResponseEntity<>(handler, HttpStatus.OK);
//    }

    @RequestMapping(value = "/asn1/decode", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public ResponseEntity<Asn1Handler> decode(@RequestBody Asn1VO asn1VO){

        Asn1Handler handler =null;
        try{
            handler = new Asn1Handler(asn1VO,true);
        } catch (Exception e){
            throw new GenericControlException(e.getMessage());
        }
        return new ResponseEntity<>(handler, HttpStatus.OK);
    }

    @ExceptionHandler(GenericControlException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(GenericControlException e) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
        error.setErrorMessage(e.getErrorMessage());
        e.printStackTrace();

        return new ResponseEntity<>(error, HttpStatus.OK);
    }

}
