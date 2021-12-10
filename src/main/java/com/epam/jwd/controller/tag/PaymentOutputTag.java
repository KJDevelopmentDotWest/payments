package com.epam.jwd.controller.tag;

import com.epam.jwd.service.dto.paymentdto.PaymentDto;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Objects;

public class PaymentOutputTag extends SimpleTagSupport {
    private static final String COLUMN_START_TAG = "<td>";
    private static final String COLUMN_END_TAG = "</td>";
    private static final String STRING_YES = "YES";
    private static final String STRING_NO = "NO";
    private static final String DASH_SYMBOL = "-";

    private static final String STRING_LINK_EDIT_START = """
            <form class="inline" method="post" action="/payments?command=show_edit_payment" >
                <button class="btn btn-exsm btn-primary" type="submit">Edit</button>
                <input type="hidden" name="paymentId" value=
            """;

    private static final String STRING_LINK_CHECKOUT_START = """
            <form class="inline" method="post" action="/payments?command=show_checkout" >
                <button class="btn btn-exsm btn-primary" type="submit">Pay</button>
                <input type="hidden" name="paymentId" value=
            """;

    private static final String STRING_LINK_DELETE_START = """
            <form class="inline" method="post" action="/payments?command=delete_payment" >
                <button class="btn btn-exsm btn-primary" type="submit">Delete</button>
                <input type="hidden" name="paymentId" value=
            """;

    private static final String STRING_FORM_TAG_END = "></form>";

    private PaymentDto paymentDto;

    public void setPaymentDto(PaymentDto paymentDto) {
        this.paymentDto = paymentDto;
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
                .append(paymentDto.getName())
                .append(COLUMN_END_TAG);
        stringBuilder
                .append(COLUMN_START_TAG)
                .append(paymentDto.getPrice())
                .append(COLUMN_END_TAG);
        stringBuilder.append(COLUMN_START_TAG)
                .append(paymentDto.getDestinationAddress())
                .append(COLUMN_END_TAG);
        stringBuilder.append(COLUMN_START_TAG);
        if (!Objects.isNull(paymentDto.getTime())){
            stringBuilder.append(DateFormat.getInstance().format(paymentDto.getTime()));
        } else {
            stringBuilder.append(DASH_SYMBOL);
        }
        stringBuilder.append(COLUMN_END_TAG);
        if (paymentDto.getCommitted()){
            stringBuilder.append(COLUMN_START_TAG)
                    .append(STRING_YES)
                    .append(COLUMN_END_TAG)
                    .append(COLUMN_START_TAG)
                    .append(COLUMN_END_TAG)
                    .append(COLUMN_START_TAG)
                    .append(COLUMN_END_TAG);
        } else {
            stringBuilder.append(COLUMN_START_TAG)
                    .append(STRING_NO)
                    .append(COLUMN_END_TAG);

            stringBuilder.append(COLUMN_START_TAG)
                    .append(STRING_LINK_EDIT_START)
                    .append(paymentDto.getId())
                    .append(STRING_FORM_TAG_END)
                    .append(COLUMN_END_TAG);

            stringBuilder.append(COLUMN_START_TAG)
                    .append(STRING_LINK_CHECKOUT_START)
                    .append(paymentDto.getId())
                    .append(STRING_FORM_TAG_END)
                    .append(COLUMN_END_TAG);
        }

        stringBuilder.append(COLUMN_START_TAG)
                .append(STRING_LINK_DELETE_START)
                .append(paymentDto.getId())
                .append(STRING_FORM_TAG_END)
                .append(COLUMN_END_TAG);
        return stringBuilder.toString();
    }
}
