package com.kidscademy.model;

import java.io.IOException;

import js.tiny.container.core.Factory;
import js.tiny.container.servlet.RequestContext;
import js.util.Strings;

public class BuildException extends IOException
{
  /**
   * Java serialization version.
   */
  private static final long serialVersionUID = -4469058426561464601L;

  public BuildException(String message, Object... args)
  {
    super(message(message, args));
  }

  private static String message(String message, Object... args)
  {
    RequestContext context = Factory.getInstance(RequestContext.class);
    return Strings.format("Fail to process files archive uploaded from |%s|. %s", context.getRemoteHost(), Strings.format(message, args));
  }
}
