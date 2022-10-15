// Реализовать, с учетом ооп подхода, приложение
// Для проведения исследований с генеалогическим древом.
// Идея: описать некоторое количество компонент, например:
// модель человека
// компонента хранения связей и отношений между людьми: родитель, ребёнок - классика,
//  но можно подумать и про отношение, брат, свекровь, сестра и т. д.
// компонент для проведения исследований
// дополнительные компоненты, например отвечающие за вывод данных в консоль, загрузку 
// и сохранения в файл, получение\построение отдельных моделей человека
// Под “проведением исследования” можно понимать получение всех детей выбранного человека.
// * на первом этапе сложно применять сразу все концепты ООП, упор делается на инкапсуляцию.
//  Если получится продумать иерархию каких-то компонент - здорово. После первой лекции, они
//   не знают про абстракцию и интерфейсы.
package ru.gb;

import java.util.ArrayList;

public class HW_1 {
  public static void main(String[] args) {
    Person irina = new Person("ИРИНА");
    Person vasya = new Person("ВАСЯ");
    Person masha = new Person("МАША");
    Person julia = new Person("ЮЛЯ");
    Person ivan = new Person("ИВАН");
    Person olga = new Person("ОЛЬГА");
    Person petr = new Person("ПЕТР");

    GeoTree gt = new GeoTree();
    gt.append(irina, vasya);
    gt.append(irina, masha);
    gt.append(vasya, julia);
    gt.append(vasya, ivan);
    gt.append(olga, petr);

    System.out.println(irina.getFullName() + " родитель для " + new Research(gt).spend(irina, Relationship.parent));
    System.out.println(vasya.getFullName() + " родитель для " + new Research(gt).spend(vasya, Relationship.parent));
    System.out.println( new Research(gt).spend(vasya, Relationship.children)+ " дети для " + vasya.getFullName());
    System.out.println(new Research(gt).spend(julia, Relationship.children)+ " дети для " + julia.getFullName());
    System.out.println(olga.getFullName() + " родитель для " + new Research(gt).spend(olga, Relationship.parent));
  }
}

enum Relationship {
  parent,
  children

}

class Person {
  private String fullName;

  public String getFullName() {
    return fullName;
  }

  public Person(String fullName) {
    this.fullName = fullName;
  }

  @Override
  public String toString() {
    return String.format("(%s)", this.fullName);
  }
}

class Node {
  public Node(Person p1, Relationship re, Person p2) {
    this.p1 = p1;
    this.re = re;
    this.p2 = p2;
  }

  Person p1;
  Relationship re;
  Person p2;

  @Override
  public String toString() {
    return String.format("<%s %s %s>", p1, re, p2);
  }
}

class GeoTree {
  private ArrayList<Node> tree = new ArrayList<>();

  public ArrayList<Node> getTree() {
    return tree;
  }

  public void append(Person parent, Person children) {
    tree.add(new Node(parent, Relationship.parent, children));
    tree.add(new Node(children, Relationship.children, parent));

  }
}

class Research {
  ArrayList<Node> tree;

  public Research(GeoTree geoTree) {
    tree = geoTree.getTree();
  }

  public ArrayList<Person> spend(Person p, Relationship re) {
    ArrayList<Person> result = new ArrayList<>();
    for (Node t : tree) {
      if (t.p1.getFullName() == p.getFullName() && t.re == re) {
        result.add(t.p2);
      }
    }
    return result;
  }
}
