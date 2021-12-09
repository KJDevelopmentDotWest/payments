package com.epam.jwd.controller.tag;

import com.epam.jwd.service.dto.creditcarddto.CreditCardDto;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class CreditCardOutputTag extends SimpleTagSupport {

    private static final String COLUMN_START_TAG = "<td>";
    private static final String COLUMN_END_TAG = "</td>";
    private static final String STRING_BLOCKED = "BLOCKED";
    private static final String DASH_SYMBOL = "-";
    private static final String STRING_LINK_BLOCK_CREDIT_CARD_START = """
            <form class="inline" method="post" action="/payments?command=block_credit_card" >
                <button class="btn btn-exsm btn-primary" type="submit">block</button>
                <input type="hidden" name="creditCardId" value=
            """;
    private static final String STRING_LINK_ADD_FOUNDS_START = """
            <form class="inline" method="post" action="/payments?command=add_funds" >
                <input id="funds" type="number" name="funds" oninput="validateform()">
                <button id="submitbutton" class="btn btn-exsm btn-primary" type="submit">add</button>
                <input type="hidden" name="creditCardId" value=
            """;
    private static final String STRING_FORM_TAG_END = "></form>";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

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
                .append(COLUMN_START_TAG);
        if (!Objects.isNull(creditCardDto.getExpireDate())){
            stringBuilder.append(dateFormat.format(creditCardDto.getExpireDate()));
        } else {
            stringBuilder.append(DASH_SYMBOL);
        }
        stringBuilder
                .append(COLUMN_END_TAG);

        stringBuilder.append(COLUMN_START_TAG)
                .append(creditCardDto.getBankAccount().getBalance())
                .append(COLUMN_END_TAG);

        if (creditCardDto.getBankAccount().getBlocked()){
            stringBuilder.append(COLUMN_START_TAG)
                    .append(STRING_BLOCKED)
                    .append(COLUMN_END_TAG);
        } else {
            stringBuilder.append(COLUMN_START_TAG)
                    .append(STRING_LINK_BLOCK_CREDIT_CARD_START)
                    .append(creditCardDto.getId())
                    .append(STRING_FORM_TAG_END)
                    .append(COLUMN_END_TAG);

            stringBuilder.append(COLUMN_START_TAG)
                    .append(STRING_LINK_ADD_FOUNDS_START)
                    .append(creditCardDto.getId())
                    .append(STRING_FORM_TAG_END)
                    .append(COLUMN_END_TAG);
        }
        return stringBuilder.toString();
    }
}
