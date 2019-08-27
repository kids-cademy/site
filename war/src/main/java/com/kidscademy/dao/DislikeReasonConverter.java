package com.kidscademy.dao;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.kidscademy.model.DislikeReason;

@Converter(autoApply = true)
public class DislikeReasonConverter implements AttributeConverter<DislikeReason, String>
{
  @Override
  public String convertToDatabaseColumn(DislikeReason reason)
  {
    return reason.name();
  }

  @Override
  public DislikeReason convertToEntityAttribute(String value)
  {
    return DislikeReason.valueOf(value);
  }
}
