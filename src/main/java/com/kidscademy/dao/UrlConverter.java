package com.kidscademy.dao;

import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UrlConverter implements AttributeConverter<URL, String>
{
  @Override
  public String convertToDatabaseColumn(URL url)
  {
    return url.toExternalForm();
  }

  @Override
  public URL convertToEntityAttribute(String value)
  {
    try {
      return new URL(value);
    }
    catch(MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }
}
