package ar.edu.itba.paw.cryptuki.form;


import ar.edu.itba.paw.persistence.Message;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class messageForm {
    @NotNull
    private Integer userId;

    @NotNull
    @Size(min=1,max = 255)
    private String message;

   @NotNull
   private Integer tradeId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }


    public Message.Builder toBuilder(){
        return new Message.Builder(this.message)
                .withSender(this.userId)
                .withTrade(this.tradeId);
    }

}
