package fun.gengzi.algorithm;

public class MyLinkedList {

    public static class Node{
        Node prev,next;
        int key,value;
        public Node(int key,int value){
            this.key = key;
            this.value = value;
        }

    }

    private Node head,tail;

    public MyLinkedList(){
        // 初始化头和尾节点
        head = new Node(0,0);
        tail = new Node(0,0);
        head.next = tail;
        tail.prev = head;
    }


    public void remove(Node node){
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }


    /**
     * 添加到队尾
     * @param node
     */
    public void add(Node node){
        node.prev = tail.prev;
        node.next = tail;
        tail.prev.next = node;
        tail.prev = node;
    }

    public Node get(int key) {
        Node temp = head;
        while (temp != tail) {
            if (temp.key == key) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    public static void main(String[] args) {

        MyLinkedList myLinkedList = new MyLinkedList();
        Node node1 = new Node(2, 2);
        myLinkedList.add(new Node(1,1));
        myLinkedList.add(node1);
        myLinkedList.add(new Node(3,3));
        myLinkedList.remove(node1);
        Node node = myLinkedList.get(1);

        System.out.println("获取到的元素"+ node.key);

    }
}
