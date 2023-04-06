package main.java.mylib.datastructures.linear;

import main.java.mylib.datastructures.nodes.SNode;

public class SLL<T extends Comparable<T>> {

    protected SNode<T> head;
    protected SNode<T> tail;
    protected int size;
    protected boolean sorted;

    public SLL() {
        head = null;
        tail = null;
        size = 0;
        sorted = false;
    }

    public SLL(SNode<T> node) {
        head = node;
        tail = head;
        size = 1;
        sorted = false;

    }

    public SNode<T> getHead() {
        return this.head;
    }

    public SNode<T> getTail() {
        return this.tail;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isSorted() {
        return this.sorted;
    }

    public void insertHead(SNode<T> node) {
        if (head == null) {
            head = node;
            tail = head;
            size++;
            sorted = false;

        } else {
            node.setNext(head);
            head = node;
            size++;
            sorted = false;
        }

    }

    public void insertTail(SNode<T> node) {
        if (head == null) {
            head = node;
            tail = head;
            size++;
            sorted = false;
        } else {
            tail.setNext(node);
            tail = node;
            size++;
            sorted = false;
        }
    }

    public void insert(SNode<T> node, int position) {

        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Position is out of bounds");
        } else if (position == 0) {
            insertHead(node);
        } else if (position == size) {
            insertTail(node);
        } else {
            SNode<T> current = head;
            for (int i = 0; i < position - 1; i++) {
                current = current.getNext();
            }
            node.setNext(current.getNext());
            current.setNext(node);
            size++;
            sorted = false;
        }
    }

    // TODO SHOULD THIS CHECK FOR REFERENCES OR VALUES?
    public SNode<T> search(SNode<T> node) {
        if (head == null) {
            return null;
        }
        if (head == node) {
            return head;
        }
        if (tail == node) {
            return tail;
        }

        SNode<T> current = head;
        while (current != null) {
            if (current == node) {
                return current;
            }
            current = current.getNext();
        }
        return null;

    }

    public void deleteHead() {
        if (head != null) {
            head = head.getNext();
            size--;
        }
    }

    public void deleteTail() {
        if (head != null) {
            if (head.getNext() == null) {
                head = null;
                tail = null;
                size--;
            } else {
                SNode<T> current = head;
                while (current.getNext().getNext() != null) {
                    current = current.getNext();
                }
                current.setNext(null);
                tail = current;
                size--;
            }
        }
    }

    public void delete(SNode<T> node) {
        if (head != null) {
            if (head == node) {
                deleteHead();
            } else if (tail == node) {
                deleteTail();
            } else {
                SNode<T> current = head;
                while (current.getNext() != node) {
                    current = current.getNext();
                }
                current.setNext(node.getNext());
                size--;
            }
        }
    }

    public void sortedInsert(SNode<T> node) {
        if (!sorted) {
            sort();
        }
        if (head == null) {
            head = node;
            tail = head;
        } else if (((Comparable<T>) head.getValue()).compareTo(node.getValue()) > 0) {
            node.setNext(head);
            head = node;
        } else if (((Comparable<T>) tail.getValue()).compareTo(node.getValue()) < 0) {
            tail.setNext(node);
            tail = node;
        } else {
            SNode<T> current = head;
            while (current.getNext() != null
                    && ((Comparable<T>) current.getNext().getValue()).compareTo(node.getValue()) < 0) {
                current = current.getNext();
            }
            node.setNext(current.getNext());
            current.setNext(node);
        }
        size++;
        sorted = true;
    }
    // https://www.geeksforgeeks.org/insertion-sort-for-singly-linked-list/

    // public void sort() {
    // if (head == null || head.getNext() == null) {
    // sorted = true;
    // return; // List is already sorted
    // }
    // SNode<T> current = head.getNext();
    // while (current != null) {
    // SNode<T> temp = current;
    // while (temp != null
    // && ((Comparable<T>) temp.getValue()).compareTo(temp.getNext().getValue()) <
    // 0) {
    // // Swap nodes
    // T tempValue = temp.getValue();
    // temp.setValue(temp.getNext().getValue());
    // temp.getNext().setValue(tempValue);
    // temp = temp.getNext();
    // }
    // current = current.getNext();
    // }
    // sorted = true; // Update sort status
    // // }

    public void sort() {
        if (head == null || head.getNext() == null) {
            sorted = true;
            return; // List is already sorted
        }
        SNode<T> current = head.getNext();
        while (current != null) {
            SNode<T> temp = head;
            while (temp != current && ((Comparable<T>) current.getValue()).compareTo(temp.getValue()) > 0) {
                temp = temp.getNext();
            }
            if (temp != current) {
                T tempValue = current.getValue();
                current.setValue(temp.getValue());
                temp.setValue(tempValue);
            }
            current = current.getNext();
        }
        sorted = true; // Update sort status
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
        sorted = false;
    }

    public void print() {
        System.out.print("Data Structure Information: ");
        System.out.print("List Length: " + this.size);
        System.out.print("Sort Status: " + this.sorted);
        System.out.print("List Values: ");
        SNode<T> current = head;
        while (current != tail) {
            System.out.print(current.getValue() + " ");
            current = current.getNext();
        }
        System.out.println();
    }

}
