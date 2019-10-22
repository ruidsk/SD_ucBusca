Selector selector = Selector.open(); 
// Register sockets with selector

//....

while (true) {
    selector.select();
    Iterator it = selector.selectedKeys().iterator(); 
    while (it.hasNext()) {
        SelectionKey sk = (SelectionKey)it.next(); 
        // Handle IO operation. Possibly registering 
        // more IO operations in the selector
        //...
    } 
}