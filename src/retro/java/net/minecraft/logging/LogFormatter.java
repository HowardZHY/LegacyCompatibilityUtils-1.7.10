package net.minecraft.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

@SuppressWarnings("unused")
public class LogFormatter extends Formatter {

    public SimpleDateFormat field_98228_b;

    public final LogAgent field_98229_a;

    public LogFormatter(LogAgent logAgent) {
        this.field_98229_a = logAgent;
        this.field_98228_b = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public LogFormatter(LogAgent logAgent, LogAgentEmptyAnon empty) {
        this(logAgent);
    }

    @Override
    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.field_98228_b.format(record.getMillis()));
        if (LogAgent.func_98237_a(this.field_98229_a) != null) {
            builder.append(LogAgent.func_98237_a(this.field_98229_a));
        }
        builder.append(" [").append(record.getLevel().getName()).append("] ");
        builder.append(this.formatMessage(record));
        builder.append('\n');
        Throwable throwable = record.getThrown();
        if (throwable != null) {
            StringWriter writer = new StringWriter();
            throwable.printStackTrace(new PrintWriter(writer));
            builder.append(writer);
        }
        return builder.toString();
    }

}
