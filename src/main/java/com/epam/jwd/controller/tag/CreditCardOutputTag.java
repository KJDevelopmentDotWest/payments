package com.epam.jwd.controller.tag;

import com.epam.jwd.service.dto.creditcarddto.CreditCardDto;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;

public class CreditCardOutputTag extends SimpleTagSupport {

    private static final String COLUMN_START_TAG = "<td>";
    private static final String COLUMN_END_TAG = "</td>";
    private static final String STRING_BLOCKED = "BLOCKED";
    private static final String STRING_LINK_BLOCK_CREDIT_CARD = """
            <form class="inline" method="post" action="/payments?command=edit_payment" >
                <button class="btn btn-exsm btn-primary" type="submit">edit</button>
                <input type="hidden" name="paymentId" value="${requestScope.payments.get(i).getId()}">
            </form>""";
    private static final String STRING_LINK_ADD_FOUNDS = "ADD";

    private CreditCardDto creditCardDto;

    public void setCreditCardDto(CreditCardDto creditCardDto){
        this.creditCardDto = creditCardDto;
    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        out.print(generateHtmlExpression());
    }

    private String generateHtmlExpression(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(COLUMN_START_TAG)
                .append(creditCardDto.getName())
                .append(COLUMN_END_TAG);
        stringBuilder
                .append(COLUMN_START_TAG)
                .append(creditCardDto.getCardNumber())
                .append(COLUMN_END_TAG);
        stringBuilder
                .append(COLUMN_START_TAG)
                .append(creditCardDto.getExpireDate())
                .append(COLUMN_END_TAG);
        stringBuilder.append(COLUMN_START_TAG)
                .append(creditCardDto.getBankAccount().getBalance())
                .append(COLUMN_END_TAG);
        stringBuilder.append(COLUMN_START_TAG);
        if (creditCardDto.getBankAccount().getBlocked()){
            stringBuilder.append(STRING_BLOCKED);
        } else {
            stringBuilder.append(STRING_LINK_BLOCK_CREDIT_CARD);
        }
        stringBuilder.append(COLUMN_END_TAG);
        stringBuilder.append(COLUMN_START_TAG)
                .append(STRING_LINK_ADD_FOUNDS)
                .append(COLUMN_END_TAG);
        return stringBuilder.toString();
    }
}
