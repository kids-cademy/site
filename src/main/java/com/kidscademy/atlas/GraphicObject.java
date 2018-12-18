package com.kidscademy.atlas;

@SuppressWarnings("unused")
public class GraphicObject
{
  private int id;
  private String name;
  private String display;
  private String iconPath;

  public GraphicObject()
  {
  }

  public GraphicObject(int id, String name, String display, String iconPath)
  {
    super();
    this.id = id;
    this.name = name;
    this.display = display;
    this.iconPath = iconPath;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if(this == obj) return true;
    if(obj == null) return false;
    if(getClass() != obj.getClass()) return false;
    GraphicObject other = (GraphicObject)obj;
    if(id != other.id) return false;
    return true;
  }

  @Override
  public String toString()
  {
    return display;
  }
}
