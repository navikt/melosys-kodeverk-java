package no.nav.melosys.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FreeMarkerTemplateService {

    private final Configuration cfg;

    public FreeMarkerTemplateService(@Value("${template.mappe}") String templateMappe) {
        cfg = new Configuration(Configuration.VERSION_2_3_27);
        cfg.setClassForTemplateLoading(this.getClass(), templateMappe);
    }

    public String generereKildeKodeFraTemplate(Map root, String templateNavn) throws IOException, TemplateException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            Template temp = cfg.getTemplate(templateNavn);
            Writer out = new OutputStreamWriter(byteArrayOutputStream);
            temp.process(root, out);
            return new String(byteArrayOutputStream.toByteArray());

    }

    public Configuration getCfg() {
        return cfg;
    }
}
