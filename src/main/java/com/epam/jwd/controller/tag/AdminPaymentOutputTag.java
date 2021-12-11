package com.epam.jwd.controller.tag;

import com.epam.jwd.service.dto.paymentdto.PaymentDto;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Objects;

public class AdminPaymentOutputTag extends SimpleTagSupport {
    private static final Logger logger = LogManager.getLogger(PaymentOutputTag.class);

    private static final String COLUMN_START_TAG = "<td>";
    private static final String COLUMN_END_TAG = "</td>";
    private static final String STRING_YES = "YES";
    private static final String STRING_NO = "NO";

    private PaymentDto paymentDto;

    /**
     *
     * @param paymentDto payments that tag will operate
     */
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
                .append(paymentDto.getId())
                .append(COLUMN_END_TAG);
        stringBuilder
                .append(COLUMN_START_TAG)
                .append(paymentDto.getUserId())
                .append(COLUMN_END_TAG);
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
            stringBuilder.append("-");
        }
        stringBuilder.append(COLUMN_START_TAG);
        if (paymentDto.getCommitted()){
            stringBuilder.append(STRING_YES);
        } else {
            stringBuilder.append(STRING_NO);
        }
        stringBuilder.append(COLUMN_END_TAG);
        return stringBuilder.toString();
    }
}
