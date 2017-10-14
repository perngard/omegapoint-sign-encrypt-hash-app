package se.omegapoint.web.signencrypthashapp.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.omegapoint.web.signencrypthashapp.encode.Encoder;
import se.omegapoint.web.signencrypthashapp.encrypt.CryptoHandler;
import se.omegapoint.web.signencrypthashapp.error.ErrorResponse;
import se.omegapoint.web.signencrypthashapp.exception.GenericControlException;
import se.omegapoint.web.signencrypthashapp.hash.HashCalculator;
import se.omegapoint.web.signencrypthashapp.hmac.HMacCalculator;
import se.omegapoint.web.signencrypthashapp.vo.EncoderVO;
import se.omegapoint.web.signencrypthashapp.vo.EncryptVO;
import se.omegapoint.web.signencrypthashapp.vo.HMacVO;
import se.omegapoint.web.signencrypthashapp.vo.HashVO;

@RestController
public class WebController {

    @RequestMapping(value = "/encode", method = RequestMethod.POST, headers = "Content-Type=application/json")
    public ResponseEntity<Encoder> encode(@RequestBody EncoderVO encoderVO){

        Encoder encoder =null;
        try{
            encoder = new Encoder(encoderVO);
        } catch (Exception e){
            throw new GenericControlException(e.getMessage());
        }
        return new ResponseEntity<>(encoder, HttpStatus.OK);
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
    public ResponseEntity<CryptoHandler> calculate(@RequestBody EncryptVO aesVO){

        CryptoHandler CryptoHandler =null;
        try{
            CryptoHandler = new CryptoHandler(aesVO);
        } catch (Exception e){
            throw new GenericControlException(e.getMessage());
        }
        return new ResponseEntity<>(CryptoHandler, HttpStatus.OK);
    }

    @ExceptionHandler(GenericControlException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(GenericControlException e) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
        error.setErrorMessage(e.getErrorMessage());

        return new ResponseEntity<>(error, HttpStatus.OK);
    }

}
