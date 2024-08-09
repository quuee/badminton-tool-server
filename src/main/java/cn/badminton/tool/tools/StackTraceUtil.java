package cn.badminton.tool.tools;

import lombok.experimental.UtilityClass;

import java.util.stream.Stream;

@UtilityClass
public class StackTraceUtil {
    public String getTrace(Throwable throwable){
        StringBuilder builder = new StringBuilder();
        Stream.of(throwable.getStackTrace()).forEach(
                msg-> builder.append("\t\r\n").append(msg)
        );
        return builder.toString();
    }
}
