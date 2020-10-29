package io.documentnode.minilog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The aim of creating this logger instead of using existing solutions is
 * to avoid any external dependencies and Java reflection.
 *
 * It makes life easier when compiling Java into native shared library.
 * Also the result shared library will be much smaller.
 *
 * @author jake
 */
public class Logger {

  public static Level DEFAULT_LEVEL = Level.DEBUG;
  public static String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

  private static Level LEVEL = DEFAULT_LEVEL;
  private static String DATE_TIME_FORMAT = DEFAULT_DATE_TIME_FORMAT;
  private static File FILE = null;
  private static final PrintWriter CONSOLE = new PrintWriter(System.out);

  /**
   * Creates a new {@link Logger} instance with a given class type.
   *
   * @param classType
   * @return
   */
  public static Logger create(Class<?> classType) {
    return new Logger(classType.getName());
  }

  /**
   * @return the current logging level.
   */
  public static Level level() {
    return Logger.LEVEL;
  }

  /**
   * Sets the logging level for the whole application. Default value is DEBUG.
   *
   * @param level
   */
  public static void setLevel(Level level) {
    Logger.LEVEL = level;
  }

  /**
   * @return the current date time format.
   */
  public static String dateTimeFormat() {
    return Logger.DATE_TIME_FORMAT;
  }

  /**
   * Sets the date time format used in logging messages.
   * Default value is "yyyy-MM-dd HH:mm:ss.SSSZ".
   *
   * @param dateTimeFormat
   */
  public static void setDateTimeFormat(String dateTimeFormat) {
    try {
      // Test the format before using it
      new SimpleDateFormat(dateTimeFormat);

      Logger.DATE_TIME_FORMAT = dateTimeFormat;
    } catch (IllegalArgumentException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * @return the current logging file.
   */
  public static File file() {
    return Logger.FILE;
  }

  /**
   * Sets a logging file. Default is null, which means no logging messages
   * will be written in a file.
   *
   * @param file
   */
  public static void setFile(File file) {
    Logger.FILE = file;
  }

  private final String className;

  public Logger(String className) {
    this.className = className;
  }

  private interface MsgWriter {

    void write(PrintWriter printWriter);
  }

  private String createLogRecord(String msg) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
    String dateTime = dateFormat.format(new Date());
    return String.format("%s [%s] %s - %s",
        dateTime, LEVEL.name().charAt(0), className, msg);
  }

  private void log(String msg) {
    String logRecord = createLogRecord(msg);
    log(printWriter -> {
      printWriter.println(logRecord);
      printWriter.flush();
    });
  }

  private void log(String msg, Throwable t) {
    String logRecord = createLogRecord(msg);
    log(printWriter -> {
      printWriter.println(logRecord);
      t.printStackTrace(printWriter);
      printWriter.flush();
    });
  }

  private void log(MsgWriter writer) {
    writer.write(CONSOLE);

    // Log to file is specified
    if (FILE != null) {
      try {
        if (!FILE.exists()) {
          FILE.createNewFile();
        }

        FileWriter fw = new FileWriter(FILE, true);
        BufferedWriter bw = new BufferedWriter(fw);
        try (PrintWriter pw = new PrintWriter(bw)) {
          writer.write(pw);
        }
      } catch (IOException ex) {
        ex.printStackTrace();
        FILE = null;
      }
    }
  }

  /**
   * Log a message at the TRACE level.
   *
   * @param msg
   */
  public void trace(String msg) {
    if (Level.TRACE.ordinal() >= LEVEL.ordinal()) {
      log(msg);
    }
  }

  /**
   * Log a message at the TRACE level according to the specified
   * format and argument.
   *
   * @param format
   * @param arg
   */
  public void trace(String format, Object arg) {
    trace(String.format(format, arg));
  }

  /**
   * Log a message at the TRACE level according to the specified
   * format and arguments.
   *
   * @param format
   * @param arguments
   */
  public void trace(String format, Object... arguments) {
    trace(String.format(format, arguments));
  }

  /**
   * Log a message at the TRACE level according to the specified
   * format and arguments.
   *
   * @param format
   * @param arg1
   * @param arg2
   */
  public void trace(String format, Object arg1, Object arg2) {
    trace(String.format(format, arg1, arg2));
  }

  /**
   * Log an exception (throwable) at the TRACE level with an
   * accompanying message.
   *
   * @param msg
   * @param t
   */
  public void trace(String msg, Throwable t) {
    if (Level.TRACE.ordinal() >= LEVEL.ordinal()) {
      log(msg, t);
    }
  }

  /**
   * Log a message at the DEBUG level.
   *
   * @param msg
   */
  public void debug(String msg) {
    if (Level.DEBUG.ordinal() >= LEVEL.ordinal()) {
      log(msg);
    }
  }

  /**
   * Log a message at the DEBUG level according to the specified
   * format and argument.
   *
   * @param format
   * @param arg
   */
  public void debug(String format, Object arg) {
    debug(String.format(format, arg));
  }

  /**
   * Log a message at the DEBUG level according to the specified
   * format and arguments.
   *
   * @param format
   * @param arguments
   */
  public void debug(String format, Object... arguments) {
    debug(String.format(format, arguments));
  }

  /**
   * Log a message at the DEBUG level according to the specified
   * format and arguments.
   *
   * @param format
   * @param arg1
   * @param arg2
   */
  public void debug(String format, Object arg1, Object arg2) {
    debug(String.format(format, arg1, arg2));
  }

  /**
   * Log an exception (throwable) at the DEBUG level with an
   * accompanying message.
   *
   * @param msg
   * @param t
   */
  public void debug(String msg, Throwable t) {
    if (Level.DEBUG.ordinal() >= LEVEL.ordinal()) {
      log(msg, t);
    }
  }

  /**
   * Log a message at the INFO level.
   *
   * @param msg
   */
  public void info(String msg) {
    if (Level.INFO.ordinal() >= LEVEL.ordinal()) {
      log(msg);
    }
  }

  /**
   * Log a message at the INFO level according to the specified
   * format and argument.
   *
   * @param format
   * @param arg
   */
  public void info(String format, Object arg) {
    info(String.format(format, arg));
  }

  /**
   * Log a message at the INFO level according to the specified
   * format and arguments.
   *
   * @param format
   * @param arguments
   */
  public void info(String format, Object... arguments) {
    info(String.format(format, arguments));
  }

  /**
   * Log a message at the INFO level according to the specified
   * format and arguments.
   *
   * @param format
   * @param arg1
   * @param arg2
   */
  public void info(String format, Object arg1, Object arg2) {
    info(String.format(format, arg1, arg2));
  }

  /**
   * Log an exception (throwable) at the INFO level with an
   * accompanying message.
   *
   * @param msg
   * @param t
   */
  public void info(String msg, Throwable t) {
    if (Level.INFO.ordinal() >= LEVEL.ordinal()) {
      log(msg, t);
    }
  }

  /**
   * Log a message at the WARN level.
   *
   * @param msg
   */
  public void warn(String msg) {
    if (Level.WARN.ordinal() >= LEVEL.ordinal()) {
      log(msg);
    }
  }

  /**
   * Log a message at the WARN level according to the specified
   * format and argument.
   *
   * @param format
   * @param arg
   */
  public void warn(String format, Object arg) {
    warn(String.format(format, arg));
  }

  /**
   * Log a message at the WARN level according to the specified
   * format and arguments.
   *
   * @param format
   * @param arguments
   */
  public void warn(String format, Object... arguments) {
    warn(String.format(format, arguments));
  }

  /**
   * Log a message at the WARN level according to the specified
   * format and arguments.
   *
   * @param format
   * @param arg1
   * @param arg2
   */
  public void warn(String format, Object arg1, Object arg2) {
    warn(String.format(format, arg1, arg2));
  }

  /**
   * Log an exception (throwable) at the WARN with an
   * accompanying message.
   *
   * @param msg
   * @param t
   */
  public void warn(String msg, Throwable t) {
    if (Level.WARN.ordinal() >= LEVEL.ordinal()) {
      log(msg, t);
    }
  }

  /**
   * Log a message at the ERROR level.
   *
   * @param msg
   */
  public void error(String msg) {
    if (Level.ERROR.ordinal() >= LEVEL.ordinal()) {
      log(msg);
    }
  }

  /**
   * Log a message at the ERROR level according to the specified
   * format and argument.
   *
   * @param format
   * @param arg
   */
  public void error(String format, Object arg) {
    error(String.format(format, arg));
  }

  /**
   * Log a message at the ERROR level according to the specified
   * format and arguments.
   *
   * @param format
   * @param arguments
   */
  public void error(String format, Object... arguments) {
    error(String.format(format, arguments));
  }

  /**
   * Log a message at the ERROR level according to the specified
   * format and arguments.
   *
   * @param format
   * @param arg1
   * @param arg2
   */
  public void error(String format, Object arg1, Object arg2) {
    error(String.format(format, arg1, arg2));
  }

  /**
   * Log an exception (throwable) at the ERROR level with an
   * accompanying message.
   *
   * @param msg
   * @param t
   */
  public void error(String msg, Throwable t) {
    if (Level.ERROR.ordinal() >= LEVEL.ordinal()) {
      log(msg, t);
    }
  }
}
