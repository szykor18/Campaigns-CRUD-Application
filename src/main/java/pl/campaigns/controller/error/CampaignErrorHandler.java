package pl.campaigns.controller.error;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.campaigns.model.CampaignNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CampaignErrorHandler {

    @ExceptionHandler(CampaignNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CampaignErrorResponse handleCampaignNotFoundException(CampaignNotFoundException exception) {
        return new CampaignErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiValidationResponse handleCampaignBadRequest(MethodArgumentNotValidException exception) {
        List<String> messagesFromException = getMessagesFromException(exception);
        return new ApiValidationResponse(messagesFromException, HttpStatus.BAD_REQUEST);
    }

    private List<String> getMessagesFromException(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
    }
}
