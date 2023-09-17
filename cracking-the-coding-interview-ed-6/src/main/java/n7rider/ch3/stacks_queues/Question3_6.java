package n7rider.ch3.stacks_queues;

import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class Question3_6 {

    /**
     * 3.6
     * Animal Shelter: An animal shelter, which holds only dogs and cats, operates on a strictly"first in, first
     * out" basis. People must adopt either the "oldest" (based on arrival time) of all animals at the shelter,
     * or they can select whether they would prefer a dog or a cat (and will receive the oldest animal of
     * that type). They cannot select which specific animal they would like. Create the data structures to
     * maintain this system and implement operations such as enqueue, dequeueAny, dequeueDog,
     * and dequeueCat. You may use the built-in Linked list data structure.
     * 
     * ---
     * Post-run observations:
     * If you are using LinkedList you don't even need to create a queue. Just call pollLast(), add() etc from LL directly.
     *
     * After comparing with solution:
     * The book went with my initial idea of storing timestamp. It's easier but also has an overhead for each node. A tradeoff is to be decided.
     * If we consider 10k elements, adding timestamp field is faster, but also adds 10k*~32bytes=~320kb.
     *
     * To save space, the book uses inheritance rather than having enum saved in the queue. Could have done that.
     *
     *
     */

    enum AnimalType {
        CAT,
        DOG
    }

    static class Animal {
        AnimalType type;
        String name;

        Animal(String name, AnimalType type) {
            this.name = name;
            this.type = type;
        }
    }

    static class AnimalShelter {
        LinkedList<Animal> animalQueue = new LinkedList<>();

        Animal dequeueAny() {
            return animalQueue.pollLast();
        }

        Animal dequeueAnimalType(AnimalType animalType) {
            // Since LL is allowed, I'm using this inbuilt iterator. Otherwise, we poll through manually from the last
            // Popping and adding like stack is not right because the insert order gets changed
            var iterator = animalQueue.descendingIterator();

            while(iterator.hasNext()) {
                Animal lastAnimal = iterator.next();
                if(animalType.equals(lastAnimal.type)) {
                    animalQueue.remove(lastAnimal);
                    return lastAnimal;
                }
            }
            return null;
        }

        Animal dequeueDog() {
            return dequeueAnimalType(AnimalType.DOG);
        }

        Animal dequeueCat() {
            return dequeueAnimalType(AnimalType.CAT);
        }

        void enqueue(Animal animal) {
            animalQueue.addFirst(animal);
        }
    }

    public static void main(String[] args) throws Exception {
        AnimalShelter emptyShelter = new AnimalShelter();
        assertNull(emptyShelter.dequeueAny());

        AnimalShelter workingShelter = new AnimalShelter();
        Animal cat1 = new Animal("cat1", AnimalType.CAT);
        Animal dog1 = new Animal("dog1", AnimalType.DOG);
        Animal cat2 = new Animal("cat2", AnimalType.CAT);
        Animal dog2 = new Animal("dog2", AnimalType.DOG);
        Animal cat3 = new Animal("cat3", AnimalType.CAT);
        Animal dog3 = new Animal("dog3", AnimalType.DOG);
        workingShelter.enqueue(cat1);
        workingShelter.enqueue(dog1);
        workingShelter.enqueue(cat2);
        workingShelter.enqueue(dog2);
        workingShelter.enqueue(cat3);
        workingShelter.enqueue(dog3);

        assertEquals("cat1", workingShelter.dequeueAny().name);
        assertEquals("cat2", workingShelter.dequeueCat().name);
        assertEquals("dog1", workingShelter.dequeueDog().name);
        assertEquals("dog2", workingShelter.dequeueAny().name);


    }
}

/**
 * Use LinkedList to implement queue - Using custom Node is simpler
 *
 * Queue - 2 approaches | Queue for each animal or a single queue
 * Former:
 * - Simple to maintain
 * - Dequeue Any needs to compare arrival item
 *
 * Latter:
 * - Slightly more memory (for the queue overhead)
 * - Dequeue is complex
 *
 * Domain:
 * enum Animal - Dog | Cat
 * Queue - head:Node, enqueue, dequeue
 * AnimalShelter:
 * fields - DogQueue, CatQueue
 * methods - dequeueAny, enqueue
 *
 * Dequeue Any logic:
 * compare both queues' head
 * dequeue the smaller one
 *
 * DequeueDog or DequeueCat logic (if using single queue):
 * keep going down the list until iter.next == askedAnimal
 * Connect iter to iter.next.next (This is not essentially a queue - another reason to not go with this approach)
 *
 * Design update:
 * There is no need for arrivalTime because the queue is designed to do it, without arrival time, the best way is to create a single queue
 * If we add arrival time, it is an overhead added to each node
 *
 *
 */