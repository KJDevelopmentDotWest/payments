package com.epam.jwd.controller.tag;

import com.epam.jwd.service.dto.paymentdto.PaymentDto;
import com.epam.jwd.service.impl.PaymentService;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class PaymentOutputTag extends SimpleTagSupport {
    private static final Logger logger = LogManager.getLogger(PaymentOutputTag.class);

    private static final String BUTTON_HTML = """
            <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                            <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked>
                            <label class="btn btn-outline-primary" for="btnradio1">English</label>
                            <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off">
                            <label class="btn btn-outline-primary" for="btnradio2">Russian</label>
                          </div>""";

    private Integer paymentDto;
    private final PaymentService paymentService = new PaymentService();

    public void setPaymentDto(Integer paymentDto) {
        this.paymentDto = paymentDto;
    }

    @Override
    public void doTag() throws IOException {
        JspWriter out = getJspContext().getOut();
        //List<PaymentDto> paymentDto = paymentService.getByUserId(paymentId);
        //logger.info(paymentId.toString());
        //out.print(paymentDto);
    }
}
