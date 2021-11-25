package com.epam.jwd.controller.tag;

import com.epam.jwd.service.dto.userdto.AccountDto;
import com.epam.jwd.service.dto.userdto.UserDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.AccountService;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Objects;

public class AdminUsersOutputTag extends SimpleTagSupport {
    private static final Logger logger = LogManager.getLogger(AdminUsersOutputTag.class);

    private static final String COLUMN_START_TAG = "<td>";
    private static final String COLUMN_END_TAG = "</td>";
    private static final String STRING_ACTIVE = "ACTIVE";
    private static final String STRING_LINK_UNBLOCK_USER_START = """
            <form class="inline" method="post" action="/payments?command=unblock_user" >
                <button class="btn btn-exsm btn-primary" type="submit">unblock</button>
                <input type="hidden" name="userId" value=
            """;
    private static final String STRING_FORM_TAG_END = "></form>";

    private UserDto userDto;

    public void setUserDto(UserDto userDto){
        this.userDto = userDto;
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
                .append(userDto.getId())
                .append(COLUMN_END_TAG);
        stringBuilder
                .append(COLUMN_START_TAG)
                .append(userDto.getLogin())
                .append(COLUMN_END_TAG);
        stringBuilder
                .append(COLUMN_START_TAG)
                .append(userDto.getRole())
                .append(COLUMN_END_TAG);
        stringBuilder
                .append(COLUMN_START_TAG);
        if (!userDto.getActive()){
            stringBuilder.append(STRING_LINK_UNBLOCK_USER_START);
            stringBuilder
                    .append(STRING_FORM_TAG_END);
        } else {
            stringBuilder.append(STRING_ACTIVE);
        }
        if (!Objects.isNull(userDto.getAccountId())){
            try {
                AccountService service = new AccountService();
                AccountDto accountDto = service.getById(userDto.getId());
                stringBuilder
                        .append(COLUMN_START_TAG)
                        .append(accountDto.getName())
                        .append(COLUMN_END_TAG);
                stringBuilder
                        .append(COLUMN_START_TAG)
                        .append(accountDto.getSurname())
                        .append(COLUMN_END_TAG);
                stringBuilder
                        .append(COLUMN_START_TAG)
                        .append(accountDto.getProfilePictureId())
                        .append(COLUMN_END_TAG);
            } catch (ServiceException e) {
                logger.error(e.getErrorCode());
                stringBuilder
                        .append(COLUMN_START_TAG)
                        .append("-")
                        .append(COLUMN_END_TAG);
                stringBuilder
                        .append(COLUMN_START_TAG)
                        .append("-")
                        .append(COLUMN_END_TAG);
                stringBuilder
                        .append(COLUMN_START_TAG)
                        .append("-")
                        .append(COLUMN_END_TAG);
            }
        }
        else {
            stringBuilder
                    .append(COLUMN_START_TAG)
                    .append("-")
                    .append(COLUMN_END_TAG);
            stringBuilder
                    .append(COLUMN_START_TAG)
                    .append("-")
                    .append(COLUMN_END_TAG);
            stringBuilder
                    .append(COLUMN_START_TAG)
                    .append("-")
                    .append(COLUMN_END_TAG);
        }
        return stringBuilder.toString();
    }
}
