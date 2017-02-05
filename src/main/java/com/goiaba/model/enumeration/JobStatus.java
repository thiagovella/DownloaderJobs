package com.goiaba.model.enumeration;

public enum JobStatus {
  ACTIVE("Active"),
  STOPED("Stoped");
  
  private final String value;

  /**
   * @param valueType
   */
  private JobStatus(final String value) {
      this.value = value;
  }

  @Override
  public String toString() {
      return value;
  }
}
