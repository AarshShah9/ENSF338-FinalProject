package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.*;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import main.java.mylib.datastructures.linear.CDLL;
import main.java.mylib.datastructures.nodes.DNode;

public class CDLLTest {
    private final ArrayList<Integer> DUMMY_DATA = new ArrayList<Integer>(
            Arrays.asList(64, 94, 58, 1, 69, 82, 93, 65, 76, 96));

    @Test
    public void testConstructorNoArg() {
        CDLL<Integer> list = new CDLL<>();

        assertNull("Head should be null", list.getHead());
        assertNull("Tail should be null", list.getTail());
        assertEquals("Size should be 0", 0, list.getSize());
        assertFalse("Sorted should be false", list.isSorted());
    }

    @Test
    public void testConstructorWithData() {
        CDLL<Integer> list = new CDLL<>(new DNode<Integer>(1));

        assertEquals("Head should be 1", 1, (int) list.getHead().getValue());
        assertEquals("Tail should be 1", 1, (int) list.getTail().getValue());
        assertEquals("The tail should point to the head", list.getHead(), list.getTail().getNext());
        assertEquals("Size should be 1", 1, list.getSize());
        assertFalse("Sorted should be false", list.isSorted());
    }

    @Test
    public void testGetHead() {
        CDLL<Integer> list1 = new CDLL<Integer>();
        assertNull("Head should be null", list1.getHead());

        CDLL<Integer> list2 = new CDLL<Integer>(new DNode<Integer>(1));
        assertEquals("Head should be 1", 1, (int) list2.getHead().getValue());

    }

    @Test
    public void testGetTail() {
        CDLL<Integer> list1 = new CDLL<Integer>();
        assertNull("Tail should be null", list1.getTail());

        CDLL<Integer> list2 = new CDLL<Integer>(new DNode<Integer>(1));
        assertEquals("Tail should be 1", 1, (int) list2.getTail().getValue());
    }

    @Test
    public void testGetSize() {
        CDLL<Integer> list1 = new CDLL<Integer>();
        assertEquals("Size should be 0", 0, list1.getSize());

        CDLL<Integer> list2 = new CDLL<Integer>(new DNode<Integer>(1));
        assertEquals("Size should be 1", 1, list2.getSize());

        CDLL<Integer> list3 = new CDLL<Integer>();
        for (int i = 0; i < DUMMY_DATA.size(); i++) {
            list3.insertHead(new DNode<Integer>(DUMMY_DATA.get(i)));
        }
        list3.insertTail(new DNode<Integer>(1));
        list3.insertTail(new DNode<Integer>(2));
        assertEquals("Size should be 12", 12, list3.getSize());

    }

    @Test
    public void testIsSorted() {
        CDLL<Integer> list1 = new CDLL<Integer>();
        assertFalse("List should not be sorted", list1.isSorted());
    }

    @Test
    public void insertInsertHead() {
        CDLL<Integer> list = new CDLL<Integer>();

        for (int i = 0; i < DUMMY_DATA.size(); i++) {
            list.insertHead(new DNode<Integer>(DUMMY_DATA.get(i)));
        }

        assertEquals("Head should be 96", 96, (int) list.getHead().getValue());
        assertEquals("Tail should be 64", 64, (int) list.getTail().getValue());
        assertEquals("Size should be 10", 10, list.getSize());
        assertFalse("List should not be sorted", list.isSorted());

    }

    @Test
    public void insertInsertTail() {
        CDLL<Integer> list = new CDLL<Integer>();

        for (int i = 0; i < DUMMY_DATA.size(); i++) {
            list.insertTail(new DNode<Integer>(DUMMY_DATA.get(i)));
        }

        assertEquals("Head should be 64", 64, (int) list.getHead().getValue());
        assertEquals("Tail should be 96", 96, (int) list.getTail().getValue());
        assertEquals("Size should be 10", 10, list.getSize());
        assertFalse("List should not be sorted", list.isSorted());
    }

    @Test
    public void testInsertAtPosition() {
        ArrayList<Integer> expected = new ArrayList<Integer>(
                Arrays.asList(1, 2, 4, 3));

        CDLL<Integer> list = new CDLL<Integer>(new DNode<Integer>(1));
        list.insert(new DNode<Integer>(2), 1);
        list.insert(new DNode<Integer>(3), 2);
        list.insert(new DNode<Integer>(4), 2);

        boolean valid = true;
        int i = 0;
        for (DNode<Integer> node = list.getHead(); node != list.getTail(); node = node.getNext(), i++) {
            if (node.getValue() != expected.get(i)) {
                valid = false;
                break;
            }
        }

        assertTrue("Insert at position is not working as it should", valid);
        assertEquals("Size should be 4", 4, list.getSize());
        assertFalse("List should not be sorted", list.isSorted());

    }

    @Test
    public void testInsertAtInvalidPosition() {
        CDLL<Integer> list = new CDLL<Integer>(new DNode<Integer>(1));
        boolean exceptionCaught = false;
        try {
            list.insert(new DNode<Integer>(2), 10);
            fail("Insert at invalid position should throw an exception");
        } catch (IndexOutOfBoundsException e) {
            exceptionCaught = true;
        } catch (Exception e) {
            fail("Insert at invalid position should throw an IndexOutOfBoundsException");
        }

        assertTrue("Insert at invalid position should throw an IndexOutOfBoundsException",
                exceptionCaught);

    }

    @Test
    public void testSortedInsert() {
        ArrayList<Integer> expected = new ArrayList<Integer>(
                Arrays.asList(0, 1, 2, 3, 4));

        CDLL<Integer> list = new CDLL<Integer>(new DNode<Integer>(1));
        list.insertHead(new DNode<Integer>(3));
        list.insertTail(new DNode<Integer>(4));
        list.sortedInsert(new DNode<Integer>(2));
        list.sortedInsert(new DNode<Integer>(0));

        boolean valid = true;
        int i = 0;
        for (DNode<Integer> node = list.getHead(); node != list.getTail(); node = node.getNext(), i++) {
            if (node.getValue() != expected.get(i)) {
                valid = false;
                break;
            }
        }

        assertTrue("Sorted insert is not working as it should", valid);
        assertEquals("The head should be 0", 0, (int) list.getHead().getValue());
        assertEquals("The tail should be 4", 4, (int) list.getTail().getValue());
        assertEquals("Size should be 5", 5, list.getSize());
        assertTrue("List should be sorted", list.isSorted());

        DNode<Integer> node = list.getHead();
        for (; node != list.getTail(); node = node.getNext()) {
            if (node.getNext() == null || node.getNext().getPrevious() != node) {
                valid = false;
                break;
            }
        }

        assertTrue("Doubly linked list is not doubly linked", valid);
        assertEquals("List should be circular", list.getHead(), list.getTail().getNext());
    }

    @Test
    public void testSearchWithValidNode() {
        CDLL<Integer> list = new CDLL<Integer>(new DNode<Integer>(1));
        DNode<Integer> nodeToCheck = new DNode<Integer>(4);
        list.insert(new DNode<Integer>(2), 1);
        list.insert(nodeToCheck, 2);

        DNode<Integer> node = list.search(nodeToCheck);

        assertNotNull(node);
        assertEquals("Node data should be 4", 4, (int) node.getValue());
    }

    @Test
    public void testSearchWithInvalidNode() {
        CDLL<Integer> list = new CDLL<Integer>(new DNode<Integer>(1));
        DNode<Integer> nodeToCheck = new DNode<Integer>(4);

        list.insert(new DNode<Integer>(2), 1);
        // nodeToCheck is not in the list

        DNode<Integer> node = list.search(nodeToCheck);

        assertNull("Search should return null pointer since node doesn't exist", node);
    }

    @Test
    public void testDeleteHead() {
        CDLL<Integer> list = new CDLL<Integer>(new DNode<Integer>(1));
        list.insertHead(new DNode<Integer>(2));

        list.deleteHead();

        assertEquals("Head should be 1", 1, (int) list.getHead().getValue());
    }

    @Test
    public void testDeleteTail() {
        CDLL<Integer> list = new CDLL<Integer>(new DNode<Integer>(1));
        list.insertHead(new DNode<Integer>(2));

        list.deleteTail();

        assertEquals("Tail should be 2", 2, (int) list.getTail().getValue());
    }

    @Test
    public void testDeleteNode() {
        CDLL<Integer> list = new CDLL<Integer>(new DNode<Integer>(1));
        DNode<Integer> nodeToDelete = new DNode<Integer>(2);
        list.insert(nodeToDelete, 1);

        list.delete(nodeToDelete);

        assertNull("Node should be deleted", list.search(nodeToDelete));
    }

    @Test
    public void testSort() {
        ArrayList<Integer> expected = new ArrayList<Integer>(
                Arrays.asList(0, 1, 2, 3, 4, 5));

        CDLL<Integer> list = new CDLL<Integer>(new DNode<Integer>(1));
        list.insert(new DNode<Integer>(3), 1);
        list.insert(new DNode<Integer>(4), 2);
        list.insertTail(new DNode<Integer>(0));

        list.insert(new DNode<Integer>(2), 1);
        list.insertHead(new DNode<Integer>(5));

        list.sort();

        boolean valid = true;
        int i = 0;
        DNode<Integer> node = list.getHead();
        do {
            if (node.getValue() != expected.get(i)) {
                valid = false;
                break;
            }
            i++;
            node = node.getNext();
        } while (node != list.getHead() && i < expected.size());

        assertTrue("Sort is not working as it should", valid);
        assertTrue("List should be sorted", list.isSorted());
        assertTrue("Head should be 0", 0 == list.getHead().getValue());
        assertTrue("Tail should be 5", 5 == list.getTail().getValue());
        assertTrue("Size should be 6", 6 == list.getSize());

        DNode<Integer> node2 = list.getHead();
        for (; node2 != list.getTail(); node2 = node2.getNext()) {
            if (node2.getNext() == null || node2.getNext().getPrevious() != node2) {
                valid = false;
                break;
            }
        }

        assertTrue("Doubly linked list is not doubly linked", valid);
        assertEquals("List should be circular", list.getHead(), list.getTail().getNext());
    }

    @Test
    public void testClear() {
        CDLL<Integer> list = new CDLL<Integer>(new DNode<Integer>(1));
        list.insert(new DNode<Integer>(2), 1);
        list.insert(new DNode<Integer>(3), 2);
        list.insert(new DNode<Integer>(4), 2);

        list.clear();

        assertNull("Head should be null", list.getHead());
        assertNull("Tail should be null", list.getTail());
        assertEquals("Size should be 0", 0, list.getSize());
        assertFalse("List should not be sorted", list.isSorted());
    }

    private final ByteArrayOutputStream outputCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputCaptor));
    }

    @After
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    public void testPrint() {
        CDLL<Integer> list = new CDLL<Integer>(new DNode<Integer>(1));
        list.insert(new DNode<Integer>(2), 1);
        list.insert(new DNode<Integer>(3), 2);
        list.insert(new DNode<Integer>(4), 2);

        list.print();
        assertEquals(
                "Circularly Doubly Linked List Information: \nList Length: 4\nSort Status: false\nList Values: \n1 2 4 3 ",
                outputCaptor.toString());
    }

    @Test
    public void testCircularlyDoublyLinkedList() {
        CDLL<Integer> list = new CDLL<Integer>(new DNode<Integer>(1));
        boolean valid = true;
        list.insert(new DNode<Integer>(2), 1);
        list.insertHead(new DNode<Integer>(3));
        list.insertTail(new DNode<Integer>(20));

        DNode<Integer> node = list.getHead();
        for (; node != list.getTail(); node = node.getNext()) {
            if (node.getNext() == null || node.getNext().getPrevious() != node) {
                valid = false;
                break;
            }
        }

        assertTrue("Doubly linked list is not doubly linked", valid);
        assertEquals("List should be circular", list.getHead(), list.getTail().getNext());
    }

}
