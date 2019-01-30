package net.technolords.library.log4j2.util;

public class RootCauseHelper {

    public static Throwable getRootCause(Throwable cause) {
        if (cause.getCause() != null) {
            return getRootCause(cause.getCause());
        } else {
            return cause;
        }
    }
}
