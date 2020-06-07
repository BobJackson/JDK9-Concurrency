package com.wangyousong.concurrency.ch11.recipe12;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.RecursiveAction;

public class PersonMapTask extends RecursiveAction {

    /**
     *
     */
    private static final long serialVersionUID = 6687678520745563790L;

    private final List<Person> persons;
    private final ConcurrentHashMap<String, ConcurrentLinkedDeque<Person>> personMap;

    public PersonMapTask(List<Person> persons, ConcurrentHashMap<String, ConcurrentLinkedDeque<Person>> personMap) {
        this.persons = persons;
        this.personMap = personMap;
    }

    @Override
    protected void compute() {

        if (persons.size() < 1000) {

            for (Person person : persons) {
                ConcurrentLinkedDeque<Person> personList = personMap.computeIfAbsent(person.getFirstName(), name -> new ConcurrentLinkedDeque<>());
                personList.add(person);
            }
            return;
        }

        PersonMapTask child1, child2;

        child1 = new PersonMapTask(persons.subList(0, persons.size() / 2), personMap);
        child2 = new PersonMapTask(persons.subList(persons.size() / 2, persons.size()), personMap);

        invokeAll(child1, child2);

    }

}
