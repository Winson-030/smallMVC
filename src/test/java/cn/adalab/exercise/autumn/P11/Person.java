package cn.adalab.exercise.autumn.P11;

import java.util.Objects;

public class Person {
  private String name;
  private String country;

  public Person() {
  }

  public String getName() {
    return name;
  }

  public String getCountry() {
    return country;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return Objects.equals(name, person.name) && Objects.equals(country, person.country);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, country);
  }
}
