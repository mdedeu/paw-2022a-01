package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

public abstract class ThymeleafMailMessage extends MailMessage {

    private String template;

    private TemplateEngine templateEngine;
    private Locale locale = Locale.ENGLISH;

    public ThymeleafMailMessage(MailMessage mailMessage, String template, TemplateEngine templateEngine) {
        super(mailMessage);
        super.setHtml(true);
        this.template = template;
        this.templateEngine = templateEngine;
    }

    @Override
    public void setHtml(boolean isHtml) {
        throw new UnsupportedOperationException("Thymeleaf emails are always html.");
    }

    @Override
    public void setBody(String message) {
        throw new UnsupportedOperationException("Thymeleaf emails are always html.");
    }

    protected String getTemplate() {
        return this.template;
    }
    protected abstract Context getContext();
    public String getBody() {
        return templateEngine.process(getTemplate(), getContext());
    }
    public Locale getLocale() {
        return locale;
    }
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}

