package com.epam.jwd.controller.tag;

import com.epam.jwd.service.dto.profilepicturedto.ProfilePictureDto;
import com.epam.jwd.service.exception.ServiceException;
import com.epam.jwd.service.impl.ProfilePictureService;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;

public class ImageOutputTag extends SimpleTagSupport {

    private static final Logger logger = LogManager.getLogger(ImageOutputTag.class);

    private Integer pictureId;
    private Integer width;
    private Integer height;

    private static final Integer DEFAULT_WIDTH = 300;
    private static final Integer DEFAULT_HEIGHT = 300;

    private final ProfilePictureService service = new ProfilePictureService();

    public void setPictureId(Integer pictureId) {
        this.pictureId = pictureId;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @Override
    public void doTag() throws JspException, IOException {

        ProfilePictureDto profilePicture;
        URL res;
        File file = null;

        try {
            profilePicture = service.getById(pictureId);
            res = getClass().getClassLoader().getResource(profilePicture.getPath());

            file = Paths.get(res.toURI()).toFile();

        } catch (ServiceException e) {
            logger.error(e.getErrorCode());
        } catch (URISyntaxException e) {
            logger.error(e);
        }

        JspWriter out = getJspContext().getOut();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<img width=\"")
                .append(Optional.ofNullable(width).orElse(DEFAULT_WIDTH))
                .append("\" height=\"")
                .append(Optional.ofNullable(width).orElse(DEFAULT_HEIGHT))
                .append("\" src=\"data:image/jpeg;base64,")
                .append(encodeFileToBase64(file))
                .append("\">");

        out.print(stringBuilder.toString());
    }

    private String encodeFileToBase64(File file){
        String encodedFile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedFile = new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
        } catch (IOException | NullPointerException e) {
            logger.error(e);
        }

        return encodedFile;
    }
}
