package com.kidscademy;

import com.kidscademy.atlas.Instrument;

import js.annotation.Public;
import js.annotation.Service;

@Service
@Public
public interface AtlasController
{
  Instrument getInstrument(int instrumentId);
}
