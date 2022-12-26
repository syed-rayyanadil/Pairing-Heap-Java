
package pairingheap;

import java.util.*;

 class Node{
    int data;
    Node leftChild;
    Node nextSibling;
    Node prev;

    public Node(int data){

       this.data = data;
        leftChild = null;
        nextSibling = null;
        prev = null;
    }
    public String toString(){
        return ""+data;
    }
}
class PairHeap{

    public Node root; 
    public Node[] Array;
    public PairHeap(){
        root = null;
        Array = new Node[10];
      }

    public boolean isEmpty() {
        return root == null;
    }
    
    public Node FindMin(){
        if(root==null)
            return null;
        else
      return root;
    }
//function to insert data    
    public Node insert(int data){
    
        Node newnode = new Node(data);
        if (root == null)
            root = newnode;
        else
            root = Merge(root, newnode);
        return newnode;
    }
//function to merge 
    public Node Merge(Node r, Node newnode){
    
        if (newnode == null)
           return r;
        //check newnode is lesser than root or not
        if (newnode.data < r.data){
            newnode.prev = r.prev;
            r.prev = newnode;
            r.nextSibling = newnode.leftChild;
            if (r.nextSibling != null)
                r.nextSibling.prev = r;
            newnode.leftChild = r;
            return newnode;
        }
        else{

            newnode.prev = r;
            r.nextSibling = newnode.nextSibling;
            if (r.nextSibling != null)
                r.nextSibling.prev = r;
            newnode.nextSibling = r.leftChild;
            if (newnode.nextSibling != null)
                newnode.nextSibling.prev = newnode;
            r.leftChild = newnode;
            return r;
        }
    }
    //function to delete minimum value
    public int deleteMin(){

        if (isEmpty())
            return -1;
        
           int rData = root.data;
        if (root.leftChild == null)
            root = null;
        else
            root = TwoPassPairing(root.leftChild);  //to link subtrees
        return rData;
    }
    public Node TwoPassPairing(Node first){
    
        if( first.nextSibling == null )
            return first;

        int num;
        for (num = 0; first != null; num++){
        
            Array = resize_array(Array,num);
            Array[num] = first;

            first.prev.nextSibling = null;  
            first = first.nextSibling;
        }
        Array = resize_array(Array,num);
        Array[num] = null;

        int i;
        for (i=0; i+1 < num; i+=2) //first pass ,left to right ,two at a time
            Array[i] = Merge(Array[i], Array[i+1]);
        int j = i-2;
        if (j == num-3)
            Array[j] = Merge(Array[j], Array[j+2]);

        for (j=i-2; j>=2; j-=2)   // second pass,right to left , one at a time
            Array[j-2] = Merge(Array[j-2], Array[j]);
        return Array[0];
    }
 public void decreaseKey(Node p,int value){  //function to change the node value by lesser than the current 
            if (p.data < value)
              return;

    p.data = value;
    if (p != root){
    
        if (p.nextSibling != null)
           p.nextSibling.prev = p.prev;
        if (p.prev.leftChild == p){
            p.prev.leftChild = p.nextSibling;
        }
        else{
            p.prev.nextSibling = p.nextSibling;
        }
            p.nextSibling = null;
            Merge(root, p);

    }
    }
    public Node[] resize_array(Node[] array, int index){ // resize array , used in two pass pairing function
    
        if (index == array.length){
      
            Node[] temp = array;
            array = new Node[index * 2];
            for(int i=0; i<index; i++)
                array[i] = temp[i];
        }
        return array;
    }

   

    public void inorder(){ // function to print pairing heap
        inorder(root);
    }
    private void inorder(Node r){
    
        if (r != null){
            inorder(r.leftChild);
            System.out.print(r.data +" ");
            inorder(r.nextSibling);
        }
    }
}

public class PairingHeap {

    public static void main(String[] args) {
     
        Scanner s=new Scanner(System.in);
   
      PairHeap pair=new PairHeap();
      Node p;
      int take=0,val;
      
      while(take!=6){  
      System.out.println("\n\n1- Insert\n2- check Empty\n3- Delete Minimum\n4- Find Min\n5- Print\n6- Exit\n");
       
        System.out.println("Enter choice : ");
        take=s.nextInt();
        if(take>6) System.out.println("invalid choice");
        if(take==1){
            System.out.println("Enter value");
           p = pair.insert(s.nextInt());
            System.out.println("want to decrease key\n1- yes\n2- No");
            val=s.nextInt();
         if(val==1){
             System.out.println("enter new value");
             int newval=s.nextInt();
            pair.decreaseKey(p, newval);
         }           
         else continue;
        }
        if(take==2)
              System.out.println("Empty Status : "+pair.isEmpty());
        if(take==3){
              System.out.println("minmum deleted");
              pair.deleteMin();
        }
              
              if(take==4)
                  System.out.println("Minimum = "+pair.FindMin());
              if(take==5){
              System.out.println("\nInOrder : ");
              pair.inorder();
        }
        
    }
    }
}
