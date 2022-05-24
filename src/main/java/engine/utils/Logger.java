package engine.utils;

public final class Logger {

    public enum LoggerLevel {
        INFO, WARN, ERROR
    }

    private static LoggerLevel loggerLevel = LoggerLevel.ERROR;

    public static <T> void Info(T info) {
        System.out.println(info);
    }

    public static <T> void Warn(T warn) {
        System.out.println(warn);
    }

    public static <T> void Error(T error) {
        System.err.println(error);
    }

    public static LoggerLevel GetLoggerLevel() { return loggerLevel; }
    public static void SetLoggerLevel(LoggerLevel loggerLevel) { Logger.loggerLevel = loggerLevel; }

}
