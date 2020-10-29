package io.documentnode.minilog;


import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author jake
 */
public class LoggerTest {

  private static final Logger log = Logger.create(LoggerTest.class);

  @BeforeMethod
  void resetLogger() {
    Logger.setLevel(Logger.DEFAULT_LEVEL);
    Logger.setDateTimeFormat(Logger.DEFAULT_DATE_TIME_FORMAT);
    Logger.setFile(null);
  }

  @Test
  void testCreate() {
    // When
    Logger log = Logger.create(LoggerTest.class);

    // Then
    assertThat(log).isNotNull();
  }

  @Test
  void testSetLevel() {
    // When
    Logger.setLevel(Level.ERROR);

    // Then
    assertThat(Logger.level()).isEqualByComparingTo(Level.ERROR);
  }

  @Test
  void testSetDateTimeFormat() {
    // Given
    String dateTimeFormat = "yyyyMMdd_HHmmss";

    // When
    Logger.setDateTimeFormat(dateTimeFormat);

    // Then
    assertThat(Logger.dateTimeFormat()).isEqualTo(dateTimeFormat);
  }

  @Test
  void testSetDateTimeFormat_shouldUseDefault() {
    // Given
    String dateTimeFormat = UUID.randomUUID().toString();

    // When
    Logger.setDateTimeFormat(dateTimeFormat);

    // Then
    assertThat(Logger.dateTimeFormat())
        .isEqualTo(Logger.DEFAULT_DATE_TIME_FORMAT);
  }

  @Test
  void testSetFile() throws IOException {
    // Given
    File file = File.createTempFile("minilog", ".log");

    // When
    Logger.setFile(file);

    // Then
    assertThat(Logger.file()).isEqualTo(file);
  }

  @Test
  void testTrace() throws IOException {
    // Given
    Logger.setLevel(Level.TRACE);
    File file = File.createTempFile("minilog", ".log");
    Logger.setFile(file);
    Logger log = Logger.create(LoggerTest.class);
    String msg = UUID.randomUUID().toString();

    // When
    log.trace(msg);

    // Then
    String logFileContent = Files.readString(file.toPath());
    assertThat(logFileContent).contains(msg);
  }

  @Test
  void testTrace_shouldNotLog() throws IOException {
    // Given
    Logger.setLevel(Level.DEBUG);
    File file = File.createTempFile("minilog", ".log");
    Logger.setFile(file);
    Logger log = Logger.create(LoggerTest.class);
    String msg = UUID.randomUUID().toString();

    // When
    log.trace(msg);

    // Then
    String logFileContent = Files.readString(file.toPath());
    assertThat(logFileContent).doesNotContain(msg);
  }

  @Test
  void testDebug() throws IOException {
    // Given
    Logger.setLevel(Level.DEBUG);
    File file = File.createTempFile("minilog", ".log");
    Logger.setFile(file);
    Logger log = Logger.create(LoggerTest.class);
    String msg = UUID.randomUUID().toString();

    // When
    log.debug(msg);

    // Then
    String logFileContent = Files.readString(file.toPath());
    assertThat(logFileContent).contains(msg);
  }

  @Test
  void testDebug_shouldNotLog() throws IOException {
    // Given
    Logger.setLevel(Level.INFO);
    File file = File.createTempFile("minilog", ".log");
    Logger.setFile(file);
    Logger log = Logger.create(LoggerTest.class);
    String msg = UUID.randomUUID().toString();

    // When
    log.debug(msg);

    // Then
    String logFileContent = Files.readString(file.toPath());
    assertThat(logFileContent).doesNotContain(msg);
  }

  @Test
  void testInfo() throws IOException {
    // Given
    Logger.setLevel(Level.INFO);
    File file = File.createTempFile("minilog", ".log");
    Logger.setFile(file);
    Logger log = Logger.create(LoggerTest.class);
    String msg = UUID.randomUUID().toString();

    // When
    log.info(msg);

    // Then
    String logFileContent = Files.readString(file.toPath());
    assertThat(logFileContent).contains(msg);
  }

  @Test
  void testInfo_shouldNotLog() throws IOException {
    // Given
    Logger.setLevel(Level.WARN);
    File file = File.createTempFile("minilog", ".log");
    Logger.setFile(file);
    Logger log = Logger.create(LoggerTest.class);
    String msg = UUID.randomUUID().toString();

    // When
    log.info(msg);

    // Then
    String logFileContent = Files.readString(file.toPath());
    assertThat(logFileContent).doesNotContain(msg);
  }

  @Test
  void testWarn() throws IOException {
    // Given
    Logger.setLevel(Level.WARN);
    File file = File.createTempFile("minilog", ".log");
    Logger.setFile(file);
    Logger log = Logger.create(LoggerTest.class);
    String msg = UUID.randomUUID().toString();

    // When
    log.warn(msg);

    // Then
    String logFileContent = Files.readString(file.toPath());
    assertThat(logFileContent).contains(msg);
  }

  @Test
  void testWarn_shouldNotLog() throws IOException {
    // Given
    Logger.setLevel(Level.ERROR);
    File file = File.createTempFile("minilog", ".log");
    Logger.setFile(file);
    Logger log = Logger.create(LoggerTest.class);
    String msg = UUID.randomUUID().toString();

    // When
    log.warn(msg);

    // Then
    String logFileContent = Files.readString(file.toPath());
    assertThat(logFileContent).doesNotContain(msg);
  }

  @Test
  void testError() throws IOException {
    // Given
    Logger.setLevel(Level.ERROR);
    File file = File.createTempFile("minilog", ".log");
    Logger.setFile(file);
    Logger log = Logger.create(LoggerTest.class);
    String msg = UUID.randomUUID().toString();

    // When
    log.error(msg);

    // Then
    String logFileContent = Files.readString(file.toPath());
    assertThat(logFileContent).contains(msg);
  }
}
